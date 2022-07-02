package it.unisa.walletmanagement.Control.Impostazioni;

import android.content.Context;

import java.security.MessageDigest;

import it.unisa.walletmanagement.Model.Storage.FileManager;

public class SecurityManager {
    private String fileName;
    private Context context;

    public SecurityManager(Context context) {
        this.context = context;
        this.fileName = "password.txt";
    }

    public void doSavePassword(String password){
        String hash = md5(password);
        FileManager.writeToFile(context, fileName, hash, false);
    }

    public boolean isSecurityEnabled(){
        String hash_password = FileManager.readFromFile(context, fileName);
        if(hash_password != null && hash_password.length() > 0){
            return true;
        }
        return false;
    }

    public boolean checkLogin(String password){
        String hash = md5(password);
        if(FileManager.readFromFile(context,fileName).equals(hash)){
            return true;
        }
        return false;
    }

    public boolean doChangePassword(String oldPassword, String newPassword){
        String hash = md5(oldPassword);
        if(FileManager.deleteRecordFromFile(context, fileName, hash)){
            doSavePassword(newPassword);
        }
        return false;
    }

    public boolean doRemovePassword(String password){
        String hash = md5(password);
        if(FileManager.deleteRecordFromFile(context, fileName, hash)){
            return true;
        }
        return false;
    }

    public static final String md5(final String toEncrypt) {
        try {
            final MessageDigest digest = MessageDigest.getInstance("md5");
            digest.update(toEncrypt.getBytes());
            final byte[] bytes = digest.digest();
            final StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(String.format("%02X", bytes[i]));
            }
            return sb.toString().toLowerCase();
        } catch (Exception exc) {
            return "";
        }
    }
}
