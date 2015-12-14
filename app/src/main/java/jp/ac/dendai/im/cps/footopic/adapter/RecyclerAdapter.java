package jp.ac.dendai.im.cps.footopic.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import jp.ac.dendai.im.cps.footopic.R;
import jp.ac.dendai.im.cps.footopic.bean.ArticleBean;
import jp.ac.dendai.im.cps.footopic.bean.UserBean;

/**
 * Created by naoya on 15/12/11.
 * RecyclerViewのアダプター
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private Context mContext;
    private SortedList<ArticleBean> sortedList;

    public RecyclerAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
        sortedList = new SortedList<>(ArticleBean.class, new SortedListCallback(this));
    }

    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerAdapter.ViewHolder holder, final int position) {
        // データ表示
        if (sortedList != null && sortedList.size() > position && sortedList.get(position) != null) {
            ArticleBean article = sortedList.get(position);
            UserBean user = article.getUser();

            Uri uri = Uri.parse(user.getImage().getThumb_url());
            holder.thumb.setImageURI(uri);

            String time = article.getCreated_at();
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
        if (sortedList != null) {
            return sortedList.size();
        } else {
            return 0;
        }
    }

    public void addDataOf(List<ArticleBean> dataList) {
        sortedList.addAll(dataList);
    }

    public void removeDataOf(List<ArticleBean> dataList) {
        sortedList.beginBatchedUpdates();
        for (ArticleBean data : dataList) {
            sortedList.remove(data);
        }
        sortedList.endBatchedUpdates();
    }

    public void clearData() {
        sortedList.clear();
    }

    public SortedList<ArticleBean> getList() {
        return sortedList;
    }

    /**
     * ViewHolder
     * ItemのViewは固定なのでインナークラス
     */
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

    private static class SortedListCallback extends SortedList.Callback<ArticleBean> {
        private RecyclerView.Adapter adapter;

        public SortedListCallback(@NonNull RecyclerView.Adapter adapter) {
            this.adapter = adapter;
        }

        @Override
        public int compare(ArticleBean o1, ArticleBean o2) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                Date date1 = sdf.parse(o1.getCreated_at());
                Date date2 = sdf.parse(o2.getCreated_at());

                return date2.compareTo(date1);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            return 0;
        }

        @Override
        public void onInserted(int position, int count) {
            adapter.notifyItemRangeInserted(position, count);
        }

        @Override
        public void onRemoved(int position, int count) {
            adapter.notifyItemRangeRemoved(position, count);
        }

        @Override
        public void onMoved(int fromPosition, int toPosition) {
            adapter.notifyItemMoved(fromPosition, toPosition);
        }

        @Override
        public void onChanged(int position, int count) {
            adapter.notifyItemRangeChanged(position, count);
        }

        @Override
        public boolean areContentsTheSame(ArticleBean oldItem, ArticleBean newItem) {
            String oldText = oldItem.getText();
            if (oldText == null) {
                return newItem.getText() == null;
            }
            return oldText.equals(newItem.getText());
        }

        @Override
        public boolean areItemsTheSame(ArticleBean item1, ArticleBean item2) {
            return item1.getId() == item2.getId();
        }
    }
}
