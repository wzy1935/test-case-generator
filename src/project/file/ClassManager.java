package project.file;

import javax.lang.model.SourceVersion;
import javax.tools.*;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Locale;
import java.util.Set;

public class ClassManager {

    public static void main(String[] args) throws FileNotFoundException, ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {


        JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
        ClassLoader classLoader = ToolProvider.getSystemToolClassLoader();
        FileInputStream fileInputStream = new FileInputStream(new File("src/project/file/output.txt"));
        System.setIn(fileInputStream);
        PrintStream fileOutputStream = new PrintStream(new File("src/project/file/result.txt"));
        System.setOut(fileOutputStream);
        javaCompiler.run(null,null,null,"src/project/file/Test.java");
        Class c = Class.forName("Test");
        String st = Arrays.toString(c.getMethods());
        System.out.println(st);
        c.getMethod("main",String[].class).invoke(null, (Object) args);
    }

}
