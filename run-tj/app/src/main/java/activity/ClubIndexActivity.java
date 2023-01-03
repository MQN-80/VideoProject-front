package activity;

import Utils.ACache;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication.R;
import net.asyncCall;

import java.util.HashMap;
import java.util.Map;

public class ClubIndexActivity extends AppCompatActivity //社团首页
{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_index);
        Intent intent=this.getIntent();
        String name=intent.getStringExtra("ClubData");
        TextView textView=findViewById(R.id.Club_Name);
        textView.setText(name);
        Button button=findViewById(R.id.Button_returnClubPage);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}
