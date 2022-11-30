package Service;

import DBHelper.DBHelper;
import Utils.ACache;
import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.TextView;
import com.example.myapplication.R;
import net.JWebSocket;
import net.asyncCall;
import okhttp3.Response;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.content.ContentValues.TAG;

public class MessageService extends Service {
    private static WebSocketClient webSocketClient;

    private static URI serverURI = URI.create("ws://10.0.2.2:9688");   //websocket端口地址
    public MessageService() {

    }
    public class MyBinder extends Binder {
        public MessageService getService(){
            return MessageService.this;
        }
    }
    //通过binder实现client和service之间的通信
    private  MyBinder binder=new MyBinder();
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        DBHelper dbHelper = new DBHelper(this, "test.db", null, 1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        webSocketClient=new WebSocketClient(serverURI) {
            @Override
            public void onOpen(ServerHandshake handshakedata) {
                System.out.println("开始监听");
            }

            @Override
            //接收到服务端信息,将其加到数据库中,目前假定只收到正常消息
            public void onMessage(String message) {
                ContentValues values=new ContentValues();
                JSONObject mid= null;
                //获取当前时间
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();
                try {
                    mid = new JSONObject(message);
                    values.put("id",mid.getInt("id"));
                    values.put("user_id",mid.getInt("user_id"));
                    values.put("user_name",mid.getString("user_name"));
                    values.put("message",mid.getString("message"));
                    values.put("time",dateFormat.format(date));
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                //进行插入
                db.insert("group_message",null ,values);
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                System.out.println("停止监听");

            }

            @Override
            public void onError(Exception ex) {
                System.out.println("监听出错");

            }
        };   //连接服务器ip
        webSocketClient.connect();

    }

    /**
     *
     * 发送信息记得严格按对应json形式封装好,发送时用新线程发
     */
    public void sendMessage(String messageBody) throws InterruptedException {
        Thread th=new Thread(new Runnable() {
            @Override
            public void run() {
                webSocketClient.send(messageBody);
            }
        });
        th.start();
    }



}