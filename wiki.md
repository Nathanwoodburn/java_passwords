# Password storage security


Storing user passwords should be done in a secure way to ensure the security of your users. However, there is not just one way to store you passwords. Most programs store passwords using a hashing and salting library. However, it is is useful to understand how these libraries work.

### Hashing
Hashing is a major concept in cryptography. Hashing is a method of converting input data to a fixed-length string of characters (typically a hexadecimal number). This is a one way repeatable function. This means any input will always return the same output, but it is very difficult to obtain the input using the output.

The basic workflow for using hashing to store passwords is as follows:

The user sets password by sending password to a server. The server hashes the password and *only* stores the hash in the database.

The user then verifies the password (eg. signing in) by sending the password to the server. The server hashes the password from the user and compares it against the value stored in the database.

Benefits:
- Passwords are not stored
- Minimal user impact

Issues:
- Hashing is quick which allows people to hash all common used passwords to then store for use in "reversing" the hash.

The most common hashing algorithm is SHA256 (Secure hash algorithm 256 bit).

Example code:
```java
import java.math.BigInteger;  
import java.nio.charset.StandardCharsets;  
import java.security.MessageDigest;  
import java.security.NoSuchAlgorithmException;  
  
public class passwords {  
    public static void main(String[] args) throws NoSuchAlgorithmException {  
        // Hash password
        String input = "password";  
        MessageDigest md = MessageDigest.getInstance("SHA-256");  
        byte[] hash = md.digest(input.getBytes(StandardCharsets.UTF_8));  
        // Convert to hex string  
        BigInteger number = new BigInteger(1, hash);  
        StringBuilder hexString = new StringBuilder(number.toString(16));  
  
        // Pad with leading zeros if necessary  
        while (hexString.length() < 64)  
        {  
            hexString.insert(0, '0');  
        }  
        String hashHex = hexString.toString();  
        System.out.println(hashHex);  
    }  
}
```

### Salt and Pepper
The main problem with just using hashing is the repeatability. Whenever I hash `password` I get `5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8`. This makes it simple and quick to generate hashes for common passwords.

The easiest solution to this problem is adding a little something to password before you hash it. This is a salt. A salt is added to the password (eg. `password` is changed to `password754f94da7639b6e49b8abb40f622acef`) before hashing. This salt is generated per user and is stored with the hash (usually separated with a `$` from the hash).
To verify the password the server will retrieve the salt from the database and add it to the password. Then the server hashes and compares it to the stored hash to verify the users password.

A pepper is very similar to a salt, but it is never stored in the database. There are a few ways to implement a pepper. One method is hard coding the pepper into the program before deploying to the server. This increases security in applications with the database on a separate server to the main program.
Another option for a pepper is a device fingerprint. This will use a defining feature of the computer such as the hardware product codes to generate the pepper. This has a downside of not allow easy database migration to a new server.
The last popular implementation for a pepper is a randomly generated pepper using a small sample to generate from. This slows down password verification as the program will need to iterate over the peppers until it finds the pepper that makes the hash match (or if no pepper makes the hash match the password is incorrect).

Benefits:
- More secure from databases of known hashes
- Slower to guess hash do to the added pepper

Issues:
- Still reasonably quick to guess passwords with short pepper


Example hashing with both salt and pepper
```java
import java.math.BigInteger;  
import java.net.NetworkInterface;  
import java.net.SocketException;  
import java.nio.charset.StandardCharsets;  
import java.security.MessageDigest;  
import java.security.NoSuchAlgorithmException;  
import java.security.SecureRandom;  
import java.util.Enumeration;  
  
public class example {  
    public static void main(String[] args) throws NoSuchAlgorithmException, SocketException {  
        // Hash password and convert to hex string  
        String input = "password";  
  
        // Salt  
        SecureRandom random = new SecureRandom();  
        StringBuilder salt = new StringBuilder();  
        for (int i = 0; i < 16; i++) {  
            salt.append(String.format("%02X", random.nextInt()));  
        }  
  
        // Pepper using hardware addresses of network interfaces  
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
  
        // Add salt and pepper to password  
        input = input + salt + pepper;  
  
        MessageDigest md = MessageDigest.getInstance("SHA-256");  
        byte[] hash = md.digest(input.getBytes(StandardCharsets.UTF_8));  
        // Convert to hex string  
        BigInteger number = new BigInteger(1, hash);  
        StringBuilder hexString = new StringBuilder(number.toString(16));  
  
        // Pad with leading zeros if necessary  
        while (hexString.length() < 64)  
        {  
            hexString.insert(0, '0');  
        }  
        String storedPassword = hexString + "$" + salt;  
        System.out.println(storedPassword);  
    }  
}
```

Example verification
```java
String check = "password";  
String[] parts = storedPassword.split("\\$");  
String saltedCheck = check + parts[1];  
String pepperedCheck = saltedCheck + pepper;  
byte[] hashedCheck = md.digest(pepperedCheck.getBytes(StandardCharsets.UTF_8));  
// Convert to hex string  
BigInteger number2 = new BigInteger(1, hashedCheck);  
StringBuilder hexString2 = new StringBuilder(number2.toString(16));  
while (hexString2.length() < 64)  
{  
    hexString2.insert(0, '0');  
}  
  
if (hexString2.toString().equals(parts[0])) {  
    System.out.println("Match!");
} else {  
    System.out.println("No match!");
}
```


## Newer algorithm
A method to increase security is by making the hashing slower. This makes guessing hashes slower. Argon2 offers an algorithm that is resistant to specially built hashing hardware (often called an ASIC). This algorithm is specifically designed for password security rather than reusing other hashing algorithms such as SHA256.


```embed
title: "Argon2 - Wikipedia"
image: "https://en.wikipedia.org/static/images/icons/wikipedia.png"
description: ""
url: "https://en.wikipedia.org/wiki/Argon2"
```


# References
GeeksforGeeks. "SHA-256 Hash in Java." Accessed October 26, 2023. https://www.geeksforgeeks.org/sha-256-hash-in-java/

Wikipedia. "Argon2" Accessed October 26, 2023. https://en.wikipedia.org/wiki/Argon2