package me.richo.cabtraining.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import me.richo.cabtraining.R;
import me.richo.cabtraining.skeleton.DataEvent;
import me.richo.cabtraining.skeleton.HistoryData;
import me.richo.cabtraining.skeleton.User;
import me.richo.cabtraining.util.Database;
import me.richo.cabtraining.util.HistoryManager;
import me.richo.cabtraining.util.RunnableThread;

/**
 * Class ini dibuat untuk menghasilkan daftar pelatihan
 */
public class ListTraining extends RecyclerView.Adapter<ListTraining.ViewHolder> {

    public static String TAG = "List View";

    private final List<User> users; //List untuk masing-masing alat
    private final Context context;

    /**
     * Konstruktor class
     * @param context
     * @param users
     */
    public ListTraining(Context context, List<User> users) {
        this.users = users;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inisialisasi tampilan daftar
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.userlist, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.nameText.setText(users.get(position).getName());

        //Membuat prosess baru yang terpisah untuk mengolah input dari pengguna
        final RunnableThread[] task = {
                new RunnableThread()
        };

        //Mengambil sesi alat
        User user = users.get(position);
        String id = user.getId();

        //Mengeset listener untuk ketika data berubah
        user.setDataChangedListener(() -> {
            //TODO: Fungsi ini akan dipanggil ketika data pada alat berubah
            if(user.isTestComplete(0) && user.isTestComplete(1) && user.isTestComplete(2)){
                //TODO: Jika semua tes selesai, maka tes akan disimpan ke riwayat tes dan reset sesi
                HistoryManager.getInstance().getHistoryData().getTimeStamps().add(new HistoryData.TimeStamp(System.currentTimeMillis(), user.getName(), "Exam is complete."));
                HistoryManager.getInstance().save();
                HistoryManager.getInstance().notifyUpdate();
                user.reset();

                for (int i = 0; i < holder.btn_test.length; i++) {
                    holder.btn_test[i].setText("Test " + (i + 1));
                    holder.btn_test[i].setBackgroundTintList(context.getResources().getColorStateList(R.color.primary));
                }
            }
        });

        //Inisialisasi fungsi tiap tombol
        for (int i = 0; i < holder.btn_test.length; i++) {
            Button button = holder.btn_test[i];
            switch (i){
                //Lakukan Kompresi
                case 0:
                    button.setOnClickListener( view -> {
                        if(user.isTestComplete(1)){
                            Toast.makeText(context, "Test is already completed", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if(task[0].getState().equals(Thread.State.TERMINATED)){
                            task[0] = new RunnableThread();
                        }
                        if(task[0].getState().equals(Thread.State.NEW)){
                            button.setBackgroundTintList(context.getResources().getColorStateList(R.color.secondary_text));
                            task[0].setOnRun(() -> {
                                final int maxTime = 30;
                                final int maxScore = 30;
                                AtomicInteger score = new AtomicInteger();
                                AtomicInteger count = new AtomicInteger();
                                AtomicBoolean isComplete = new AtomicBoolean(false);

                                Database.EventListener eventListener = (event) -> {
                                    if(!event.getDataId().equals(id)) return;
                                    score.getAndIncrement();
                                    score.getAndIncrement();
                                    if(score.get() >= maxScore){
                                        isComplete.set(true);
                                    }
                                    view.post(() -> {
                                        button.setText(score.get() + " (" + count + ")");
                                        count.getAndIncrement();
                                    });
                                };

                                Database.getInstance().getKompresiEvents().add(eventListener);

                                while (!(count.get() >= maxTime)){
                                    if(isComplete.get()){
                                        break;
                                    }
                                    try {
                                        view.post(() -> {
                                            button.setText(score.get() + "/" + maxScore + " (" + (count.get()) + ")");
                                        });
                                        Thread.sleep(1000);
                                        count.getAndIncrement();
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }

                                view.post(()-> {
                                    if(isComplete.get()){
                                        button.setText("Complete!");
                                        button.setBackgroundTintList(context.getResources().getColorStateList(R.color.success));
                                        user.setTest(0, true);
                                    } else {
                                        button.setText("Failure!");
                                        button.setBackgroundTintList(context.getResources().getColorStateList(R.color.danger));
                                    }

                                });

                                Database.getInstance().getKompresiEvents().remove(eventListener);
                            });
                            task[0].start();
                            return;
                        }
                        Toast.makeText(context, "Task is running", Toast.LENGTH_SHORT).show();
                    });
                    break;
                //Miringkan kepala
                case 1:
                    button.setOnClickListener( view -> {
                        if(user.isTestComplete(1)){
                            Toast.makeText(context, "Test is already completed", Toast.LENGTH_SHORT).show();
                            return;
                        } else if(!user.isTestComplete(0)){
                            Toast.makeText(context, "please complete previous task before", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if(task[0].getState().equals(Thread.State.TERMINATED)){
                            task[0] = new RunnableThread();
                        }
                        if(task[0].getState().equals(Thread.State.NEW)){
                            button.setBackgroundTintList(context.getResources().getColorStateList(R.color.secondary_text));
                            task[0].setOnRun(() -> {
                                final int maxTime = 10;
                                final int maxScore = 1;
                                AtomicInteger score = new AtomicInteger();
                                AtomicInteger count = new AtomicInteger();
                                AtomicBoolean isComplete = new AtomicBoolean(false);

                                Database.EventListener eventListener = (event) -> {
                                    if(!event.getDataId().equals(id)) return;
                                    score.getAndIncrement();
                                    if(score.get() >= maxScore){
                                        isComplete.set(true);
                                    }
                                    view.post(() -> {
                                        button.setText(score.get() + " (" + count + ")");
                                        count.getAndIncrement();
                                    });
                                };

                                Database.getInstance().getKemiringanEvents().add(eventListener);

                                while (!(count.get() >= maxTime)){
                                    if(isComplete.get()){
                                        break;
                                    }
                                    try {
                                        view.post(() -> {
                                            button.setText(score.get() + "/" + maxScore + " (" + (count.get()) + ")");
                                        });
                                        Thread.sleep(1000);
                                        count.getAndIncrement();
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }

                                view.post(()-> {
                                    if(isComplete.get()){
                                        button.setText("Complete!");
                                        button.setBackgroundTintList(context.getResources().getColorStateList(R.color.success));
                                        user.setTest(1, true);
                                    } else {
                                        button.setText("Failure!");
                                        button.setBackgroundTintList(context.getResources().getColorStateList(R.color.danger));
                                    }

                                });

                                Database.getInstance().getKemiringanEvents().remove(eventListener);
                            });
                            task[0].start();
                            return;
                        }
                        Toast.makeText(context, "Task is running", Toast.LENGTH_SHORT).show();
                    });
                    break;
                //Tutup hidung korban
                case 2:
                    button.setOnClickListener( view -> {
                        if(user.isTestComplete(2)){
                            Toast.makeText(context, "Test is already completed", Toast.LENGTH_SHORT).show();
                            return;
                        } else if(!user.isTestComplete(1)){
                            Toast.makeText(context, "please complete previous task before", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        button.setText("Push!");
                        if(task[0].getState().equals(Thread.State.TERMINATED)){
                            task[0] = new RunnableThread();
                        }
                        if(task[0].getState().equals(Thread.State.NEW)){
                            button.setBackgroundTintList(context.getResources().getColorStateList(R.color.secondary_text));
                            task[0].setOnRun(() -> {
                                final int maxTime = 69;
                                final int maxScore = 2;
                                AtomicInteger count = new AtomicInteger();
                                AtomicInteger score = new AtomicInteger();
                                AtomicBoolean isPressed = new AtomicBoolean(false);
                                AtomicBoolean isComplete = new AtomicBoolean(false);

                                Database.StateEventListener stateEventListener = new Database.StateEventListener() {
                                    @Override
                                    public void onOn(DataEvent event) {
                                        if(!event.getDataId().equals(id)) return;
                                        if(!isPressed.get()){
                                            isPressed.set(true);
                                        }
                                    }

                                    @Override
                                    public void onOff(DataEvent event) {
                                        if(!event.getDataId().equals(id)) return;
                                        if(isPressed.get()){
                                            isPressed.set(false);
                                        }
                                    }

                                    @Override
                                    public void onEvent(DataEvent event) {
                                        if(!event.getDataId().equals(id)) return;
                                        if(isPressed.get()){
                                            score.getAndIncrement();
                                        }
                                    }
                                };

                                Database.getInstance().getHidungEvents().add(stateEventListener);


                                while (!(count.get() >= maxTime)){

                                    try {
                                        if(!isPressed.get()){
                                            view.post(()-> {
                                                button.setText("Push!\n" + score.get() + "/" + maxScore + " (" + (count.get()) + ")");
                                                button.setBackgroundTintList(context.getResources().getColorStateList(R.color.danger));
                                            });
                                        } else {
                                            view.post(()-> {
                                                button.setText("Breath!\n" + score.get() + "/" + maxScore + " (" + (count.get()) + ")");
                                                button.setBackgroundTintList(context.getResources().getColorStateList(R.color.secondary_text));
                                            });
                                        }
                                        if(score.get() >= maxScore){
                                            isComplete.set(true);
                                            break;
                                        }
                                        Thread.sleep(1000);
                                        count.getAndIncrement();
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                                if(isComplete.get()){
                                    view.post(() -> {
                                        button.setText("Complete!");
                                        button.setBackgroundTintList(context.getResources().getColorStateList(R.color.success));
                                        user.setTest(2, true);
                                    });
                                } else {
                                    view.post(() -> {
                                        button.setText("Failure!");
                                        button.setBackgroundTintList(context.getResources().getColorStateList(R.color.danger));
                                    });
                                }
                                Database.getInstance().getHidungEvents().remove(stateEventListener);
                            });
                            task[0].start();
                            return;
                        }
                        Toast.makeText(context, "Task is running", Toast.LENGTH_SHORT).show();
                    });
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    /**
     * Class ini dibuat sebagai struktur tampilan daftar
     */
    protected class ViewHolder extends RecyclerView.ViewHolder {

        TextView nameText;
        Button[] btn_test;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.user_id);
            btn_test = new Button[]{
                    itemView.findViewById(R.id.test_1),
                    itemView.findViewById(R.id.test_2),
                    itemView.findViewById(R.id.test_3),
            };
        }
    }

}
