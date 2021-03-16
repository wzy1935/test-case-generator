package project.core.model;

import java.util.Random;

public class Word extends Element{
    Computable value = null;

    @Override
    public String toString() {
        if (isFinal && !defaultString.equals("")) return defaultString;
        String output = value.toString();
        if (defaultString.equals("")) defaultString = output;
        return output;
    }

    public Computable getValue() {
        return value;
    }

    public void setValue(Computable value) {
        this.value = value;
    }

    @Override
    public void setDeck(boolean isDeck) {

    }

    @Override
    public void setFinal(boolean isFinal) {
        this.isFinal = isFinal;
    }

}
