package activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication.R;
import net.asyncCall;
import okhttp3.Response;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CenterActivity extends AppCompatActivity implements View.OnClickListener{

    //用户头像
    ImageView avatar;

    final Handler handle =new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    Bitmap bmp=(Bitmap)msg.obj;
                    avatar.setImageBitmap(bmp);
                    break;
            }
        };

    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 加载静态页面
        setContentView(R.layout.activity_user_center);
        avatar = findViewById(R.id.imageView7);
        // 在这个线程中设置头像
        new Thread(new Runnable() {
            @Override
            public void run() {
                asyncCall asyncCall = new asyncCall();
                ArrayList<String> idList = new ArrayList<>();
                // 要查询的id
                idList.add("8");
                // 返回response解析Json
                Response response = asyncCall.getAsync("/user",idList);
                try {
                    JSONObject object = new JSONObject(Objects.requireNonNull(response.body()).string());
                    // Json处理器
                    String UserIcon = object.getString("avator");
                    // 设置头像为用户头像
                    Message msg = new Message();
                    msg.what = 0;
                    msg.obj = Utils.BitMap.returnBitMap(UserIcon);;
                    handle.sendMessage(msg);
                } catch (IOException | JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

        // 设置跳转按钮
        TextView runToRecord = findViewById(R.id.jumpToRunRecord);
        runToRecord.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.jumpToRunRecord:{
                Intent intent = new Intent(CenterActivity.this, RunRecordActivity.class);
                startActivity(intent);
                break;
            }
        }
    }

}
