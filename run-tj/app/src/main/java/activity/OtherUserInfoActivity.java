package activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication.R;

public class OtherUserInfoActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_other_user_info);
        Intent intent = this.getIntent();
        String user_id = intent.getStringExtra("id");

    }
    @Override
    public void onClick(View view) {

    }
}
