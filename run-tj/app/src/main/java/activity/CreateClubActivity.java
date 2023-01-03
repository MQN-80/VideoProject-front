package activity;

import Utils.ACache;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.example.myapplication.R;
import net.asyncCall;

import java.util.HashMap;
import java.util.Map;

public class CreateClubActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editText1;
    EditText editText2;
    public void onClick(View view) {
        switch (view.getId())
        {
            case(R.id.Button_returnClubPage):{
                finish();
                break;
            }
            case(R.id.input_createClub):{
                ACache mCache=ACache.get(this);
                asyncCall call=new asyncCall();
                Map<String,String> res=new HashMap<>();
                res.put("desc",editText2.getText().toString());
                res.put("name",editText1.getText().toString());
                res.put("user_id",mCache.getAsString("user_id"));
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        call.postAsync("/club",res);
                    }
                }).start();
                AlertDialog alertDialog1 = new AlertDialog.Builder(this)
                        .setTitle("通知")//标题
                        .setMessage("社团创建成功！")//内容
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent2 = new Intent("android.intent.action.CART_BROADCAST");
                                intent2.putExtra("data","refresh");
                                LocalBroadcastManager.getInstance(CreateClubActivity.this).sendBroadcast(intent2);
                                sendBroadcast(intent2);
                                finish();
                            }
                        })
                        .create();
                alertDialog1.show();
                break;
            }
        }
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_club);
        //设置返回按钮
        Button returnButton=findViewById(R.id.Button_returnClubPage);
        returnButton.setOnClickListener(this);
        //绑定社团名字输入框
        editText1=findViewById(R.id.input_clubName);
        //绑定社团描述输入框
        editText2=findViewById(R.id.input_clubDesc);
        //绑定创建社团按钮
        Button button=findViewById(R.id.input_createClub);
        button.setOnClickListener(this);
    }
}
