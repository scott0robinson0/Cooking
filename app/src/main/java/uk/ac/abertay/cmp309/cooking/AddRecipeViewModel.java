package uk.ac.abertay.cmp309.cooking;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class AddRecipeViewModel extends ViewModel {
    private ArrayList<Ingredient> ingredients;
    private ArrayList<Instruction> instructions;
    private String recipeName;
    private String ingredientName;
    private String ingredientQuantity;
    private String instruction;
    private String timer;

    public AddRecipeViewModel() {
        this.ingredients = new ArrayList<>();
        this.instructions = new ArrayList<>();
        this.recipeName = "";
        this.ingredientName = "";
        this.ingredientQuantity = "";
        this.instruction = "";
        this.timer = "";
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public ArrayList<Instruction> getInstructions() {
        return instructions;
    }

    public void setInstructions(ArrayList<Instruction> instructions) {
        this.instructions = instructions;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
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
