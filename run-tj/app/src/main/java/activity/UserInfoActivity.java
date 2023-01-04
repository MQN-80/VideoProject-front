package activity;

import Utils.ACache;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication.R;
import net.asyncCall;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class UserInfoActivity extends AppCompatActivity implements View.OnClickListener{
    ImageView avatar;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_info);

        avatar = findViewById(R.id.avatar9);

        TextView line1_1 = findViewById(R.id.line1_1);
        TextView line2_1 = findViewById(R.id.line2_1);
        TextView line3_1 = findViewById(R.id.line3_1);
        TextView line4_1 = findViewById(R.id.line4_1);
        TextView line5_1 = findViewById(R.id.line5_1);
        TextView line6_1 = findViewById(R.id.line6_1);
        TextView line7_1 = findViewById(R.id.line7_1);

        TextView line1_2 = findViewById(R.id.line1_2);
        TextView line2_2 = findViewById(R.id.line2_2);
        TextView line3_2 = findViewById(R.id.line3_2);
        TextView line4_2 = findViewById(R.id.line4_2);
        TextView line5_2 = findViewById(R.id.line5_2);
        TextView line6_2 = findViewById(R.id.line6_2);
        TextView line7_2 = findViewById(R.id.line7_2);

        line1_1.setText("昵称");
        line2_1.setText("性别");
        line3_1.setText("手机号");
        line4_1.setText("邮箱");
        line5_1.setText("生日");
        line6_1.setText("个人介绍");
        line7_1.setText("创建时间");

        final Handler handle =new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        JSONObject object=(JSONObject)msg.obj;
                        // Json处理器
                        try {
                            String UserIcon = object.getString("avator");
                            Log.i("头像是",UserIcon);
                            String name = object.getString("name");
                            String gender = object.getString("gender");
                            String phone = object.getString("phone");
                            String email = object.getString("email");
                            String birthday = object.getString("birthday");
                            String detail = object.getString("detail");
                            String create_time = object.getString("createTime").substring(0,9);
                            Bitmap bmp = Utils.BitMap.returnBitMap(UserIcon);
                            avatar.setImageBitmap(bmp);
                            line1_2.setText(name);
                            line2_2.setText(gender);
                            line3_2.setText(phone);
                            line4_2.setText(email);
                            line5_2.setText(birthday);
                            line6_2.setText(detail);
                            line7_2.setText(create_time);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                }
            };

        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                asyncCall asyncCall = new asyncCall();
                ArrayList<String> idList = new ArrayList<>();
                ACache mCache=ACache.get(UserInfoActivity.this);
                // 获取要查询记录的id
                idList.add(mCache.getAsString("user_id"));
                // 返回response解析Json
                Response response = asyncCall.getAsync("/user",idList);
                try{
                    JSONObject object = new JSONObject(Objects.requireNonNull(response.body()).string());
                    Message msg = new Message();
                    msg.what = 0;
                    msg.obj = object;
                    handle.sendMessage(msg);
                }
                catch (IOException | JSONException e){
                    throw new RuntimeException(e);
                }
            }
        }).start();

        // 设置返回按钮
        ImageView backToCenter2 = findViewById(R.id.backToCenter2);
        backToCenter2.setOnClickListener(this);
        // 找编辑
        TextView edit = findViewById(R.id.edit_userInfo);
        edit.setOnClickListener(this);
        //找头像按钮
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.backToCenter2:{
                Intent intent = new Intent(UserInfoActivity.this,UserCenterActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.edit_userInfo: {
                Intent intent = new Intent(UserInfoActivity.this,UserInfoEditActivity.class);
                startActivity(intent);
                break;
            }
        }
    }
}
