package github.hmasum18.architecture.view.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import github.hmasum18.architecture.R;
import github.hmasum18.architecture.dagger.component.AppComponent;
import github.hmasum18.architecture.databinding.FragmentAddItemBinding;
import github.hmasum18.architecture.databinding.FragmentFakeStoreBinding;
import github.hmasum18.architecture.view.Adapters.ProductAdapter;
import github.hmasum18.architecture.view.App;
import github.hmasum18.architecture.viewModel.ProductViewModel;


public class FakeStoreFragment extends Fragment {
    private FragmentFakeStoreBinding mVB;

    @Inject
    ProductViewModel productViewModel;

    @Inject
    ProductAdapter productAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // init view model
        AppComponent appComponent = ((App)(getActivity().getApplication())).getAppComponent();
        appComponent.inject(this);

        // Inflate the layout for this fragment
        mVB = FragmentFakeStoreBinding.inflate(inflater, container, false);

        mVB.rvProduct.setAdapter(productAdapter);

        return mVB.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        productViewModel.getProducts()
                .observe(getViewLifecycleOwner(), products -> {
                    productAdapter.setProductList(products);
                });
    }
}