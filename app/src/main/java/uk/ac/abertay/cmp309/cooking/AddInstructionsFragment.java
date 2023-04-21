package uk.ac.abertay.cmp309.cooking;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;

public class AddInstructionsFragment extends Fragment {
    EditText etInstruction;
    EditText etTimer;
    ImageButton btnAddInstruction;
    ArrayList<Instruction> instructions = new ArrayList<>();
    AddInstructionsAdapter addInstructionsAdapter;
    IngredientsInstructionsViewModel viewModel;

    public AddInstructionsFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_add_instructions, container, false);

        viewModel = new ViewModelProvider(requireActivity()).get(IngredientsInstructionsViewModel.class);
        etInstruction = view.findViewById(R.id.etInstruction);
        etTimer = view.findViewById(R.id.etTimer);
        btnAddInstruction = view.findViewById(R.id.btnAddInstruction);
        ListView lvInstructions = view.findViewById(R.id.lvInstructions);

        addInstructionsAdapter = new AddInstructionsAdapter(getContext(), instructions);
        lvInstructions.setAdapter(addInstructionsAdapter);

        btnAddInstruction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etInstruction.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "Instruction is mandatory", Toast.LENGTH_SHORT).show();
                }
                else {
                    Instruction instruction = new Instruction(etInstruction.getText().toString(), etTimer.getText().toString());
                    instructions.add(instruction);
                    addInstructionsAdapter.notifyDataSetChanged();
                    etInstruction.setText("");
                    etTimer.setText("");
                }
            }
        });

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (!instructions.isEmpty())
            viewModel.setInstructions(instructions);
    }
}
