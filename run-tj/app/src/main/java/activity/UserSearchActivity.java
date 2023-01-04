package activity;

import Utils.ACache;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import bean.ClubBoxLayout;
import bean.Follow_box;
import com.example.myapplication.R;
import net.asyncCall;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class UserSearchActivity extends AppCompatActivity implements View.OnClickListener{

    EditText editText;

    final Handler handle =new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            LinearLayout Frame = findViewById(R.id.UserSearch_Box);
            LayoutInflater layoutInflater = LayoutInflater.from(UserSearchActivity.this);
            switch (msg.what) {
                case 0:
                    try {
                        JSONObject jsonObject = (JSONObject) msg.obj;
                        String UserIcon = jsonObject.getString("avator");
                        // 获得Bitmap
                        Bitmap avatar = Utils.BitMap.returnBitMap(UserIcon);
                        String name = jsonObject.getString("name");
                        String id = jsonObject.getString("id");
                        Follow_box follow_box = (Follow_box) layoutInflater.inflate(R.layout.follow_box_real, null, false);
                        follow_box.setNameInFollow(name);
                        follow_box.setStatusInFollow("未关注");
                        follow_box.setAvatarInFollow(avatar);
                        follow_box.bindStatusInFollow(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ACache mCache=ACache.get(UserSearchActivity.this);
                                asyncCall call = new asyncCall();
                                Map<String, String> res = new HashMap<>();
                                Map<String, String> head = new HashMap<>();
                                head.put("satoken",mCache.getAsString("token"));
                                res.put("followUserId", id);
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        call.postAsync("/follow",head, res);
                                    }
                                }).start();
                                AlertDialog alertDialog1 = new AlertDialog.Builder(UserSearchActivity.this)
                                        .setTitle("通知")//标题
                                        .setMessage("用户关注成功！")//内容
                                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                finish();
                                            }
                                        })
                                        .create();
                                alertDialog1.show();
                            }
                        });
                        Frame.addView(follow_box);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                    break;
            }
        }
    };

    public void onClick(View view) {
        LinearLayout Frame = findViewById(R.id.UserSearch_Box);
        switch (view.getId()) {
            case (R.id.Button_returnClubPage): {
                finish();
                break;
            }
            case (R.id.getUserSearchButton):{
                Frame.removeAllViews();
                //启动搜索
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        asyncCall asyncCall = new asyncCall();
                        ArrayList<String> search_info=new ArrayList<>();
                        search_info.add(editText.getText().toString());
                        // 返回response解析Json
                        Response response = asyncCall.getAsync("/SearchUser",search_info);
                        try{
                            JSONArray jsonArray = new JSONArray(Objects.requireNonNull(response.body()).string());
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
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
                break;
            }
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);
        Button returnButton=findViewById(R.id.Button_returnClubPage);
        returnButton.setOnClickListener(this);
        //绑定输入框
        editText=findViewById(R.id.search_user_input);
        //绑定搜索按钮
        Button searchButton=findViewById(R.id.getUserSearchButton);
        searchButton.setOnClickListener(this);
    }
}
