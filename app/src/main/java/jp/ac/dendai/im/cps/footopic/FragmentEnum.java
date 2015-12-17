package jp.ac.dendai.im.cps.footopic;

import android.support.v4.app.Fragment;

import jp.ac.dendai.im.cps.footopic.fragments.ArticleFragment;
import jp.ac.dendai.im.cps.footopic.fragments.RecyclerViewFragment;
import jp.ac.dendai.im.cps.footopic.fragments.UserInfoFragment;
import jp.ac.dendai.im.cps.footopic.fragments.ViewPagerFragment;

/**
 * Created by naoya on 15/12/17.
 */
public enum FragmentEnum {
    Article(0),
    RecyclerView(1),
    UserInfo(2),
    ViewPager(3);

    private final int id;

    private FragmentEnum(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static Fragment getEmptyInstance(int id) {
        switch (id) {
            case 0: {
                return new ArticleFragment();
            }
            case 1: {
                return new RecyclerViewFragment();
            }
            case 2: {
                return new UserInfoFragment();
            }
            case 3: {
                return new ViewPagerFragment();
            }
            default: {
                return null;
            }
        }
    }
}
