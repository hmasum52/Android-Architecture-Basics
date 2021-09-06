package github.hmasum18.architecture.service.api;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.lang.reflect.Type;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * This class fetch data from the remote server as json object or json array
 *
 * It calls api end point for data by retrofit2
 * and notify the repositories when data is received.
 *
 * @author Hasan Masum
 * @version 1.0
 * @since 21/01/2021
 */
public class JsonApiCaller<T>{
    public static final String TAG = "JsonApiCaller:-->";
    private final ApiEndPoints apiEndPoints; //api endpoints
    private OnFinishListener<T> onFinishListener;
    private final Gson gson = new Gson();
    private final Type type;

    /**
     *  if we have a class name "Dummy"
     *  <ul>
     *      <li>type is Dummy.class for Dummy object</li>
     *      <li>type is Dummy[].class for a array of Dummy class</li>
     *      <li>type is new TypeToken<List<Dummy>>(){}.getType() for a list of Dummy class</li>
     *  </ul>
     *
     // * @param type is the type of response we want to get
     */
    public JsonApiCaller(Type type, Retrofit retrofit){
        this.type = type;
        this.apiEndPoints = retrofit.create(ApiEndPoints.class);
    }
    public void addOnFinishListener(OnFinishListener<T> onFinishListener) {
        this.onFinishListener = onFinishListener;
    }

    public JsonApiCaller<T> GET(String relativePath){
        Call<JsonElement> call = apiEndPoints.GET(relativePath);
        this.enqueueRequest(call);
        return this;
    }

    public JsonApiCaller<T> GET(Map<String,String> headerMap, String relativePath){
        Call<JsonElement> call = apiEndPoints.GET(headerMap,relativePath);
        this.enqueueRequest(call);
        return this;
    }

    public JsonApiCaller<T> POST(String relativePath, Object object) {
        Call<JsonElement> call = apiEndPoints.POST(relativePath, object);
        Log.d(TAG, "POST()=> posting json element ");
        this.enqueueRequest(call);
        return this;
    }

    public JsonApiCaller<T> POST(Map<String,String> headerMap,String relativePath, Object object) {
        Call<JsonElement> call = apiEndPoints.POST(headerMap,relativePath, object);
        Log.d(TAG, "POST()=> posting json element ");
        this.enqueueRequest(call);
        return this;
    }

    private void enqueueRequest(Call<JsonElement> call){
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if(response.isSuccessful()){
                    JsonElement json = response.body();
                    Log.d(TAG,"enqueueRequest>onResponse: "+call.request().url()+" call is successful");
                    Log.d(TAG,"enqueueRequest>onResponse:"+type);
                    onFinishListener.onSuccess(gson.fromJson(json,type));
                }else{
                    onFinishListener.onFailure(new Exception("Error "+response.code()));
                    Log.d(TAG,"enqueueRequest>onResponse:  process is not successful. Response code: "+response.code());
                }
            }
            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                String className = t.getClass().toString();
                onFinishListener.onFailure(new Exception(t));
                if(className.endsWith("UnknownHostException") )
                    Log.d(TAG,"enqueueRequest > Server is not responding");
                t.printStackTrace();
            }
        });
    }
}
