package activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import bean.ChatLayout;
import bean.ClubMember;
import bean.member_box;
import com.example.myapplication.R;
import net.asyncCall;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.zip.Inflater;

public class MemberListActivity extends AppCompatActivity implements View.OnClickListener{

    String []rankList={"用户","管理员","社长"};
    public void onClick(View view) {
        switch (view.getId())
        {
            case(R.id.Button_returnClubPage):{
                finish();
                break;
            }
        }
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_list);
        //绑定返回按钮
        Button button=findViewById(R.id.Button_returnClubPage);
        button.setOnClickListener(this);
        //动态添加成员列表
        Intent intent=this.getIntent();
        ClubMember memberList=(ClubMember) intent.getSerializableExtra("memberList");
        String clubId=intent.getStringExtra("clubId");
        String rank=intent.getStringExtra("rank");
        LinearLayout Frame=findViewById(R.id.Member_box);
        LayoutInflater layoutInflater = LayoutInflater.from(MemberListActivity.this);
        Iterator<String> iterator1=memberList.Member_Name.iterator();
        Iterator<String> iterator2=memberList.Member_Id.iterator();
        Iterator<String> iterator3=memberList.Rank.iterator();
        while(iterator1.hasNext())
        {
            String name=iterator1.next();
            String id=iterator2.next();
            String member_rank=iterator3.next();
            member_box memberBox = (member_box) layoutInflater.inflate(R.layout.member_box_real, null, false);
            memberBox.setMember_Name(name);
            memberBox.setMember_Id(id);
            memberBox.setMember_Rank(rankList[Integer.parseInt(member_rank)]);
            memberBox.setButton1(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(rank.equals("0"))
                    {
                        AlertDialog alertDialog1 = new AlertDialog.Builder(MemberListActivity.this)
                                .setTitle("警告")//标题
                                .setMessage("您没有足够权限！")//内容
                                .create();
                        alertDialog1.show();
                    }
                    else {
                        asyncCall call = new asyncCall();
                        Map<String, String> res = new HashMap<>();
                        res.put("as_id", clubId);
                        res.put("user_id", id);
                        res.put("manager_id", "0");
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                call.deleteAsync("/manager", res);
                            }
                        }).start();
                        AlertDialog alertDialog1 = new AlertDialog.Builder(MemberListActivity.this)
                                .setTitle("通知")//标题
                                .setMessage("已踢出该成员！")//内容
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        finish();
                                    }
                                })
                                .create();
                        alertDialog1.show();
                    }
                }
            });
            memberBox.setButton2(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(rank.equals("0")||rank.equals("1"))
                    {
                        AlertDialog alertDialog1 = new AlertDialog.Builder(MemberListActivity.this)
                                .setTitle("警告")//标题
                                .setMessage("只有社长能够更改权限！")//内容
                                .create();
                        alertDialog1.show();
                    }
                    else {
                        asyncCall call = new asyncCall();
                        Map<String, String> res = new HashMap<>();
                        res.put("as_id", clubId);
                        res.put("user_id", id);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                call.putAsync("/changeRank", res);
                            }
                        }).start();
                        AlertDialog alertDialog1 = new AlertDialog.Builder(MemberListActivity.this)
                                .setTitle("通知")//标题
                                .setMessage("进行权限更改！")//内容
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        finish();
                                    }
                                })
                                .create();
                        alertDialog1.show();
                    }
                }
            });
            Frame.addView(memberBox);
        }
    }
}
