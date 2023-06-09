package uk.ac.abertay.cmp309.cooking;

import androidx.lifecycle.ViewModel;

public class RecipeViewModel extends ViewModel {
    private Recipe recipe;
    private String ingredientName;
    private String ingredientQuantity;
    private String instruction;
    private String timer;

    public RecipeViewModel() {
        this.recipe = new Recipe();
        this.ingredientName = "";
        this.ingredientQuantity = "";
        this.instruction = "";
        this.timer = "";
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public String getIngredientQuantity() {
        return ingredientQuantity;
    }

    public void setIngredientQuantity(String ingredientQuantity) {
        this.ingredientQuantity = ingredientQuantity;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public String getTimer() {
        return timer;
    }

    public void setTimer(String timer) {
        this.timer = timer;
    }
}