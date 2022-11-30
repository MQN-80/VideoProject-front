package activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import androidx.appcompat.app.AppCompatActivity;
import bean.ChatBox_right;
import bean.Record_box;
import com.example.myapplication.R;

public class RunRecordActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_run_record);

        // 找到记录栏
        LinearLayout recordFrame=findViewById(R.id.recordBox);

        Record_box record_box;
        // 动态添加
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        record_box = (Record_box) layoutInflater.inflate(R.layout.record_box_real,null,false);
        recordFrame.addView(record_box);
    }

    @Override
    public void onClick(View view) {

    }
}
