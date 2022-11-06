package bean;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.myapplication.R;

public class HeadFrameLayout  extends LinearLayout {
    private ImageView HeadIcon;
    private TextView User_Name;
    private TextView User_State;
    private Button HeadFrameButton;

    public HeadFrameLayout(Context context, AttributeSet attrs){
        super(context,attrs);
        LayoutInflater.from(context).inflate(R.layout.club_headframe,this);
        HeadIcon=findViewById(R.id.HeadIcon);
        User_Name=findViewById(R.id.UserName);
        User_State=findViewById(R.id.UserState);
        HeadFrameButton=findViewById(R.id.HeadFrameButton);
    }

    public void setUser_Name(String user_name) {
        User_Name.setText(user_name);
    }

    public void setUser_State(String user_state) {
        User_State.setText(user_state);
    }

    public void setHeadIcon(Bitmap icon) {
        HeadIcon.setImageBitmap(icon);
    }
}
