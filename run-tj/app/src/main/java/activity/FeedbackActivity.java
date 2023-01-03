package activity;

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

public class FeedbackActivity extends AppCompatActivity implements View.OnClickListener{

    EditText editText;

    String as_id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_feedback);
        Intent intent = this.getIntent();
        //设置返回按钮
        ImageView returnButton=findViewById(R.id.backToCenter3);
        returnButton.setOnClickListener(this);

        //绑定反馈信息输入框
        editText = findViewById(R.id.input_feedback);
        //绑定修改按钮
        Button button = findViewById(R.id.input_PushFeedback);
        button.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case(R.id.backToCenter3):{
                finish();
                break;
            }
            case(R.id.input_PushFeedback):{
                asyncCall call=new asyncCall();
                Map<String,String> res=new HashMap<>();
                res.put("id",as_id);
                res.put("content",editText.getText().toString());
                Log.i("FeedBackInfo",res.toString());
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        call.postAsync("/feedback",res);
                    }
                }).start();
                finish();
                break;
            }
        }
    }
}
