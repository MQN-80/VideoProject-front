package activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMapUtils;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.amap.api.maps2d.model.PolylineOptions;
import com.amap.api.services.core.ServiceSettings;
import com.example.myapplication.R;

import com.amap.api.maps2d.AMap;

import net.asyncCall;

import java.text.BreakIterator;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import Utils.ACache;


public class RunmapActivity extends AppCompatActivity  {
    /**
     * 异常距离，如果超过这个距离，则说明移动距离异常,避免定位抖动造成的误差
     */
    private static final int DISTANCE_ERROR = 30;
    private MapView mapView;
    private AMap aMap;

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener =  new MyAMapLocationListener();
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    //此为坐标经纬度的记录变量
    //定位蓝点
    MyLocationStyle myLocationStyle;

    //运动步数记录的传感器
    private SensorManager mSensorManager;
    private MySensorEventListener mListener;

    //设置暂停
    boolean ifPause=false;


    private LatLng currentLatLng;
    private long totalDistance=0;//距离
    private long  mStepDetector=0;//步数
    private Chronometer chronometer;//计时器
    private long recordingTime = 0;// 记录下来的总时间

    private Context that=this;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.run_map);
        mapView = (MapView)findViewById(R.id.map);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mapView.onCreate(savedInstanceState);
        //初始化地图控制器对象
        aMap = mapView.getMap();//获取地图控件引用

        //地图缩放比例
        aMap.moveCamera(CameraUpdateFactory.zoomTo(19));
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        //初始化定位
        try {
            //隐私政策合规
            ServiceSettings.updatePrivacyShow(this,true,true);
            ServiceSettings.updatePrivacyAgree(this,true);
            mLocationClient = new AMapLocationClient(getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();

        AMapLocationClientOption option = new AMapLocationClientOption();
        /*
         * 设置定位场景，目前支持三种场景（签到、出行、运动，默认无场景）
         */
        option.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.Sport);//运动场景设置
        if(null != mLocationClient){
            mLocationClient.setLocationOption(option);
            //设置场景模式后最好调用一次stop，再调用start以保证场景模式生效
            mLocationClient.stopLocation();
            mLocationClient.startLocation();
        }

        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否允许模拟位置,默认为true，允许模拟位置
        mLocationOption.setMockEnable(false);
        //单位是毫秒，默认30000毫秒，建议超时时间不要低于8000毫秒。
        mLocationOption.setHttpTimeOut(2000);
        //关闭缓存机制
        mLocationOption.setLocationCacheEnable(false);

        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();


        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        //aMap.getUiSettings().setMyLocationButtonEnabled(true);设置默认定位按钮是否显示，非必需设置。

        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW);
        myLocationStyle.strokeWidth((float)0.05);

        myLocationStyle.showMyLocation(true);
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        Log.e("初始化", "完成！！！！！！");

/*
        initSenorManager();//运动步数初始化
*/
        //动态设置权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACTIVITY_RECOGNITION}, 1);
            } else {
                Toast toast = Toast.makeText(this, "已授权", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        }
        mSensorManager=(SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mListener = new MySensorEventListener();

        //计时器启动
        chronometer=findViewById(R.id.chronometer);
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.setFormat("已用时间：%s");
        chronometer.start();

        Button button_pause=findViewById(R.id.Runmap_pause);
        Button button_stop=findViewById(R.id.Runmap_stop);
        button_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!ifPause)
                {//监听按钮，如果点击，就暂停地图设置
                mapView.onPause();
                mSensorManager.unregisterListener(mListener);
                mLocationClient.stopLocation();

                chronometer.stop();
                // 保存这次记录了的时间。SystemClock.elapsedRealtime()是系统启动到现在的毫秒数
                recordingTime=SystemClock.elapsedRealtime()-chronometer.getBase();//getBase():返回时间
                //Log.e("停止计时", String.valueOf(chronometer.getBase()));


                ifPause=true;
                button_pause.setText("恢复");
                }
                else{
                    mapView.onResume();

                    mSensorManager.registerListener(mListener,
                            mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR),
                            SensorManager.SENSOR_DELAY_NORMAL);

                    mLocationClient.startLocation();
                    // 跳过已经记录了的时间，起到继续计时的作用
                    chronometer.setBase(SystemClock.elapsedRealtime()-recordingTime);
                    chronometer.start();
                    button_pause.setText("暂停");
                    ifPause=false;
                }
            }
        });
        //退出按钮传输数据
        button_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //监听按钮，如果点击，就跳转，并传输数据
                chronometer.stop();

                String Time=getChronometerSeconds(chronometer);
                Log.e("停止计时", Time);
                int pace;//步幅
                int steps_frequency = (int) (mStepDetector / (Integer.valueOf(Time).intValue() * 60));
                if(mStepDetector==0)
                    pace=0;
                else {
                    pace= (int) (totalDistance / mStepDetector);
                }

                //传输数据
                ACache mCache=ACache.get(that);
                String as_id=mCache.getAsString("user_id");
                asyncCall call=new asyncCall();
                Map<String,String> res=new HashMap<>();
                res.put("user_id",as_id);
                if(totalDistance!=0){
                    res.put("distance", String.valueOf(totalDistance));
                }else{
                    res.put("distance","0");
                }
                if(pace!=0){
                    res.put("pace", String.valueOf(pace));
                }else{
                    res.put("pace","0");
                }
                if(mStepDetector!=0){
                    res.put("steps", String.valueOf(mStepDetector));
                }else{
                    res.put("steps","0");
                }
                if(steps_frequency!=0){
                    res.put("steps_frequency", String.valueOf(steps_frequency));
                }else{
                    res.put("steps_frequency","0");
                }
                if(!Time.isEmpty()){
                    res.put("time",Time);
                }else{
                    res.put("time","0");
                }
                Log.i("RunRecord",res.toString());
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        call.putAsync("/record",res);
                    }
                }).start();
                AlertDialog alertDialog1 = new AlertDialog.Builder(that)
                        .setTitle("通知")//标题
                        .setMessage("跑步结束！")//内容
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        })
                        .create();
                alertDialog1.show();

                mapView.onDestroy();

                if (null != mLocationClient) {
                    mLocationClient.onDestroy();
                }

            }
        });
    }

    //设置记录运动的步数
    private void initSenorManager(){
        //动态设置权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACTIVITY_RECOGNITION}, 1);
            } else {
                Toast toast = Toast.makeText(this, "已授权", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        }
        mSensorManager=(SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mListener = new MySensorEventListener();

        mSensorManager.registerListener(mListener,
                mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR),
                SensorManager.SENSOR_DELAY_NORMAL);

/*
        mSensorManager.registerListener(mListener,
                mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER),
                SensorManager.SENSOR_DELAY_NORMAL);
*/
    }


    class MySensorEventListener implements SensorEventListener {
        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_STEP_DETECTOR) {
                if (event.values[0] == 1.0f) {
                    mStepDetector++;//当前运动步数加一

                    TextView tvStep=findViewById(R.id.tv_step);
                    tvStep.setText(String.format("当前步数：%d ", mStepDetector));
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            Log.d("onAccuracyChanged", String.valueOf(accuracy));
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        mapView.onResume();

        mSensorManager.registerListener(mListener,
                mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR),
                SensorManager.SENSOR_DELAY_NORMAL);

        mLocationClient.startLocation();

        // 跳过已经记录了的时间，起到继续计时的作用
        chronometer.setBase(SystemClock.elapsedRealtime()-recordingTime);
        chronometer.start();

/*
        mSensorManager.registerListener(mListener,
                mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER),
                SensorManager.SENSOR_DELAY_NORMAL);
*/

    }
    @Override
    protected void onPause(){
        super.onPause();
        mapView.onPause();
        mSensorManager.unregisterListener(mListener);

        mLocationClient.stopLocation();

        chronometer.stop();
        // 保存这次记录了的时间。SystemClock.elapsedRealtime()是系统启动到现在的毫秒数
        recordingTime=SystemClock.elapsedRealtime()-chronometer.getBase();//getBase():返回时间

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        mapView.onDestroy();

        if (null != mLocationClient) {
            mLocationClient.onDestroy();
        }
    }

    private class MyAMapLocationListener implements AMapLocationListener {

        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (aMapLocation != null) {
                if (aMapLocation.getErrorCode() == 0) {
                    //此处获得成功，可以参照返回值表取需要的参数
                    Log.e("位置：", aMapLocation.getAddress());
                    if(currentLatLng==null)
                    {
                        currentLatLng=new LatLng(aMapLocation.getLatitude(),aMapLocation.getLongitude());
                        //设置中心位置
                        aMap.moveCamera(CameraUpdateFactory.changeLatLng(currentLatLng));

                    }
                    LatLng lastLatLng=currentLatLng;
                    currentLatLng=new LatLng(aMapLocation.getLatitude(),aMapLocation.getLongitude());

                    //设置中心位置
                    aMap.moveCamera(CameraUpdateFactory.changeLatLng(currentLatLng));

                    //计算与前一次定位的距离，为0则不动
                    float movedDistance = AMapUtils.calculateLineDistance(currentLatLng, lastLatLng);
                    if(movedDistance>=DISTANCE_ERROR||movedDistance==0){
                        Log.e("没运动","没运动"+movedDistance);
                        return;
                    }

                    Random random=new Random();
                    mStepDetector= (long) (mStepDetector+movedDistance*random.nextInt(2));

                    aMap.addPolyline(new PolylineOptions().add(lastLatLng,currentLatLng).width(10).color(Color.argb(255,1,1,1)));
                    totalDistance+=movedDistance;
                    //在界面上显示总里程和当前的速度
                    displayInfo(totalDistance,aMapLocation.getSpeed());
                } else {
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError", "location Error, ErrCode:"
                            + aMapLocation.getErrorCode() + ", errInfo:"
                            + aMapLocation.getErrorInfo());
                }
            }
        }

    }

    @SuppressLint("DefaultLocale")
    private void displayInfo(long totalDistance, float speed) {
        TextView mTvDistance=findViewById(R.id.tv_distance);
        TextView mTvSpeed=findViewById(R.id.tv_speed);
        TextView mTvStep=findViewById(R.id.tv_step);

        mTvDistance.setText(String.format("总路程：%d m", totalDistance));
        mTvSpeed.setText(String.format("当前速度: %s m/s", speed));
        mTvStep.setText(String.format("当前步数: %d",mStepDetector));
    }


    /**
     *
     * @param cmt  Chronometer控件
     * @return 小时+分钟+秒数  的所有秒数
     */
    public  static String getChronometerSeconds(Chronometer cmt) {
        int totalss = 0;
        Log.e("时间string长度", String.valueOf(((String) cmt.getText()).length()));
        Log.e("时间", (String) cmt.getText());
        String string = cmt.getText().toString();
        if(string.length()==12){
            String[] split1 =string.split("：");
            string=split1[1];
            Log.e("时间", string);
            String[] split = string.split(":");
            String string2 = split[0];
            int hour = Integer.parseInt(string2);
            int Hours =hour*3600;
            String string3 = split[1];
            int min = Integer.parseInt(string3);
            int Mins =min*60;
            int  SS =Integer.parseInt(split[2]);
            totalss = Hours+Mins+SS;
            return String.valueOf(totalss);
        }

        else if(string.length()==10){
            String[] split1 =string.split("：");
            string=split1[1];
            Log.e("时间", string);
            String[] split = string.split(":");
            String string3 = split[0];
            int min = Integer.parseInt(string3);
            int Mins =min*60;
            int  SS =Integer.parseInt(split[1]);

            totalss =Mins+SS;
            return String.valueOf(totalss);
        }
        return String.valueOf(totalss);


    }
}
