package uk.ac.abertay.cmp309.cooking;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

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
    private static final String CHANNEL_ID_LOW = "cooking_low";
    private static final String CHANNEL_ID_IMPORTANT = "cooking_important";
    public static final String ACTION_RESET = "uk.ac.abertay.cmp309.ACTION_RESET";
    private int NOTIFICATION_ID;
    private NotificationManager notificationManager;
    private Notification.Builder timerNotification;

    public ExecuteInstructionTimerFragment() {}

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
        NOTIFICATION_ID = args.getInt("position");

        Recipe recipe = args.getParcelable("recipe");
        Instruction instruction = recipe.getInstructions().get(NOTIFICATION_ID - 1);

        startTimeMillis = Long.parseLong(instruction.timer) * 60000;
        timeLeftMillis = startTimeMillis;

        TimeReceiver timeReceiver = new TimeReceiver();
        IntentFilter timerFilter = new IntentFilter();
        timerFilter.addAction(ACTION_RESET);
        getActivity().registerReceiver(timeReceiver, timerFilter);

        ((TextView) view.findViewById(R.id.timerTxtInstruction)).setText(instruction.instruction);

        notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(new NotificationChannel(CHANNEL_ID_LOW,"LOW", NotificationManager.IMPORTANCE_LOW));
        notificationManager.createNotificationChannel(new NotificationChannel(CHANNEL_ID_IMPORTANT,"IMPORTANT", NotificationManager.IMPORTANCE_HIGH));

        Intent openIntent = new Intent(getContext(), ExecuteRecipeActivity.class);
        openIntent.putExtra("position", NOTIFICATION_ID);
//        openIntent.putExtra("recipe", recipe);
        openIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        PendingIntent openPI = PendingIntent.getActivity(getContext(), NOTIFICATION_ID, openIntent, 0);

//        Intent resetIntent = new Intent();
//        resetIntent.setAction(ACTION_RESET);
//        PendingIntent resetPI = PendingIntent.getBroadcast(getContext(), NOTIFICATION_ID, resetIntent, 0);
//        Icon resetIcon = Icon.createWithResource(getContext(), android.R.drawable.ic_menu_revert);
//        Notification.Action resetAction = new Notification.Action.Builder(resetIcon, "Reset", resetPI).build();

        timerNotification = new Notification.Builder(getContext(), CHANNEL_ID_NORMAL)
                .setSmallIcon(R.drawable.baseline_timer_24)
                .setContentTitle(String.valueOf(NOTIFICATION_ID))
//                .addAction(resetAction)
                .setChannelId(CHANNEL_ID_LOW)
                .setContentIntent(openPI);

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
                timerNotification.setContentText("Timer finished")
                        .setChannelId(CHANNEL_ID_IMPORTANT);
                notificationManager.notify(NOTIFICATION_ID, timerNotification.build());
                timerNotification.setChannelId(CHANNEL_ID_LOW);
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
        int minutes = (int)(timeMillis / 1000) / 60;
        int seconds = (int)(timeMillis / 1000) % 60;

        return String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
    }

    private class TimeReceiver extends BroadcastReceiver {
        final static int NOTIFICATION_ID_TIMER = 2;
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case ACTION_RESET:
                    resetTimer();
                    break;
            }
        }
    }
}
