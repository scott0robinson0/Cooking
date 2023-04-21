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

public class AddIngredientsFragment extends Fragment {
    EditText etIngredientName;
    EditText etIngredientQuantity;
    ImageButton btnAddIngredient;
    ArrayList<Ingredient> ingredients = new ArrayList<>();
    AddIngredientsAdapter addIngredientsAdapter;
    IngredientsInstructionsViewModel viewModel;
    public AddIngredientsFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_add_ingredients, container, false);

        viewModel = new ViewModelProvider(requireActivity()).get(IngredientsInstructionsViewModel.class);
        ListView lvIngredients = view.findViewById(R.id.lvIngredients);
        etIngredientName = view.findViewById(R.id.etIngredientName);
        etIngredientQuantity = view.findViewById(R.id.etIngredientQuantity);
        btnAddIngredient = view.findViewById(R.id.btnAddIngredient);

        addIngredientsAdapter = new AddIngredientsAdapter(getContext(), ingredients);
        lvIngredients.setAdapter(addIngredientsAdapter);

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
                    ingredients.add(ingredient);
                    addIngredientsAdapter.notifyDataSetChanged();
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
        if (!ingredients.isEmpty())
            viewModel.setIngredients(ingredients);
    }
}
