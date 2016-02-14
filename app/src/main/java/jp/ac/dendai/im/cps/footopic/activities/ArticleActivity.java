package jp.ac.dendai.im.cps.footopic.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import jp.ac.dendai.im.cps.footopic.FragmentEnum;
import jp.ac.dendai.im.cps.footopic.R;
import jp.ac.dendai.im.cps.footopic.fragments.ViewPagerFragment;

public class ArticleActivity extends AppCompatActivity {
    private static final String TAG = ArticleActivity.class.getSimpleName();
    private FragmentManager manager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);


        Bundle args = new Bundle();
        String[] titles = new String[] {"article", "comment", "edit"};
        int[] fragments = new int[] {FragmentEnum.RecyclerView.getId(), FragmentEnum.RecyclerView.getId()};
        manager = getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.container, ViewPagerFragment.newInstance(titles, fragments, null, null))
                .commit();
    }
}
