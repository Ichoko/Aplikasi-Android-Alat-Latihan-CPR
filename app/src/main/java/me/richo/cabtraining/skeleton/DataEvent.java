package me.richo.cabtraining.skeleton;

/**
 * Class ini dibuat untuk sebagai penampung peristiwa data
 */
public class DataEvent {
    private final Data data;
    private final String dataId;

    public DataEvent(String dataId, Data data) {
        this.data = data;
        this.dataId = dataId;
    }

    public Data getData() {
        return data;
    }

    public String getDataId() {
        return dataId;
    }
}
