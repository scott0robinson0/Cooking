package uk.ac.abertay.cmp309.cooking;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Recipe implements Parcelable {

    public static final String COLLECTION_PATH = "recipes";
    public static final String KEY_INGREDIENTS = "ingredients";
    public static final String KEY_INSTRUCTIONS = "instructions";
    public static final String KEY_NAME = "name";
    private String name;
    private ArrayList<Ingredient> ingredients;
    private ArrayList<Instruction> instructions;
    private String id;

    public Recipe() {}

    protected Recipe(Parcel in) {
        name = in.readString();
        ingredients = in.createTypedArrayList(Ingredient.CREATOR);
        instructions = in.createTypedArrayList(Instruction.CREATOR);
        id = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeTypedList(ingredients);
        dest.writeTypedList(instructions);
        dest.writeString(id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public void addIngredient(Ingredient ingredient) {
        this.ingredients.add(ingredient);
    }

    public void addInstruction(Instruction instruction) {
        this.instructions.add(instruction);
    }

    public ArrayList<Instruction> getInstructions() {
        return instructions;
    }

    public void setInstructions(ArrayList<Instruction> instructions) {
        this.instructions = instructions;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
