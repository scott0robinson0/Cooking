package uk.ac.abertay.cmp309.cooking;

import androidx.lifecycle.ViewModel;

public class RecipeViewModel extends ViewModel {
    private Recipe recipe;

    public RecipeViewModel() {
        this.recipe = new Recipe();
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
}