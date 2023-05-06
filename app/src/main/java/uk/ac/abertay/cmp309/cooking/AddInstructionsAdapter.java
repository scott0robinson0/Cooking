package uk.ac.abertay.cmp309.cooking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class AddInstructionsAdapter extends ArrayAdapter<Instruction> {
    public AddInstructionsAdapter(Context context, ArrayList<Instruction> instructions) {
        super(context, 0, instructions);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Instruction instruction = getItem(position);
        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_add_list_item, parent, false);

        TextView tvInstructionName = convertView.findViewById(R.id.tv1);
        TextView tvInstructionQuantity = convertView.findViewById(R.id.tv2);
        ImageButton btnDelete = convertView.findViewById(R.id.btnDelete);

        btnDelete.setImageResource(R.drawable.baseline_delete_24);

        btnDelete.setOnClickListener(view -> remove(instruction));

        tvInstructionName.setText(instruction.instruction);
        tvInstructionQuantity.setText(instruction.timer);

        return convertView;
    }
}
