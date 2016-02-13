package jp.ac.dendai.im.cps.footopic.network;

import com.squareup.okhttp.HttpUrl;

/**
 * Created by hiro on 12/15/15.
 */
public class DonUrlBuilder {
    public static final String DON_SCHEME = "http";
    public static final String DON_HOST = "staging.don.cps.im.dendai.ac.jp";
    public static final String ROOT = "api";
    public static final String VERSION = "v1";
    public static final String ARTICLES = "articles";
    public static final String ARTICLES_RECENT = "recent";
    public static final String ARTICLES_SHOW = "show";
    public static final String USERS = "users";
    public static final String USERS_SHOW = "show";

    /**
     * /api/v1/
     * @return
     */
    public static HttpUrl.Builder buildRootUrl() {
        return new HttpUrl.Builder()
                .scheme(DON_SCHEME)
                .host(DON_HOST)
                .addPathSegment(ROOT)
                .addPathSegment(VERSION);
    }

    /**
     * /api/v1/articles
     *
     * @return
     */
    public static HttpUrl.Builder buildArticlesUrl() {
        return buildRootUrl().addPathSegment(ARTICLES);
    }

    /**
     * /api/v1/articles/recent
     *
     * @return
     */
    public static HttpUrl.Builder buildArticlesRecentUrl() {
        return buildArticlesUrl().addPathSegment(ARTICLES_RECENT);
    }

    /**
     * /api/v1/articles/show
     *
     * @return
     */
    public static HttpUrl.Builder buildArticlesShowUrl() {
        return buildArticlesUrl().addPathSegment(ARTICLES_SHOW);
    }

    /**
     * /api/v1/users
     *
     * @return
     */
    public static HttpUrl.Builder buildUsersUrl() {
        return buildRootUrl().addPathSegment(USERS);
    }

    /**
     * /api/v1/users/show
     *
     * @return
     */
    public static HttpUrl.Builder buildUsersShowUrl() {
        return buildUsersUrl().addPathSegment(USERS_SHOW);
    }
}
