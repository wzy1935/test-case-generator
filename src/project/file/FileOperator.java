package project.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileOperator {

    public String input(String fileName) throws FileNotFoundException {
        File file = new File("src/project/file/" + fileName);
        StringBuilder st = new StringBuilder();
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            st.append(scanner.nextLine());
        }
        return st.toString();

    }

    public void output(String fileName, String content) throws IOException {
        File file = new File("src/project/file/" + fileName);
        FileWriter fileWriter = new FileWriter(file.getPath());
        fileWriter.flush();
        fileWriter.write(content);
        fileWriter.close();

    }
}
