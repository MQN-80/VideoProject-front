package net;

import okhttp3.Headers;
import okhttp3.Response;

import java.util.ArrayList;
import java.util.Map;

public interface netCall {
    public Response getAsync(String url, Map<String, String> headerParams, ArrayList<String> bodyParams);


    public Response getAsync(String url, ArrayList<String> bodyParams);
    public String getBodyParams(ArrayList<String> bodyParams);
    public Headers setHeaderParams(Map<String, String> headerParams);
    public Response postAsync(String url, Map<String, String> headerParams, Map<String, String> bodyParams);
    public Response postAsync(String url, Map<String, String> bodyParams);

    public Response putAsync(String url, Map<String, String> bodyParams);
    public Response deleteAsync(String url, Map<String, String> bodyParams);


}
