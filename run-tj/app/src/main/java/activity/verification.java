package activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import androidx.navigation.ui.AppBarConfiguration;
import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityVerificationBinding;

public class verification extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityVerificationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        Button submit=findViewById(R.id.verify_submit);  //监听验证码输入
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText phone=findViewById(R.id.verify_phone);
                EditText code=findViewById(R.id.verify_code);
                if(phone.getText().toString()!=""&&code.getText().toString()!="")   //假如都不为空，则进行密码验证
                {
                    if(true)   //此处为验证码接口,验证成功则跳转到注册页面
                    {
                        Intent intent=new Intent();
                        intent.setClass(verification.this, RegisterActivity.class);
                        startActivity(intent);
                    }
                    else   //验证失败清空输入再次输入
                    {
                        phone.setText("");
                        code.setText("");
                    }

                }
                else
                {
                    phone.setText("");
                    code.setText("");
                }
            }
        });
    }
}