package jp.ac.dendai.im.cps.footopic.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.Arrays;

import jp.ac.dendai.im.cps.footopic.R;
import jp.ac.dendai.im.cps.footopic.adapters.CommentRecyclerAdapter;
import jp.ac.dendai.im.cps.footopic.entities.Article;
import jp.ac.dendai.im.cps.footopic.fragments.ArticlePagerFragment;
import jp.ac.dendai.im.cps.footopic.fragments.CommentFragment;
import jp.ac.dendai.im.cps.footopic.network.DonApiClient;

public class ArticleActivity extends AppCompatActivity
        implements ArticlePagerFragment.OnArticlePagerInteractionListener, CommentFragment.OnCommentFragmentInteractionListener {

    private static final String TAG = ArticleActivity.class.getSimpleName();
    private FragmentManager manager;
    private Handler handler = new Handler();
    private Article article;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        int articleId = getIntent().getIntExtra("article_id", -1);
        if (articleId < 0){
            finish();
        }

        DonApiClient client = new DonApiClient() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.e(TAG, "onFailure: dame", e.fillInStackTrace());
            }

            @Override
            public void onResponse(Response response) throws IOException {
                final String responseCode = response.body().string();

                Log.d(TAG, "onPostCompleted ok");
                Log.d(TAG, responseCode);

                article = new ObjectMapper().readValue(responseCode, new TypeReference<Article>() {});

                manager = getSupportFragmentManager();
                manager.beginTransaction()
                        .replace(R.id.article_container, ArticlePagerFragment.newInstance(article))
                        .commit();
            }
        };
        client.getArticle(articleId);
    }

    @Override
    public void onArticlePagerInteraction() {
    }

    @Override
    public void onActivityCreated(RecyclerView recyclerView) {
        ((CommentRecyclerAdapter) recyclerView.getAdapter())
                .addDataOf(Arrays.asList(article.getComments()));
    }
}
