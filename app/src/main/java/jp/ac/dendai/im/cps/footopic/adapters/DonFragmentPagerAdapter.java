package jp.ac.dendai.im.cps.footopic.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import jp.ac.dendai.im.cps.footopic.fragments.RecyclerFragment;

/**
 * Created by naoya on 15/12/11.
 * TopページPagerのアダプター
 */
public class DonFragmentPagerAdapter extends FragmentPagerAdapter {

    /**
     * TopページのPagerAdapter
     * @param fm
     */
    public DonFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: {
                return new RecyclerFragment();
            }
            case 1: {
                return new RecyclerFragment();
            }
            case 2: {
                return new RecyclerFragment();
            }
            default: {
                return new RecyclerFragment();
            }
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0: {
                return "recent";
            }
            case 1: {
                return "tag";
            }
            default: {
                return "Page" + position;
            }
        }
    }
}
