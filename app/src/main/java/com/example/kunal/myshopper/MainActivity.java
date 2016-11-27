package com.example.kunal.myshopper;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import fragment.AllFragment;
import fragment.ElectronicProducts;
import fragment.FurnitureFragment;
import fragment.MyCart;
import model.Products;

public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawer;
    private Handler mHandler;
    public static int navItemIndex = 0;

    private static final String TAG_ALL = "allItmes";
    private static final String TAG_ELECTRONICS = "electronics";
    private static final String TAG_FURNITURE = "furniture";
    private static final String TAG_MYCART = "myCart";
    public static String CURRENT_TAG = TAG_ALL;
    FloatingActionButton fab;
    private String[] activityTitles;
    NavigationView navigationView;
    public static List<Products> productsList;

    static String tvDesc;
    static String vacDesc;
    static String tabDesc;
    static  String chair;
    static  String almirah;
    static String oven;
    private static List<Products> cartList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        productsList = new ArrayList<>();
        mHandler = new Handler();
         drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        // load toolbar titles from string resources
        activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);


        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_ALL;
            loadHomeFragment();
        }

        tvDesc = getString(R.string.tvdesc);
        vacDesc = getString(R.string.vacDesc);
        tabDesc = getString(R.string.tableDesc);
        almirah = getString(R.string.almirahDesc);
        oven = getString(R.string.ovenDesc);
        chair = getString(R.string.chairDesc);

        prepareProducts();

        if(getIntent().getStringExtra("FLAG") != null){
                navItemIndex = 3;
        CURRENT_TAG = TAG_ALL;
        loadHomeFragment();
    }

    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();



    }

    public void loadcart() {

        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Fragment fragment = new MyCart();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, TAG_MYCART);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };
    }




    private void loadHomeFragment() {
// selecting appropriate nav menu item
        selectNavMenu();

        // set toolbar title
        setToolbarTitle();

        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };


        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }

        // show or hide the fab button
        //toggleFab();

        //Closing drawer on item click
        drawer.closeDrawers();

        // refresh toolbar menu
        invalidateOptionsMenu();
    }

    public static void prepareProducts() {


        int[] items = new int[]{
                R.drawable.tv1,
                R.drawable.vaccumcleaner,
                R.drawable.oven,
                R.drawable.table,
                R.drawable.chair1,
                R.drawable.almirah,};


        Products a = new Products("Samsung 32 inch", "ELEC", 40000,items[0],tvDesc,11);
        productsList.add(a);

        a =  new Products("Eureka Forbes Vaccum cleaner", "ELEC", 16999,items[1],vacDesc,12);
        productsList.add(a);

        a =  new Products("IFB microwave oven", "ELEC", 8900,items[2],oven,13);
        productsList.add(a);

        a =  new Products("Wooden Table", "FUR", 7800,items[3],tabDesc,14);
        productsList.add(a);

        a =  new Products("Office Chair", "FUR", 5504,items[4],chair,15);
        productsList.add(a);

        a =  new Products("Almirah", "FUR", 18000,items[5],almirah,16) ;
        productsList.add(a);

    }

    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
    }

    private Fragment getHomeFragment() {
        switch (navItemIndex) {
            case 0:

                AllFragment aLlFragment = new AllFragment();
                return aLlFragment;
            case 1:

                ElectronicProducts electronicProducts = new ElectronicProducts();
                return electronicProducts;
            case 2:

                FurnitureFragment furnitureFragment = new FurnitureFragment();
                return furnitureFragment;
            case 3:

                MyCart myCart = new MyCart();
                return myCart;

            default:
                return new AllFragment();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        // Close the drawer
        drawer.closeDrawers();
        // Load the fragment required
        int id = item.getItemId();
        switch (id) {
            case R.id.drawer_all:
                navItemIndex = 0;
                CURRENT_TAG = TAG_ALL;
                break;
            case R.id.drawer_electronics:
                navItemIndex = 1;
                CURRENT_TAG = TAG_ELECTRONICS;
                break;
            case R.id.drawer_furniture:
               navItemIndex = 2;
                CURRENT_TAG = TAG_FURNITURE;
                break;
            case R.id.drawer_cart:
                navItemIndex = 3;
                CURRENT_TAG = TAG_MYCART;
                break;

            default:
                navItemIndex = 0;

        }
        if (item.isChecked()) {
            item.setChecked(false);
        } else {
            item.setChecked(true);
        }
        item.setChecked(true);

        loadHomeFragment();
        return true;
    }



    @Override
    public void onBackPressed() {
         drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();

        }
    }


}
