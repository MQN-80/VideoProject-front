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
    private TextView Member_Phone;

    private Button button;

    public member_box(Context context, AttributeSet attrs){
        super(context,attrs);
        LayoutInflater.from(context).inflate(R.layout.member_box,this);
        Member_Id=findViewById(R.id.MemberId);
        Member_Name=findViewById(R.id.MemberName);
        Member_Phone=findViewById(R.id.MemberPhone);
        button=findViewById(R.id.quitClubButton);
    }

    public TextView getMember_Id() {
        return Member_Id;
    }

    public TextView getMember_Name() {
        return Member_Name;
    }

    public TextView getMember_Phone() {
        return Member_Phone;
    }

    public Button getButton() {
        return button;
    }

    public void setMember_Id(String member_Id) {
        Member_Id.setText(member_Id);
    }
    public void setMember_Name(String member_Name) {
        Member_Name.setText(member_Name);
    }
    public void setMember_Phone(String member_Phone) {
        Member_Phone.setText(member_Phone);
    }

    public void setButton(OnClickListener onClickListener) {
        this.button.setOnClickListener(onClickListener);
    }
}
