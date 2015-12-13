package jp.ac.dendai.im.cps.footopic;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;

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
    public void onRecyclerFragmentInteraction(int position, MotionEvent e) {
        Log.d(TAG, "MainActivity onRecycler position: " + position + "\nMotion: " + e.toString());
    }
}
