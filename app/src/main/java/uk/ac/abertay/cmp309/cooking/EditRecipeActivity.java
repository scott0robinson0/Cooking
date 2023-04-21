package uk.ac.abertay.cmp309.cooking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.TextView;

public class EditRecipeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_recipe);

        Recipe recipe = getIntent().getParcelableExtra("recipe");
        RecipeViewModel viewModel = new ViewModelProvider(this).get(RecipeViewModel.class);
        viewModel.setRecipe(recipe);
        EditIngredientsFragment editIngredientsFragment = new EditIngredientsFragment();
        EditInstructionsFragment editInstructionsFragment = new EditInstructionsFragment();
        EditFinaliseRecipeFragment editFinaliseRecipeFragment = new EditFinaliseRecipeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.editFlFragmentContainer, editIngredientsFragment).commit();
        ((TextView)findViewById(R.id.editTvRecipeName)).setText(recipe.getName());

        findViewById(R.id.editBtnInstructions).setOnClickListener(view -> getSupportFragmentManager().beginTransaction().replace(R.id.editFlFragmentContainer, editInstructionsFragment).commit());

        findViewById(R.id.editBtnIngredients).setOnClickListener(view -> getSupportFragmentManager().beginTransaction().replace(R.id.editFlFragmentContainer, editIngredientsFragment).commit());

        findViewById(R.id.editBtnDone).setOnClickListener(view -> getSupportFragmentManager().beginTransaction().replace(R.id.editFlFragmentContainer, editFinaliseRecipeFragment).commit());
    }
}