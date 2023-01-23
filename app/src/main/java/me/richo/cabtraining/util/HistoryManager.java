package me.richo.cabtraining.util;

import android.content.Context;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.richo.cabtraining.skeleton.HistoryData;

/**
 * Class ini dibuat untuk sebagai pengelolaan data riwayat
 */
public class HistoryManager {

    public interface HistoryUpdateListener {
        void onHistoryUpdate();
    }

    private final static List<HistoryUpdateListener> historyUpdateListeners = new ArrayList<>();

    private static HistoryManager instance;

    private HistoryData historyData;

    /**
     * Membuat instansi dari class ini
     * @param context
     */
    public static void createInstance(Context context){
        instance = new HistoryManager(context);
    }

    /**
     * Mengambil instansi class ini
     * @return
     */
    public static HistoryManager getInstance() {
        return instance;
    }

    File file;

    /**
     * Konstuktor class.
     * Berguna untuk membuat file json dalam folder data aplikasi sebagai penyimpan data-data riwayat ujian
     * @param context
     */
    HistoryManager(Context context){
        file = new File(context.getFilesDir(), "data.json");
        if(!file.exists()){
            try {
                file.createNewFile();
                historyData = new HistoryData(new ArrayList<>());
                save();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                FileReader reader = new FileReader(file);
                historyData = new Gson().fromJson(reader, HistoryData.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(historyData==null){
            historyData = new HistoryData(new ArrayList<>());
            save();
        }
    }

    /**
     * Menyimpan data ke file json
     */
    public void save(){
        try {
            Utils.writeToFile(new Gson().toJson(historyData),file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Memancarkan sinyal untuk listener perubahan data riwayat
     */
    public void notifyUpdate(){
        for(HistoryUpdateListener l : historyUpdateListeners){
            l.onHistoryUpdate();
        }
    }

    /**
     * Menambahkan listener perubahan data riwayat
     * @param l method yang akan dipanggil saat data riwayat berubah
     */
    public void addHistoryUpdateListener(HistoryUpdateListener l){
        historyUpdateListeners.add(l);
    }

    /**
     * Mengambil data riwayat
     * @return data riwayat
     */
    public HistoryData getHistoryData() {
        return historyData;
    }
}
