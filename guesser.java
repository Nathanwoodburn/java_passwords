import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;
public class guesser {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        System.out.println("Guessing hash of a random 6 digit password");
        Random random = new Random();
        int seed= random.nextInt(1000000);
        String randomPassword = getRandomPassword(seed);
        System.out.println("Random 6-character string: " + randomPassword);

        System.out.println("Hashed password");
        String hashed = hash.hashstring(randomPassword);
        System.out.println(hashed);

        System.out.println("Guessing...");
        long startTime = System.nanoTime();


        boolean match = false;
        int check=0;
        // Check hash table
        try{
            BufferedReader reader = new BufferedReader(new FileReader("hashtable.txt"));
            String line;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (line.contains(hashed)) {
                    System.out.println("Match!");
                    System.out.println("Password is: " + line.split("\\$")[0]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        // Calculate the time taken in seconds
        double seconds = (double)duration / 1_000_000_000.0;
        System.out.println("Time taken: " + seconds + " seconds");

        System.out.println("Guessing hash of a random 6 digit password with salt");
        System.out.println("Hashed password");
        String salt = bytetohex.toHexString(saltedHash.getSalt());
        // Remove starting 0s
        while (salt.charAt(0) == '0') {
            salt = salt.substring(1);
        }
        String salted = randomPassword + salt;
        String hashedSalted = hash.hashstring(salted);

        System.out.println("Guessing...");
        startTime = System.nanoTime();

        match = false;
        check=0;
        while (!match) {
            String guess = getRandomPassword(check);
            check++;
            String saltedGuess = guess + salt;
            String hashedGuess = hash.hashstring(saltedGuess);
            if (hashedGuess.equals(hashedSalted)) {
                match = true;
                System.out.println("Match!");
                System.out.println("Password is: " + guess);
            }
        }

        endTime = System.nanoTime();
        duration = (endTime - startTime);
        seconds = (double)duration / 1_000_000_000.0;
        System.out.println("Time taken: " + seconds + " seconds");


        System.out.println("Guessing hash of a random 6 digit password with pepper");

        System.out.println("Hashed password");
        String pepper = pepperedHash.getPepper();
        String peppered = randomPassword + pepper;
        String hashedPeppered = hash.hashstring(peppered);

        System.out.println("Guessing...");
        startTime = System.nanoTime();
        match = false;
        check=0;

        while (!match) {
            String guess = getRandomPassword(check);
            check++;
            if (pepperedHash.checkPeppered(guess,hashedPeppered)) {
                match = true;
                System.out.println("Match!");
                System.out.println("Password is: " + guess);
            }
        }
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        seconds = (double)duration / 1_000_000_000.0;
        System.out.println("Time taken: " + seconds + " seconds");



    }

    public static String getRandomPassword(int value) {
        // Define the characters that can be used in the random string
        String characters = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

        // Initialize a StringBuilder to build the random string
        StringBuilder randomString = new StringBuilder(6);

        // Generate a string using the value
        for (int i = 0; i < 6; i++) {
            randomString.append(characters.charAt(value % characters.length()));
            value /= characters.length();
        }


        return randomString.toString();
    }
}
