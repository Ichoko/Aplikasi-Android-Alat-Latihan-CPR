package me.richo.cabtraining.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Class ini dibuat untuk utilitas
 */
public class Utils {

    /**
     * Mengubah aliran data ke teks
     * @param is aliran data input
     * @return text dari aliran data
     * @throws Exception
     */
    public static String convertStreamToString(InputStream is) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        reader.close();
        return sb.toString();
    }

    /**
     * Memcara data ke teks
     * @param file file yang akan dibaca
     * @return teks yang dibaca dari file
     * @throws Exception
     */
    public static String getStringFromFile (File file) throws Exception {
        FileInputStream fin = new FileInputStream(file);
        String ret = convertStreamToString(fin);
        fin.close();
        return ret;
    }

    /**
     * Menulis data teks ke file
     * @param data data teks
     * @param file target
     * @throws IOException
     */
    public static void writeToFile(String data, File file) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
        outputStreamWriter.write(data);
        outputStreamWriter.close();
    }

}
