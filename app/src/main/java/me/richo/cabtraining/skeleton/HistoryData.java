package me.richo.cabtraining.skeleton;

import java.util.List;

/**
 * Class ini dibuat untuk penampung data daftar riwayat data
 */
public class HistoryData {

    public HistoryData(List<TimeStamp> timeStamps) {
        this.timeStamps = timeStamps;
    }

    List<TimeStamp> timeStamps;

    public List<TimeStamp> getTimeStamps() {
        return timeStamps;
    }

    /**
     * Class ini dibuat untuk penampung baris dari daftar riwayat data
     */
    public static class TimeStamp {
        public final long time;
        public final String name;
        public final String discription;

        public TimeStamp(long time, String name, String discription) {
            this.time = time;
            this.name = name;
            this.discription = discription;
        }

    }
}
