package project.core.model;

import java.util.HashMap;

public class Parsable {
    protected Environment env;

    public Environment getEnvironment() {
        if (this.env == null) {
            env = new Environment();
            return env;
        }
        return env;
    }

}