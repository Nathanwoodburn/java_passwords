import java.io.File;
import java.security.NoSuchAlgorithmException;

public class hashTable {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        System.out.println("Hashing all 6 digit passwords");
//        Save to hashtable.txt
        File file = new File("hashtable.txt");
        if (file.exists()) {
            file.delete();
        }
        // Create a new file in the current directory
        try {
            file.createNewFile();
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        // Write to the file
        for (int i = 0; i < 1000000; i++) {
            String randomPassword = guesser.getRandomPassword(i);
            String hashed = hash.hashstring(randomPassword);
            String line = randomPassword + "$" + hashed + "\n";
            try {
                java.nio.file.Files.write(java.nio.file.Paths.get("hashtable.txt"), line.getBytes(), java.nio.file.StandardOpenOption.APPEND);
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }
    }
}
