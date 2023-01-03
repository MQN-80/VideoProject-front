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

public class SendPostActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editText1;

    public void onClick(View view) {
        switch (view.getId())
        {
            case(R.id.Button_returnClubPage):{
                finish();
                break;
            }
            case(R.id.input_sendPost):{
                asyncCall call=new asyncCall();
                Map<String,String> res=new HashMap<>();
                ACache mCache=ACache.get(this);
                String token=mCache.getAsString("token");
                Map<String,String> head=new HashMap<>();
                head.put("satoken",token);
                res.put("context",editText1.getText().toString());
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        call.postAsync("/article",head,res);
                    }
                }).start();
                AlertDialog alertDialog1 = new AlertDialog.Builder(this)
                        .setTitle("通知")//标题
                        .setMessage("动态发表成功！")//内容
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent2 = new Intent("android.intent.action.CART_BROADCAST");
                                intent2.putExtra("data","refresh1");
                                LocalBroadcastManager.getInstance(SendPostActivity.this).sendBroadcast(intent2);
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
        setContentView(R.layout.send_post);
        //设置返回按钮
        Button returnButton=findViewById(R.id.Button_returnClubPage);
        returnButton.setOnClickListener(this);
        //绑定动态输入框
        editText1=findViewById(R.id.input_post);
        //绑定发表动态按钮
        Button button=findViewById(R.id.input_sendPost);
        button.setOnClickListener(this);
    }
}
