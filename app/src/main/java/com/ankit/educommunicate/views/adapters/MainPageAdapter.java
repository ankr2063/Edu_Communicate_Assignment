package com.ankit.educommunicate.views.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.ankit.educommunicate.views.fragments.InboxFragment;
import com.ankit.educommunicate.views.fragments.OutboxFragment;

public class MainPageAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;

    public MainPageAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                InboxFragment tab1 = new InboxFragment();
                return tab1;
            case 1:
                OutboxFragment tab2 = new OutboxFragment();
                return tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
