package uk.ac.abertay.cmp309.cooking;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class AddRecipeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        AddIngredientsFragment addIngredientsFragment = new AddIngredientsFragment();
        AddInstructionsFragment addInstructionsFragment = new AddInstructionsFragment();
        AddFinaliseRecipeFragment addFinaliseRecipeFragment = new AddFinaliseRecipeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.flFragmentContainer, addIngredientsFragment).commit();

        findViewById(R.id.btnInstructions).setOnClickListener(view -> getSupportFragmentManager().beginTransaction().replace(R.id.flFragmentContainer, addInstructionsFragment).commit());

        findViewById(R.id.btnIngredients).setOnClickListener(view -> getSupportFragmentManager().beginTransaction().replace(R.id.flFragmentContainer, addIngredientsFragment).commit());

        findViewById(R.id.btnDone).setOnClickListener(view -> getSupportFragmentManager().beginTransaction().replace(R.id.flFragmentContainer, addFinaliseRecipeFragment).commit());
    }
}