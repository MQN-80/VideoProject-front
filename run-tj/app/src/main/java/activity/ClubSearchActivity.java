package activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import bean.ClubBoxLayout;
import bean.Record_box;
import com.example.myapplication.R;
import net.asyncCall;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class ClubSearchActivity extends AppCompatActivity implements View.OnClickListener// 社团搜索页
{
    EditText editText;
    final Handler handle =new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            LinearLayout Frame=findViewById(R.id.ClubSearch_Box);
            LayoutInflater layoutInflater = LayoutInflater.from(ClubSearchActivity.this);
            switch (msg.what) {
                case 0:
                    try {
                        JSONObject jsonObject = (JSONObject) msg.obj;
                        String name = jsonObject.getString("associationName");
                        String id = jsonObject.getString("id");
                        String memberNum=jsonObject.getString("memberNum");
                        String desc=jsonObject.getString("associationDesc");
                        ClubBoxLayout clubBoxLayout=(ClubBoxLayout) layoutInflater.inflate(R.layout.club_list_real, null, false);
                        clubBoxLayout.setClub_Name(name);
                        clubBoxLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(ClubSearchActivity.this, ClubIndexActivity.class);
                                intent.putExtra("ClubName", name);
                                intent.putExtra("ClubId", id);
                                intent.putExtra("ClubDesc", desc);
                                intent.putExtra("ClubJudge","0");
                                startActivity(intent);
                            }
                        });
                        Frame.addView(clubBoxLayout);
                    }
                    catch (JSONException e){
                        throw new RuntimeException(e);
                    };
                    break;
            }
        };

    };
    public void onClick(View view) {
        LinearLayout Frame=findViewById(R.id.ClubSearch_Box);
        switch (view.getId())
        {
            case(R.id.Button_returnClubPage):{
                finish();
                break;
            }
            case(R.id.getSearchButton):{
                Frame.removeAllViews();
                //启动搜索
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        asyncCall asyncCall = new asyncCall();
                        ArrayList<String> search_info=new ArrayList<>();
                        search_info.add(editText.getText().toString());
                        // 返回response解析Json
                        Response response = asyncCall.getAsync("/SearchClub",search_info);
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
        setContentView(R.layout.activity_search_club);
        Button returnButton=findViewById(R.id.Button_returnClubPage);
        returnButton.setOnClickListener(this);
        //绑定输入框
        editText=findViewById(R.id.input_box);
        //进页面首先展示所有社团
        new Thread(new Runnable() {
            @Override
            public void run() {
                asyncCall asyncCall = new asyncCall();
                // 返回response解析Json
                Response response = asyncCall.getAsync("/club",null);
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
        //绑定搜索按钮
        Button searchButton=findViewById(R.id.getSearchButton);
        searchButton.setOnClickListener(this);
    }
}
