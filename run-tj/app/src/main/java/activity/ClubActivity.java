package activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import bean.ChatLayout;
import bean.HeadFrameLayout;
import com.example.myapplication.R;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ClubActivity extends AppCompatActivity {
    private ChatLayout chatLayout;
    private HeadFrameLayout headFrameLayout;
    String imageUrl = "http://hiphotos.baidu.com/baidu/pic/item/7d8aebfebf3f9e125c6008d8.jpg";

    String []arr={"金逸太强了小组","软工人","Java EE已退群设计","2022软件设计模式课程群","杀软二次元群"};

    public Bitmap returnBitMap(String url) {
        Bitmap bmp = null;
        try {
            URL MyUrl = new URL(url);
            // 获得连接
            HttpURLConnection conn = (HttpURLConnection) MyUrl.openConnection();
            conn.setConnectTimeout(6000);//设置超时
            conn.setDoInput(true);
            conn.setUseCaches(false);//不缓存
            conn.connect();
            InputStream is = conn.getInputStream();//获得图片的数据流
            bmp = BitmapFactory.decodeStream(is);//读取图像数据
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bmp;
    }

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
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_club);
        //绑定聊天框布局
        LinearLayout Frame=findViewById(R.id.chatFrame);
        chatLayout=findViewById(R.id.chatLayout);
        chatLayout.setClub_Chat("2052523 陈柯羲 ：[动画表情]");
        chatLayout.setClub_Name("软件工程无重量级小组");
        Log.i("ClubActivity","图片替换开始执行！");
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap=returnBitMap(imageUrl);
                Message msg = new Message();
                msg.what = 0;
                msg.obj = bitmap;
                handle.sendMessage(msg);
            }
        }).start();
        chatLayout.setTime("19：34");

        //绑定顶部栏布局
        headFrameLayout=findViewById(R.id.headFrameLayout);
        headFrameLayout.setUser_Name("田所浩二");
        headFrameLayout.setUser_State("正在观看 《真夏夜之梦》");

        LayoutInflater layoutInflater = LayoutInflater.from(this);
        for(Integer i=1;i<6;i++) {
            Log.i("ClubActivity","循环执行"+i);
            ChatLayout chatLayout1 = (ChatLayout) layoutInflater.inflate(R.layout.club_chat_real, null, false);
            chatLayout1.setClub_Name(arr[i-1]);
            chatLayout1.setClub_Chat("2051914 金逸 ：我来全写完了");
            Frame.addView(chatLayout1);
        }
    }

}
