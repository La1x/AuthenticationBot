package ru.laix.authentication;

import javax.jws.soap.SOAPBinding;
import javax.print.DocFlavor;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by La1x on 14.09.2016.
 */
public class AuthenticationUtil {
    //hashmap of data base
    private static Map<String, UserData> Database;
    private static final String DB_FILENAME = "authBotDB.txt";

    public static void loadDataBase() throws IOException {
        if(Files.notExists(Paths.get(DB_FILENAME))) {
            Files.createFile(Paths.get(DB_FILENAME));
            Database = new HashMap<String, UserData>();
        } else {
            List<String> content = Files.readAllLines(Paths.get(DB_FILENAME));
            Database = new HashMap<String, UserData>();
            for (String line : content) {
                String[] s = line.split(",");
                Database.put(s[0], new UserData(s[1], s[2]));
            }
        }
    }

    public static boolean addNewUser(String login, String password) throws NoSuchAlgorithmException {
        if (Database.containsKey(login) == true) {
            return false;
        } else {
            String salt = getRandomSalt();
            Database.put(login, new UserData(sha256(password + salt), salt));
            String newUser = login + "," + sha256(password + salt) + "," + salt + "\n";
            try {
                Files.write(Paths.get(DB_FILENAME), newUser.getBytes(), StandardOpenOption.APPEND);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }
    }

    public void setUserStatus() {

    }

    public static boolean checkPassword(String login, String password) throws NoSuchAlgorithmException {
        if (Database.get(login) == null || Database.isEmpty()) {
            return false;
        }
        String salt = Database.get(login).getSalt();
        String hashFromDB = Database.get(login).getHashPasswordSalt();
        String hashFromPass = sha256(password + salt);
        return hashFromDB.equals(hashFromPass);
    }

    public static UserData getUserData(String login) {
        return new UserData(Database.get(login).getHashPasswordSalt(), Database.get(login).getSalt());
    }

    public static String sha256(String input) throws NoSuchAlgorithmException {
        MessageDigest mDigest = MessageDigest.getInstance("SHA-256");
        byte[] result = mDigest.digest(input.getBytes());
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < result.length; i++) {
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

    public static String getRandomSalt() {
        Random rand = new SecureRandom();
        int number = rand.nextInt(100);
        return String.valueOf(number);
    }
}
