import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class saltedHash {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter a string to hash and salt: ");
        String input = reader.readLine();

        try {
            String salt = bytetohex.toHexString(getSalt());
            // Remove starting 0s
            while (salt.charAt(0) == '0') {
                salt = salt.substring(1);
            }

            String salted = input + salt;
            System.out.println("Stored as:");
            String hashed = hash.hashstring(salted);
            String stored = hashed + "$"+salt;
            System.out.println(stored);

            System.out.println("Enter a string to check: ");
            String check = reader.readLine();

            // Split stored by the colon
            String[] parts = stored.split("\\$");
            // Salt the checked
            String saltedCheck = check + parts[1];
            // Hash the salted checked
            String hashedCheck = hash.hashstring(saltedCheck);
            // Compare the hashed salted checked to the stored hash
            if (hashedCheck.equals(parts[0])) {
                System.out.println("Match!");
            } else {
                System.out.println("No match!");
            }


        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.toString());
        }

    }

    public static byte[] getSalt() throws NoSuchAlgorithmException {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }
}
