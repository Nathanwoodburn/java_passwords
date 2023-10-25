import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class pepperedHash {
    public final static String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter a string to hash and pepper: ");
        String input = reader.readLine();

        try {
            String pepper = getPepper();
            System.out.println("Pepper is: " + pepper);

            String peppered = input + getPepper();
            System.out.println("Stored as:");
            String hashed = hash.hashstring(peppered);
            System.out.println(hashed);

            System.out.println("Enter a string to check: ");
            String check = reader.readLine();

            if (checkPeppered(check,hashed)){
                System.out.println("Match!");
            } else {
                System.out.println("No match!");
            }



        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.toString());
        }
    }

    public static boolean checkPeppered(String check,String hashed) throws NoSuchAlgorithmException {
        boolean match = false;
        for (int i = 0; i<52;i++){
            String pepperedCheck = check + alphabet.charAt(i);
            String hashedCheck = hash.hashstring(pepperedCheck);
            if (hashedCheck.equals(hashed)) {
                match = true;
                break;
            }
        }
        return match;
    }

    public static String getPepper() {
        // Return random a-zA-Z
        Random random = new Random();
        int randomIndex = random.nextInt(52);

        // Get the random character
        char randomChar = alphabet.charAt(randomIndex);
        return Character.toString(randomChar);
    }
}