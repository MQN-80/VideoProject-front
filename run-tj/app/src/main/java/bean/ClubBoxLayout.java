package bean;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.myapplication.R;

public class ClubBoxLayout extends LinearLayout {
    private ImageView Icon;
    private TextView Club_Name;

    public ClubBoxLayout(Context context, AttributeSet attrs){
        super(context,attrs);
        LayoutInflater.from(context).inflate(R.layout.club_list,this);
        Icon=findViewById(R.id.ClubIcon);
        Club_Name=findViewById(R.id.ClubName);
    }

    public void setClub_Name(String club_Name) {
        Club_Name.setText(club_Name);
    }

    public void setIcon(Bitmap icon) {
        Icon.setImageBitmap(icon);
    }
}
