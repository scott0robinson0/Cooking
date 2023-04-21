package uk.ac.abertay.cmp309.cooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroupOverlay;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ViewRecipesActivity extends AppCompatActivity {

    private ListView listView;
    CollectionReference recipesCollection = FirebaseFirestore.getInstance().collection(Recipe.COLLECTION_PATH);
    ArrayList<Recipe> recipes = new ArrayList<>();
    RecipeNameAdapter adapter;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipes);

        listView = findViewById(R.id.lvRecipes);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Recipe recipe = (Recipe)adapterView.getAdapter().getItem(i);

                PopupMenu menu = new PopupMenu(ViewRecipesActivity.this, view);
                menu.getMenuInflater().inflate(R.menu.popup_menu, menu.getMenu());
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
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
                                db.collection("recipes").document(recipe.getId())
                                        .delete()
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Log.d("Firestore", "DocumentSnapshot successfully written!");
                                                Toast.makeText(getApplicationContext(), "Recipe deleted.", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.d("Firestore", "Error writing document");
                                            }
                                        });
                                break;
                        }
                        return true;
                    }
                });
                menu.show();
            }
        });

        populateList();
    }

    private void populateList() {
        recipesCollection.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Recipe recipe = document.toObject(Recipe.class);
                                recipe.setId(document.getId());
                                recipes.add(recipe);
                            }
                            adapter = new RecipeNameAdapter(ViewRecipesActivity.this, recipes);
                            listView.setAdapter(adapter);
                        } else {
                            Log.d("populateList()", "Error getting documents");
                        }
                    }
                });
    }
}