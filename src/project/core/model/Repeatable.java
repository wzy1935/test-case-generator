package project.core.model;

public class Repeatable extends Parsable {
    private Element element = null;
    private Parsable value = null;

    public Repeatable (Element element, Parsable value) {
        this.element = element;
        this.value = value;
    }

    @Override
    public String toString() {
        int times = Integer.parseInt(value.toString());
        StringBuilder st = new StringBuilder();
        for (int i = 1; i <= times; i++) {
            st.append(element.toString());
            if (element instanceof Word) st.append(i == times ? "" : " ");
        }
        return st.toString();
    }
}
