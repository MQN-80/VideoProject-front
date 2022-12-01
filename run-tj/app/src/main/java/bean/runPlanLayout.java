package bean;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import com.example.myapplication.R;

public class runPlanLayout extends LinearLayout {
    public runPlanLayout(Context context, AttributeSet attrs){
        super(context,attrs);
        LayoutInflater.from(context).inflate(R.layout.club_chat,this);
    }
}
