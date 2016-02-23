package jp.ac.dendai.im.cps.footopic.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by naoya on 16/02/23.
 */
public class ArticlePagerAdapter extends FragmentPagerAdapter {
    private static final String TAG = ArticlePagerAdapter.class.getSimpleName();

    public ArticlePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return super.getPageTitle(position);
    }
}
