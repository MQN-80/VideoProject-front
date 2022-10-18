package net;

import android.util.Log;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import static android.content.ContentValues.TAG;

//注意,所有网络请求均需在新线程中启动
public class asyncCall implements netCall {
    OkHttpClient client;
    String url;

    public asyncCall(String url) {
        this.url = url;
        client = new OkHttpClient();
    }
    public asyncCall(){
        this.url="http://10.0.2.2:8081";
        client = new OkHttpClient();
    }

    //同步get请求
    public Response getAsync(String url, Map<String, String> headerParams, ArrayList<String> bodyParams) {

        String urlNew=this.url+url;
        urlNew += getBodyParams(bodyParams);
        Headers headers = setHeaderParams(headerParams);
        //2.创建Request对象，设置一个url地址,设置请求方式。
        Request request = new Request.Builder().url(urlNew).get().headers(headers).build();
        Call call = client.newCall(request);
        try {
            Response response = call.execute();
            Log.i(TAG, "body="+response.body().string());
            return response;
        }catch(IOException e){
            e.printStackTrace();
        }
        return null;

    }

    public Response getAsync(String url, ArrayList<String> bodyParams) {
        String urlNew=this.url+url;
        urlNew += getBodyParams(bodyParams);
        Log.d(TAG, urlNew);
        //2.创建Request对象，设置一个url地址,设置请求方式。
        Request request = new Request.Builder().url(urlNew).get().build();
        Call call = client.newCall(request);
        try {
            Response response = call.execute();
            //System.out.println(response.body().toString());
            return  response;
            //Log.d(TAG, "body="+response.body().string());
        }catch(IOException e){
            e.printStackTrace();
        }
        return  null;


    }
    public String getBodyParams(ArrayList<String> bodyParams)
    {
        if (bodyParams != null && bodyParams.size() > 0) {
            StringBuffer stringBuffer = new StringBuffer("");
            for (String key : bodyParams) {
                    //如果参数不是null并且不是""，就拼接起来
                    stringBuffer.append("/");
                    stringBuffer.append(key);
            }
            return stringBuffer.toString();
        } else {
            return "";
        }

    }
    public Headers setHeaderParams(Map<String, String> headerParams) {
        Headers headers = null;
        Headers.Builder headersbuilder = new Headers.Builder();
        if (headerParams != null && headerParams.size() > 0) {
            for (String key : headerParams.keySet()) {

                    //如果参数不是null并且不是""，就拼接起来
                    headersbuilder.add(key, headerParams.get(key));
            }
        }

        headers = headersbuilder.build();
        return headers;
    }
    public Response postAsync(String url, Map<String, String> headerParams, Map<String, String> bodyParams)
    {
        String urlNew=this.url+url;
        FormBody.Builder formBody=new FormBody.Builder();
        for(String key:bodyParams.keySet()){
            formBody.add(key,bodyParams.get(key));
        }
        RequestBody body=formBody.build();
        Headers headers = setHeaderParams(headerParams);
        //2.创建Request对象，设置一个url地址,设置请求方式。
        Request request = new Request.Builder().url(urlNew).post(body).headers(headers).build();
        Call call = client.newCall(request);
        try {
            Response response = call.execute();
            //Log.i(TAG, "body="+response.body().string());
            return response;
        }catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }
    public Response postAsync(String url, Map<String, String> bodyParams){
        String urlNew=this.url+url;
        FormBody.Builder formBody=new FormBody.Builder();
        for(String key:bodyParams.keySet()){
            formBody.add(key,bodyParams.get(key));
        }
        RequestBody body=formBody.build();
        //2.创建Request对象，设置一个url地址,设置请求方式。
        Request request = new Request.Builder().url(urlNew).post(body).build();
        Call call = client.newCall(request);
        try {
            Response response = call.execute();
           // Log.i(TAG, "body="+response.body().string());
            return response;
        }catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }


}
