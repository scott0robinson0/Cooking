package uk.ac.abertay.cmp309.cooking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ViewIngredientsAdapter extends ArrayAdapter<Ingredient> {
    public ViewIngredientsAdapter(Context context, ArrayList<Ingredient> ingredients) {
        super(context, 0, ingredients);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Ingredient ingredient = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_view_list_item, parent, false);

        TextView tvIngredientName = convertView.findViewById(R.id.viewTv1);
        TextView tvIngredientQuantity = convertView.findViewById(R.id.viewTv2);

        tvIngredientName.setText(ingredient.name);
        tvIngredientQuantity.setText(ingredient.quantity);

        return convertView;
    }
}