package ru.laix.authentication;

/**
 * Created by La1x on 15.09.2016.
 */
public class UserData {
    private String hashPasswordSalt;
    private String salt;
    //private boolean isOnline;

    public UserData(String h, String s) {
        hashPasswordSalt = h;
        salt = s;
    }

    public void setHashPasswordSalt(String h) {
        hashPasswordSalt = h;
    }

    public void setSalt(String s) {
        salt = s;
    }

    public String getHashPasswordSalt() {
        return hashPasswordSalt;
    }

    public String getSalt() {
        return salt;
    }
}
