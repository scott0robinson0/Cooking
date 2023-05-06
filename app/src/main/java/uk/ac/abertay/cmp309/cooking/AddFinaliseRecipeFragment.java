package uk.ac.abertay.cmp309.cooking;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddFinaliseRecipeFragment extends Fragment {
    private List<Ingredient> ingredients = new ArrayList<>();
    private List<Instruction> instructions = new ArrayList<>();
    private AddRecipeViewModel viewModel;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private EditText etRecipeName;

    public AddFinaliseRecipeFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_finalise_recipe, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(AddRecipeViewModel.class);
        ingredients = viewModel.getIngredients();
        instructions = viewModel.getInstructions();
        etRecipeName = view.findViewById(R.id.etRecipeName);

        etRecipeName.setText(viewModel.getRecipeName());

        view.findViewById(R.id.btnConfirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String recipeName = etRecipeName.getText().toString();
                if (recipeName.equals("")) {
                    Toast.makeText(getContext(), "Recipe name is mandatory.", Toast.LENGTH_SHORT).show();
                } else if (ingredients.isEmpty()) {
                    Toast.makeText(getContext(), "Ingredients are mandatory.", Toast.LENGTH_SHORT).show();
                } else if (instructions.isEmpty()) {
                    Toast.makeText(getContext(), "Instructions are mandatory.", Toast.LENGTH_SHORT).show();
                } else {
                    Map<String, Object> recipe = new HashMap<>();
                    recipe.put(Recipe.KEY_NAME, recipeName);
                    recipe.put(Recipe.KEY_INGREDIENTS, ingredients);
                    recipe.put(Recipe.KEY_INSTRUCTIONS, instructions);

                    db.collection("recipes").document()
                            .set(recipe)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.d("Firestore", "DocumentSnapshot successfully written!");
                                    Toast.makeText(getContext(), "Recipe added.", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getContext(), MainActivity.class));
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("Firestore", "Error writing document");
                                }
                            });
                }
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        viewModel.setRecipeName(etRecipeName.getText().toString());
    }
}
