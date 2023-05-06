package uk.ac.abertay.cmp309.cooking;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class AddRecipeActivity extends AppCompatActivity {
    private int currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        AddIngredientsFragment addIngredientsFragment = new AddIngredientsFragment();
        AddInstructionsFragment addInstructionsFragment = new AddInstructionsFragment();
        AddFinaliseRecipeFragment addFinaliseRecipeFragment = new AddFinaliseRecipeFragment();

        if (savedInstanceState != null) {
            currentFragment = savedInstanceState.getInt("current_fragment");
            switch (currentFragment) {
                case 0:
                    getSupportFragmentManager().beginTransaction().replace(R.id.flFragmentContainer, addIngredientsFragment).commit();
                    break;
                case 1:
                    getSupportFragmentManager().beginTransaction().replace(R.id.flFragmentContainer, addInstructionsFragment).commit();
                    break;
                case 2:
                    getSupportFragmentManager().beginTransaction().replace(R.id.flFragmentContainer, addFinaliseRecipeFragment).commit();
                    break;
            }
        } else {
            currentFragment = 0;
            getSupportFragmentManager().beginTransaction().replace(R.id.flFragmentContainer, addIngredientsFragment).commit();
        }

        findViewById(R.id.btnIngredients).setOnClickListener(view -> {
            currentFragment = 0;
            getSupportFragmentManager().beginTransaction().replace(R.id.flFragmentContainer, addIngredientsFragment).commit();
        });

        findViewById(R.id.btnInstructions).setOnClickListener(view -> {
            currentFragment = 1;
            getSupportFragmentManager().beginTransaction().replace(R.id.flFragmentContainer, addInstructionsFragment).commit();
        });

        findViewById(R.id.btnDone).setOnClickListener(view -> {
            currentFragment = 2;
            getSupportFragmentManager().beginTransaction().replace(R.id.flFragmentContainer, addFinaliseRecipeFragment).commit();
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("current_fragment", currentFragment);
    }
}