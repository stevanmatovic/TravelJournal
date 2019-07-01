package com.example.traveljournal.adapter;

import android.content.Context;

import com.example.traveljournal.fragment.DocumentsFragment;
import com.example.traveljournal.fragment.MemoriesFragment;
import com.example.traveljournal.fragment.PlanningFragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class TripFragmentPagerAdapter extends FragmentPagerAdapter {

    private Context context;

    public TripFragmentPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new PlanningFragment();
        } else if (position == 1){
            return new MemoriesFragment();
        } else {
            return new DocumentsFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        switch (position) {
            case 0:
                return "Planning";
            case 1:
                return "Memories";
            case 2:
                return "Documents";
            default:
                return null;
        }
    }

}

