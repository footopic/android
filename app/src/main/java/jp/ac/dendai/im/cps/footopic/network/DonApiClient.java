package jp.ac.dendai.im.cps.footopic.network;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.Map;

public abstract class DonApiClient {

    public static String ARTICLE_PER_PAGE = "20";
    private OkHttpClient client = new OkHttpClient();
    private Request request;
    private Map<String, String> params;
    private Map<String, String> requestBodies;
    private DonApiClient own;

    public DonApiClient() {
        own = this;
    }

    public void getArticleList(int page, boolean includeDetails) {
        HttpUrl.Builder builder = DonUrlBuilder.buildArticlesUrl();

        builder.addQueryParameter("page", String.valueOf(page));
        builder.addQueryParameter("per_page", ARTICLE_PER_PAGE);
        if (includeDetails) {
            builder.addQueryParameter("include_details", "true");
        }
        enqueue(builder);
    }

    public void getRecentArticleList(int page, boolean includeDetails) {
        HttpUrl.Builder builder = DonUrlBuilder.buildArticlesRecentUrl();

        builder.addQueryParameter("page", String.valueOf(page));
        builder.addQueryParameter("per_page", ARTICLE_PER_PAGE);
        if (includeDetails) {
            builder.addQueryParameter("include_details", "true");
        }
        enqueue(builder);
    }

    public void getRecentArticleList(int page) {
        getRecentArticleList(page, false);
    }

    public void getRecentArticleList(boolean includeDetails) {
        getRecentArticleList(1, includeDetails);
    }

    public void getRecentArticleList() {
        getRecentArticleList(1, false);
    }


    public void getArticle(int articleId) {
        HttpUrl.Builder builder = DonUrlBuilder.buildArticlesShowUrl();

        builder.addQueryParameter("id", String.valueOf(articleId));
        enqueue(builder);
    }

    public void getUserList(boolean includeDetails) {
        HttpUrl.Builder builder = DonUrlBuilder.buildUsersUrl();

        if (includeDetails) {
            builder.addQueryParameter("include_details", "true");
        }
        enqueue(builder);
    }

    public void getUser(int userId) {
        HttpUrl.Builder builder = DonUrlBuilder.buildUsersShowUrl();
        builder.addQueryParameter("user_id", String.valueOf(userId));
        enqueue(builder);
    }

    public void getUser(String uid) {
        HttpUrl.Builder builder = DonUrlBuilder.buildUsersShowUrl();
        builder.addQueryParameter("uid", uid);
        enqueue(builder);
    }

    private void enqueue(HttpUrl.Builder builder) {
        request = new Request.Builder()
                .url(builder.build())
                .get().build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                own.onFailure(request, e);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                own.onResponse(response);
            }
        });
    }

    public abstract void onFailure(Request request, IOException e);

    public abstract void onResponse(Response response) throws IOException;

}

