package github.hmasum18.architecture.view.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import github.hmasum18.architecture.R;
import github.hmasum18.architecture.dagger.component.AppComponent;
import github.hmasum18.architecture.databinding.FragmentProductDetailsBinding;
import github.hmasum18.architecture.service.model.Product;
import github.hmasum18.architecture.view.App;
import github.hmasum18.architecture.viewModel.ProductViewModel;

public class ProductDetailsFragment extends Fragment {
    public static final String PRODUCT_ID = "product_id";

    private FragmentProductDetailsBinding mVB;

    @Inject
    ProductViewModel productViewModel;

    private int productId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            productId = getArguments().getInt(PRODUCT_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // init view model
        AppComponent appComponent = ((App)(getActivity().getApplication())).getAppComponent();
        appComponent.inject(this);

        // Inflate the layout for this fragment
        mVB = FragmentProductDetailsBinding.inflate(inflater, container, false);
        return mVB.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        productViewModel.getProduct(productId)
                .observe(getViewLifecycleOwner(), product -> {
                    if(product == null)
                        return;
                    updateViews(product);
                });
    }

    private void updateViews(Product product) {
        Glide.with(mVB.getRoot()).load(product.getImage()).into(mVB.ivProduct);

        // init product title
        mVB.tvProductTitle.setText(product.getTitle());
        // init category name
        mVB.tvCategory.setText(product.getCategory().toUpperCase());
        // init rating
        mVB.ratingBar.setRating(product.getRating().getRate());
        // rating count
        mVB.tvRateCount.setText(product.getRating().getCount()+"");

        // init price
        String price = "$"+product.getPrice();
        mVB.tvPrice.setText(price);

        //description
        mVB.tvDescription.setText(product.getDescription());

    }
}