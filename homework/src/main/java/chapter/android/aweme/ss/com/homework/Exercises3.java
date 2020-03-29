package chapter.android.aweme.ss.com.homework;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.List;

import chapter.android.aweme.ss.com.homework.model.Message;
import chapter.android.aweme.ss.com.homework.model.PullParser;

public class Exercises3 extends AppCompatActivity implements DYAdapter.ListItemClickListener {

    private static final String TAG = "clw";
    private DYAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private RecyclerView.ViewHolder tempHolder;
    public List<Message> messages;
    private Toast mToast;

    public void showDialog (View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.mipmap.ic_launcher_round);
        builder.setTitle("提示");
        builder.setMessage("本功能将在正式版中登场");
        builder.setPositiveButton("是",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {}
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void initColor() {
        LinearLayout BGColor = (LinearLayout)findViewById(R.id.BGColor);
        BGColor.setBackgroundColor(getResources().getColor(R.color.colorBackground));
        TextView message = (TextView)findViewById(R.id.message);
        message.setTextColor(getResources().getColor(R.color.white));
        Button btn0 = (Button)findViewById(R.id.btn0);
        btn0.setBackgroundColor(getResources().getColor(R.color.colorBackground));
        Button btn1 = (Button)findViewById(R.id.btn1);
        btn1.setBackgroundColor(getResources().getColor(R.color.colorBackground));
        Button btn2 = (Button)findViewById(R.id.btn2);
        btn2.setBackground(getResources().getDrawable(R.mipmap.ic_launcher));
        Button btn3 = (Button)findViewById(R.id.btn3);
        btn3.setBackgroundColor(getResources().getColor(R.color.colorBackground));
        Button btn4 = (Button)findViewById(R.id.btn4);
        btn4.setBackgroundColor(getResources().getColor(R.color.colorBackground));
    }

    private void initListItem() {
        //获取数据
        try {
            InputStream assetInput = getAssets().open("data.xml");
            messages = PullParser.pull2xml(assetInput);
            for (int i = 0; i < messages.size(); i++) {
                Log.d("msg" + Integer.toString(i), messages.get(i).toString());
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        //获取recyclerView控件
        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerView);

        //为recyclerView设置布局管理器
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //为recycler设置数据适配器
        mAdapter = new DYAdapter(messages.size(), this, messages);
        mRecyclerView.setAdapter(mAdapter);

        //滑动效果
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            // 最后一个完全可见项的位置
            private int lastCompletelyVisibleItemPosition;

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (visibleItemCount > 0 && lastCompletelyVisibleItemPosition >= totalItemCount - 1) {
                        Toast.makeText(Exercises3.this, "已滑动到底部!,触发loadMore", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                if (layoutManager instanceof LinearLayoutManager) {
                    lastCompletelyVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastCompletelyVisibleItemPosition();
                }
                Log.d("ex3", "onScrolled: lastVisiblePosition=" + lastCompletelyVisibleItemPosition);
            }
        });

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises3);
        initColor();
        initListItem();
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        Log.d(TAG, "onListItemClick: ");
        if (mToast != null) {
            mToast.cancel();
        }
        String toastMessage = "Item #" + clickedItemIndex + " clicked.";
        mToast = Toast.makeText(this, toastMessage, Toast.LENGTH_LONG);

        mToast.show();
    }
}
