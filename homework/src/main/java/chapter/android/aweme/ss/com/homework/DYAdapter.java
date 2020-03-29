package chapter.android.aweme.ss.com.homework;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import chapter.android.aweme.ss.com.homework.R;
import chapter.android.aweme.ss.com.homework.model.Message;
import chapter.android.aweme.ss.com.homework.widget.CircleImageView;


/**
 * 适配器
 */
public class DYAdapter extends RecyclerView.Adapter<DYAdapter.NumberViewHolder> {

    private static final String TAG = "DYAdapter";

    private int mNumberItems;

    private final ListItemClickListener mOnClickListener;

    private static int viewHolderCount;

    private List<Message> messages;

    public DYAdapter(int numListItems, ListItemClickListener listener,List<Message>message) {
        mNumberItems = numListItems;
        mOnClickListener = listener;
        viewHolderCount = 0;
        messages = message;
    }
    @NonNull
    @Override
    public NumberViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.im_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        NumberViewHolder viewHolder = new NumberViewHolder(view);

        viewHolder.description.setText("ViewHolder index: " + viewHolderCount);

        viewHolder.itemView.setBackgroundColor(Color.rgb(22,24,25));

        Log.d(TAG, "onCreateViewHolder: number of ViewHolders created: " + viewHolderCount);
        viewHolderCount++;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NumberViewHolder numberViewHolder, int position) {
        Log.d(TAG, "onBindViewHolder: #" + position);
        numberViewHolder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mNumberItems;
    }

    public class NumberViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private CircleImageView head;
        private ImageView guanfang;
        private TextView title;
        private TextView description;
        private TextView time;
        public int pos;

        public NumberViewHolder(@NonNull View itemView) {
            super(itemView);
            head = (CircleImageView) itemView.findViewById(R.id.iv_avatar);
            guanfang = (ImageView) itemView.findViewById(R.id.robot_notice);
            title = (TextView) itemView.findViewById(R.id.tv_title);
            description = (TextView) itemView.findViewById(R.id.tv_description);
            time = (TextView) itemView.findViewById(R.id.tv_time);
            itemView.setOnClickListener(this);
        }

        public void bind(int position) {
            pos = position;
            title.setText(messages.get(position).getTitle());
            description.setText(messages.get(position).getDescription());
            time.setText(messages.get(position).getTime());
            switch (messages.get(position).getIcon()) {
                case "TYPE_USER":
                    head.setImageResource(R.drawable.icon_girl);
                    break;
                case "TYPE_STRANGER":
                    head.setImageResource(R.drawable.session_stranger);
                    break;
                case "TYPE_ROBOT":
                    head.setImageResource(R.drawable.session_robot);
                    break;
                case "TYPE_SYSTEM":
                    head.setImageResource(R.drawable.session_system_notice);
                    break;
                case "TYPE_GAME":
                    head.setImageResource(R.drawable.icon_micro_game_comment);
                    break;
            }
            if(messages.get(position).isOfficial()) {
                guanfang.setVisibility(View.VISIBLE);
            } else {
                guanfang.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            if (mOnClickListener != null) {
                mOnClickListener.onListItemClick(clickedPosition);
            }
        }
    }

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }
}
