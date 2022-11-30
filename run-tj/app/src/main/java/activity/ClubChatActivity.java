package activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import bean.ChatBox_left;
import bean.ChatBox_right;
import com.example.myapplication.R;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ClubChatActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_chat);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        LinearLayout Frame = findViewById(R.id.chat_ChatBox);
        Button button=findViewById(R.id.Button_returnClubPage);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Intent intent=this.getIntent();
        String name=intent.getStringExtra("ClubData");
        TextView textView=findViewById(R.id.chat_ClubName);
        textView.setText(name);
        Map<String,String> Arr=new HashMap<>();
        Arr.put("金逸 21:01:08","我来全写了");
        Arr.put("金逸 21:01:57","OK我写完了");
        Arr.put("展驰 21:02:23","写写你的");
        Arr.put("陈柯羲 21:03:08","恁强啊");
        Arr.put("龚况驰宇 21:04:16","太强");
        Arr.put("金逸 21:05:27","我保研了，而且帮你们一起保了");
        Arr.put("展驰 21:08:08","okok");
        Arr.put("陈柯羲 21:08:08","okok");
        Arr.put("龚况驰宇 21:08:08","okok");
        Arr.put("哈陛下 21:08:08","okok");
        Arr.put("欧西给 21:08:08","okok");
        Arr.put("虎哥 21:08:08","okok");
        Iterator<String> iterator=Arr.keySet().iterator();
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        while(iterator.hasNext())
        {
            String key=iterator.next();
            if(key.contains("陈柯羲"))
            {
                ChatBox_right chatBox_right=(ChatBox_right) layoutInflater.inflate(R.layout.chat_box_right_real, null, false);
                chatBox_right.setUser_Name(key);
                chatBox_right.setUser_Chat(Arr.get(key));
                Frame.addView(chatBox_right);
            }
            else
            {
                ChatBox_left chatBox_left=(ChatBox_left) layoutInflater.inflate(R.layout.chat_box_left_real, null, false);
                chatBox_left.setUser_Name(key);
                chatBox_left.setUser_Chat(Arr.get(key));
                Frame.addView(chatBox_left);
            }
        }
    }
}
