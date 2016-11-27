package Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.kunal.myshopper.DetailActivity;
import com.example.kunal.myshopper.R;

import java.util.List;

import model.Products;

/**
 * Created by KUNAL on 25-11-2016.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {

    private Context mContext;
    private List<Products> productList;



    public ProductAdapter(Context mContext, List<Products> list) {
        this.mContext = mContext;
        this.productList = list;
    }

    public class MyViewHolder   extends RecyclerView.ViewHolder{
        public TextView title, count;
        public ImageView thumbnail, overflow;
        public MyViewHolder(View view) {

            super(view);
            title = (TextView) view.findViewById(R.id.title);
            count = (TextView) view.findViewById(R.id.count);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            overflow = (ImageView) view.findViewById(R.id.overflow);
        }
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final Products products = productList.get(position);
        holder.title.setText(products.getName());
        holder.count.setText(mContext.getResources().getString(R.string.Rs)+products.getPrice());

        Glide.with(mContext).load(products.getImageId()).into(holder.thumbnail);

        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(mContext, DetailActivity.class);


                intent.putExtra("DATA",products);

                mContext.startActivity(intent);
            }
        });

        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.overflow,products);
            }
        });
    }

    private void showPopupMenu(View view, Products products) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_me, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener(products));
        popup.show();
    }

    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        Products products;
        public MyMenuItemClickListener(Products products) {
            this.products = products;
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_addtoCart:

                    Intent intent =  new Intent(mContext, DetailActivity.class);
                    intent.putExtra("DATA",products);
                    mContext.startActivity(intent);

                    return true;
                default:
            }
            return false;
        }
    }

    @Override
    public int getItemCount() {

        if(productList!=null && productList.size()>0) {
            return productList.size();
        }
        return 0;

    }


}
