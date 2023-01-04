package activity;

import Utils.ACache;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication.R;
import net.asyncCall;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UserInfoEditActivity extends AppCompatActivity implements View.OnClickListener{

    EditText editName;
    EditText editGender;
    EditText editEmail;
    EditText editBirthday;
    EditText editDetail;
    String as_id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_user_info_edit);
        //设置返回按钮
        ImageView returnButton=findViewById(R.id.UserInfoEditBackToCenter);
        returnButton.setOnClickListener(this);
        // 绑定输入框
        editName = findViewById(R.id.editName);
        editGender = findViewById(R.id.editGender);
        editEmail = findViewById(R.id.editEmail);
        editBirthday = findViewById(R.id.editBirthday);
        editDetail = findViewById(R.id.editDetail);
        //绑定修改按钮
        Button button = findViewById(R.id.input_editUserInfo);
        button.setOnClickListener(this);
        //绑定换头像
        ImageView avatar=findViewById(R.id.avatarInEdit);
        avatar.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case (R.id.UserInfoEditBackToCenter): {
                finish();
                break;
            }
            case(R.id.input_editUserInfo):{
                ACache mCache=ACache.get(UserInfoEditActivity.this);
                asyncCall call=new asyncCall();
                Map<String,String> res=new HashMap<>();
                res.put("id",mCache.getAsString("user_id"));
                if(!editBirthday.getText().toString().isEmpty()){
                    res.put("birthday",editBirthday.getText().toString());
                }else{
                    res.put("birthday",mCache.getAsString("birthday"));
                }
                if(!editDetail.getText().toString().isEmpty()){
                    res.put("detail",editDetail.getText().toString());
                }else{
                    res.put("detail",mCache.getAsString("detail"));
                }
                if(!editEmail.getText().toString().isEmpty()){
                    res.put("email",editEmail.getText().toString());
                }else{
                    res.put("email",mCache.getAsString("email"));
                }
                if(!editGender.getText().toString().isEmpty()){
                    res.put("gender",editGender.getText().toString());
                }else{
                    res.put("gender",mCache.getAsString("gender"));
                }
                if(!editName.getText().toString().isEmpty()){
                    res.put("name",editName.getText().toString());
                }else{
                    res.put("name",mCache.getAsString("name"));
                }
                Log.i("UserInfoEditInfo",res.toString());
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        call.putAsync("/user",res);
                    }
                }).start();
                finish();
                break;
            }
            case(R.id.avatarInEdit): {
                Intent intent = new Intent(UserInfoEditActivity.this, testActivity.class);
                startActivity(intent);
                break;
            }
        }
    }
}
