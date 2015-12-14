package jp.ac.dendai.im.cps.footopic.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.ac.dendai.im.cps.footopic.R;
import jp.ac.dendai.im.cps.footopic.adapters.RecyclerAdapter;
import jp.ac.dendai.im.cps.footopic.entities.Article;
import jp.ac.dendai.im.cps.footopic.listeners.BottomLoadListener;
import jp.ac.dendai.im.cps.footopic.listeners.RecyclerViewOnGestureListener;
import jp.ac.dendai.im.cps.footopic.network.HttpRequest;
import jp.ac.dendai.im.cps.footopic.utils.DividerItemDecoration;
import jp.ac.dendai.im.cps.footopic.utils.SpinningProgressDialog;

/**
 * Articlesページの {@link Fragment}
 */
public class RecyclerFragment extends Fragment implements RecyclerView.OnItemTouchListener {
    private Activity mActivity = null;
    private OnRecyclerFragmentInteractionListener mListener;
    private View mView;
    private ArrayList<Article> articles;

    // RecyclerView, Adapter
    private RecyclerView mRecyclerView = null;
    private RecyclerAdapter mRecyclerAdapter = null;
    private GestureDetectorCompat detector;

    private final SpinningProgressDialog progressDialog = SpinningProgressDialog.newInstance("Loading...", "記事を読み込んでいます。");

    private Handler handler = new Handler();

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mActivity = activity;
            mListener = (OnRecyclerFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_recycler, container, false);

        // RecyclerViewを参照
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.recycler_view);

        // レイアウトマネージャを設定(ここで縦方向の標準リストであることを指定)
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mActivity));
        mRecyclerView.addOnItemTouchListener(this);
        mRecyclerView.addOnScrollListener(new BottomLoadListener((LinearLayoutManager) mRecyclerView.getLayoutManager()) {
            @Override
            public void onLoadMore(int current_page) {
                Log.d("onLoadMore", String.valueOf(current_page));
                progressDialog.show(getFragmentManager(), "DialogFragment");

                HttpRequest request = new HttpRequest() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                        Log.e("onFailure", "dame", e.fillInStackTrace());
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), "記事を読み込めませんでした\nresponse: " + request.body().toString(), Toast.LENGTH_SHORT);
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
                                    articles = new ObjectMapper().readValue(responseCode, new TypeReference<List<Article>>() {});
                                    ((RecyclerAdapter) mRecyclerView.getAdapter()).addDataOf(articles);

                                    progressDialog.dismiss();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                };

                Map<String, String> params = new HashMap<>();
                params.put("page", String.valueOf(current_page));
                params.put("per_page", "20");
                params.put("include_details", "true");
                request.setParams(params);
                request.getRecentArticleList();
            }
        });

        return mView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        progressDialog.show(getFragmentManager(), "DialogFragment");

        HttpRequest request = new HttpRequest() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.e("onFailure", "dame", e.fillInStackTrace());
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "記事を読み込めませんでした\nresponse: " + request.body().toString(), Toast.LENGTH_SHORT);
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
                            articles = new ObjectMapper().readValue(responseCode, new TypeReference<List<Article>>() {});
                            // ListViewと同じ
                            mRecyclerAdapter = new RecyclerAdapter(mActivity);

                            mRecyclerView.setAdapter(mRecyclerAdapter);

                            // Listenerの登録
                            detector = new GestureDetectorCompat(getActivity(), new RecyclerViewOnGestureListener(mRecyclerView, mListener, articles));

                            ((RecyclerAdapter) mRecyclerView.getAdapter()).clearData();
                            ((RecyclerAdapter) mRecyclerView.getAdapter()).addDataOf(articles);

                            progressDialog.dismiss();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        };

        Map<String, String> params = new HashMap<>();
        params.put("page", "1");
        params.put("per_page", "20");
        params.put("include_details", "true");
        request.setParams(params);
        request.getRecentArticleList();
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        detector.onTouchEvent(e);
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

    /**
     * {@link RecyclerView} 用のItemClickListener
     */
    public interface OnRecyclerFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onRecyclerFragmentInteraction(int position, Article article);
    }

}
