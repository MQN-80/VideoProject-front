package bean;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.myapplication.R;

public class CommunityBox extends LinearLayout {
    private ImageView Icon;

    private TextView User_Name;

    private TextView Create_Time;

    private TextView Community_Box;

    public CommunityBox(Context context, AttributeSet attrs){
        super(context,attrs);
        LayoutInflater.from(context).inflate(R.layout.community_box,this);
        Icon=findViewById(R.id.Community_UserIcon);
        User_Name=findViewById(R.id.Community_UserName);
        Create_Time=findViewById(R.id.Community_CreateTime);
        Community_Box=findViewById(R.id.Community_TextBox);
    }

    public void setCommunity_Box(String CommunityBox) {
        Community_Box.setText(CommunityBox);
    }

    public void setCreate_Time(String CreateTime) {
        Create_Time.setText(CreateTime);
    }

    public void setUser_Name(String UserName) {
        User_Name.setText(UserName);
    }

    public void setIcon(Bitmap icon) {
        Icon.setImageBitmap(icon);
    }
}
