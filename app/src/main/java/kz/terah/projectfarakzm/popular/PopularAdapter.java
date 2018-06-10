package kz.terah.projectfarakzm.popular;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import kz.terah.projectfarakzm.models.Popular;

public class PopularAdapter extends FragmentPagerAdapter {
    private List<Popular> popularList = new ArrayList<>();

    public PopularAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setPopularList(List<Popular> popularList) {
        this.popularList.clear();
        this.popularList.addAll(popularList);
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return PopularFragment.newInstance(popularList.get(position));
    }

    @Override
    public int getCount() {
        return popularList.size();
    }
}
