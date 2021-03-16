package project.core.model;

import java.util.ArrayList;

public class Range extends Computable {
    ArrayList<Object> pool = new ArrayList<>();
    Object from,to;
    boolean isPool = true;

    public Range(boolean isPool) {
        this.isPool = isPool;
    }

    public Range(Object item) {
        pool.add(item);
    }

    public void setRange(Object from, Object to) {
        this.from = from;
        this.to = to;
    }

    public void setPool(ArrayList<Object> pool) {
        this.pool = pool;
    }

    public void addPool(Object item) {
        pool.add(item);
    }

    @Override
    public Object getValueObject() {
        if (isPool) {
            int rnd = (int) (Math.random() * pool.size());
            Object tmp = pool.get(rnd);
            if (tmp instanceof Computable) return ((Computable) tmp).getValueObject();
            else return tmp;
        } else {
            Object a,b;
            if (from instanceof Computable) b = ((Computable) from).getValueObject();
            else b = from;
            if (to instanceof Computable) a = ((Computable) to).getValueObject();
            else a = to;

            Object c = null;
            if (a instanceof String || b instanceof String)         return null;
            if (a instanceof Integer && b instanceof Integer)       c =  (int)          a - (int) b;
            else if (a instanceof Integer && b instanceof Double)   c =  (int)      a - (double) b;
            else if (a instanceof Double && b instanceof Integer)   c =  (double)   a - (int) b;
            else if (a instanceof Double && b instanceof Double)    c =  (double)    a - (double) b;

            if (a instanceof Integer && b instanceof Integer)       return (int) b + (int) (Math.random() * ((int) c));
            else if (b instanceof Double)   return (double) b + (Math.random() * ((double) c));
            else if (b instanceof Integer)   return (int) b + (Math.random() * ((double) c));

        }
        return null;
    }
}
