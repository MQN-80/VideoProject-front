package activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication.R;
import net.asyncCall;

import java.util.HashMap;
import java.util.Map;

public class ClubInfoChangeActivity extends AppCompatActivity implements View.OnClickListener {

    Button button;
    EditText editText;
    String as_id;

    TextView textView;
    public void onClick(View view) {
        switch (view.getId())
        {
            case(R.id.Button_returnClubPage):{
                finish();
                break;
            }
            case(R.id.input_changeInfo):{
                asyncCall call=new asyncCall();
                Map<String,String> res=new HashMap<>();
                res.put("id",as_id);
                res.put("info",editText.getText().toString());
                res.put("type",textView.getText().toString());
                Log.i("ClubChangeInfo",res.toString());
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        call.postAsync("/clubInfo",res);
                    }
                }).start();
                AlertDialog alertDialog1 = new AlertDialog.Builder(this)
                        .setTitle("通知")//标题
                        .setMessage("社团信息修改成功！")//内容
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                })
                        .create();
                alertDialog1.show();
                break;
            }
            case(R.id.button_ChooseInfo):
            {
                ShowChoise();
                break;
            }
        }
    }
    private void ShowChoise()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(ClubInfoChangeActivity.this,android.R.style.Theme_Holo_Light_Dialog);
        //builder.setIcon(R.drawable.ic_launcher);
        builder.setTitle("请选择你要修改的社团信息类型");
        //    指定下拉列表的显示数据
        final String[] choices = {"name", "desc"};
        //    设置一个下拉的列表选择项
        builder.setItems(choices, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                textView.setText(choices[which]);
            }
        });
        builder.show();
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_info);
        Intent intent=this.getIntent();
        //设置返回按钮
        Button returnButton=findViewById(R.id.Button_returnClubPage);
        returnButton.setOnClickListener(this);
        //绑定修改信息选择按钮
        button=findViewById(R.id.button_ChooseInfo);
        button.setOnClickListener(this);
        //绑定社团信息输入框
        editText=findViewById(R.id.input_clubInfo);
        //绑定社团信息显示框
        textView=findViewById(R.id.Info_box);
        //获取社团id
        as_id=intent.getStringExtra("clubId");
        //绑定修改按钮
        Button button=findViewById(R.id.input_changeInfo);
        button.setOnClickListener(this);
    }
}