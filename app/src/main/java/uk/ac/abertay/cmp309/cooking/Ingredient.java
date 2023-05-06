package uk.ac.abertay.cmp309.cooking;

import android.os.Parcel;
import android.os.Parcelable;

public class Ingredient implements Parcelable {
    public String name;
    public String quantity;

    public Ingredient() { }

    public Ingredient(String name, String quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    protected Ingredient(Parcel in) {
        name = in.readString();
        quantity = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(quantity);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };
}
