package jp.ac.dendai.im.cps.footopic.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import jp.ac.dendai.im.cps.footopic.R;
import jp.ac.dendai.im.cps.footopic.adapters.DonFragmentPagerAdapter;
import jp.ac.dendai.im.cps.footopic.entities.Article;
import jp.ac.dendai.im.cps.footopic.entities.Image;
import jp.ac.dendai.im.cps.footopic.entities.User;

public class ArticlePagerFragment extends Fragment implements CommentFragment.OnCommentFragmentInteractionListener {

    private static final String TAG = ArticlePagerFragment.class.getSimpleName();

    private OnArticlePagerInteractionListener mListener;

    private static final String PARAM_TITLE = "title";
    private static final String PARAM_TEXT = "text";
    private static final String PARAM_TAGS = "tags";
    private static final String PARAM_CREATED_AT = "created_at";
    private static final String PARAM_USER_IMAGE_URL = "user_image_url";
    private static final String PARAM_USER_SCREEN_NAME = "user_screen_name";
    private static final String PARAM_USER_NAME = "user_name";

    private String title;
    private String text;
    private String[] tags;
    private String createdAt;
    private String userName;
    private String userScreenName;
    private String userImageUrl;
    private Article article;

    public ArticlePagerFragment() {
        // Required empty public constructor
    }

    public static ArticlePagerFragment newInstance(Article article) {
        ArticlePagerFragment fragment = new ArticlePagerFragment();
        Bundle bundle = new Bundle();
        bundle.putString(PARAM_TITLE, article.getTitle());
        bundle.putString(PARAM_TEXT, article.getText());
        bundle.putStringArray(PARAM_TAGS, article.getTags());
        bundle.putString(PARAM_CREATED_AT, article.getCreated_at());

        bundle.putString(PARAM_USER_NAME, article.getUser().getName());
        bundle.putString(PARAM_USER_SCREEN_NAME, article.getUser().getScreen_name());
        bundle.putString(PARAM_USER_IMAGE_URL, article.getUser().getImage().getUrl());
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString(PARAM_TITLE);
            text = getArguments().getString(PARAM_TEXT);
            tags = getArguments().getStringArray(PARAM_TAGS);
            createdAt = getArguments().getString(PARAM_CREATED_AT);
            userName = getArguments().getString(PARAM_USER_NAME);
            userScreenName = getArguments().getString(PARAM_USER_SCREEN_NAME);
            userImageUrl = getArguments().getString(PARAM_USER_IMAGE_URL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_article_pager, container, false);
        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        final TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);

        article = new Article();
        article.setTitle(title);
        article.setText(text);
        article.setTags(tags);
        article.setCreated_at(createdAt);
        User user = new User();
        user.setName(userName);
        user.setScreen_name(userScreenName);
        Image image = new Image();
        image.setUrl(userImageUrl);
        image.setThumb_url(userImageUrl);
        user.setImage(image);
        article.setUser(user);

        ArticleFragment articleFragment = ArticleFragment.newInstance(article);
        CommentFragment commentFragment = CommentFragment.newInstance();

        String[] titles = new String[] {"article", "comment"};
        Fragment[] fragments = new Fragment[] {articleFragment, commentFragment};
        final DonFragmentPagerAdapter adapter = new DonFragmentPagerAdapter(getActivity().getSupportFragmentManager(), fragments, titles);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnArticlePagerInteractionListener) {
            mListener = (OnArticlePagerInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onActivityCreated(RecyclerView recyclerView) {
        mListener.onArticlePagerInteraction();
    }

    public interface OnArticlePagerInteractionListener {
        // TODO: Update argument type and name
        void onArticlePagerInteraction();
    }
}
