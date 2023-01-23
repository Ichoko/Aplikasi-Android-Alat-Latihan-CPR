package me.richo.cabtraining;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;

import me.richo.cabtraining.util.HistoryManager;
import me.richo.cabtraining.util.VPAdapter;

/**
 * MainActivity ini merupakan entry point dari aplikasi
 */
public class MainActivity extends AppCompatActivity {

    private static String TAG = "MainActivity";

    /**
     * Fungsi akan dipanggil ketika Activity dibuat
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Inisialisasi Activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Inisialisasi History Manager
        HistoryManager.createInstance(this);

        //Inisialisasi TabLayout
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPager2 viewPager = findViewById(R.id.viewpager);
        VPAdapter vpAdapter = new VPAdapter(this);
        viewPager.setAdapter(vpAdapter);
        viewPager.setUserInputEnabled(false);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {

            @Override
            public void onPageSelected(int position) {
                tabLayout.getTabAt(position).select();
            }

        });
    }

}