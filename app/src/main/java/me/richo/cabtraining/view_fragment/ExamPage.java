package me.richo.cabtraining.view_fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import me.richo.cabtraining.R;
import me.richo.cabtraining.adapter.ListTraining;
import me.richo.cabtraining.skeleton.User;
import me.richo.cabtraining.util.Database;

public class ExamPage extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_exam_page, container, false);

        //Inisialisasi firebase database
        FirebaseDatabase.getInstance();

        //Inisialisasi pengelola basis data firebase
        Database.getInstance();

        //Mendaftarkan alat yang akan digunakan.
        List<User> users = new ArrayList<>();
        users.add(new User("AlatA", "KIT A"));
        users.add(new User("AlatB", "KIT B"));
        users.add(new User("AlatC", "KIT C"));

        //Inisialisasi daftar latihan
        ListTraining listTrainingAdapter = new ListTraining(getContext(), users);
        RecyclerView recyclerView = view.findViewById(R.id.user_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(listTrainingAdapter);
        return view;
    }
}