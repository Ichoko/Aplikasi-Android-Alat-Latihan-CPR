package me.richo.cabtraining.util;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

import me.richo.cabtraining.view_fragment.ExamPage;
import me.richo.cabtraining.view_fragment.HistoryPage;
import me.richo.cabtraining.view_fragment.TutorialPage;

/**
 * Class ini dibuat untuk sebagai penampung fragment-fragment pada tab view
 */
public class VPAdapter extends FragmentStateAdapter {

    private final List<Fragment> fragments = new ArrayList<>();


    public VPAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        fragments.add(new TutorialPage());
        fragments.add(new ExamPage());
        fragments.add(new HistoryPage());
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragments.get(position);
    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }

}
