package com.example.mikhail.stockstore.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.mikhail.stockstore.Tabs.FriendsNewsTab;
import com.example.mikhail.stockstore.Tabs.allCategoriesTab;
import com.example.mikhail.stockstore.Tabs.allCompaniesTab;
import com.example.mikhail.stockstore.Tabs.allFriendsTab;
import com.example.mikhail.stockstore.Tabs.allStocksTab;

/**
 * Created by mikhail on 09.12.15.
 */
public class FriendsViewPagerAdapter extends FragmentStatePagerAdapter {
    CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created


    // Build a Constructor and assign the passed Values to appropriate values in the class
    public FriendsViewPagerAdapter(FragmentManager fm,CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);

        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;

    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0: {
                return new allFriendsTab();
            }
            default: {
                return new FriendsNewsTab();
            }
            // default: {return null;}
        }
       // return null;
    }

    // This method return the titles for the Tabs in the Tab Strip

    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    // This method return the Number of tabs for the tabs Strip

    @Override
    public int getCount() {
        return NumbOfTabs;
    }
}
