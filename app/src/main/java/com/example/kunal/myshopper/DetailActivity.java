package com.example.kunal.myshopper;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import Adapter.ProductCartAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;
import fragment.MyCart;
import model.Global;
import model.Products;
import model.SharedPrefrencesProduct;

public class DetailActivity extends AppCompatActivity {
    SharedPrefrencesProduct prefrencesMovie;
     Products  prodct;

    @Bind(R.id.name)
    TextView prodName;

    @Bind(R.id.cost)
    TextView price;

    @Bind(R.id.prod_description)
    TextView description;

    @Bind(R.id.backdrop)
    ImageView mainImage;

    @Bind(R.id.add_to_cart)
    Button btn;

    @Bind(R.id.gotocart)
    Button btncart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       /* getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.cart);*/

        prefrencesMovie = new SharedPrefrencesProduct();
        Intent intent = getIntent();

        prodct = intent.getExtras().getParcelable("DATA");

        initCollapsingToolbar();
        prodName.setText(prodct.getName());

       price.setText(String.valueOf(prodct.getPrice()));


        int id = prodct.getImageId();

        Glide.with(DetailActivity.this).load(id).into(mainImage);
       // mainImage.setImageDrawable(id);

        description.setText(prodct.getDescription());

        if (checkFavouriteItem(prodct)) {
            btn.setText("REMOVE FROM CART");
            btn.setTag("red");
        } else {
            btn.setText("ADD TO CART");
            btn.setTag("grey");
        }

        btncart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   FragmentManager fm = getFragmentManager();

                Intent intent1 = new Intent(DetailActivity.this,MainActivity.class);

                intent1.putExtra("FLAG","CART");
                startActivity(intent1);
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String tag = btn.getTag().toString();

                if(btn.getText().equals("ADD TO CART")){
                    prefrencesMovie.addFavorite(DetailActivity.this, prodct);
                    btn.setText("REMOVE FROM CART");
                    btn.setTag("red");

                    Global.amt = Global.amt + prodct.getPrice();

                    ArrayList<Products> product = prefrencesMovie.getFavorites(DetailActivity.this);

                    Toast.makeText(DetailActivity.this, "Product added to cart Successfully!!", Toast.LENGTH_SHORT).show();

                    if(MyCart.adapter != null) {
                       // Toast.makeText(DetailActivity.this, "pls add sometihg to cart", Toast.LENGTH_SHORT).show();
                        MyCart.adapter = new
                                ProductCartAdapter(DetailActivity.this, product);

                        MyCart.recyclerView.
                                setAdapter(MyCart.adapter);

                    }
                }

                else if(btn.getText().equals("REMOVE FROM CART")) {

                    prefrencesMovie.removeFromFavourites(DetailActivity.this, prodct);

                    btn.setText("ADD TO CART");
                    btn.setTag("grey");
                    Toast.makeText(DetailActivity.this, "Product removed from cart Successfully!!", Toast.LENGTH_SHORT).show();
                    ArrayList<Products> product = prefrencesMovie.getFavorites(DetailActivity.this);

                    Global.amt = Global.amt - prodct.getPrice();

                    if(MyCart.adapter != null) {
                        MyCart.adapter = new
                                ProductCartAdapter(DetailActivity.this, product);

                        MyCart.recyclerView.
                                setAdapter(MyCart.adapter);
                    }

                }
            }
        });


        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_cart) {
            Intent intent1 = new Intent(DetailActivity.this,MainActivity.class);

            intent1.putExtra("FLAG","CART");
            startActivity(intent1);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(prodct.getName());
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(getString(R.string.app_name));
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }


    public boolean checkFavouriteItem(Products prodct) {
        boolean check = false;
        List<Products> favourites = prefrencesMovie.getFavorites(DetailActivity.this);

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
