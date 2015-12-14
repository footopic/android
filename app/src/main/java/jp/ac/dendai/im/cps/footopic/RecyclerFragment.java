package jp.ac.dendai.im.cps.footopic;

import android.app.Activity;
import android.os.Bundle;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jp.ac.dendai.im.cps.footopic.Listener.BottomLoadListener;
import jp.ac.dendai.im.cps.footopic.Listener.RecyclerViewOnGestureListener;
import jp.ac.dendai.im.cps.footopic.adapter.RecyclerAdapter;
import jp.ac.dendai.im.cps.footopic.bean.ArticleBean;
import jp.ac.dendai.im.cps.footopic.util.DividerItemDecoration;
import jp.ac.dendai.im.cps.footopic.util.HttpPostHandler;
import jp.ac.dendai.im.cps.footopic.util.HttpPostTask;
import jp.ac.dendai.im.cps.footopic.util.SpinningProgressDialog;

public class RecyclerFragment extends Fragment implements RecyclerView.OnItemTouchListener {
    private Activity mActivity = null;
    private OnRecyclerFragmentInteractionListener mListener;
    private View mView;
    private ArrayList<ArticleBean> articles;

    // RecyclerView, Adapter
    private RecyclerView mRecyclerView = null;
    private RecyclerAdapter mRecyclerAdapter = null;
    private GestureDetectorCompat detector;

    private final SpinningProgressDialog progressDialog = SpinningProgressDialog.newInstance("Loading...", "記事を読み込んでいます。");

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
                HttpPostHandler postHandler = new HttpPostHandler() {
                    @Override
                    public void onPostCompleted(String response) {
                        Log.d("onPostCompleted", "ok");
                        Log.d("onPostCompleted", response);

                        try {
                            articles = new ObjectMapper().readValue(response, new TypeReference<List<ArticleBean>>() {});
                            ((RecyclerAdapter) mRecyclerView.getAdapter()).addDataOf(articles);

                            progressDialog.dismiss();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onPostFailed(String response) {
                        Log.d("onPostFailed", "no");
                        Log.d("onPostFailed", response);

                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), "記事を読み込めませんでした\nresponse: " + response, Toast.LENGTH_SHORT);
                    }
                };

                HttpPostTask task = new HttpPostTask(getString(R.string.url_article_recent), postHandler, HttpType.Get);
                task.addPostParam("page", String.valueOf(current_page));
                task.addPostParam("per_page", "20");
                task.addPostParam("include_details", "true");
                task.execute();
            }
        });

        return mView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        progressDialog.show(getFragmentManager(), "DialogFragment");

        HttpPostHandler postHandler = new HttpPostHandler() {
            @Override
            public void onPostCompleted(String response) {
                Log.d("onPostCompleted", "ok");
                Log.d("onPostCompleted", response);

                try {
                    articles = new ObjectMapper().readValue(response, new TypeReference<List<ArticleBean>>() {});
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

            @Override
            public void onPostFailed(String response) {
                Log.d("onPostFailed", "no");
                Log.d("onPostFailed", response);

                progressDialog.dismiss();
                Toast.makeText(getActivity(), "記事を読み込めませんでした\nresponse: " + response, Toast.LENGTH_SHORT);
            }
        };

        HttpPostTask task = new HttpPostTask(getString(R.string.url_article_recent), postHandler, HttpType.Get);
        task.addPostParam("page", "1");
        task.addPostParam("per_page", "20");
        task.addPostParam("include_details", "true");
        task.execute();
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
     *
     */
    public interface OnRecyclerFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onRecyclerFragmentInteraction(int position, ArticleBean article);
    }

}
