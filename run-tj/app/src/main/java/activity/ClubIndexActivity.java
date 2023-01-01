package activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import bean.ClubMember;
import com.example.myapplication.R;
import net.asyncCall;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.*;

public class ClubIndexActivity extends AppCompatActivity implements View.OnClickListener //社团首页
{
    String judge;
    String id;
    public void onClick(View view) {
        switch (view.getId())
        {
            case(R.id.Button_returnClubPage):{
                finish();
                break;
            }
            case(R.id.joinClub):{
                if(judge.equals("0"))
                {
                    asyncCall call=new asyncCall();
                    Map<String,String> res=new HashMap<>();
                    res.put("as_id",id);
                    res.put("user_id","6");
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            call.postAsync("/manager",res);
                        }
                    }).start();
                }
                else
                {
                    asyncCall call=new asyncCall();
                    Map<String,String> res=new HashMap<>();
                    res.put("as_id",id);
                    res.put("user_id","6");
                    res.put("manager_id","0");
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            call.deleteAsync("/manager",res);
                        }
                    }).start();
                }
                break;
            }
            case(R.id.club_index_member):{
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        asyncCall asyncCall = new asyncCall();
                        ArrayList<String> list = new ArrayList<>();
                        Map<String,String> res=new HashMap<>();
                        res.put("as_id",id);
                        res.put("rank","0");
                        list.add(id);
                        list.add("0");
                        // 返回response解析Json
                        Response response = asyncCall.getAsync("/manager",list);
                        try{
                            ClubMember User=new ClubMember();
                            JSONArray jsonArray = new JSONArray(Objects.requireNonNull(response.body()).string());
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String name=jsonObject.getString("name");
                                String id=jsonObject.getString("id");
                                String phone=jsonObject.getString("phone");
                                User.addClubMember(id,name,phone);
                            }
                            Log.i("ClubIndexActivity",User.Member_Id.toString());
                            Intent intent = new Intent(ClubIndexActivity.this, MemberListActivity.class);
                            intent.putExtra("memberList",User);
                            intent.putExtra("clubId",id);
                            startActivity(intent);
                        }
                        catch (IOException | JSONException e){
                            throw new RuntimeException(e);
                        }
                    }
                }).start();
                break;
            }
            case(R.id.changeInfo):{
                Intent intent = new Intent(ClubIndexActivity.this, ClubInfoChangeActivity.class);
                intent.putExtra("clubId",id);
                startActivity(intent);
                break;
            }
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_index);
        Intent intent=this.getIntent();
        judge=intent.getStringExtra("ClubJudge");
        String name=intent.getStringExtra("ClubName");
        id=intent.getStringExtra("ClubId");
        String desc=intent.getStringExtra("ClubDesc");
        //设置社团名字
        TextView textView1=findViewById(R.id.Club_Name);
        textView1.setText(name);
        //设置社团介绍
        TextView textView2=findViewById(R.id.Club_Desc);
        textView2.setText(desc);
        //设置返回按钮
        Button button=findViewById(R.id.Button_returnClubPage);
        button.setOnClickListener(this);
        //设置修改社团信息按钮
        Button button2=findViewById(R.id.changeInfo);
        button2.setOnClickListener(this);
        //设置加入社团、退出社团
        Button button1=findViewById(R.id.joinClub);
        if(judge.equals("1"))//已加入社团
        {
            button1.setText("退出社团");
            button1.setBackgroundColor(Color.parseColor("#ff6347"));
        }
        button1.setOnClickListener(this);
        //设置查看社团成员
        LinearLayout button3=findViewById(R.id.club_index_member);
        button3.setOnClickListener(this);
    }
}
