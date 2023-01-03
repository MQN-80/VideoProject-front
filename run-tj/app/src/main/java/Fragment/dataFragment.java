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

    }



}