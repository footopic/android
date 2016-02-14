package jp.ac.dendai.im.cps.footopic.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import jp.ac.dendai.im.cps.footopic.R;
import jp.ac.dendai.im.cps.footopic.entities.Article;
import us.feras.mdv.MarkdownView;


public class ArticleFragment extends Fragment {

    private static final String PARAM_TITLE = "title";
    private static final String PARAM_TEXT = "text";
    private static final String PARAM_TAGS = "tags";
    private static final String PARAM_CREATED_AT = "created_at";

    private static final String PARAM_USER_IMAGE_URL = "user_image_url";
    private static final String PARAM_USER_SCREEN_NAME = "user_screen_name";
    private static final String PARAM_USER_NAME = "user_name";

    /**
     * @param article ArticleBean
     * @return A new instance of fragment ArticleFragment.
     */
    public static ArticleFragment newInstance(Article article) {
        Bundle bundle = new Bundle();
        bundle.putString(PARAM_TITLE, article.getTitle());
        bundle.putString(PARAM_TEXT, article.getText());
        bundle.putStringArray(PARAM_TAGS, article.getTags());
        bundle.putString(PARAM_CREATED_AT, article.getCreated_at());

        bundle.putString(PARAM_USER_NAME, article.getUser().getName());
        bundle.putString(PARAM_USER_SCREEN_NAME, article.getUser().getScreen_name());
        bundle.putString(PARAM_USER_IMAGE_URL, article.getUser().getImage().getUrl());
        ArticleFragment fragment = new ArticleFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public ArticleFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle bundle = getArguments();

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_article, container, false);
        TextView name = (TextView) v.findViewById(R.id.detail_name_text);
        TextView title = (TextView) v.findViewById(R.id.detail_title_text);
        TextView tags = (TextView) v.findViewById(R.id.detail_tags_text);

        Uri uri = Uri.parse(bundle.getString(PARAM_USER_IMAGE_URL));
        SimpleDraweeView draweeView = (SimpleDraweeView) v.findViewById(R.id.detail_thumb);
        draweeView.setImageURI(uri);

        String time = bundle.getString(PARAM_CREATED_AT);
        name.setText(bundle.getString(PARAM_USER_NAME) + " が " + time + " に投稿");
        title.setText(bundle.getString(PARAM_TITLE));

        tags.setText(TextUtils.join(" ", bundle.getStringArray(PARAM_TAGS)));

        MarkdownView mdView = (MarkdownView) v.findViewById(R.id.markdownView);
        mdView.loadMarkdown(bundle.getString(PARAM_TEXT));

        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
