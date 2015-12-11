package jp.ac.dendai.im.cps.footopic;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

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
                break;
            }
            case 1: {
                break;
            }
            case 2: {
                break;
            }
        }
    }

    @Override
    public int getCount() {
        return 0;
    }
}
