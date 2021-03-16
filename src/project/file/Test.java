import java.util.Scanner;

public class Test {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        scanner.next();

        System.out.println("success!");
    }

    @Override
    public String toString() {
        System.out.println("success!");
        return "success";
    }
}