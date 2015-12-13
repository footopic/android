package jp.ac.dendai.im.cps.footopic;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import jp.ac.dendai.im.cps.footopic.bean.ArticleBean;
import jp.ac.dendai.im.cps.footopic.bean.UserBean;
import us.feras.mdv.MarkdownView;


public class ArticleFragment extends Fragment {

    private ArticleBean article;

    /**
     * @param article ArticleBean
     * @return A new instance of fragment ArticleFragment.
     */
    public static ArticleFragment newInstance(ArticleBean article) {
        ArticleFragment fragment = new ArticleFragment();
        fragment.setArticle(article);
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
        if (article == null) {
            getFragmentManager().popBackStack();
        }

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_article, container, false);
        ImageView thumb = (ImageView) v.findViewById(R.id.detail_thumb);
        TextView name = (TextView) v.findViewById(R.id.detail_name_text);
        TextView title = (TextView) v.findViewById(R.id.detail_title_text);
        TextView tags = (TextView) v.findViewById(R.id.detail_tags_text);

        UserBean user = article.getUser();

        Glide.with(this)
                .load(user.getImage().getUrl())
                .centerCrop()
                .crossFade()
                .into(thumb);

        String time = article.getCreated_at().substring(0, 9) + " " + article.getCreated_at().substring(11, 16);
        name.setText(user.getScreen_name() + " が " + time + " に投稿");
        title.setText(article.getTitle());

        String tmp = "";
        for (String str : article.getTags()) {
            tmp += str + " ";
        }
        tags.setText(tmp);

        MarkdownView mdView = (MarkdownView) v.findViewById(R.id.markdownView);
        mdView.loadMarkdown(article.getText());

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

    public void setArticle(ArticleBean article) {
        this.article = article;
    }

}
