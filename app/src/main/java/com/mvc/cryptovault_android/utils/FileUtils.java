package com.mvc.cryptovault_android.utils;

import android.os.Environment;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * sava file,  used to sava json data
 */
public class FileUtils {
    private static String filePath = Environment.getExternalStorageDirectory().getAbsolutePath();

    public static void saveFile(String fileName, Object clazz) {
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(filePath + "/" + fileName + ".txt");
            Gson gson = new Gson();
            String newsJson = gson.toJson(clazz);
            outputStream.write(newsJson.getBytes());
            outputStream.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static File getFile(String fileName) {
        File file = new File(filePath + "/" + fileName + ".txt");
        if (!exists(file)) {
            return file;
        }
        return null;
    }

    /**
     * 如果是文件夹  就认为是空的
     *
     * @param file
     * @return
     */
    public static boolean exists(File file) {
        if (file.isDirectory()) {
            return true;
        } else if (file.length() == 0) {
            return true;
        }
        return false;
    }

    public static String getFileToString(String fileName) {
        File file = getFile(fileName);
        BufferedReader reader = null;
        FileInputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        if (file == null) {
            return "";
        }
        try {
            inputStream = new FileInputStream(file);
            inputStreamReader = new InputStreamReader(inputStream);
            reader = new BufferedReader(inputStreamReader);
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
                inputStream.close();
                inputStreamReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }
}
