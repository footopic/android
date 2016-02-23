package jp.ac.dendai.im.cps.footopic.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import jp.ac.dendai.im.cps.footopic.R;
import jp.ac.dendai.im.cps.footopic.fragments.ArticlePagerFragment;

public class ArticleActivity extends AppCompatActivity {

    private static final String TAG = ArticleActivity.class.getSimpleName();
    private FragmentManager manager;
    private Handler handler = new Handler();
    private MainActivity mActivity;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        int articleId = getIntent().getIntExtra("article_id", -1);
        if (articleId < 0){
            finish();
        }

        manager = getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.article_container, ArticlePagerFragment.newInstance(articleId))
                .commit();
    }

}
