package jp.ac.dendai.im.cps.footopic;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import jp.ac.dendai.im.cps.footopic.entities.Article;
import jp.ac.dendai.im.cps.footopic.entities.User;
import jp.ac.dendai.im.cps.footopic.fragments.ArticleFragment;
import jp.ac.dendai.im.cps.footopic.fragments.ArticleListFragment;
import jp.ac.dendai.im.cps.footopic.fragments.RecyclerFragment;
import jp.ac.dendai.im.cps.footopic.network.DonApiClient;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        ArticleListFragment.OnArticleListFragmentInteractionListener, RecyclerFragment.OnRecyclerFragmentInteractionListener {

    private static final String TAG = "Don";
    private FragmentManager manager;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        initUser(navigationView);

        manager = getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.container, ArticleListFragment.newInstance(manager))
                .commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_articles: {
                setTitle("Articles");
                break;
            }
            case R.id.nav_tags: {
                setTitle("Tags");
                break;
            }
            case R.id.nav_members: {
                setTitle("Members");
                break;
            }
            case R.id.nav_settings: {
                setTitle("Settings");
                break;
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onArticleListFragmentInteraction(Uri uri) {
    }

    @Override
    public void onRecyclerFragmentInteraction(int position, int articleId) {
        Log.d(TAG, "MainActivity onRecycler position: " + position);

        DonApiClient request = new DonApiClient() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.e("onFailure", "dame", e.fillInStackTrace());
            }

            @Override
            public void onResponse(Response response) throws IOException {
                final String responseCode = response.body().string();

                Log.d("onPostCompleted", "ok");
                Log.d("onPostCompleted", responseCode);

                Log.d("onPostCompleter", response.request().toString());

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Article article = new ObjectMapper().readValue(responseCode, new TypeReference<Article>(){});
                            fragmentReplace(ArticleFragment.newInstance(article));

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        };

        request.getArticle(articleId);
    }

    public void fragmentReplace(Fragment fragment) {
        manager.beginTransaction()
                .add(R.id.container, fragment)
                .addToBackStack(null)
                .commit();
    }

    /**
     * {@link NavigationView} に表示するUserの初期化
     * @param v
     */
    private void initUser(final View v) {
        DonApiClient request = new DonApiClient() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.e("onFailure", "dame", e.fillInStackTrace());
            }

            @Override
            public void onResponse(Response response) throws IOException {
                final String responseCode = response.body().string();

                Log.d("onPostCompleted", "ok");
                Log.d("onPostCompleted", responseCode);

                Log.d("onPostCompleter", response.request().toString());

                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            final User user = new ObjectMapper().readValue(responseCode, new TypeReference<User>() {
                            });

                            final Uri uri = Uri.parse(user.getImage().getUrl());

                            ((SimpleDraweeView) v.findViewById(R.id.user_thumb)).setImageURI(uri);
                            ((TextView) v.findViewById(R.id.user_name)).setText(user.getScreen_name());
                            ((TextView) v.findViewById(R.id.user_mail)).setText(user.getScreen_name() + "@cps.im.dendai.ac.jp");

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        };

        request.getUser(3);
    }
}
