package activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
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
                asyncCall call=new asyncCall();
                Map<String,String> res=new HashMap<>();
                res.put("desc",editText2.getText().toString());
                res.put("name",editText1.getText().toString());
                res.put("user_id","6");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        call.postAsync("/club",res);
                    }
                }).start();
                finish();
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
