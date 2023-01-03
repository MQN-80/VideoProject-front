package activity;

import Utils.ACache;
import Utils.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.myapplication.databinding.ActivityRegisterBinding;
import net.asyncCall;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ACache mCache;   //引入缓存
    private Context that=this;

    private boolean valid=false;   //判断手机验证是否成功
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);
        mCache = ACache.get(this);
        mCache.put("phone","");
        System.out.println(mCache.getAsString("phone"));
        listenVerify();
        is_valid();
    }
    //监听是否发出获取验证码请求
    public void listenVerify(){
        Button send=findViewById(R.id.verify_send);  //监听发送按钮
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String telRegex = "[1][34578]\\d{9}";
                asyncCall call=new asyncCall();
                EditText text=findViewById(R.id.phone_verify);   //获取手机号
                String mid=text.getText().toString();
                //验证手机号码是否为正常格式
                if(mid.matches(telRegex)){
                    Map<String,String> res=new HashMap<>();
                    res.put("phone",mid);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            call.postAsync("/sendMessage",res);
                        }
                    }).start();
                    mCache.put("phone",mid);
                    Dialog.showDialog("提示","验证码已发送",that);
                }
                else{
                    text.setText("");   //错误手机号则置空
                    Dialog.showDialog("提示","手机号错误",that);

                }
            }
        });

    }

    /**
     * 监听短信验证码确认按钮
     */
    public void is_valid(){
        Button send=findViewById(R.id.verify_ok);  //监听发送按钮
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText message=findViewById(R.id.verify_register);
                String mid=message.getText().toString();
                if(mid.equals("")){
                    Dialog.showDialog("提示","请填写验证码",that);
                }
                else{
                    asyncCall call=new asyncCall();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            ArrayList<String>res=new ArrayList<>();
                            EditText text=findViewById(R.id.phone_verify);
                            String phone=text.getText().toString();
                            if(!phone.equals("")){
                                res.add(phone);
                                res.add(mid);
                                Response response=call.getAsync("/checkCode",res);
                                String mid= null;
                                try {
                                    mid = response.body().string();
                                    if(mid.equals("success"))
                                        userinfoSubmit();   //验证成功将其改为true
                                    else{
                                        message.setText("");
                                        Message msg = new Message();
                                        msg.what = 1;
                                        handle.sendMessage(msg);
                                    }

                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                System.out.println(mid);
                            }
                            else{
                                Message msg = new Message();
                                msg.what = 0;
                                handle.sendMessage(msg);
                            }
                        }
                    }).start();
                }

            }
        });

    }
    public void jump() {
        Intent intent = new Intent();
        intent.setClass(RegisterActivity.this, MainActivity.class);
        startActivity(intent);
    }
   public Handler handle =new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    Dialog.showDialog("提示","请填写手机号",that);
                    break;
                case 1:
                    Dialog.showDialog("提示","验证码错误",that);
                    break;
            }
        };
    };
    //上传用户信息
    public void userinfoSubmit(){
            EditText phone = findViewById(R.id.phone_verify);
            EditText user_name = findViewById(R.id.register_name);
            EditText user_password = findViewById(R.id.register_password);
            String phones = phone.getText().toString();
            String username = user_name.getText().toString();
            String password = user_password.getText().toString();
            if (username.equals("") || password.equals("")) {
                Dialog.showDialog("提示", "请填写完整后再注册", this);
            } else {
                asyncCall call = new asyncCall();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Map<String,String>info=new HashMap<>();
                        info.put("name",username);
                        info.put("phone",phones);
                        info.put("password",password);
                        info.put("avator","http://106.12.131.109:8083/avator/E6F83FB5674D78A9E05011AC0200343C.jpg");
                        call.postAsync("/user",info);
                    }

                }).start();
            }
            jump();
        }

    }








