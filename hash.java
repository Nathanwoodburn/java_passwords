import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class hash {
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter a string to hash: ");
        String input = reader.readLine();
        String stored = hashstring(input);

        System.out.println("Stored as:");
        System.out.println(stored);

        System.out.println("Check password:");
        String check = reader.readLine();
        if (hashstring(check).equals(stored)) {
            System.out.println("Match!");
        } else {
            System.out.println("No match!");
        }

    }

    public static String hashstring(String input) throws NoSuchAlgorithmException
    {
        // Create MessageDigest instance for SHA-256
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        // Hash password and convert to hex string
        byte[] hash = md.digest(input.getBytes(StandardCharsets.UTF_8));
        return bytetohex.toHexString(hash);
    }
}
