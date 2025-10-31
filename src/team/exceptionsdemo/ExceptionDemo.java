package team.exceptionsdemo;

import java.io.IOException;

public class ExceptionDemo {
    public static void main(String[] args) {
        try {
            // checked example
            readConfig();

            // unchecked example
            System.out.println(10/0);

        } catch (IOException e) {
            System.out.println("Checked exception: " + e.getMessage());
        } catch (ArithmeticException e) {
            System.out.println("Arithmetic exception: " + e.getMessage());
        } finally {
            System.out.println("Finally block");
        }
    }
    static void readConfig() throws IOException {
        // generic message
        throw  new IOException("Read config error");
    }
}
