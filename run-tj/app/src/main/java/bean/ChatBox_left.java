package bean;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.myapplication.R;

public class ChatBox_left extends LinearLayout {
    private ImageView HeadIcon;
    private TextView User_Name;
    private TextView User_Chat;

    public ChatBox_left(Context context, AttributeSet attrs){
        super(context,attrs);
        LayoutInflater.from(context).inflate(R.layout.chat_box_left,this);
        HeadIcon=findViewById(R.id.chat_HeadIcon_Left);
        User_Name=findViewById(R.id.chat_UserName_Left);
        User_Chat=findViewById(R.id.chat_UserBox_Left);
    }

    public void setHeadIcon(Bitmap headIcon) {
        HeadIcon.setImageBitmap(headIcon);
    }

    public void setUser_Chat(String user_Chat) {
        User_Chat.setText(user_Chat);
    }

    public void setUser_Name(String user_Name) {
        User_Name.setText(user_Name);
    }
}
