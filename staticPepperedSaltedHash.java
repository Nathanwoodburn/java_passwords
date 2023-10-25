import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;
import java.util.Random;

public class staticPepperedSaltedHash {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter a string to hash, salt and pepper:");
        String input = reader.readLine();

        try {
            String salt = bytetohex.toHexString(saltedHash.getSalt());
            while (salt.charAt(0) == '0') {
                salt = salt.substring(1);
            }
            String pepper = getPepper();
            System.out.println("Pepper is: " + pepper);

            String saltedPeppered = input + salt + getPepper();
            System.out.println("Stored as:");
            String hashed = hash.hashstring(saltedPeppered);
            String hashedWSalt = hashed + "$" + salt;
            System.out.println(hashedWSalt);

            System.out.println("Enter a string to check: ");
            String check = reader.readLine();
            String checkSalted = check + hashedWSalt.split("\\$")[1];

            if (checkPeppered(checkSalted,hashed)){
                System.out.println("Match!");
            } else {
                System.out.println("No match!");
            }



        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.toString());
        }
    }

    public static boolean checkPeppered(String check,String hashed) throws NoSuchAlgorithmException, SocketException {
        boolean match = false;
        String pepperedCheck = check + getPepper();
        String hashedCheck = hash.hashstring(pepperedCheck);
        if (hashedCheck.equals(hashed)) match = true;
        return match;
    }

    public static String getPepper() throws SocketException {
        Enumeration<NetworkInterface> nis = NetworkInterface.getNetworkInterfaces();
        StringBuilder pepper = new StringBuilder();
        while (nis.hasMoreElements()) {
            NetworkInterface ni = nis.nextElement();
            byte[] mac = ni.getHardwareAddress();
            if (mac != null) {
                for (int i = 0; i < mac.length; i++) {
                    pepper.append(String.format("%02X", mac[i]));
                }
            }
        }
        return pepper.toString();

    }
}