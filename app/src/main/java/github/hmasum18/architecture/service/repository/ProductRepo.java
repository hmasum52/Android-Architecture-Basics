package github.hmasum18.architecture.service.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.ExecutorService;

import javax.inject.Inject;

import github.hmasum18.architecture.service.api.JsonApiCaller;
import github.hmasum18.architecture.service.api.OnFinishListener;
import github.hmasum18.architecture.service.model.Product;
import github.hmasum18.architecture.service.room.Doas.ProductDao;
import retrofit2.Retrofit;

public class ProductRepo {
    private static final String TAG = "ProductRepo";

    @Inject
    Retrofit retrofit;

    @Inject
    ExecutorService roomExecutorService;

    @Inject
    ProductDao productDao;

    @Inject
    public ProductRepo() {
    }

    public LiveData<List<Product>> getProducts() {
        fetchProducts();
        return productDao.getAllProductsLiveData();
    }

    public void fetchProducts() {
        Type type = new TypeToken<List<Product>>() {
        }.getType();
        JsonApiCaller<List<Product>> caller = new JsonApiCaller<>(type, retrofit);
        caller.GET("products")
                .addOnFinishListener(new OnFinishListener<List<Product>>() {
                    @Override
                    public void onSuccess(List<Product> products) {
                        roomExecutorService.execute(() -> {
                            productDao.insert(products);
                        });
                    }

                    @Override
                    public void onFailure(Exception e) {
                        Log.e(TAG, "onFailure: failed fetching products");
                    }
                });
    }

    public LiveData<Product> getProduct(int productId) {
        return  productDao.getProductLiveDataById(productId);
    }

    private void fetchProductById(int productId) {
        JsonApiCaller<Product> caller = new JsonApiCaller<>(Product.class, retrofit);
        caller.GET("products/"+productId)
                .addOnFinishListener(new OnFinishListener<Product>() {
                    @Override
                    public void onSuccess(Product product) {
                        roomExecutorService.execute(()->{
                            productDao.insert(product);
                        });
                    }

                    @Override
                    public void onFailure(Exception e) {

                    }
                });
    }
}
