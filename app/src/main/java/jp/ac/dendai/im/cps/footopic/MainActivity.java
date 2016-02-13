package jp.ac.dendai.im.cps.footopic;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jp.ac.dendai.im.cps.footopic.adapters.RecyclerAdapter;
import jp.ac.dendai.im.cps.footopic.entities.Article;
import jp.ac.dendai.im.cps.footopic.entities.User;
import jp.ac.dendai.im.cps.footopic.fragments.ArticleFragment;
import jp.ac.dendai.im.cps.footopic.fragments.RecyclerViewFragment;
import jp.ac.dendai.im.cps.footopic.fragments.ViewPagerFragment;
import jp.ac.dendai.im.cps.footopic.listeners.OnChildItemClickListener;
import jp.ac.dendai.im.cps.footopic.listeners.OnItemClickListener;
import jp.ac.dendai.im.cps.footopic.network.DonApiClient;
import jp.ac.dendai.im.cps.footopic.utils.App;
import jp.ac.dendai.im.cps.footopic.utils.SpinningProgressDialog;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, RecyclerViewFragment.RecyclerViewAction,
        ViewPagerFragment.OnViewPagerFragmentInteractionListener, OnItemClickListener {

    private static final String TAG = "MainActivity";
    private FragmentManager manager;
    private Toolbar toolbar;
    private Handler handler = new Handler();
    private MainActivity mActivity;
//    private final SpinningProgressDialog progressDialog = SpinningProgressDialog.newInstance("Loading...", "記事を読み込んでいます。");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mActivity = this;

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Articles");
        toolbar.setNavigationIcon(R.drawable.ic_action_image_dehaze);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int stack = manager.getBackStackEntryCount();
                if (stack == 0) {
                    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                    drawer.openDrawer(GravityCompat.START);
                } else {
                    onBackPressed();
                }
            }
        });

        Bundle args = new Bundle();
        String[] titles = new String[] {"recent", "tag"};
        int[] fragments = new int[] {FragmentEnum.RecyclerView.getId(), FragmentEnum.RecyclerView.getId()};
        manager = getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.container, ViewPagerFragment.newInstance(titles, fragments, null, null))
                .commit();
    }

    @Override
    protected void onStart() {
        super.onStart();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_articles);
        initUser(navigationView);
    }

    @Override
    public void onBackPressed() {
        int stack = manager.getBackStackEntryCount();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (stack == 1) {
            manager.popBackStack();
            toolbar.setNavigationIcon(R.drawable.ic_action_image_dehaze);
        } else if (stack > 1) {
            manager.popBackStack();
        } else if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

//        int stack = manager.getBackStackEntryCount();
//
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else if (stack == 1){
//            manager.popBackStack();
//            toolbar.setNavigationIcon(R.drawable.ic_action_image_dehaze);
//        } else if (stack > 1){
//            manager.popBackStack();
//        } else {
//            super.onBackPressed();
//        }
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

    public void fragmentReplace(Fragment fragment) {
        manager.beginTransaction()
                .add(R.id.container, fragment)
                .addToBackStack(null)
                .commit();
        toolbar.setNavigationIcon(R.drawable.ic_navigation_back);
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
                            Log.e(TAG, "initUser", e.fillInStackTrace());
                            Toast.makeText(App.getInstance(), "Userを読み込めませんでした。", Toast.LENGTH_SHORT).show();
                        } catch (NullPointerException e) {
                            Log.e(TAG, "initUser", e.fillInStackTrace());
                            Toast.makeText(App.getInstance(), "Userを読み込めませんでした。", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        };

        request.getUser(3);
    }

    @Override
    public void onItemClick(int id, OnChildItemClickListener.ItemType type) {
        if (type ==  OnChildItemClickListener.ItemType.Article) {
            DonApiClient client = new DonApiClient() {
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

            client.getArticle(id);

        } else if (type == OnChildItemClickListener.ItemType.Member) {
//            toolbar.setNavigationIcon(R.drawable.ic_navigation_back);
            Log.d("Activity onItemClick", "member id: " + String.valueOf(id));

            // Supply num input as an argument.
            Intent intent = new Intent(MainActivity.this, UserActivity.class);
            intent.putExtra("user_id", id);
            startActivity(intent);
        }
    }

    @Override
    public void onViewPagerFragmentInteraction(Fragment[] fragments) {
        // TODO: listener
    }

    @Override
    public void onLoadMoreAction(final RecyclerView recyclerView, int current_page) {
        Log.d("onLoadMore", String.valueOf(current_page));
//        progressDialog.show(getSupportFragmentManager(), "DialogFragment");

        DonApiClient request = new DonApiClient() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.e("onFailure", "dame", e.fillInStackTrace());
//                progressDialog.dismiss();
                Toast.makeText(mActivity, "記事を読み込めませんでした\nresponse: " + request.body().toString(), Toast.LENGTH_SHORT);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                final String responseCode = response.body().string();

                Log.d("onPostCompleted", "ok");
                Log.d("onPostCompleted", responseCode);

                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            ArrayList<Article> articles = new ObjectMapper().readValue(responseCode, new TypeReference<List<Article>>(){});
                            ((RecyclerAdapter) recyclerView.getAdapter()).addDataOf(articles);

//                            progressDialog.dismiss();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        };

        request.getRecentArticleList(current_page);
    }

    @Override
    public void onActivityCreatedAction(final RecyclerView recyclerView, final RecyclerViewFragment fragment) {
//        progressDialog.show(getSupportFragmentManager(), "DialogFragment");

        DonApiClient request = new DonApiClient() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.e("onFailure", "dame", e.fillInStackTrace());
//                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "記事を読み込めませんでした\nresponse: " + request.body().toString(), Toast.LENGTH_SHORT);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                final String responseCode = response.body().string();

                Log.d("onPostCompleted", "ok");
                Log.d("onPostCompleted", responseCode);

                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            ArrayList<Article> arrays = new ObjectMapper().readValue(responseCode, new TypeReference<List<Article>>(){});
                            // ListViewと同じ
                            RecyclerAdapter adapter = new RecyclerAdapter(mActivity, fragment);

                            recyclerView.setAdapter(adapter);

                            ((RecyclerAdapter) recyclerView.getAdapter()).clearData();
                            ((RecyclerAdapter) recyclerView.getAdapter()).addDataOf(arrays);

//                            progressDialog.dismiss();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        };

        request.getRecentArticleList(true);
    }

    void showDialog(String title, String message) {
        // DialogFragment.show() will take care of adding the fragment
        // in a transaction.  We also want to remove any currently showing
        // dialog, so make our own transaction and take care of that here.
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        DialogFragment newFragment = SpinningProgressDialog.newInstance(title, message);
        newFragment.show(ft, "dialog");
    }
}
