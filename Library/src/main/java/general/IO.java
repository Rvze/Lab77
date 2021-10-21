package general;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public interface IO {
    Scanner reader = new Scanner(new InputStreamReader(System.in));
    BufferedReader read = new BufferedReader(new InputStreamReader(System.in));

    default String readLine() throws IOException {
        return reader.nextLine();
    }

    default String read() throws IOException {
        return read.readLine();
    }

    default void println(String s) {
        System.out.println(s);
    }

    default void print(String s) {
        System.out.print(s);
    }

    default void errPrint(String s) {
        System.err.println(s);
    }

    default Scanner getReader() {
        return reader;
    }
}
