package bean;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.myapplication.R;

public class Follow_box extends LinearLayout {

    private ImageView avatarInFollow;
    private TextView nameInFollow;
    private TextView statusInFollow;


    public Follow_box(Context context, AttributeSet attrs){
        super(context,attrs);
        LayoutInflater.from(context).inflate(R.layout.follow_box,this);
        avatarInFollow = findViewById(R.id.avatarInFollow);
        nameInFollow = findViewById(R.id.nameInFollow);
        statusInFollow = findViewById(R.id.statusInFollow);
    }

    public void setAvatarInFollow(Bitmap avatarInFollow2) {
        avatarInFollow.setImageBitmap(avatarInFollow2);
    }

    public void setNameInFollow(String nameInFollow2) {
        nameInFollow.setText(nameInFollow2);
    }

    public void setStatusInFollow(String statusInFollow2) {
        statusInFollow.setText(statusInFollow2);
    }

    public void bindNameInFollow(OnClickListener onClickListener){
        nameInFollow.setOnClickListener(onClickListener);
    }

    public void bindStatusInFollow(OnClickListener onClickListener){
        statusInFollow.setOnClickListener(onClickListener);
    }
}
