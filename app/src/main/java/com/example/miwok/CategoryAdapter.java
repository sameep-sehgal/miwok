package com.example.miwok;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.Objects;

public class CategoryAdapter extends FragmentStateAdapter {

    public CategoryAdapter(FragmentActivity fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(position == 0) return new NumbersFragment();
        else if(position == 1) return new FamilyFragment();
        else if(position == 2) return new ColorsFragment();
        else return new PhrasesFragment();
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
