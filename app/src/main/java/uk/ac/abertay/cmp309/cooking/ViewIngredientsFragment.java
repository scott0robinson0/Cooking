package uk.ac.abertay.cmp309.cooking;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;

public class ViewIngredientsFragment extends Fragment {
    Recipe recipe;
    ViewIngredientsAdapter adapter;
    RecipeViewModel viewModel;

    public ViewIngredientsFragment() { }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_view_ingredients, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(RecipeViewModel.class);
        recipe = viewModel.getRecipe();
        ListView listView = view.findViewById(R.id.lvViewIngredients);
        adapter = new ViewIngredientsAdapter(getContext(), recipe.getIngredients());
        listView.setAdapter(adapter);
        return view;
    }
}
