package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.myapplication.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        //监听跳转按钮
        TextView button=findViewById(R.id.tv_register);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent();
                intent.setClass(MainActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
        Button login=findViewById(R.id.login);  //监听登录按钮
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText info=findViewById(R.id.login_info);
                EditText password=findViewById(R.id.login_password);
                if(info.getText().toString()!=""&&password.getText().toString()!="")   //假如都不为空，则进行密码验证
                {

                }
                else
                {
                    info.setText("");
                    password.setText("");
                }
            }
        });
    }

}