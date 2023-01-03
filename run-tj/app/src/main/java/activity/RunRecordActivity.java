package activity;

import Utils.ACache;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import androidx.appcompat.app.AppCompatActivity;
import bean.ChatBox_right;
import bean.Record_box;
import com.example.myapplication.R;
import net.asyncCall;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class RunRecordActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_run_record);

        // 找到记录栏
        LinearLayout recordFrame=findViewById(R.id.recordBox);

        //Record_box record_box;
        LayoutInflater layoutInflater = LayoutInflater.from(RunRecordActivity.this);
        final Handler handle =new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        try {
                            JSONObject jsonObject = (JSONObject) msg.obj;
                            String month_day_temp = jsonObject.getString("createTime");
                            // 取月日
                            String month_day = month_day_temp.substring(5, 10);
                            // 取时间
                            String time = month_day_temp.substring(11, 19);
                            String distance = jsonObject.getString("distance");
                            String pace = jsonObject.getString("pace");
                            String runtime = jsonObject.getString("time");
                            String status = "未支付"; //end of 取

                            Record_box record_box_inside; // 每次新建一个
                            record_box_inside = (Record_box) layoutInflater.inflate(R.layout.record_box_real, null, false);
                            record_box_inside.setMonth_day(month_day);
                            record_box_inside.setTime(time);
                            record_box_inside.setDistance(distance);
                            record_box_inside.setPace(pace);
                            record_box_inside.setRun_time(runtime);
                            record_box_inside.setStatus(status);
                            recordFrame.addView(record_box_inside);
                        }
                        catch (JSONException e){
                            throw new RuntimeException(e);
                        };
                        break;
                }
            };

        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                ACache mCache=ACache.get(RunRecordActivity.this);
                asyncCall asyncCall = new asyncCall();
                ArrayList<String> idList = new ArrayList<>();
                //Record_box record_box_inside;
                // 获取要查询记录的id
                idList.add(mCache.getAsString("user_id"));
                // 返回response解析Json
                Response response = asyncCall.getAsync("/record",idList);
                try{
                    JSONArray jsonArray = new JSONArray(Objects.requireNonNull(response.body()).string());
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Message msg = new Message();
                        msg.what = 0;
                        msg.obj = jsonObject;
                        handle.sendMessage(msg);
                    }
                }
                catch (IOException | JSONException e){
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    @Override
    public void onClick(View view) {

    }
}
