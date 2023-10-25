import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class plainText {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter a string to hash: ");
        String input = reader.readLine();

        System.out.println("Stored as:");
        System.out.println(input);

        System.out.println("Check password:");
        String check = reader.readLine();
        if (check.equals(input)) {
            System.out.println("Match!");
        } else {
            System.out.println("No match!");
        }
    }
}
