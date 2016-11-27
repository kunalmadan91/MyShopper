package model;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by KUNAL on 23-03-2016.
 */
public class SharedPrefrencesProduct {

    public static final String PREFS_NAME = "CART_PRODUCT";
    public static final String FAVORITES = "MYCART";

    public SharedPrefrencesProduct() {
        super();
    }

    public void saveFavorites(Context context, List<Products> cart) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        //settings = context.getSharedPreferences("",Context.)
        settings = context.getSharedPreferences(PREFS_NAME,
               Context.MODE_PRIVATE);
        editor = settings.edit();

        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(cart);

            editor.putString(FAVORITES, jsonFavorites);

        editor.commit();
    }

    public void addFavorite(Context context, Products prdct) {
        List<Products> favorites = getFavorites(context);
        if (favorites == null)
            favorites = new ArrayList<Products>();
        favorites.add(prdct);
        saveFavorites(context, favorites);
    }

    public ArrayList<Products> getFavorites(Context context) {
        SharedPreferences settings;
        List<Products> favorites;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        if (settings.contains(FAVORITES)) {
            String jsonFavorites = settings.getString(FAVORITES, null);
            Gson gson = new Gson();
            Products[] favoriteItems = gson.fromJson(jsonFavorites,
                    Products[].class);

            favorites = Arrays.asList(favoriteItems);
            favorites = new ArrayList<Products>(favorites);
        } else
            return null;

        return (ArrayList<Products>) favorites;
    }

    public void removeFromFavourites(Context context, Products popularMovies) {
            ArrayList<Products> products = getFavorites(context);


            for(int i = 0; i < products.size(); i ++ ){
                Products movie = products.get(i);
                if(movie.getProductId() == (popularMovies.getProductId())){
                    products.remove(i);
                }
            }
            saveFavorites(context, (List<Products>) products);

        int size = products.size();
    }
}
