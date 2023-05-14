package uk.ac.abertay.cmp309.cooking;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class ViewRecipesActivity extends AppCompatActivity {

    private ListView listView;
    private final CollectionReference recipesCollection = FirebaseFirestore.getInstance().collection(Recipe.COLLECTION_PATH);
    private final ArrayList<Recipe> recipes = new ArrayList<>();
    private RecipeNameAdapter adapter;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipes);

        listView = findViewById(R.id.lvRecipes);

        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Recipe recipe = (Recipe)adapterView.getAdapter().getItem(i);

            PopupMenu menu = new PopupMenu(ViewRecipesActivity.this, view);
            menu.getMenuInflater().inflate(R.menu.popup_menu, menu.getMenu());
            menu.setOnMenuItemClickListener(menuItem -> {
                switch (menuItem.getTitle().toString()) {
                    case "Start":
                        Intent executeIntent = new Intent(ViewRecipesActivity.this, ExecuteRecipeActivity.class);
                        executeIntent.putExtra("recipe", recipe);
                        startActivity(executeIntent);
                        break;
                    case "View":
                        Intent viewIntent = new Intent(ViewRecipesActivity.this, ViewRecipeActivity.class);
                        viewIntent.putExtra("recipe", recipe);
                        startActivity(viewIntent);
                        break;
                    case "Edit":
                        Intent editIntent = new Intent(ViewRecipesActivity.this, EditRecipeActivity.class);
                        editIntent.putExtra("recipe", recipe);
                        startActivity(editIntent);
                        break;
                    case "Delete":
                        db.collection(Recipe.COLLECTION_PATH).document(recipe.getId())
                                .delete()
                                .addOnSuccessListener(unused -> {
                                    Log.d(Utils.TAG, "DocumentSnapshot successfully written!");
                                    Toast.makeText(getApplicationContext(), "Recipe deleted.", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                })
                                .addOnFailureListener(e -> Log.d(Utils.TAG, "Error writing document"));
                        break;
                }
                return true;
            });
            menu.show();
        });

        populateList();
    }

    private void populateList() {
        recipesCollection.get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Recipe recipe = document.toObject(Recipe.class);
                            recipe.setId(document.getId());
                            recipes.add(recipe);
                        }
                        adapter = new RecipeNameAdapter(ViewRecipesActivity.this, recipes);
                        listView.setAdapter(adapter);
                    } else {
                        Log.d(Utils.TAG, "Error getting documents");
                    }
                });
    }
}