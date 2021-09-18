package github.hmasum18.architecture.view.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import github.hmasum18.architecture.R;
import github.hmasum18.architecture.dagger.component.AppComponent;
import github.hmasum18.architecture.databinding.FragmentFakeStoreBinding;
import github.hmasum18.architecture.view.Adapters.ProductAdapter;
import github.hmasum18.architecture.view.App;
import github.hmasum18.architecture.viewModel.ProductViewModel;


public class FakeStoreFragment extends Fragment {
    private static final String TAG = "FakeStoreFragment";

    private FragmentFakeStoreBinding mVB;

    @Inject
    ProductViewModel productViewModel;

    @Inject
    ProductAdapter productAdapter;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //tutorial: https://developer.android.com/guide/fragments/appbar
        Log.d(TAG, "onCreate: register to option menu click call backs");
        super.setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        Log.d(TAG, "onCreateOptionsMenu: ");
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.item_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
       if (item.getItemId() == R.id.switch_app) {
            NavHostFragment.findNavController(this).navigate(
                    R.id.itemListFragment
            );
        }
        return super.onOptionsItemSelected(item);
    }

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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        productViewModel.getProducts()
                .observe(getViewLifecycleOwner(), products -> {
                    productAdapter.setProductList(products);
                });

        productAdapter.setProductOnClickListener(product -> {
            Bundle bundle = new Bundle();
            bundle.putInt(ProductDetailsFragment.PRODUCT_ID, product.getId());
            NavHostFragment.findNavController(this)
                    .navigate(R.id.productDetailsFragment, bundle);
        });
    }
}