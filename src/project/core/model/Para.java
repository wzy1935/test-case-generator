package project.core.model;

import java.util.LinkedList;

public class Para extends Element {

    @Override
    public String toString() {
        if (isFinal && !defaultString.equals("")) return defaultString;

        StringBuilder st = new StringBuilder();
        LinkedList<Parsable> children = getChildren();
        int i = 0;
        for (Parsable item : children) {
            i++;
            if (!(item instanceof Environment)) {
                st.append(item.toString());
            } else {
                if (!isFinal) {
                    ((Element) ((Environment) item).getOriginal()).resetFinal();
                }
            }

        }

        if (defaultString.equals("")) defaultString = st.toString();
        return st.toString();
    }

    @Override
    public void setDeck(boolean isDeck) {

    }

    @Override
    public void setFinal(boolean isFinal) {
        this.isFinal = isFinal;
    }
}
