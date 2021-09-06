package github.hmasum18.architecture.service.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import javax.inject.Inject;

import github.hmasum18.architecture.service.api.JsonApiCaller;
import github.hmasum18.architecture.service.api.OnFinishListener;
import github.hmasum18.architecture.service.model.Product;
import retrofit2.Retrofit;

public class ProductRepo{
    private static final String TAG = "ProductRepo";

    @Inject
    Retrofit retrofit;

    @Inject
    public ProductRepo() {
    }

    private MutableLiveData<List<Product>> productList = new MutableLiveData<>();

    public LiveData<List<Product>> getProducts(){
        productList = new MutableLiveData<>();
        fetchProducts();
        return productList;
    }

    public void fetchProducts(){
        Type type = new TypeToken<List<Product>>(){}.getType();
        JsonApiCaller<List<Product>> caller = new JsonApiCaller<>(type, retrofit);
        caller.GET("products")
                .addOnFinishListener(new OnFinishListener<List<Product>>() {
                    @Override
                    public void onSuccess(List<Product> products) {
                        productList.postValue(products);
                    }

                    @Override
                    public void onFailure(Exception e) {
                        Log.e(TAG, "onFailure: failed fetching products");
                    }
                });
    }
}
