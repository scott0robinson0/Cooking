package uk.ac.abertay.cmp309.cooking;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class ViewRecipeActivity extends AppCompatActivity {
    private int currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipe);

        Recipe recipe = getIntent().getParcelableExtra("recipe");
        RecipeViewModel viewModel = new ViewModelProvider(this).get(RecipeViewModel.class);
        viewModel.setRecipe(recipe);
        ((TextView)findViewById(R.id.viewTvRecipeName)).setText(recipe.getName());

        ViewIngredientsFragment viewIngredientsFragment = new ViewIngredientsFragment();
        ViewInstructionsFragment viewInstructionsFragment = new ViewInstructionsFragment();

        if (savedInstanceState != null) {
            currentFragment = savedInstanceState.getInt("current_fragment");
            switch (currentFragment) {
                case 0:
                    getSupportFragmentManager().beginTransaction().replace(R.id.viewFlFragmentContainer, viewIngredientsFragment).commit();
                    break;
                case 1:
                    getSupportFragmentManager().beginTransaction().replace(R.id.viewFlFragmentContainer, viewInstructionsFragment).commit();
                    break;
            }
        } else {
            currentFragment = 0;
            getSupportFragmentManager().beginTransaction().replace(R.id.viewFlFragmentContainer, viewIngredientsFragment).commit();
        }

        findViewById(R.id.viewBtnIngredients).setOnClickListener(view -> {
            getSupportFragmentManager().beginTransaction().replace(R.id.viewFlFragmentContainer, viewIngredientsFragment).commit();
            currentFragment = 0;
        });

        findViewById(R.id.viewBtnInstructions).setOnClickListener(view -> {
            getSupportFragmentManager().beginTransaction().replace(R.id.viewFlFragmentContainer, viewInstructionsFragment).commit();
            currentFragment = 1;
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("current_fragment", currentFragment);
    }
}