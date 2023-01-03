package Fragment;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

import activity.RunmapActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link runFragment#} factory method to
 * create an instance of this fragment.
 */
public class runFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_run, container, false);
    }
    @Override
    public void onStart(){
        super.onStart();
        //获取按钮
        Button button = (Button) getActivity().findViewById(R.id.Runmap_start);

        //按钮进行监听
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //监听按钮，如果点击，就跳转
                Intent intent = new Intent();
                //前一个（MainActivity.this）是目前页面，后面一个是要跳转的下一个页面
                intent.setClass(getActivity(), RunmapActivity.class);
                startActivity(intent);
            }
        });
    }
}