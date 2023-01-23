package me.richo.cabtraining.util;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import me.richo.cabtraining.skeleton.Data;
import me.richo.cabtraining.skeleton.DataEvent;

/**
 * Class ini dibuat untuk sebagai pengolahan data mentah dari firebase
 */
public class Database {

    public static String TAG = "Database";

    private static Database instance;
    private LinkedHashMap<String, Data> userdatas;

    /**
     * EventListener
     */
    public interface EventListener {
        void onEvent(DataEvent event);
    }

    /**
     * EventListener
     */
    public interface StateEventListener {
        void onOn(DataEvent event);
        void onOff(DataEvent event);
        void onEvent(DataEvent event);
    }

    private final List<EventListener> kompresiEvents;
    private final List<EventListener> kemiringanEvents;
    private final List<StateEventListener> hidungEvents;

    /**
     * Construktor
     */
    Database(){
        kompresiEvents = new ArrayList<>();
        kemiringanEvents = new ArrayList<>();
        hidungEvents = new ArrayList<>();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference db = firebaseDatabase.getReference();
        db.addValueEventListener(new ValueEventListener() {
            //TODO: Pengolahan data mentah dari firebase
            HashMap<String, Boolean> kompresi = new HashMap<>();
            HashMap<String, Boolean> kemiringan = new HashMap<>();
            HashMap<String, Long> hidung = new HashMap<>();
            HashMap<String, Boolean> suara = new HashMap<>();

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Memasukan data mentah ke class User
                Map<String, Object> datas = (Map<String, Object>) dataSnapshot.getValue();
                Log.d(TAG, "onDataChange: " + datas);
                for(String dataId : datas.keySet()){
                    userdatas.put(dataId.trim(), Data.castFrom(datas.get(dataId)));

                    //Tekanan
                    if(!kompresi.containsKey(dataId)) kompresi.put(dataId, false);
                    if(kompresi.get(dataId)){
                        if(userdatas.get(dataId).getTekanan() > 200) {
                            kompresi.put(dataId, true);
                            for (EventListener event : kompresiEvents){
                                event.onEvent(new DataEvent(dataId, userdatas.get(dataId)));
                            }
                        }
                    } else {
                        if(userdatas.get(dataId).getTekanan() > 200) {
                            kompresi.put(dataId, true);
                        }
                    }


                    //Kemiringan
                    if(!kemiringan.containsKey(dataId)) kemiringan.put(dataId, false);
                    if(kemiringan.get(dataId)){
                        if(userdatas.get(dataId).getKemiringan() > 15) {
                            kemiringan.put(dataId, false);
                            for (EventListener event : kemiringanEvents){
                                event.onEvent(new DataEvent(dataId, userdatas.get(dataId)));
                            }
                        }
                    } else {
                        if(userdatas.get(dataId).getKemiringan() < 10) {
                            kemiringan.put(dataId, true);
                        }
                    }

                    //Hidung
                    if(!hidung.containsKey(dataId)) hidung.put(dataId, 0L);
                    if(userdatas.get(dataId).getHidung() != hidung.get(dataId)){
                        hidung.put(dataId, userdatas.get(dataId).getHidung());
                        if(userdatas.get(dataId).getHidung() == 0) {
                            for (StateEventListener event : hidungEvents){
                                event.onOff(new DataEvent(dataId, userdatas.get(dataId)));
                            }
                        } else if(userdatas.get(dataId).getHidung() == 1){
                            for (StateEventListener event : hidungEvents){
                                event.onOn(new DataEvent(dataId, userdatas.get(dataId)));
                            }
                        }
                    }

                    //Suara
                    if(!suara.containsKey(dataId)) suara.put(dataId, false);
                    if(suara.get(dataId)){
                        if(userdatas.get(dataId).getSuara() == 1) {
                            suara.put(dataId, false);
                            for (StateEventListener event : hidungEvents){
                                event.onEvent(new DataEvent(dataId, userdatas.get(dataId)));
                            }
                        }
                    } else {
                        if(userdatas.get(dataId).getSuara() == 0) {
                            suara.put(dataId, true);
                        }
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        userdatas = new LinkedHashMap<>();
    }

    public LinkedHashMap<String, Data> getUserdatas() {
        return userdatas;
    }

    public List<EventListener> getKompresiEvents() {
        return kompresiEvents;
    }

    public List<EventListener> getKemiringanEvents() {
        return kemiringanEvents;
    }

    public List<StateEventListener> getHidungEvents() {
        return hidungEvents;
    }

    /**
     * Membuat dan sekaligus mengambil instansi database
     * @return instansi database
     */
    public static Database getInstance(){
        if(instance == null){
            instance = new Database();
        }
        return instance;
    };
}
