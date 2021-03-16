import project.core.model.Element;
import project.core.parser.StringParser;
import project.file.FileOperator;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws CloneNotSupportedException, IOException {
        FileOperator fileOperator = new FileOperator();
        StringParser stringParser = new StringParser(fileOperator.input("input.txt"));
        String answer = ((Element)stringParser.parseRoot()).toString();
        fileOperator.output("output.txt",answer);

    }
}
