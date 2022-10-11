package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import androidx.navigation.ui.AppBarConfiguration;
import com.example.myapplication.databinding.ActivityMainBinding;
import net.asyncCall;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Thread(new Runnable() {
            @Override
            public void run() {
                asyncCall a=new asyncCall("http://10.0.2.2:8081/");
                ArrayList<String> a1=new ArrayList<>();
                a1.add("4");
                Log.d(TAG, "111");
                a.getAsync("user",a1);
            }
        }).start();

        setContentView(R.layout.activity_main);

        //监听跳转按钮
        TextView button=findViewById(R.id.tv_register);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent();
                intent.setClass(MainActivity.this, verification.class);
                startActivity(intent);
            }
        });
        Button login=findViewById(R.id.login);  //监听登录按钮
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }
    public void login(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                EditText info=findViewById(R.id.login_info);
                EditText password=findViewById(R.id.login_password);
                if(info.getText().toString()!=""&&password.getText().toString()!="")   //假如都不为空，则进行密码验证
                {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            asyncCall login=new asyncCall();
                            Map<String, String> params=new HashMap<String, String>();
                            params.put("phone",info.getText().toString());
                            params.put("password",password.getText().toString());
                            login.postAsync("/Login",params);
                        }
                    }).start();

                }
                else
                {
                    info.setText("");
                    password.setText("");
                }
            }
        }).start();
    }

}