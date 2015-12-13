package jp.ac.dendai.im.cps.footopic.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import jp.ac.dendai.im.cps.footopic.R;
import jp.ac.dendai.im.cps.footopic.bean.ArticleBean;
import jp.ac.dendai.im.cps.footopic.bean.UserBean;

/**
 * Created by naoya on 15/12/11.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private ArrayList<ArticleBean> mData;
    private Context mContext;

    public RecyclerAdapter(Context context, ArrayList<ArticleBean> data) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
        mData = data;
    }

    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerAdapter.ViewHolder holder, final int position) {
        // データ表示
        if (mData != null && mData.size() > position && mData.get(position) != null) {
            ArticleBean article = mData.get(position);
            UserBean user = article.getUser();

//            AsyncImageTask imageTask = new AsyncImageTask(holder.thumb);
//            imageTask.execute(user.getImage().getThumb_url());
            Glide.with(mContext)
                    .load(user.getImage().getThumb_url())
                    .centerCrop()
                    .crossFade()
                    .into(holder.thumb);

            String time = article.getCreated_at().substring(0, 9) + " " + article.getCreated_at().substring(11, 16);
            holder.name.setText(user.getScreen_name() + " が " + time + " に投稿");
            holder.title.setText(article.getTitle());

            String tags = "";
            for (String str : article.getTags()) {
                tags += str + " ";
            }
            holder.tags.setText(tags);
        }
    }

    @Override
    public int getItemCount() {
        if (mData != null) {
            return mData.size();
        } else {
            return 0;
        }
    }

    // ViewHolder(固有ならインナークラスでOK)
    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView thumb;
        TextView name;
        TextView title;
        TextView tags;

        public ViewHolder(View itemView) {
            super(itemView);
            thumb = (ImageView) itemView.findViewById(R.id.thumb);
            name = (TextView) itemView.findViewById(R.id.name_text);
            title = (TextView) itemView.findViewById(R.id.title_text);
            tags = (TextView) itemView.findViewById(R.id.tags_text);
        }
    }
}
