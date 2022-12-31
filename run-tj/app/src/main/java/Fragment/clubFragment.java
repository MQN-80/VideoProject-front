package Fragment;

import activity.ClubChatActivity;
import activity.ClubIndexActivity;
import activity.ClubSearchActivity;
import activity.CreateClubActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import bean.ChatLayout;
import bean.ClubBoxLayout;
import bean.CommunityBox;
import bean.HeadFrameLayout;
import com.example.myapplication.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link clubFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class clubFragment extends Fragment implements View.OnClickListener{

    private ChatLayout chatLayout;

    private HeadFrameLayout headFrameLayout;

    View ClubView;

    String imageUrl = "http://hiphotos.baidu.com/baidu/pic/item/7d8aebfebf3f9e125c6008d8.jpg";

    String []arr={"金逸太强了小组","软工人","Java EE已退群设计","2022软件设计模式课程群","杀软二次元群",
            "金逸太强了小组","软工人","Java EE已退群设计","2022软件设计模式课程群","杀软二次元群","金逸太强了小组",
            "软工人","Java EE已退群设计","2022软件设计模式课程群","杀软二次元群"};

    final Handler handle =new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    Log.d("Main2Activity","handleMessage");
                    Bitmap bmp=(Bitmap)msg.obj;
                    chatLayout.setIcon(bmp);
                    break;
            }
        };

    };

    public void onClick(View view) {
        LinearLayout Frame=ClubView.findViewById(R.id.chatFrame);
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        switch (view.getId()) {
            case R.id.button_ToClub: {
                Frame.removeAllViews();
                for (Integer i = 1; i < 16; i++) {
                    String name1 = arr[i - 1];
                    ClubBoxLayout clubBoxLayout = (ClubBoxLayout) layoutInflater.inflate(R.layout.club_list_real, null, false);
                    clubBoxLayout.setClub_Name(arr[i - 1]);
                    clubBoxLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getActivity(), ClubIndexActivity.class);
                            intent.putExtra("ClubData", name1);
                            startActivity(intent);
                        }
                    });
                    Frame.addView(clubBoxLayout);
                }
                break;
            }
            case R.id.button_ToCommunity:{
                Frame.removeAllViews();
                CommunityBox communityBox=(CommunityBox) layoutInflater.inflate(R.layout.community_box_real,null,false);
                Frame.addView(communityBox);
                break;
            }
            case R.id.button_ToMessage:{
                Frame.removeAllViews();
                for(Integer i=1;i<16;i++) {
                    String name=arr[i-1];
                    Log.i("ClubActivity","循环执行"+i);
                    ChatLayout chatLayout1 = (ChatLayout) layoutInflater.inflate(R.layout.club_chat_real, null, false);
                    chatLayout1.setClub_Name(arr[i-1]);
                    chatLayout1.setClub_Chat("2051914 金逸 ：我来全写完了");
                    chatLayout1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getActivity(), ClubChatActivity.class);
                            intent.putExtra("ClubData",name);
                            startActivity(intent);
                        }
                    });
                    Frame.addView(chatLayout1);
                }
                break;
            }
            case R.id.button6:{
                Intent intent = new Intent(getActivity(), ClubSearchActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.HeadFrameButton:{
                Intent intent = new Intent(getActivity(), CreateClubActivity.class);
                startActivity(intent);
                break;
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ClubView = inflater.inflate(R.layout.fragment_club, container, false);

        // 绑定按钮

        // 绑定跳转社团按钮
        Button button1=ClubView.findViewById(R.id.button_ToClub);
        button1.setOnClickListener(this);

        // 绑定跳转社区按钮
        Button button2=ClubView.findViewById(R.id.button_ToCommunity);
        button2.setOnClickListener(this);

        // 绑定跳转聊天按钮
        Button button3=ClubView.findViewById(R.id.button_ToMessage);
        button3.setOnClickListener(this);

        // 绑定搜索按钮
        LinearLayout button4=ClubView.findViewById(R.id.button6);
        button4.setOnClickListener(this);

        Button button5=ClubView.findViewById(R.id.HeadFrameButton);
        button5.setOnClickListener(this);

        chatLayout=ClubView.findViewById(R.id.chatLayout);
        chatLayout.setClub_Chat("2052523 陈柯羲 ：[动画表情]");
        chatLayout.setClub_Name("软件工程无重量级小组");
        Log.i("ClubActivity","图片替换开始执行！");
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap=Utils.BitMap.returnBitMap(imageUrl);
                Message msg = new Message();
                msg.what = 0;
                msg.obj = bitmap;
                handle.sendMessage(msg);
            }
        }).start();
        chatLayout.setTime("19：34");

        //绑定顶部栏布局
        headFrameLayout=ClubView.findViewById(R.id.headFrameLayout);
        headFrameLayout.setUser_Name("田所浩二");
        headFrameLayout.setUser_State("正在观看 《真夏夜之梦》");
        return ClubView;
    }
}