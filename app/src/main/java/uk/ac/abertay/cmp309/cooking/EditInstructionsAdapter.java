package uk.ac.abertay.cmp309.cooking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class EditInstructionsAdapter extends ArrayAdapter<Instruction> {
    public EditInstructionsAdapter(Context context, ArrayList<Instruction> instructions) {
        super(context, 0, instructions);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Instruction instruction = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_edit_list_item, parent, false);

        EditText etInstruction = convertView.findViewById(R.id.editEt1);
        EditText etTimer = convertView.findViewById(R.id.editEt2);
        ImageButton btnDelete = convertView.findViewById(R.id.editBtnDelete);

        btnDelete.setImageResource(R.drawable.baseline_delete_24);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                remove(instruction);
            }
        });

        etInstruction.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                instruction.instruction = etInstruction.getText().toString();
            }
        });

        etTimer.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                instruction.timer = etTimer.getText().toString();
            }
        });

        etInstruction.setText(instruction.instruction);
        etTimer.setText(instruction.timer);

        return convertView;
    }
}
