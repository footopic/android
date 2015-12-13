package jp.ac.dendai.im.cps.footopic;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import jp.ac.dendai.im.cps.footopic.bean.ArticleBean;

public class MainActivity extends AppCompatActivity implements
        ArticleListFragment.OnArticleListFragmentInteractionListener, RecyclerFragment.OnRecyclerFragmentInteractionListener {

    private static final String TAG = "Don";

    private FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager = getSupportFragmentManager();

        manager.beginTransaction()
                .replace(R.id.container, ArticleListFragment.newInstance(manager))
                .commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onArticleListFragmentInteraction(Uri uri) {

    }

    @Override
    public void onRecyclerFragmentInteraction(int position, ArticleBean article) {
        Log.d(TAG, "MainActivity onRecycler position: " + position);

        fragmentReplace(ArticleFragment.newInstance(article));
    }

    public void fragmentReplace(Fragment fragment) {
        manager.beginTransaction()
                .add(R.id.container, fragment)
                .addToBackStack(null)
                .commit();
    }
}
