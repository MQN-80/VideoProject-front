package activity;

import Service.MessageService;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import com.example.myapplication.MainActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.myapplication.R;

public class UserCenterActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_center);

        // 获取页面上的底部导航栏控件
        BottomNavigationView navView = findViewById(R.id.nav_view);

        // 配置navigation与底部菜单之间的联系
        // 底部菜单的样式里面的item里面的ID与navigation布局里面指定的ID必须相同，否则会出现绑定失败的情况
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_data,R.id.navigation_club,R.id.navigation_run,R.id.navigation_center)
                .build();
        // 建立fragment容器的控制器，这个容器就是页面的上的fragment容器
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        // 启动

        //这一行有错，不知道有啥用
        NavigationUI.setupActionBarWithNavController(this,navController, appBarConfiguration);

        NavigationUI.setupWithNavController(navView, navController);
    }


}