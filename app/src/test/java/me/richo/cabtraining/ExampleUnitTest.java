package me.richo.cabtraining;

import static org.junit.Assert.assertEquals;

import com.google.gson.Gson;

import org.junit.Test;

import java.util.ArrayList;

import me.richo.cabtraining.skeleton.HistoryData;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void gsonText(){
        HistoryData historyData = new HistoryData(new ArrayList<>());
        historyData.getTimeStamps().add(new HistoryData.TimeStamp(System.currentTimeMillis(), "Alat1", "Test Selesai!"));
        historyData.getTimeStamps().add(new HistoryData.TimeStamp(System.currentTimeMillis(), "Alat2", "Test Selesai!"));
        historyData.getTimeStamps().add(new HistoryData.TimeStamp(System.currentTimeMillis(), "Alat3", "Test Selesai!"));

        Gson gson = new Gson();
        HistoryData historyData1 = gson.fromJson(gson.toJson(historyData), HistoryData.class);

        System.out.println(historyData1.toString());
    }
}