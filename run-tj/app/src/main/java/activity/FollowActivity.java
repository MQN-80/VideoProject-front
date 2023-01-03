package activity;

import Utils.ACache;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import bean.Follow_box;
import bean.Record_box;
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

public class FollowActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_follow);

        // 找到记录栏
        LinearLayout recordFrame=findViewById(R.id.FollowBox);

        LayoutInflater layoutInflater = LayoutInflater.from(FollowActivity.this);

        final Handler handle =new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        try {
                            JSONObject jsonObject = (JSONObject) msg.obj;
                            String UserIcon = jsonObject.getString("avatar");
                            // 获得Bitmap
                            Bitmap avatar = Utils.BitMap.returnBitMap(UserIcon);
                            String name = jsonObject.getString("name");
                            String status = "已关注";
                            String user_id = jsonObject.getString("id");//end of 取

                            Follow_box follow_box_inside; // 每次新建一个
                            follow_box_inside = (Follow_box) layoutInflater.inflate(R.layout.follow_box_real,null,false);
                            follow_box_inside.setAvatarInFollow(avatar);
                            follow_box_inside.setNameInFollow(name);
                            follow_box_inside.setStatusInFollow(status);
                            follow_box_inside.bindNameInFollow(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(FollowActivity.this, OtherUserInfoActivity.class);
                                    intent.putExtra("user_id",user_id);
                                    startActivity(intent);
                                }
                            });
                            follow_box_inside.bindStatusInFollow(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    asyncCall call=new asyncCall();
                                    Map<String,String> res=new HashMap<>();

                                }
                            });
                            recordFrame.addView(follow_box_inside);

                        }
                        catch (JSONException e){
                            throw new RuntimeException(e);
                        };
                        break;
                }
            };

        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                ACache mCache=ACache.get(FollowActivity.this);
                asyncCall asyncCall = new asyncCall();
                ArrayList<String> idList = new ArrayList<>();
                // 获取要查询记录的id
                idList.add(mCache.getAsString("user_id"));
                // 返回response解析Json
                Response response = asyncCall.getAsync("/follow",idList);
                try{
                    JSONArray jsonArray = new JSONArray(Objects.requireNonNull(response.body()).string());
                    for(int i = 0; i < jsonArray.length(); i++){
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
    }

    @Override
    public void onClick(View view) {

    }
}
