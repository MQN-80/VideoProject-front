package bean;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.example.myapplication.R;

public class ChatLayout extends LinearLayout {
    private ImageView Icon;
    private TextView Club_Name;
    private TextView Club_Chat;
    private TextView Time;

    public ChatLayout(Context context, AttributeSet attrs){
        super(context,attrs);
        LayoutInflater.from(context).inflate(R.layout.club_chat,this);
        Icon=findViewById(R.id.ClubIcon);
        Club_Name=findViewById(R.id.ClubName);
        Club_Chat=findViewById(R.id.ClubDesc);
        Time=findViewById(R.id.ClubTime);
    }

    public void setClub_Name(String club_Name) {
        Club_Name.setText(club_Name);
    }

    public void setClub_Chat(String club_Chat) {
        Club_Chat.setText(club_Chat);
    }

    public void setIcon(Bitmap icon) {
        Icon.setImageBitmap(icon);
    }

    public void setTime(String time) {
        Time.setText(time);;
    }
}
