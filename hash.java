import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class hash {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter a string to hash: ");
        String input = reader.readLine();

        System.out.println("Hashed as:");
        try {
            System.out.println(hashstring(input));
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.toString());
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
