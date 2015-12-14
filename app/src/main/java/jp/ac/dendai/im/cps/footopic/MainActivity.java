package jp.ac.dendai.im.cps.footopic;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
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

import java.io.IOException;

import jp.ac.dendai.im.cps.footopic.bean.ArticleBean;
import jp.ac.dendai.im.cps.footopic.bean.UserBean;
import jp.ac.dendai.im.cps.footopic.util.HttpPostHandler;
import jp.ac.dendai.im.cps.footopic.util.HttpPostTask;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
    ArticleListFragment.OnArticleListFragmentInteractionListener, RecyclerFragment.OnRecyclerFragmentInteractionListener {

    private static final String TAG = "Don";
    private FragmentManager manager;
    private Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mActivity = this;

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

    private void initUser(final View v) {
        HttpPostHandler postHandler = new HttpPostHandler() {
            @Override
            public void onPostCompleted(String response) {
                Log.d("onPostCompleted", "ok");
                Log.d("onPostCompleted", response);

                ObjectMapper mapper = new ObjectMapper();
                try {
                    UserBean user = new ObjectMapper().readValue(response, new TypeReference<UserBean>() {});

                    Uri uri = Uri.parse(user.getImage().getUrl());
                    SimpleDraweeView draweeView = (SimpleDraweeView) findViewById(R.id.user_thumb);
                    draweeView.setImageURI(uri);
                    ((TextView) v.findViewById(R.id.user_name)).setText(user.getScreen_name());
                    ((TextView) v.findViewById(R.id.user_mail)).setText(user.getScreen_name() + "@cps.im.dendai.ac.jp");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onPostFailed(String response) {
                Log.d("onPostFailed", "no");
                Log.d("onPostFailed", response);
            }
        };

        HttpPostTask task = new HttpPostTask(getString(R.string.url_user_show), postHandler, HttpType.Get);
        task.addPostParam("user_id", "3");
        task.execute();

    }
}
