package project.core.model;

import java.util.ArrayList;

public abstract class Computable extends Parsable {

    public abstract Object getValueObject();

    public int getValueInteger() {
        return (int) getValueObject();
    }

    public String toString() {
        return getValueObject().toString();
    }

    public Object add(Computable item) {
        Object a = getValueObject();
        Object b = item.getValueObject();
        if (a instanceof Integer && b instanceof Integer) return (int)          a + (int) b;
        else if (a instanceof Integer && b instanceof Double) return (int)      a + (double) b;
        else if (a instanceof Integer && b instanceof String) return (int)      a + (String) b;
        else if (a instanceof Double && b instanceof Integer) return (double)   a + (int) b;
        else if (a instanceof Double && b instanceof Double) return (double)    a + (double) b;
        else if (a instanceof Double && b instanceof String) return (double)    a + (String) b;
        else if (a instanceof String && b instanceof Integer) return (String)   a + (int) b;
        else if (a instanceof String && b instanceof Double)  return (String)   a + (double) b;
        else if (a instanceof String && b instanceof String)  return (String)   a + (String) b;
        return null;
    }

    public Object subtract(Computable item) {
        Object a = getValueObject();
        Object b = item.getValueObject();
        if (a instanceof String || b instanceof String)  return null;
        if (a instanceof Integer && b instanceof Integer) return (int)          a - (int) b;
        else if (a instanceof Integer && b instanceof Double) return (int)      a - (double) b;
        else if (a instanceof Double && b instanceof Integer) return (double)   a - (int) b;
        else if (a instanceof Double && b instanceof Double) return (double)    a - (double) b;
        return null;
    }

    public Object multiply(Computable item) {
        Object a = getValueObject();
        Object b = item.getValueObject();
        if (a instanceof String || b instanceof String)  return null;
        if (a instanceof Integer && b instanceof Integer) return (int)          a * (int) b;
        else if (a instanceof Integer && b instanceof Double) return (int)      a * (double) b;
        else if (a instanceof Double && b instanceof Integer) return (double)   a * (int) b;
        else if (a instanceof Double && b instanceof Double) return (double)    a * (double) b;
        return null;
    }

    public Object divide(Computable item) {
        Object a = getValueObject();
        Object b = item.getValueObject();
        if (a instanceof String || b instanceof String)  return null;
        if (a instanceof Integer && b instanceof Integer) return (int)          a / (int) b;
        else if (a instanceof Integer && b instanceof Double) return (int)      a / (double) b;
        else if (a instanceof Double && b instanceof Integer) return (double)   a / (int) b;
        else if (a instanceof Double && b instanceof Double) return (double)    a / (double) b;
        return null;
    }

    public Object mod(Computable item) {
        Object a = getValueObject();
        Object b = item.getValueObject();
        if (a instanceof String || b instanceof String)  return null;
        if (a instanceof Integer && b instanceof Integer) return (int)          a % (int) b;
        else if (a instanceof Integer && b instanceof Double) return (int)      a % (double) b;
        else if (a instanceof Double && b instanceof Integer) return (double)   a % (int) b;
        else if (a instanceof Double && b instanceof Double) return (double)    a % (double) b;
        return null;
    }


}

