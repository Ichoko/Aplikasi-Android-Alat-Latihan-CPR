package me.richo.cabtraining.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import me.richo.cabtraining.R;
import me.richo.cabtraining.skeleton.HistoryData;

/**
 * Class ini dibuat untuk menghasilkan daftar riwayat
 */
public class ListHistoryAdapter extends RecyclerView.Adapter<ListHistoryAdapter.ViewHolder> {

    List<HistoryData.TimeStamp> timeStamps;

    public ListHistoryAdapter(HistoryData historyData){
        timeStamps = historyData.getTimeStamps();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inisialisasi tampilan
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Proses menampilkan data
        HistoryData.TimeStamp timeStamp = timeStamps.get(position);
        Date date = new Date(timeStamp.time);
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        holder.time.setText(formatter.format(date));
        holder.name.setText(timeStamp.name);
        holder.description.setText(timeStamp.discription);
    }

    @Override
    public int getItemCount() {
        return timeStamps.size();
    }

    /**
     * Class ini dibuat untuk sebagai struktur dari tampilan
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView time;
        TextView name;
        TextView description;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.history_time);
            name = itemView.findViewById(R.id.history_name);
            description = itemView.findViewById(R.id.history_description);
        }
    }
}
