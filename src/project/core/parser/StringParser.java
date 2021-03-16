package project.core.parser;

import project.core.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;

public class StringParser {
    private final HashSet<Character> punctuation = new HashSet<>(
            Arrays.asList(' ','(',')','[',']','{','}','=','\\','+','-','*','/','%','^',',','~','\n')
    );
    private final HashSet<String> declaration = new HashSet<>(
            Arrays.asList("para","line","word")
    );
    private final HashSet<String> restriction = new HashSet<>(
            Arrays.asList("final")
    );

    String[] data;
    Element root = new Element() {
        @Override
        public String toString() {
            LinkedList<Parsable> children = getChildren();
            Parsable main = null;
            for (Parsable p : children) {
                if (p instanceof Environment) {
                    main = ((Environment) p).getParsable("main");
                    if (main != null) {
                        return main.toString();
                    }
                }
            }
            return "main not found";
        }

        @Override
        public void setDeck(boolean isDeck) {System.out.println("try to set restriction root");}

        @Override
        public void setFinal(boolean isFinal) {System.out.println("try to set restriction root");}
    };
    int pointer = 1;
    boolean isComputing = false;

    public StringParser(String code) {
        data = split(code);
    }

    private String[] split(String code) {

        ArrayList<String> list = new ArrayList<>();
        String load = "";
        for (int i = 1; i <= code.length(); i++) {
            char tmp = code.charAt(i-1);

            if (punctuation.contains(tmp)) {
                if (!(load.equals(""))) list.add(load);
                load = "";
                if ( tmp != ' ' && tmp != '\n') list.add(tmp + "");
            } else {
                load += tmp;
            }

        }
        String[] output = new String[list.size()];
        list.toArray(output);
        return output;
    }

    private Computable cast(String str, Environment environment) {
        int dot = 0;
        boolean isDouble = true;
        boolean isInt = true;
        boolean isString = true;
        for (int i = 1; i <= str.length(); i++) {
            int chr = str.charAt(i-1);
            if (chr < 48 || chr > 57) {
                isInt = false;
                if (chr == '.') {
                    dot++;
                    if (i == 1 || i == str.length()) dot++;
                } else {
                    isDouble = false;
                }
            }
        }
        if (str.charAt(0) != '"' || str.charAt(str.length()-1) != '"') isString = false;
        if (dot != 1) isDouble = false;
        if (isString) return new Range(str.substring(1,str.length()-1));
        if (isDouble) return new Range(Double.parseDouble(str));
        if (isInt) return new Range(Integer.parseInt(str));
        return ((Word) environment.getParsable(str)).getValue();
    }

    public Parsable parseRoot() throws CloneNotSupportedException {
        pointer = 1;
        while (pointer < data.length) root.getChildren().add(parse(root));
        return root;
    }

    private Parsable parse(Element parent) throws CloneNotSupportedException {  //传入需要环境所在的element，即me期望的父节点
        Parsable me = null;
        if (pointer > data.length) return null;
        String current = data[pointer-1];

        if (declaration.contains(current)) {  //检测为environment
            me = parseEnvironment(parent);
            parent.getEnvironment().extend((Environment) me);
            return me;
        }

        if (current.equals("{")) {
            return parsePara(parent);
        }

        if (current.equals("[")) {
            return parseLine(parent);
        }

        if (current.equals("(")) {
            return parseWord(parent);
        }

        if (current.equals("*")) {
            return parseRepeat(parent);
        }

        pointer++;
        return parent.getEnvironment().getParsable(current);
    }

    private Environment parseEnvironment(Element parent) throws CloneNotSupportedException {  //创建一条环境
        Environment me = new Environment();
        me.getEnvironment().extend(parent.getEnvironment());
        String type = "";
        HashSet<String> innerRestriction = new HashSet<>();
        String name = "";
        Element object = null;

        type = data[pointer-1]; pointer++;
        while (restriction.contains(data[pointer-1])) {
            innerRestriction.add(data[pointer-1]);
            pointer++;
        }
        name = data[pointer-1];pointer++;
        pointer++;
        boolean isFinal = innerRestriction.contains("final");
        boolean isDeck = innerRestriction.contains("deck");
        object = (Element) parse(parent);
        object.setFinal(isFinal);
        object.setDeck(isDeck);
        me.put(name,object);
        return me;
    }

    private Para parsePara(Element parent) throws CloneNotSupportedException {
        Para me = new Para();
        me.getEnvironment().extend(parent.getEnvironment());
        pointer++;
        while (!data[pointer-1].equals("}")) {
            Parsable child = parse(me);
            if (child != null) me.getChildren().add(child);
        }
        pointer++;
        return me;
    }

    private Line parseLine(Element parent) throws CloneNotSupportedException {
        Line me = new Line();
        me.getEnvironment().extend(parent.getEnvironment());
        pointer++;
        while (!data[pointer-1].equals("]")) {
            Parsable child = parse(me);
            if (child != null) me.getChildren().add(child);
        }
        pointer++;
        return me;
    }

    private Word parseWord(Element parent) throws CloneNotSupportedException {
        Word me = new Word();
        me.getEnvironment().extend(parent.getEnvironment());
        pointer++;

        Computable first = null;
        if (data[pointer-1].equals("(")) first = parseWord(me).getValue();
        else if (data[pointer-1].equals("\\")) first = parseFormula(me);
        else {first = cast(data[pointer-1],me.getEnvironment());pointer++;}


        boolean isPool = data[pointer-1].equals(",");
        Range range = new Range(isPool);
        if (!isPool) {
            pointer++;
            Computable tmp = null;
            if (data[pointer-1].equals("(")) tmp = parseWord(me).getValue();
            else if (data[pointer-1].equals("\\")) tmp = parseFormula(me);
            else {tmp = cast(data[pointer-1],me.getEnvironment());pointer++;}
            range.setRange(first,tmp);
        } else {
            range.addPool(first);
            while (!data[pointer-1].equals(")")) {
                pointer++;
                Computable tmp = null;
                if (data[pointer-1].equals("(")) tmp = parseWord(me).getValue();
                else if (data[pointer-1].equals("\\")) tmp = parseFormula(me);
                else {tmp = cast(data[pointer-1],me.getEnvironment());pointer++;}
                range.addPool(tmp);
            }
        }

        me.setValue(range);
        pointer++;
        return me;
    }

    private Parsable parseRepeat(Element parent) throws CloneNotSupportedException {
        pointer++;
        Parsable repeatElement = parent.getChildren().getLast();
        Parsable repeat = null;
        if (data[pointer-1].equals("(")) {
            repeat = parse(parent);
        } else if (parent.getEnvironment().getParsable(data[pointer-1]) != null) {
            repeat = parent.getEnvironment().getParsable(data[pointer-1]);
        } else {
            repeat = cast(data[pointer-1],parent.getEnvironment());
        }

        Element last = (Element) parent.getChildren().getLast();
        parent.getChildren().removeLast();
        parent.getChildren().add(new Repeatable(last,repeat));

        pointer++;
        return null;
    }

    private Computable parseFormula(Element parent) {
        return null;
    }



}
