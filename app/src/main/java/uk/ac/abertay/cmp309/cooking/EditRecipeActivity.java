package uk.ac.abertay.cmp309.cooking;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class EditRecipeActivity extends AppCompatActivity {
    private int currentFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_recipe);

        Recipe recipe = getIntent().getParcelableExtra("recipe");
        RecipeViewModel viewModel = new ViewModelProvider(this).get(RecipeViewModel.class);
        viewModel.setRecipe(recipe);
        ((TextView)findViewById(R.id.editTvRecipeName)).setText(recipe.getName());

        EditIngredientsFragment editIngredientsFragment = new EditIngredientsFragment();
        EditInstructionsFragment editInstructionsFragment = new EditInstructionsFragment();
        EditFinaliseRecipeFragment editFinaliseRecipeFragment = new EditFinaliseRecipeFragment();

        if (savedInstanceState != null) {
            currentFragment = savedInstanceState.getInt("current_fragment");
            switch (currentFragment) {
                case 0:
                    getSupportFragmentManager().beginTransaction().replace(R.id.editFlFragmentContainer, editIngredientsFragment).commit();
                    break;
                case 1:
                    getSupportFragmentManager().beginTransaction().replace(R.id.editFlFragmentContainer, editInstructionsFragment).commit();
                    break;
                case 2:
                    getSupportFragmentManager().beginTransaction().replace(R.id.editFlFragmentContainer, editFinaliseRecipeFragment).commit();
                    break;
            }
        } else {
            currentFragment = 0;
            getSupportFragmentManager().beginTransaction().replace(R.id.editFlFragmentContainer, editIngredientsFragment).commit();
        }

        findViewById(R.id.editBtnIngredients).setOnClickListener(view -> {
            getSupportFragmentManager().beginTransaction().replace(R.id.editFlFragmentContainer, editIngredientsFragment).commit();
            currentFragment = 0;
        });

        findViewById(R.id.editBtnInstructions).setOnClickListener(view -> {
            getSupportFragmentManager().beginTransaction().replace(R.id.editFlFragmentContainer, editInstructionsFragment).commit();
            currentFragment = 1;
        });

        findViewById(R.id.editBtnDone).setOnClickListener(view -> {
            getSupportFragmentManager().beginTransaction().replace(R.id.editFlFragmentContainer, editFinaliseRecipeFragment).commit();
            currentFragment = 2;
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("current_fragment", currentFragment);
    }
}