package me.richo.cabtraining.skeleton;

/**
 * Class ini dibuat untuk penampung data input dari alat
 */
public class User {

    /**
     * Listener untuk ketika data berubah
     */
    public interface DataChangedListener {
        void onDataChange();
    }

    private String id;
    private String name;

    //Inisisalisasi masing-masing test
    private final Test[] tests = {
            new Test(),
            new Test(),
            new Test(),
    };

    private DataChangedListener dataChangedListener = null;

    /**
     * Kustrutor Class
     * @param name
     */
    public User(String id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Mengambil nama
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     * Set nilai "keberhasilan"
     * @param index
     * @param value
     */
    public void setTest(int index, boolean value) {
        tests[index].setComplete(value);
        if(dataChangedListener!=null){
            //Memancarkan sinyal perubahan data
            dataChangedListener.onDataChange();
        }
    }

    /**
     * Set listener perubahan data
     * @param l
     */
    public void setDataChangedListener(DataChangedListener l) {
        this.dataChangedListener = l;
    }

    /**
     * Mengambil status dari tes berhasil atau tidak
     * @param index
     * @return
     */
    public boolean isTestComplete(int index){
        return tests[index].isComplete();
    }

    /**
     * Reset semua tes
     */
    public void reset(){
        for(Test test : tests){
            test.setComplete(false);
        }
    }

    public String getName() {
        return name;
    }

    /**
     * Class ini dibuat untuk nempung data masing-masing test
     */
    private class Test {
        public boolean isComplete() {
            return isComplete;
        }
        public void setComplete(boolean complete) {
            isComplete = complete;
        }
        private boolean isComplete = false;
    }

}
