package activity;

import Utils.ACache;
import android.content.DialogInterface;
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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
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

    Integer rank;

    String []rankList={"用户","管理员","社长"};
    public void onClick(View view) {
        ACache mCache=ACache.get(this);
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
                    res.put("user_id",mCache.getAsString("user_id"));
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            call.postAsync("/manager",res);
                        }
                    }).start();
                    AlertDialog alertDialog1 = new AlertDialog.Builder(this)
                            .setTitle("通知")//标题
                            .setMessage("加入社团成功！")//内容
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent2 = new Intent("android.intent.action.CART_BROADCAST");
                                    intent2.putExtra("data","refresh");
                                    LocalBroadcastManager.getInstance(ClubIndexActivity.this).sendBroadcast(intent2);
                                    sendBroadcast(intent2);
                                    finish();
                                }
                            })
                            .create();
                    alertDialog1.show();
                }
                else
                {
                    asyncCall call=new asyncCall();
                    Map<String,String> res=new HashMap<>();
                    res.put("as_id",id);
                    res.put("user_id",mCache.getAsString("user_id"));
                    res.put("manager_id","0");
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            call.deleteAsync("/manager",res);
                        }
                    }).start();
                    AlertDialog alertDialog1 = new AlertDialog.Builder(this)
                            .setTitle("通知")//标题
                            .setMessage("退出社团成功！")//内容
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent2 = new Intent("android.intent.action.CART_BROADCAST");
                                    intent2.putExtra("data","refresh");
                                    LocalBroadcastManager.getInstance(ClubIndexActivity.this).sendBroadcast(intent2);
                                    sendBroadcast(intent2);
                                    finish();
                                }
                            })
                            .create();
                    alertDialog1.show();
                }
                break;
            }
            case(R.id.club_index_member):{
                if(judge.equals("0"))
                {
                    AlertDialog alertDialog1 = new AlertDialog.Builder(this)
                            .setTitle("警告")//标题
                            .setMessage("请先加入社团！")//内容
                            .create();
                    alertDialog1.show();
                }
                else if(judge.equals("1")) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            asyncCall asyncCall = new asyncCall();
                            ArrayList<String> list = new ArrayList<>();
                            Map<String, String> res = new HashMap<>();
                            res.put("as_id", id);
                            res.put("rank", "0");
                            list.add(id);
                            list.add("0");
                            // 返回response解析Json
                            Response response = asyncCall.getAsync("/manager", list);
                            try {
                                ClubMember User = new ClubMember();
                                JSONArray jsonArray = new JSONArray(Objects.requireNonNull(response.body()).string());
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String name = jsonObject.getString("name");
                                    String id = jsonObject.getString("id");
                                    String member_rank = jsonObject.getString("rank");
                                    User.addClubMember(id, name, member_rank);
                                }
                                Log.i("ClubIndexActivity", User.Member_Id.toString());
                                Intent intent = new Intent(ClubIndexActivity.this, MemberListActivity.class);
                                intent.putExtra("memberList", User);
                                intent.putExtra("clubId", id);
                                intent.putExtra("rank", rank.toString());
                                startActivity(intent);
                            } catch (IOException | JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }).start();
                }
                break;
            }
            case(R.id.changeInfo):{
                if(judge.equals("0"))
                {
                    AlertDialog alertDialog1 = new AlertDialog.Builder(this)
                            .setTitle("警告")//标题
                            .setMessage("请先加入社团！")//内容
                            .create();
                    alertDialog1.show();
                }
                else if(judge.equals("1")) {
                    if (rank < 2) {
                        AlertDialog alertDialog1 = new AlertDialog.Builder(this)
                                .setTitle("警告")//标题
                                .setMessage("您没有足够权限！")//内容
                                .create();
                        alertDialog1.show();
                    } else if (rank == 2) {
                        Intent intent = new Intent(ClubIndexActivity.this, ClubInfoChangeActivity.class);
                        intent.putExtra("clubId", id);
                        startActivity(intent);
                        finish();
                    }
                }
                break;
            }
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_index);
        ACache mCache=ACache.get(this);
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
        //查询自己在该社团的权限
        if(judge.equals("1")) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    asyncCall asyncCall = new asyncCall();
                    ArrayList<String> list = new ArrayList<>();
                    list.add(mCache.getAsString("user_id"));
                    list.add(id);
                    // 返回response解析Json
                    Response response = asyncCall.getAsync("/getRank", list);
                    try {
                        rank = Integer.parseInt(Objects.requireNonNull(response.body()).string());
                        Log.i("权限为", rank.toString());
                        TextView textView=findViewById(R.id.Club_YourRank);
                        textView.setText("您在该社团的权限为："+rankList[rank]);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }).start();
        }
    }
}
