package core;

import java.util.ArrayList;

class UserInput extends ArrayList<Character> {
    @Override
    public String toString() {
        String s = "";

        for (Character chara : this) {
            s = s + chara;
        }
        return s;
    }
}
