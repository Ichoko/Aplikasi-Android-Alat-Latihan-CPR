package me.richo.cabtraining.skeleton;

import java.util.HashMap;

/**
 * Class ini dibuat sebagai penampung data per alat
 */
public class Data {

    /**
     * @return pembacaan data hidung
     */
    public long getHidung() {
        return hidung;
    }

    /**
     * @return pembacaan data kemiringan
     */
    public long getKemiringan() {
        return kemiringan;
    }

    /**
     * @return pembacaan data suara
     */
    public long getSuara() {
        return suara;
    }

    /**
     * @return pembacaan data tekanan
     */
    public long getTekanan() {
        return tekanan;
    }

    /**
     * Konstrutor class
     *
     * @param hidung
     * @param kemiringan
     * @param suara
     * @param tekanan
     */
    public Data(long hidung, long kemiringan, long suara, long tekanan) {
        this.hidung = hidung;
        this.kemiringan = kemiringan;
        this.suara = suara;
        this.tekanan = tekanan;
    }

    /**
     * Fungsi ini untuk mengubah data mentah firebase ke penambung data class ini
     * @param obj
     * @return
     */
    public static Data castFrom(Object obj){
        HashMap<String, Long> dataMap = (HashMap<String, Long>) obj;
        long hidung = 0;
        long kemiringan = 0;
        long suara = 0;
        long tekanan = 0;
        for(String memberKey : dataMap.keySet()){
            switch (memberKey.trim()){
                case "tekanan" :
                    tekanan = dataMap.get(memberKey);
                    break;
                case "suara" :
                    suara = dataMap.get(memberKey);
                    break;
                case "kemiringan" :
                    kemiringan = dataMap.get(memberKey);
                    break;
                case "hidung" :
                    hidung = dataMap.get(memberKey);
                    break;
            }
        }

        return new Data(hidung, kemiringan, suara,  tekanan);
    }

    long hidung;
    long kemiringan;
    long suara;
    long tekanan;

}
