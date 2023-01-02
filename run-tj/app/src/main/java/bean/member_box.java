package bean;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.myapplication.R;

public class member_box extends LinearLayout {
    private TextView Member_Id;
    private TextView Member_Name;
    private TextView Member_Rank;

    private Button button1;
    private Button button2;

    public member_box(Context context, AttributeSet attrs){
        super(context,attrs);
        LayoutInflater.from(context).inflate(R.layout.member_box,this);
        Member_Id=findViewById(R.id.MemberId);
        Member_Name=findViewById(R.id.MemberName);
        Member_Rank=findViewById(R.id.MemberPhone);
        button1=findViewById(R.id.quitClubButton);
        button2=findViewById(R.id.changeRankButton);
    }

    public TextView getMember_Id() {
        return Member_Id;
    }

    public TextView getMember_Name() {
        return Member_Name;
    }

    public TextView getMember_Rank() {
        return Member_Rank;
    }

    public Button getButton1() {
        return button1;
    }

    public Button getButton2(){return button2;}

    public void setMember_Id(String member_Id) {
        Member_Id.setText(member_Id);
    }
    public void setMember_Name(String member_Name) {
        Member_Name.setText(member_Name);
    }
    public void setMember_Rank(String member_Rank) {
        Member_Rank.setText(member_Rank);
    }

    public void setButton1(OnClickListener onClickListener) {
        this.button1.setOnClickListener(onClickListener);
    }
    public void setButton2(OnClickListener onClickListener) {
        this.button2.setOnClickListener(onClickListener);
    }
}
