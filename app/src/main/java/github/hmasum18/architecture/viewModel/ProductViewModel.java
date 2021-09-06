package github.hmasum18.architecture.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import github.hmasum18.architecture.service.model.Product;
import github.hmasum18.architecture.service.repository.ProductRepo;

@Singleton
public class ProductViewModel extends ViewModel {
    @Inject
    ProductRepo productRepo;

    @Inject
    public ProductViewModel() {
    }

    public LiveData<List<Product>> getProducts(){
        return productRepo.getProducts();
    }
}
