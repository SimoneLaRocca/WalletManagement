package it.unisa.walletmanagement.Model.Storage;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileManager {

    // mode: false = write / true = append
    public static boolean writeRecordToFile(Context context, String fileName, String content, boolean mode){
        String[] list = context.fileList();
        File path = context.getFilesDir();
        FileOutputStream writer = null;
        try {
            writer = new FileOutputStream(new File(path, fileName), mode);
            content += "\n";
            writer.write(content.getBytes());
            writer.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean writeToFile(Context context, String fileName, String content, boolean mode){
        String[] list = context.fileList();
        File path = context.getFilesDir();
        FileOutputStream writer = null;
        try {
            writer = new FileOutputStream(new File(path, fileName), mode);
            writer.write(content.getBytes());
            writer.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean writeListToFile(Context context, String fileName, List<String> content, boolean mode){
        String[] list = context.fileList();
        File path = context.getFilesDir();
        FileOutputStream writer = null;
        try {
            writer = new FileOutputStream(new File(path, fileName), mode);
            for (String item : content){
                item += '\n';
                writer.write(item.getBytes());
            }
            writer.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String readFromFile(Context context, String fileName){
        File path = context.getFilesDir();
        File readFrom = new File(path, fileName);
        byte[] content =  new byte[(int) readFrom.length()];

        try {
            FileInputStream stream = new FileInputStream(readFrom);
            stream.read(content);
            return new String(content);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<String> readListFromFile(Context context, String fileName){
        File path = context.getFilesDir();
        File readFrom = new File(path, fileName);
        byte[] content =  new byte[(int) readFrom.length()];

        try {
            FileInputStream stream = new FileInputStream(readFrom);
            stream.read(content);
            List<String> list = Arrays.asList(new String(content).split("\n"));
            return list;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean deleteRecordFromFile(Context context, String fileName, String delete){
        ArrayList<String> list = new ArrayList(readListFromFile(context, fileName));
        if(list.contains(delete)){
            list.remove(delete);
            writeListToFile(context, fileName, list, false);
            return true;
        }
        return false;
    }
}
