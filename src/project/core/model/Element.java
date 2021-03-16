package project.core.model;

import java.util.LinkedList;

public abstract class Element extends Parsable {
    private LinkedList<Parsable> children = new LinkedList<>();
    protected boolean isFinal = false;
    protected boolean isDeck = false;
    protected String defaultString = "";

    @Override
    public abstract String toString();
    public abstract void setDeck(boolean isDeck);
    public abstract void setFinal(boolean isFinal);

    public LinkedList<Parsable> getChildren() {
        return children;
    }

    public void resetFinal() {
        defaultString = "";
    }

}


