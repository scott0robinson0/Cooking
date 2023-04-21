package uk.ac.abertay.cmp309.cooking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ViewRecipeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipe);

        Recipe recipe = getIntent().getParcelableExtra("recipe");
        RecipeViewModel viewModel = new ViewModelProvider(this).get(RecipeViewModel.class);
        viewModel.setRecipe(recipe);
        ViewIngredientsFragment viewIngredientsFragment = new ViewIngredientsFragment();
        ViewInstructionsFragment viewInstructionsFragment = new ViewInstructionsFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.viewFlFragmentContainer, viewIngredientsFragment).commit();
        ((TextView)findViewById(R.id.viewTvRecipeName)).setText(recipe.getName());

        findViewById(R.id.viewBtnInstructions).setOnClickListener(view -> getSupportFragmentManager().beginTransaction().replace(R.id.viewFlFragmentContainer, viewInstructionsFragment).commit());

        findViewById(R.id.viewBtnIngredients).setOnClickListener(view -> getSupportFragmentManager().beginTransaction().replace(R.id.viewFlFragmentContainer, viewIngredientsFragment).commit());
    }
}