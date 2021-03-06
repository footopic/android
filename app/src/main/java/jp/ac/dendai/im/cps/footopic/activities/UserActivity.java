package jp.ac.dendai.im.cps.footopic.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.Arrays;

import jp.ac.dendai.im.cps.footopic.FragmentEnum;
import jp.ac.dendai.im.cps.footopic.R;
import jp.ac.dendai.im.cps.footopic.adapters.RecyclerViewAdapter;
import jp.ac.dendai.im.cps.footopic.entities.Article;
import jp.ac.dendai.im.cps.footopic.entities.User;
import jp.ac.dendai.im.cps.footopic.fragments.ArticleFragment;
import jp.ac.dendai.im.cps.footopic.fragments.RecyclerViewFragment;
import jp.ac.dendai.im.cps.footopic.fragments.UserInfoFragment;
import jp.ac.dendai.im.cps.footopic.fragments.ViewPagerFragment;
import jp.ac.dendai.im.cps.footopic.listeners.OnChildItemClickListener;
import jp.ac.dendai.im.cps.footopic.listeners.OnItemClickListener;
import jp.ac.dendai.im.cps.footopic.network.DonApiClient;

public class UserActivity extends AppCompatActivity
        implements UserInfoFragment.OnFragmentInteractionListener, OnItemClickListener,
        ViewPagerFragment.OnViewPagerFragmentInteractionListener, RecyclerViewFragment.RecyclerViewAction {

    private FragmentManager manager;
    private Handler handler = new Handler();
//    private final SpinningProgressDialog progressDialog = SpinningProgressDialog.newInstance("Loading...", "記事を読み込んでいます。");
    private UserActivity mActivity;
    private Toolbar toolbar;
    private int userId;
    private static final String PARAM_SERIALIZABLE = "serializable";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        Intent intent = getIntent();
        userId = intent.getIntExtra("user_id", 1);

        mActivity = this;

        manager = getSupportFragmentManager();

        toolbar = (Toolbar) findViewById(R.id.user_toolbar);
        toolbar.setTitle("丼");
        toolbar.setNavigationIcon(R.drawable.ic_navigation_back);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("onClick", "toolbar onClick");
                onBackPressed();
            }
        });

//        progressDialog.show(getSupportFragmentManager(), "DialogFragment");

        String[] titles = new String[] {"info", "articles"};
        int[] fragments = new int[] {
                FragmentEnum.UserInfo.getId(), FragmentEnum.RecyclerView.getId()};
        String userid_key = "user_id";
        String[] bundleKeys = new String[] {userid_key, ""};
        Bundle bundle = new Bundle();
        bundle.putInt(userid_key, userId);

        manager.beginTransaction()
                .replace(R.id.container, ViewPagerFragment.newInstance(titles, fragments, bundleKeys, bundle))
                .commit();

    }

    public void fragmentReplace(Fragment fragment) {
        manager.beginTransaction()
                .add(R.id.container, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onBackPressed() {
        int stack = manager.getBackStackEntryCount();
        if (stack > 0) {
            manager.popBackStack();
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
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onViewPagerFragmentInteraction(final Fragment[] fragments) {
    }

    @Override
    public void onLoadMoreAction(RecyclerView recyclerView, int current_page) {
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
                            User user = new ObjectMapper().readValue(responseCode, new TypeReference<User>(){});
                            // ListViewと同じ
                            RecyclerViewAdapter adapter = new RecyclerViewAdapter(mActivity, fragment);

                            recyclerView.setAdapter(adapter);

                            ((RecyclerViewAdapter) recyclerView.getAdapter()).clearData();
                            ((RecyclerViewAdapter) recyclerView.getAdapter()).addDataOf(Arrays.asList(user.getRecent_articles()));

//                            progressDialog.dismiss();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        };

        request.getUser(userId);
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

        }
    }
}
