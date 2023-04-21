package uk.ac.abertay.cmp309.cooking;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Locale;

public class ExecuteInstructionTimerFragment extends Fragment {
    private TextView tvTimer;
    private Button btnStart;
    private Button btnReset;
    private CountDownTimer countDownTimer;
    private boolean timerRunning;
    private long timeLeftMillis;
    private long startTimeMillis;
    private static final String CHANNEL_ID_NORMAL = "cooking_normal";
    private int NOTIFICATION_ID;
    private NotificationManager notificationManager;
    private Notification.Builder timerNotification;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_execute_instruction_timer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        tvTimer = view.findViewById(R.id.timerTxtTimer);
        btnStart = view.findViewById(R.id.timerBtnStart);
        btnReset = view.findViewById(R.id.timerBtnReset);

        Bundle args = getArguments();
        Instruction instruction = args.getParcelable("instruction");

        startTimeMillis = Long.parseLong(instruction.timer) * 60000;
        timeLeftMillis = startTimeMillis;

        NOTIFICATION_ID = args.getInt("position");

        ((TextView) view.findViewById(R.id.timerTxtInstruction)).setText(instruction.instruction);

        notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(new NotificationChannel(CHANNEL_ID_NORMAL,"DEFAULT", NotificationManager.IMPORTANCE_DEFAULT));

        timerNotification = new Notification.Builder(getContext(), CHANNEL_ID_NORMAL)
                .setSmallIcon(R.drawable.baseline_timer_24)
                .setContentTitle(String.valueOf(NOTIFICATION_ID));

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (timerRunning) {
                    pauseTimer();
                } else {
                    startTimer();
                }
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetTimer();
            }
        });

        updateCountDownText();
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftMillis, 1000) {
            @Override
            public void onTick(long l) {
                timeLeftMillis = l;
                updateCountDownText();
                timerNotification.setContentText(formatTime(timeLeftMillis));
                timerNotification.setContentTitle(String.valueOf(NOTIFICATION_ID));
                notificationManager.notify(NOTIFICATION_ID, timerNotification.build());
            }

            @Override
            public void onFinish() {
                timerRunning = false;
                btnStart.setText("Start");
                btnReset.setVisibility(View.VISIBLE);

            }
        }.start();

        timerRunning = true;
        btnStart.setText("Pause");
        btnReset.setVisibility(View.VISIBLE);
    }

    private void pauseTimer() {
        countDownTimer.cancel();
        timerRunning = false;
        btnStart.setText("Start");
        btnReset.setVisibility(View.VISIBLE);
    }

    private void resetTimer() {
        pauseTimer();
        timeLeftMillis = startTimeMillis;
        updateCountDownText();
        btnReset.setVisibility(View.INVISIBLE);
        btnStart.setVisibility(View.VISIBLE);
    }

    private void updateCountDownText() {
        tvTimer.setText(formatTime(timeLeftMillis));
    }

    private String formatTime(long timeMillis) {
        int minutes = (int)(timeLeftMillis / 1000) / 60;
        int seconds = (int)(timeLeftMillis / 1000) % 60;

        return String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
    }
}
