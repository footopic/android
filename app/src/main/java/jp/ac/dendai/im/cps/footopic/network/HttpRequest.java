package jp.ac.dendai.im.cps.footopic.network;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.Map;

/**
 * Created by naoya on 15/12/15.
 */
public abstract class HttpRequest {

    public static final String DON_SCHEME = "http";
    public static final String DON_HOST = "staging.don.cps.im.dendai.ac.jp";
    public static final String ROOT = "api";
    public static final String VERSION = "v1";
    public static final String ARTICLES = "articles";
    public static final String ARTICLES_RECENT = "recent";
    public static final String ARTICLES_SHOW = "show";
    public static final String USERS = "users";
    public static final String USERS_SHOW = "show";

    private OkHttpClient client = new OkHttpClient();
    private Request request;
    private Map<String, String> params;
    private Map<String, String> requestBodies;
    private HttpRequest own;

    public HttpRequest() {
        own = this;
    }

    public void getArticleList() {
        enqueue(new String[] {ARTICLES});
    }

    public void getRecentArticleList() {
        enqueue(new String[] {ARTICLES, ARTICLES_RECENT});
    }

    public void getArticle() {
        enqueue(new String[] {ARTICLES, ARTICLES_SHOW});
    }

    public void getUserList() {
        enqueue(new String[] {USERS});
    }

    public void getUser() {
        enqueue(new String[] {USERS, USERS_SHOW});
    }

    private void enqueue(String[] pathes) {
        HttpUrl.Builder urlBuilder = new HttpUrl.Builder()
                .scheme(DON_SCHEME)
                .host(DON_HOST)
                .addPathSegment(ROOT)
                .addPathSegment(VERSION);

        for (String str : pathes) {
            urlBuilder.addPathSegment(str);
        }

        if (params != null) {
            for (String key : params.keySet()) {
                urlBuilder.addQueryParameter(key, params.get(key));
            }
        }

        request = new Request.Builder()
                .url(urlBuilder.build())
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

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public abstract void onFailure(Request request, IOException e);
    public abstract void onResponse(Response response) throws IOException;

}
