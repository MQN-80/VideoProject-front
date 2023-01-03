package activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication.R;
import net.asyncCall;
import okhttp3.Response;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class physicalActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_physical);
        //取到身高、体重、BMI、腰围、胸围、臀围的位置
        TextView l2ine1_2 = findViewById(R.id.l2ine1_2);
        TextView l2ine2_2 = findViewById(R.id.l2ine2_2);
        TextView l2ine3_2 = findViewById(R.id.l2ine3_2);
        TextView l2ine4_2 = findViewById(R.id.l2ine4_2);
        TextView l2ine5_2 = findViewById(R.id.l2ine5_2);
        TextView l2ine6_2 = findViewById(R.id.l2ine6_2);

        //设置返回属性
        ImageView backToCenter = findViewById(R.id.backToCenter);
        backToCenter.setOnClickListener(this);

        //设置编辑
        TextView edit = findViewById(R.id.edit_physical);
        edit.setOnClickListener(this);

        final Handler handle =new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        JSONObject object=(JSONObject)msg.obj;
                        // Json处理器
                        try {
                            String height = object.getString("height");
                            String weight = object.getString("weight");
                            String BMI = object.getString("bmi");
                            String bust = object.getString("bust");
                            String waist = object.getString("waist");
                            String hipline = object.getString("hipline");
                            l2ine1_2.setText(height);
                            l2ine2_2.setText(weight);
                            l2ine3_2.setText(BMI);
                            l2ine4_2.setText(bust);
                            l2ine5_2.setText(waist);
                            l2ine6_2.setText(hipline);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                }
            };

        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                asyncCall asyncCall = new asyncCall();
                ArrayList<String> idList = new ArrayList<>();
                // 获取要查询记录的id
                idList.add("10");
                // 返回response解析Json
                Response response = asyncCall.getAsync("/physical",idList);
                try{
                    JSONObject object = new JSONObject(Objects.requireNonNull(response.body()).string());
                    Message msg = new Message();
                    msg.what = 0;
                    msg.obj = object;
                    handle.sendMessage(msg);
                }
                catch (IOException | JSONException e){
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.backToCenter:{
                Intent intent = new Intent(physicalActivity.this,UserCenterActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.edit_physical:{
                Intent intent = new Intent(physicalActivity.this,physicalEditActivity.class);
                startActivity(intent);
                break;
            }
        }
    }
}
