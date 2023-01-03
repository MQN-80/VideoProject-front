package Fragment;

import Utils.ACache;
import activity.*;
import android.content.*;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import bean.*;
import com.example.myapplication.R;
import net.asyncCall;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.*;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link clubFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class clubFragment extends Fragment implements View.OnClickListener{

    private ChatLayout chatLayout;

    private HeadFrameLayout headFrameLayout;

    View ClubView;

    List<JSONObject> clubList=new ArrayList<>();

    List<JSONObject> postLists=new ArrayList<>();

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(getActivity());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.CART_BROADCAST");
        BroadcastReceiver mItemViewListClickReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent){
                String msg = intent.getStringExtra("data");
                if("refresh".equals(msg)){
                    refresh();
                }
                else if("refresh1".equals(msg)){
                    refresh1();
                }
            }
        };
        broadcastManager.registerReceiver(mItemViewListClickReceiver, intentFilter);
    }
    private void refresh1(){
        LinearLayout Frame=ClubView.findViewById(R.id.chatFrame);
        Frame.removeAllViews();
        Button button=ClubView.findViewById(R.id.button_ToCommunity);
        onClick(button);
    }
    private void refresh() {
        LinearLayout Frame=ClubView.findViewById(R.id.chatFrame);
        Frame.removeAllViews();
        clubList=new ArrayList<>();
        ACache mCache=ACache.get(getActivity());
        new Thread(new Runnable() {
            @Override
            public void run() {
                asyncCall asyncCall = new asyncCall();
                ArrayList<String> user_id = new ArrayList<>();
                // 获取要查询记录的id
                user_id.add(mCache.getAsString("user_id"));
                // 返回response解析Json
                Response response = asyncCall.getAsync("/getClub",user_id);
                Log.i("ClubActivity",response.toString());
                try{
                    JSONArray jsonArray = new JSONArray(Objects.requireNonNull(response.body()).string());
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        clubList.add(jsonObject);
                        Message msg = new Message();
                        msg.what = 0;
                        msg.obj = jsonObject;
                        handle.sendMessage(msg);
                    }
                }
                catch (IOException | JSONException e){
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }
    final Handler handle =new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            LinearLayout Frame=ClubView.findViewById(R.id.chatFrame);
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            ACache mCache=ACache.get(getActivity());
            switch (msg.what) {
                case 0: {
                    try {
                        JSONObject jsonObject = (JSONObject) msg.obj;
                        ClubBoxLayout clubBoxLayout = (ClubBoxLayout) layoutInflater.inflate(R.layout.club_list_real, null, false);
                        String name = jsonObject.getString("associationName");
                        String id = jsonObject.getString("id");
                        String memberNum = jsonObject.getString("memberNum");
                        String desc = jsonObject.getString("associationDesc");
                        clubBoxLayout.setClub_Name(name);
                        clubBoxLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(getActivity(), ClubIndexActivity.class);
                                intent.putExtra("ClubName", name);
                                intent.putExtra("ClubId", id);
                                intent.putExtra("ClubDesc", desc);
                                intent.putExtra("ClubJudge", "1");
                                startActivity(intent);
                            }
                        });
                        Frame.addView(clubBoxLayout);
                    }
                    catch (JSONException e){
                        throw new RuntimeException(e);
                    }
                    break;
                }
                case 1:{
                    Log.i("获得的动态列表：",postLists.toString());
                    Iterator<JSONObject> iterator=postLists.iterator();
                    while(iterator.hasNext()) {
                        try {
                            JSONObject jsonObject = iterator.next();
                            CommunityBox communityBox=(CommunityBox) layoutInflater.inflate(R.layout.community_box_real,null,false);
                            String time=jsonObject.getString("createTime");
                            Integer postId=jsonObject.getInt("id");
                            String name = jsonObject.getString("name");
                            String context=jsonObject.getString("context");
                            Integer likes=jsonObject.getInt("likes");
                            Boolean like=jsonObject.getBoolean("like");
                            communityBox.setUser_Name(name);
                            communityBox.setCommunity_Box(context);
                            communityBox.setCreate_Time(time);
                            communityBox.setLikes(likes.toString());
                            Log.i("该文章是否点赞",like.toString());
                            if(like)
                                ;
                            else {
                                communityBox.setButton(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        ImageView button=communityBox.getButton();
                                        button.setImageResource(R.drawable.liked);
                                        Integer likes=Integer.parseInt(communityBox.getLikes());
                                        likes++;
                                        communityBox.setLikes(likes.toString());
                                        asyncCall call = new asyncCall();
                                        Map<String, String> res = new HashMap<>();
                                        Map<String, String> head = new HashMap<>();
                                        head.put("satoken",mCache.getAsString("token"));
                                        Log.i("1",postId.toString());
                                        res.put("id", postId.toString());
                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                call.putAsync("/article/like",head, res);
                                            }
                                        }).start();
                                    }
                                });
                                like=true;
                            }
                            Frame.addView(communityBox);
                        }
                        catch (JSONException e){
                            throw new RuntimeException(e);
                        }
                    }
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
                        String id = jsonObject.getString("id");
                        String memberNum=jsonObject.getString("memberNum");
                        String desc=jsonObject.getString("associationDesc");
                        clubBoxLayout.setClub_Name(name);
                        clubBoxLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(getActivity(), ClubIndexActivity.class);
                                intent.putExtra("ClubName", name);
                                intent.putExtra("ClubId", id);
                                intent.putExtra("ClubDesc", desc);
                                intent.putExtra("ClubJudge","1");
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
                Button button=new Button(getContext());
                button.setText("发表动态");
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), SendPostActivity.class);
                        startActivity(intent);
                    }
                });
                Frame.addView(button);
                postLists=new ArrayList<>();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        asyncCall asyncCall = new asyncCall();
                        ArrayList<String> page = new ArrayList<>();
                        // 获取要查询记录的id
                        page.add("1");
                        // 返回response解析Json
                        Response response = asyncCall.getAsync("/article/page",page);
                        try{
                            JSONArray jsonArray = new JSONArray(Objects.requireNonNull(response.body()).string());
                            for(int i=0;i<jsonArray.length();i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                postLists.add(jsonObject);
                            }
                            Message msg = new Message();
                            msg.what = 1;
                            msg.obj = postLists;
                            handle.sendMessage(msg);
                            Log.i("获得的动态列表：",postLists.toString());
                        }
                        catch (IOException | JSONException e){
                            throw new RuntimeException(e);
                        }
                    }
                }).start();
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
        ACache mCache=ACache.get(getActivity());
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
                user_id.add(mCache.getAsString("user_id"));
                // 返回response解析Json
                Response response = asyncCall.getAsync("/getClub",user_id);
                Log.i("ClubActivity",response.toString());
                try{
                    JSONArray jsonArray = new JSONArray(Objects.requireNonNull(response.body()).string());
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        clubList.add(jsonObject);
                        Message msg = new Message();
                        msg.what = 0;
                        msg.obj = jsonObject;
                        handle.sendMessage(msg);
                    }
                }
                catch (IOException | JSONException e){
                    throw new RuntimeException(e);
                }
            }
        }).start();
        //绑定顶部栏布局
        headFrameLayout=ClubView.findViewById(R.id.headFrameLayout);
        headFrameLayout.setUser_Name(mCache.getAsString("user_id"));
        headFrameLayout.setUser_State("正在观看 《真夏夜之梦》");
        return ClubView;
    }
}