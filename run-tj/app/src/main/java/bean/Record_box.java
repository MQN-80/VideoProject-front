package bean;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.myapplication.R;

public class Record_box extends LinearLayout {

    private TextView month_day;
    private TextView time;
    private TextView distance;
    private TextView run_time;
    private TextView pace;
    private TextView status;

    public Record_box(Context context, AttributeSet attrs){
        super(context,attrs);
        LayoutInflater.from(context).inflate(R.layout.record_box,this);
        month_day = findViewById(R.id.month_day);
        time = findViewById(R.id.time);
        distance = findViewById(R.id.distance);
        run_time = findViewById(R.id.run_time);
        pace = findViewById(R.id.pace);
        status = findViewById(R.id.status);
    }

    public void setMonth_day(String month_day1) {
        month_day.setText(month_day1);
    }

    public void setTime(String time1) {
        time.setText(time1);
    }

    public void setDistance(String distance1) {
        distance.setText(distance1);
    }

    public void setRun_time(String run_time1) {
        run_time.setText(run_time1);
    }

    public void setPace(String pace1) {
        pace.setText(pace1);
    }

    public void setStatus(String status1) {
        status.setText(status1);
    }
}
