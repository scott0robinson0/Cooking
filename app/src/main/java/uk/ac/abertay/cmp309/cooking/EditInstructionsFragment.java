package uk.ac.abertay.cmp309.cooking;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class EditInstructionsFragment extends Fragment {
    EditText etInstruction;
    EditText etTimer;
    ImageButton btnAddInstruction;
    Recipe recipe;
    EditInstructionsAdapter editInstructionsAdapter;
    RecipeViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_instructions, container, false);

        viewModel = new ViewModelProvider(requireActivity()).get(RecipeViewModel.class);
        recipe = viewModel.getRecipe();
        ListView lvInstructions = view.findViewById(R.id.lvInstructions);
        etInstruction = view.findViewById(R.id.etInstruction);
        etTimer = view.findViewById(R.id.etTimer);
        btnAddInstruction = view.findViewById(R.id.btnAddInstruction);

        etInstruction.setText(viewModel.getInstruction());
        etTimer.setText(viewModel.getTimer());

        editInstructionsAdapter = new EditInstructionsAdapter(getContext(), recipe.getInstructions());
        lvInstructions.setAdapter(editInstructionsAdapter);

        btnAddInstruction.setOnClickListener(view1 -> {
            if (etInstruction.getText().toString().equals("")) {
                Toast.makeText(getContext(), "Instruction is mandatory", Toast.LENGTH_SHORT).show();
            }
            else {
                Instruction instruction = new Instruction(etInstruction.getText().toString(), etTimer.getText().toString());
                recipe.addInstruction(instruction);
                editInstructionsAdapter.notifyDataSetChanged();
                etInstruction.setText("");
                etTimer.setText("");
            }
        });
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (!recipe.getIngredients().isEmpty())
            viewModel.getRecipe().setIngredients(recipe.getIngredients());

        viewModel.setInstruction(etInstruction.getText().toString());
        viewModel.setTimer(etTimer.getText().toString());
    }
}
