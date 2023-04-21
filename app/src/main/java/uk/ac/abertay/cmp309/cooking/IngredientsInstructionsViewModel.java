package uk.ac.abertay.cmp309.cooking;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class IngredientsInstructionsViewModel extends ViewModel {
    private List<Ingredient> ingredients;
    private List<Instruction> instructions;

    public IngredientsInstructionsViewModel() {
        this.ingredients = new ArrayList<>();
        this.instructions = new ArrayList<>();
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Instruction> getInstructions() {
        return instructions;
    }

    public void setInstructions(List<Instruction> instructions) {
        this.instructions = instructions;
    }
}
