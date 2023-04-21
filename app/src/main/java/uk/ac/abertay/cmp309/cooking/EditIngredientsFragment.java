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

public class EditIngredientsFragment extends Fragment {
    EditText etIngredientName;
    EditText etIngredientQuantity;
    ImageButton btnAddIngredient;
    Recipe recipe;
    EditIngredientsAdapter editIngredientsAdapter;
    RecipeViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_ingredients, container, false);

        viewModel = new ViewModelProvider(requireActivity()).get(RecipeViewModel.class);
        recipe = viewModel.getRecipe();
        ListView lvIngredients = view.findViewById(R.id.lvIngredients);
        etIngredientName = view.findViewById(R.id.etIngredientName);
        etIngredientQuantity = view.findViewById(R.id.etIngredientQuantity);
        btnAddIngredient = view.findViewById(R.id.btnAddIngredient);

        editIngredientsAdapter = new EditIngredientsAdapter(getContext(), recipe.getIngredients());
        lvIngredients.setAdapter(editIngredientsAdapter);

        btnAddIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etIngredientName.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "Ingredient name is mandatory", Toast.LENGTH_SHORT).show();
                }
                else if (etIngredientQuantity.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "Ingredient quantity is mandatory", Toast.LENGTH_SHORT).show();
                }
                else {
                    Ingredient ingredient = new Ingredient(etIngredientName.getText().toString(), etIngredientQuantity.getText().toString());
                    recipe.addIngredient(ingredient);
                    editIngredientsAdapter.notifyDataSetChanged();
                    etIngredientName.setText("");
                    etIngredientQuantity.setText("");
                }
            }
        });

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (!recipe.getInstructions().isEmpty())
            viewModel.getRecipe().setInstructions(recipe.getInstructions());
    }
}
