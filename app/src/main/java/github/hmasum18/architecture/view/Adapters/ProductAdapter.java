package github.hmasum18.architecture.view.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import github.hmasum18.architecture.R;
import github.hmasum18.architecture.databinding.CardProductBinding;
import github.hmasum18.architecture.service.model.Product;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.Holder>{
    List<Product> productList = new ArrayList<>();
    private OnClickListener<Product> productOnClickListener;

    @Inject
    public ProductAdapter() {
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
        super.notifyDataSetChanged();
    }

    public void setProductOnClickListener(OnClickListener<Product> productOnClickListener) {
        this.productOnClickListener = productOnClickListener;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //parent is the recyclerView
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_product,parent,false);
        return new Holder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.Holder holder, int position) {
        Product product = productList.get(position);

        holder.mVB.getRoot().setOnClickListener(v -> {
            if(productOnClickListener!=null){
                productOnClickListener.onClick(product);
            }
        });

        // init image
        Glide.with(holder.mVB.getRoot()).load(product.getImage()).into(holder.mVB.ivProduct);
        // init product title
        holder.mVB.tvProductName.setText(product.getTitle());
        // init category name
        holder.mVB.tvCategoryName.setText(product.getCategory().toUpperCase());
        // init rating
        holder.mVB.ratingBar.setRating(product.getRating().getRate());
        // init price
        String price = "$"+product.getPrice();
        holder.mVB.tvPrice.setText(price);

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        CardProductBinding mVB;
        public Holder(@NonNull View itemView) {
            super(itemView);
            mVB = CardProductBinding.bind(itemView);
        }
    }
}
