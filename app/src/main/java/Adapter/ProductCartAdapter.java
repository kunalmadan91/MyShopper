package Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kunal.myshopper.DetailActivity;
import com.example.kunal.myshopper.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import model.Global;
import model.Products;
import model.SharedPrefrencesProduct;

/**
 * Created by KUNAL on 25-11-2016.
 */

public class ProductCartAdapter extends RecyclerView.Adapter<ProductCartAdapter.MyViewHolder> {

    private static  Context mContext;
    public static List<Products> productList;
    static SharedPrefrencesProduct prefrencesProduct;
    public static int totalAmt;



    public ProductCartAdapter(Context mContext, List<Products> list) {
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
                .inflate(R.layout.product_card_cart, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final Products products = productList.get(position);
        holder.title.setText(products.getName());
        holder.count.setText(mContext.getResources().getString(R.string.Rs)+products.getPrice());

        Picasso.with(mContext).load(products.getImageId()).fit().into(holder.thumbnail);
        //  Glide.with(mContext).load(products.getImageId()).into(holder.thumbnail);
        prefrencesProduct = new SharedPrefrencesProduct();
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
                //intent.putExtra("DATA",products);
                showPopupMenu(holder.overflow,products);
            }
        });
    }

    public void showPopupMenu(View view, Products products) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
       // popup.getMenu().getItem(0).setTitle("dssd");
        inflater.inflate(R.menu.menu_product, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener(products));
        popup.show();
    }


    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {


       // public ArrayList<Products> movie;
        Products products;
        public MyMenuItemClickListener(Products products) {

            this.products = products;
        }



        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_addtoCart:
                    if(checkFavouriteItem(products)) {
                        prefrencesProduct.removeFromFavourites(mContext, products);
                        Global.amt = Global.amt - products.getPrice();
                        ArrayList<Products> products = prefrencesProduct.getFavorites(mContext);

                        productList = products;

                        ProductCartAdapter.this.notifyDataSetChanged();

                        Global.amount.setText(String.valueOf(Global.amt));
                    }

                    return true;
                default:
            }
            return false;
        }
    }

    @Override
    public int getItemCount() {

        if(productList!= null)
        return productList.size();
        return 0;
    }


    public static boolean checkFavouriteItem(Products prodct) {
        boolean check = false;
        List<Products> favourites = prefrencesProduct.getFavorites(mContext);

        if (favourites != null) {
            for (Products movies1 : favourites) {
                if (movies1.getProductId() == prodct.getProductId()) {
                    check = true;
                    break;
                }
            }
        }
        return check;
    }

}
