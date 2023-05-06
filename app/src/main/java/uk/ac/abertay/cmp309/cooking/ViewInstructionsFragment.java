package uk.ac.abertay.cmp309.cooking;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class ViewInstructionsFragment extends Fragment {
    public ViewInstructionsFragment() { }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_view_instructions, container, false);
        RecipeViewModel viewModel = new ViewModelProvider(requireActivity()).get(RecipeViewModel.class);
        Recipe recipe = viewModel.getRecipe();
        ListView listView = view.findViewById(R.id.lvViewInstructions);
        ViewInstructionsAdapter adapter = new ViewInstructionsAdapter(getContext(), recipe.getInstructions());
        listView.setAdapter(adapter);
        return view;
    }
}
