package Fragment;

import activity.UserCenterActivity;
import activity.verification;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;


public class dataFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_data, container, false);
    }
    @Override
    public void onStart(){
        super.onStart();
        listen_Select();

    }
    //监听点击了哪个跳转
    public void listen_Select(){
        ImageView img1=(ImageView)getActivity().findViewById(R.id.imageView2);
        ImageView img2=(ImageView)getActivity().findViewById(R.id.imageView3);
        ImageView img3=(ImageView)getActivity().findViewById(R.id.imageView4);
        ImageView img4=(ImageView)getActivity().findViewById(R.id.imageView5);
        ImageView img5=(ImageView)getActivity().findViewById(R.id.imageView6);
        jump_page(img3,MainActivity.class);
        jump_page(img4,MainActivity.class);
        jump_page(img5,MainActivity.class);
        jump_page(img1,MainActivity.class);
        jump_page(img2,MainActivity.class);

    }
    public void jump_page(ImageView img,Class mid){
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(getActivity(), mid);
                startActivity(intent);
            }
        });
    }


}