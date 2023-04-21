package uk.ac.abertay.cmp309.cooking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class EditIngredientsAdapter extends ArrayAdapter<Ingredient> {
    public EditIngredientsAdapter(Context context, ArrayList<Ingredient> ingredients) {
        super(context, 0, ingredients);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Ingredient ingredient = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_edit_list_item, parent, false);

        EditText etIngredientName = convertView.findViewById(R.id.editEt1);
        EditText etIngredientQuantity = convertView.findViewById(R.id.editEt2);
        ImageButton btnDelete = convertView.findViewById(R.id.editBtnDelete);

        btnDelete.setImageResource(R.drawable.baseline_delete_24);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                remove(ingredient);
            }
        });

        etIngredientName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                ingredient.name = etIngredientName.getText().toString();
            }
        });

        etIngredientQuantity.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                ingredient.quantity = etIngredientQuantity.getText().toString();
            }
        });

        etIngredientName.setText(ingredient.name);
        etIngredientQuantity.setText(ingredient.quantity);

        return convertView;
    }
}
