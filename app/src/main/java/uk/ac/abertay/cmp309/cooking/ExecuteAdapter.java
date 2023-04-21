package uk.ac.abertay.cmp309.cooking;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.Objects;

public class ExecuteAdapter extends FragmentStateAdapter {
    private int NUM_PAGES;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    Recipe recipe;
    public ExecuteAdapter(@NonNull FragmentActivity fragmentActivity, Recipe recipe) {
        super(fragmentActivity);
        this.recipe = recipe;
        NUM_PAGES = recipe.getInstructions().size() + 1;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment;
        Bundle args = new Bundle();

        if (position == 0) {
            fragment = new ViewIngredientsFragment();
        } else if (Objects.equals(recipe.getInstructions().get(position - 1).timer, "")) {
            fragment = new ExecuteInstructionFragment();
            args.putParcelable("instruction", recipe.getInstructions().get(position - 1));
        } else {
            fragment = new ExecuteInstructionTimerFragment();
            args.putParcelable("instruction", recipe.getInstructions().get(position - 1));
            args.putInt("position", position);
        }

        fragment.setArguments(args);
        return fragment;
    }

    public void addFragment(Fragment fragment) {
        fragments.add(fragment);
    }

    @Override
    public int getItemCount() {
        return NUM_PAGES;
    }
}
