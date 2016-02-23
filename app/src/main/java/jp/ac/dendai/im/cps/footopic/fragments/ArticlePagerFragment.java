package jp.ac.dendai.im.cps.footopic.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import jp.ac.dendai.im.cps.footopic.R;
import jp.ac.dendai.im.cps.footopic.adapters.DonFragmentPagerAdapter;
import jp.ac.dendai.im.cps.footopic.entities.Article;
import jp.ac.dendai.im.cps.footopic.network.DonApiClient;

public class ArticlePagerFragment extends Fragment {

    private static final String TAG = ArticlePagerFragment.class.getSimpleName();

    private OnFragmentInteractionListener mListener;

    private static final String PARAM_ARTICLE_ID = "article_id";
    private int paramArticleId;
    private Handler handler = new Handler();

    public ArticlePagerFragment() {
        // Required empty public constructor
    }

    public static ArticlePagerFragment newInstance(int articleId) {
        ArticlePagerFragment fragment = new ArticlePagerFragment();
        Bundle args = new Bundle();
        args.putInt(PARAM_ARTICLE_ID, articleId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            paramArticleId = getArguments().getInt(PARAM_ARTICLE_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_article_pager, container, false);
        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        final TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);

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

                Article article = new ObjectMapper().readValue(responseCode, new TypeReference<Article>() {
                });
                ArticleFragment articleFragment = new ArticleFragment().newInstance(article);
                ArticleFragment articleFragment2 = new ArticleFragment().newInstance(article);

//                String[] titles = new String[] {"article", "comment", "edit"};
//                Fragment[] fragments = new Fragment[] {articleFragment, articleFragment, articleFragment};
                String[] titles = new String[] {"article", "article"};
                Fragment[] fragments = new Fragment[] {articleFragment, articleFragment2};
                final DonFragmentPagerAdapter adapter = new DonFragmentPagerAdapter(getActivity().getSupportFragmentManager(), fragments, titles);

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        viewPager.setAdapter(adapter);
                        tabLayout.setupWithViewPager(viewPager);
                    }
                });
            }
        };
        client.getArticle(paramArticleId);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
