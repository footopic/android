package jp.ac.dendai.im.cps.footopic.util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import jp.ac.dendai.im.cps.footopic.HttpType;

/**
 * Created by naoya on 2015/08/23.
 */
public class HttpPostTask extends AsyncTask<Void, Integer, Void> {

    private static String LOG_CODE = "HttpPostTask";
    private static String HTTP_POST_SUCCESS = "http_post_success";
    private static String HTTP_RESPONSE = "http_response";

    //設定
    private String request_encodeing = "UTF-8";
    private String response_encodeing = "UTF-8";

    //初期化
    private Activity parent_activity = null;
    private String post_url = null;
    private Handler ui_hander = null;
    private List<NameValuePair> post_parms = null;

    //処理中に使用するメンバ
    private ResponseHandler<Void> response_handler = null;
    private String http_err_msg = null;
    private String http_ret_msg = null;
    private ProgressDialog dialog = null;

    private HttpType requestType;

    public HttpPostTask(String post_url, Handler ui_handler, HttpType type) {
        this.post_url = post_url;
        this.ui_hander = ui_handler;
        this.requestType = type;

        //送信パラメータは初期化せずに、newの後にsetさせる
        post_parms = new ArrayList<NameValuePair>();
    }

    @Override
    protected Void doInBackground(Void... params) {
        Log.d(LOG_CODE, "post task start");

        URI uri = null;
        try {
            uri = new URI(post_url);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            http_err_msg = "Illegal url";
            return null;
        }

        HttpUriRequest request = null;
        if (requestType == HttpType.Post) {
            //POSTパラメータ付きでPOSTリクエストを構築
            HttpPost httpPost = new HttpPost(uri);
            try {
                httpPost.setEntity(new UrlEncodedFormEntity(post_parms, request_encodeing));
                Log.d(LOG_CODE, httpPost.getURI().toString());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                http_err_msg = "Ilegal Char Code";
                return null;
            }
            request = httpPost;
        } else {
            //GETパラメータ付きでGETリクエストを構築
            String query = URLEncodedUtils.format(post_parms, "UTF-8");
            HttpGet httpGet = new HttpGet(uri + "?" + query);
            request = httpGet;
        }


        //Postリクエスト実行
        DefaultHttpClient httpClient = new DefaultHttpClient();
        Log.d(LOG_CODE, "Post start");
        try {
            httpClient.execute(request, response_handler);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            http_err_msg = "protocol error";
        } catch (IOException e) {
            e.printStackTrace();
            http_err_msg = "IO error";
        }

        //shutdownすると通信できなくなる
        httpClient.getConnectionManager().shutdown();

        return null;
    }

    /**
     * ポストパラメータ付与
     * @param post_name
     * @param post_value
     */
    public void addPostParam(String post_name, String post_value) {
        post_parms.add(new BasicNameValuePair(post_name, post_value));
    }

    /**
     * タスク開始時
     */
    @Override
    protected void onPreExecute() {
        //ダイアログを表示
//        dialog = new ProgressDialog(parent_activity);
//        dialog.setMessage("送信中...");
//        dialog.show();

        //レスポンスハンドラを生成
        response_handler = new ResponseHandler<Void>() {
            @Override
            public Void handleResponse(HttpResponse httpResponse) throws ClientProtocolException, IOException {
                Log.d(LOG_CODE, "レスポンスコード : " + httpResponse.getStatusLine().getStatusCode());

                /*
                BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
                String str;
                while ((str = reader.readLine()) != null) {
                    Log.d(LOG_CODE, str);
                }
                */


                switch (httpResponse.getStatusLine().getStatusCode()) {
                    case HttpStatus.SC_OK: {
                        Log.d(LOG_CODE, "200");

                        HttpPostTask.this.http_ret_msg = EntityUtils.toString(
                                httpResponse.getEntity(),
                                HttpPostTask.this.response_encodeing
                        );
                        break;
                    }
                    case HttpStatus.SC_CREATED: {
                        Log.d(LOG_CODE, "201");

                        HttpPostTask.this.http_ret_msg = EntityUtils.toString(
                                httpResponse.getEntity(),
                                HttpPostTask.this.response_encodeing
                        );
                        break;
                    }
                    case HttpStatus.SC_NOT_FOUND: {
                        //404
                        Log.d(LOG_CODE, "404");
                        HttpPostTask.this.http_err_msg = EntityUtils.toString(
                                httpResponse.getEntity(),
                                HttpPostTask.this.response_encodeing
                        );
                        break;
                    }
                    default: {
                        Log.d(LOG_CODE, "通信エラー");
                        HttpPostTask.this.http_err_msg = EntityUtils.toString(
                                httpResponse.getEntity(),
                                HttpPostTask.this.response_encodeing
                        );
                    }
                }

                return null;
            }
        };
    }

    /**
     * タスク終了時処理
     * @param unused
     */
    @Override
    protected void onPostExecute(Void unused) {
        //ダイアログ削除
//        dialog.dismiss();

        //受信結果をUIに渡すために整形
        Message message = new Message();
        Bundle bundle = new Bundle();
        if (http_err_msg != null) {
            //エラー発生時
            bundle.putBoolean(HTTP_POST_SUCCESS, false);
            bundle.putString(HTTP_RESPONSE, http_err_msg);
        } else {
            //通信成功時
            bundle.putBoolean(HTTP_POST_SUCCESS, true);
            bundle.putString(HTTP_RESPONSE, http_ret_msg);
        }

        message.setData(bundle);

        ui_hander.sendMessage(message);
    }
}
