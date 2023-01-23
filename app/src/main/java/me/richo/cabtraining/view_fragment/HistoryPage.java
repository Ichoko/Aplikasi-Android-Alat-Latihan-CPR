package me.richo.cabtraining.view_fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import me.richo.cabtraining.R;
import me.richo.cabtraining.adapter.ListHistoryAdapter;
import me.richo.cabtraining.util.HistoryManager;

/**
 * Halaman History
 */
public class HistoryPage extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Inisialisasi daftar riwayat ujian
        View view = inflater.inflate(R.layout.fragment_history_page, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.history_view);
        ListHistoryAdapter historyAdapter = new ListHistoryAdapter(HistoryManager.getInstance().getHistoryData());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(historyAdapter);

        //Menambahakan History Update Listener
        HistoryManager.getInstance().addHistoryUpdateListener(() -> {
            //TODO: Fungsi ini akan dipanggil ketika data riwayat tes berubah
            view.post(() -> {
                //Mereload tampilan daftar riwayat
                historyAdapter.notifyDataSetChanged();
            });
        });
        return view;
    }
}