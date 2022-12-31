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
import bean.*;
import com.example.myapplication.R;
import net.asyncCall;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

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

    List<JSONObject> clubList=new ArrayList<>();
    String []arr={"金逸太强了小组","软工人","Java EE已退群设计","2022软件设计模式课程群","杀软二次元群",
            "金逸太强了小组","软工人","Java EE已退群设计","2022软件设计模式课程群","杀软二次元群","金逸太强了小组",
            "软工人","Java EE已退群设计","2022软件设计模式课程群","杀软二次元群"};

    final Handler handle =new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            LinearLayout Frame=ClubView.findViewById(R.id.chatFrame);
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            switch (msg.what) {
                case 0: {
                    String name = arr[0];
                    chatLayout = (ChatLayout) layoutInflater.inflate(R.layout.club_chat_real, null, false);
                    chatLayout.setClub_Name(arr[0]);
                    chatLayout.setClub_Chat("2051914 金逸 ：我来全写完了");
                    chatLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getActivity(), ClubChatActivity.class);
                            intent.putExtra("ClubData", name);
                            startActivity(intent);
                        }
                    });
                    Frame.addView(chatLayout);
                    Bitmap bmp = (Bitmap) msg.obj;
                    chatLayout.setIcon(bmp);
                    Log.i("ClubActivity", "图片连续替换开始执行！");
                    break;
                }
            }
        };

    };

    public void onClick(View view) {
        LinearLayout Frame=ClubView.findViewById(R.id.chatFrame);
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        switch (view.getId()) {
            //切换到社团列表视图
            case R.id.button_ToClub: {
                Frame.removeAllViews();
                Iterator<JSONObject> iterator=clubList.iterator();
                while(iterator.hasNext()) {
                    try {
                        JSONObject jsonObject = iterator.next();
                        ClubBoxLayout clubBoxLayout = (ClubBoxLayout) layoutInflater.inflate(R.layout.club_list_real, null, false);
                        String name=jsonObject.getString("associationName");
                        clubBoxLayout.setClub_Name(name);
                        clubBoxLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(getActivity(), ClubIndexActivity.class);
                                intent.putExtra("ClubData", name);
                                startActivity(intent);
                            }
                        });
                        Frame.addView(clubBoxLayout);
                    }
                    catch (JSONException e){
                        throw new RuntimeException(e);
                    }
                }
                break;
            }
            //切换到朋友圈视图
            case R.id.button_ToCommunity:{
                Frame.removeAllViews();
                CommunityBox communityBox=(CommunityBox) layoutInflater.inflate(R.layout.community_box_real,null,false);
                Frame.addView(communityBox);
                break;
            }
            //切换到消息视图
            case R.id.button_ToMessage:{
                Frame.removeAllViews();
                Iterator<JSONObject> iterator=clubList.iterator();
                while(iterator.hasNext()) {
                    try {
                        JSONObject jsonObject = iterator.next();
                        chatLayout = (ChatLayout) layoutInflater.inflate(R.layout.club_chat_real, null, false);
                        String name=jsonObject.getString("associationName");
                        chatLayout.setClub_Name(name);
                        chatLayout.setClub_Chat("聊天室");
                        chatLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(getActivity(), ClubChatActivity.class);
                                intent.putExtra("ClubData", name);
                                startActivity(intent);
                            }
                        });
                        Frame.addView(chatLayout);
                    }
                    catch (JSONException e){
                        throw new RuntimeException(e);
                    }
                }
                break;
            }
            //点击搜索跳转到搜索页面
            case R.id.button6:{
                Intent intent = new Intent(getActivity(), ClubSearchActivity.class);
                startActivity(intent);
                break;
            }
            //点击加号跳转到创建社团页面
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
        clubList=new ArrayList<>();

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
        /*
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
         */

        //请求用户关注的社团
        new Thread(new Runnable() {
            @Override
            public void run() {
                asyncCall asyncCall = new asyncCall();
                ArrayList<String> user_id = new ArrayList<>();
                // 获取要查询记录的id
                user_id.add("6");
                // 返回response解析Json
                Response response = asyncCall.getAsync("/getClub",user_id);
                Log.i("ClubActivity",response.toString());
                try{
                    JSONArray jsonArray = new JSONArray(Objects.requireNonNull(response.body()).string());
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        clubList.add(jsonObject);
                    }
                }
                catch (IOException | JSONException e){
                    throw new RuntimeException(e);
                }
            }
        }).start();
        onClick(button3);
        //绑定顶部栏布局
        headFrameLayout=ClubView.findViewById(R.id.headFrameLayout);
        headFrameLayout.setUser_Name("田所浩二");
        headFrameLayout.setUser_State("正在观看 《真夏夜之梦》");
        return ClubView;
    }
}