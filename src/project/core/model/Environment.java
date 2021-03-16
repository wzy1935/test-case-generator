package project.core.model;

import java.util.HashMap;

public class Environment extends Parsable {
    private HashMap<String,Element> env = new HashMap<>();
    private Parsable original = null;

    public Parsable getParsable(String name) {
        return env.get(name);
    }

    public void extend(Environment env) {
        this.env.putAll(env.env);
    }

    public void put(String name, Element element) {
        env.put(name,element);
        if (original == null) original = element;
    }

    public Environment clone() throws CloneNotSupportedException {
        return (Environment) super.clone();
    }

    public Parsable getOriginal() {
        return original;
    }

    @Override
    public String toString() {
        return "";
    }
}
