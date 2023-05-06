package uk.ac.abertay.cmp309.cooking;

import android.os.Parcel;
import android.os.Parcelable;

public class Instruction implements Parcelable {
    public String instruction;
    public String timer;

    public Instruction() { }

    public Instruction(String instruction, String timer) {
        this.instruction = instruction;
        this.timer = timer;
    }

    protected Instruction(Parcel in) {
        instruction = in.readString();
        timer = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(instruction);
        dest.writeString(timer);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Instruction> CREATOR = new Creator<Instruction>() {
        @Override
        public Instruction createFromParcel(Parcel in) {
            return new Instruction(in);
        }

        @Override
        public Instruction[] newArray(int size) {
            return new Instruction[size];
        }
    };
}
