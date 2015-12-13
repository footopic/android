package jp.ac.dendai.im.cps.footopic.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import jp.ac.dendai.im.cps.footopic.RecyclerFragment;

/**
 * Created by naoya on 15/12/11.
 */
public class DonFragmentPagerAdapter extends FragmentPagerAdapter {

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
//        return 1;
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "Articles";
    }
}
