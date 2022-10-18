package com.example.myapplication;

import Utils.PoseRecognition;
import activity.UserCenterActivity;
import activity.verification;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import androidx.navigation.ui.AppBarConfiguration;
import bean.LoginEntity;
import com.google.gson.Gson;
import net.asyncCall;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Thread(new Runnable() {
            @Override
            public void run() {
                asyncCall a=new asyncCall("http://10.0.2.2:8081/");
                ArrayList<String> a1=new ArrayList<>();
                a1.add("4");
                Response b=a.getAsync("user",a1);
            }
        }).start();
        PoseRecognition mid=new PoseRecognition();
        mid.load_model(this);
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
                            Response mid=login.postAsync("/Login",params);
                            Gson gson=new Gson();
                            LoginEntity result= null;
                            try {
                                result = gson.fromJson(mid.body().string(), LoginEntity.class);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            //假如200则表示登录成功,进行缓存,
                            if(result.data!=null) {
                                Intent intent=new Intent();
                                intent.setClass(MainActivity.this, UserCenterActivity.class);
                                startActivity(intent);
                            }
                            else{
                                info.setText("");
                                password.setText("");
                            }

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