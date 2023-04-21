package uk.ac.abertay.cmp309.cooking;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnViewRecipes).setOnClickListener(view -> startActivity(new Intent(MainActivity.this, ViewRecipesActivity.class)));
        findViewById(R.id.btnAddRecipe).setOnClickListener(view -> startActivity(new Intent(MainActivity.this, AddRecipeActivity.class)));
    }
}