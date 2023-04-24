package uk.ac.abertay.cmp309.cooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ExecuteRecipeActivity extends AppCompatActivity {
    private ViewPager2 viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_execute_recipe);

        Recipe recipe = getIntent().getParcelableExtra("recipe");

        RecipeViewModel viewModel = new ViewModelProvider(this).get(RecipeViewModel.class);
        viewModel.setRecipe(recipe);

        ((TextView)findViewById(R.id.executeTxtRecipeName)).setText(recipe.getName());

        viewPager = findViewById(R.id.executeViewPager);
        ExecuteAdapter executeAdapter = new ExecuteAdapter(this, recipe);

        viewPager.setOffscreenPageLimit(recipe.getInstructions().size());
        viewPager.setAdapter(executeAdapter);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        int position = intent.getIntExtra("position", 0);
        viewPager.setCurrentItem(position);
    }
}