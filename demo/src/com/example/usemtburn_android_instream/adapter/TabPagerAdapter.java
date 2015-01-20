package com.example.usemtburn_android_instream.adapter;

import com.example.usemtburn_android_instream.Tab;
import com.example.usemtburn_android_instream.fragment.ListFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;

public class TabPagerAdapter extends FragmentPagerAdapter {

    private Tab[] tabs;

    public TabPagerAdapter(FragmentManager fragmentManager, Tab[] tabs) {
        super(fragmentManager);
        
        this.tabs = tabs;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs[position].getTitle();
    }

    @Override
    public int getCount() {
        return tabs.length;
    }

    @Override
    public Fragment getItem(int position) {
        return ListFragment.newInstance(position, tabs[position].getUrl());
    }
    
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (position >= getCount()) {
            FragmentManager fragmentManager = ((Fragment) object).getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove((Fragment) object);
            fragmentTransaction.commit();
        }
        super.destroyItem(container, position, object);
    }
    
    public Tab getTabAt(int position) {
        return tabs[position];   
    }
}
