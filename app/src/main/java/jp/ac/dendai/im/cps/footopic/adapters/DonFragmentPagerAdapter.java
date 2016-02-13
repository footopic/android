package jp.ac.dendai.im.cps.footopic.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by naoya on 15/12/11.
 * TopページPagerのアダプター
 */
public class DonFragmentPagerAdapter extends FragmentPagerAdapter {

    private Fragment[] fragments;
    private String[] pageTitles;

    /**
     * TopページのPagerAdapter
     * @param fm
     */
    public DonFragmentPagerAdapter(FragmentManager fm, Fragment[] fragments, String[] pageTitles) {
        super(fm);

        this.fragments = fragments;
        this.pageTitles = pageTitles;
    }

    @Override
    public Fragment getItem(int position) {
        if (position >= fragments.length) {
            return null;
        } else {
            return fragments[position];
        }
    }

    @Override
    public int getCount() {
        return fragments.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position >= pageTitles.length) {
            return null;
        } else {
            return pageTitles[position];
        }
    }
}
