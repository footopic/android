package jp.ac.dendai.im.cps.footopic.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import jp.ac.dendai.im.cps.footopic.R;
import jp.ac.dendai.im.cps.footopic.adapters.CommentListAdapter;
import jp.ac.dendai.im.cps.footopic.entities.Article;
import jp.ac.dendai.im.cps.footopic.entities.User;
import jp.ac.dendai.im.cps.footopic.utils.App;
import us.feras.mdv.MarkdownView;


public class ArticleFragment extends Fragment {

    private Article article;

    /**
     * @param article ArticleBean
     * @return A new instance of fragment ArticleFragment.
     */
    public static ArticleFragment newInstance(Article article) {
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

        User user = article.getUser();

        Uri uri = Uri.parse(user.getImage().getUrl());
        SimpleDraweeView draweeView = (SimpleDraweeView) v.findViewById(R.id.detail_thumb);
        draweeView.setImageURI(uri);

        String time = article.getCreated_at();
        name.setText(user.getScreen_name() + " が " + time + " に投稿");
        title.setText(article.getTitle());

        String tmp = "";
        for (String str : article.getTags()) {
            tmp += str + " ";
        }
        tags.setText(tmp);

        MarkdownView mdView = (MarkdownView) v.findViewById(R.id.markdownView);
        mdView.loadMarkdown(article.getText());


        if (article.getComments().length > 0) {
            Log.d("ArticleFragment", article.getComments()[0].getText());
            ListView listView = (ListView) v.findViewById(R.id.comment_list);

            CommentListAdapter adapter = new CommentListAdapter(App.getInstance());
            adapter.setCommentList(article.getComments());
            listView.setAdapter(adapter);
        }

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

    private void setArticle(Article article) {
        this.article = article;
    }

}
