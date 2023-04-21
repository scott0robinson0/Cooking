package uk.ac.abertay.cmp309.cooking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class RecipeNameAdapter extends ArrayAdapter<Recipe> {
    public RecipeNameAdapter(@NonNull Context context, ArrayList<Recipe> recipes) {
        super(context, 0, recipes);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Recipe recipe = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_recipe_name, parent, false);
        }

        TextView tvRecipeName = convertView.findViewById(R.id.tvRecipeName);

        tvRecipeName.setText(recipe.getName());

        return convertView;
    }
}
