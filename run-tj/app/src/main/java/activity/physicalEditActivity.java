package activity;

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

public class physicalEditActivity extends AppCompatActivity implements View.OnClickListener{

    EditText editHeight;
    EditText editWeight;
    EditText editBmi;
    EditText editBust;
    EditText editWaist;
    EditText editHipline;
    String as_id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_physical_edit);
        //设置返回按钮
        ImageView returnButton=findViewById(R.id.PhysicalEditBackToCenter);
        returnButton.setOnClickListener(this);
        //绑定输入框
        editHeight = findViewById(R.id.edit_height);
        editWeight = findViewById(R.id.edit_weight);
        editBmi = findViewById(R.id.edit_bmi);
        editBust = findViewById(R.id.edit_bust);
        editWaist = findViewById(R.id.edit_waist);
        editHipline = findViewById(R.id.edit_hipline);
        //绑定修改按钮
        Button button = findViewById(R.id.input_editPhysical);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case (R.id.PhysicalEditBackToCenter): {
                finish();
                break;
            }
            case(R.id.input_editPhysical):{
                asyncCall call=new asyncCall();
                Map<String,String> res=new HashMap<>();
                res.put("id",as_id);
                res.put("height",editHeight.getText().toString());
                res.put("weight",editWeight.getText().toString());
                res.put("bmi",editBmi.getText().toString());
                res.put("bust",editBust.getText().toString());
                res.put("waist",editWaist.getText().toString());
                res.put("hipline",editHipline.getText().toString());
                Log.i("physicalEditInfo",res.toString());
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        call.postAsync("/physical",res);
                    }
                }).start();
                finish();
                break;
            }
        }
    }
}
