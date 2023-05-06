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

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditFinaliseRecipeFragment extends Fragment {
    private Recipe recipe;
    private RecipeViewModel viewModel;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private EditText etRecipeName;

    public EditFinaliseRecipeFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_finalise_recipe, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(RecipeViewModel.class);
        recipe = viewModel.getRecipe();
        etRecipeName = view.findViewById(R.id.etRecipeName);
        etRecipeName.setText(recipe.getName());

        view.findViewById(R.id.btnConfirm).setOnClickListener(view1 -> {
            String recipeName = etRecipeName.getText().toString();
            if (recipeName.equals("")) {
                Toast.makeText(getContext(), "Recipe name is mandatory.", Toast.LENGTH_SHORT).show();
            } else if (recipe.getIngredients().isEmpty()) {
                Toast.makeText(getContext(), "Ingredients are mandatory.", Toast.LENGTH_SHORT).show();
            } else if (recipe.getInstructions().isEmpty()) {
                Toast.makeText(getContext(), "Instructions are mandatory.", Toast.LENGTH_SHORT).show();
            } else {
                Map<String, Object> firebaseRecipe = new HashMap<>();
                firebaseRecipe.put(Recipe.KEY_NAME, recipeName);
                firebaseRecipe.put(Recipe.KEY_INGREDIENTS, recipe.getIngredients());
                firebaseRecipe.put(Recipe.KEY_INSTRUCTIONS, recipe.getInstructions());

                db.collection("recipes").document(recipe.getId())
                        .update(firebaseRecipe)
                        .addOnSuccessListener(unused -> {
                            Log.d("Firestore", "DocumentSnapshot successfully written!");
                            Toast.makeText(getContext(), "Recipe updated.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getContext(), MainActivity.class));
                        }).addOnFailureListener(e -> Log.d("Firestore", "Error writing document"));
            }
        });

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        recipe.setName(etRecipeName.getText().toString());
        viewModel.setRecipe(recipe);
    }
}