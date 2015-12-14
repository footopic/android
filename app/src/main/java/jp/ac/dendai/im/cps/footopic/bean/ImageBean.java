package jp.ac.dendai.im.cps.footopic.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jp.ac.dendai.im.cps.footopic.R;
import jp.ac.dendai.im.cps.footopic.util.App;

/**
 * Created by naoya on 15/12/12.
 * サムネイルの画像のURLの構造
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class ImageBean {
    private String url;
    private String thumb_url;

    public String getUrl() {
        return App.getInstance().getString(R.string.url_root) + url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumb_url() {
        return App.getInstance().getString(R.string.url_root) + thumb_url;
    }

    public void setThumb_url(String thumb_url) {
        this.thumb_url = thumb_url;
    }

    @Override
    public String toString() {
        String str = "url: " + url + "\n" +
                "thumb_url: " + thumb_url;

        return str;
    }
}
