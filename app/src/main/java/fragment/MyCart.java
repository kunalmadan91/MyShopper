package fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kunal.myshopper.R;

import java.util.List;

import Adapter.ProductCartAdapter;
import model.Global;
import model.Products;
import model.SharedPrefrencesProduct;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MyCart.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MyCart#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyCart extends Fragment {



    public static RecyclerView recyclerView;
    public static ProductCartAdapter adapter;
    private static List<Products> cartList;
    SharedPrefrencesProduct prefrencesProduct;
    TextView pay,textempty;
    ImageView emptyimg;
    LinearLayout layout;
    Button btn;




    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MyCart() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyCart.
     */
    // TODO: Rename and change types and number of parameters
    public static MyCart newInstance(String param1, String param2) {
        MyCart fragment = new MyCart();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         int totalAmt = 0;
        View view = inflater.inflate(R.layout.fragment_my_cart, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        layout = (LinearLayout) view.findViewById(R.id.empty_layout);
        Global.amount = (TextView) view.findViewById(R.id.amt);
        pay = (TextView) view.findViewById(R.id.pay);
        textempty = (TextView) view.findViewById(R.id.textEmpty);
        emptyimg = (ImageView) view.findViewById(R.id.emptyimg);
        btn = (Button) view.findViewById(R.id.btn);
        prefrencesProduct = new SharedPrefrencesProduct();

      cartList =  prefrencesProduct.getFavorites(getActivity());


        if(Global.amt ==0 || cartList == null) {
            Global.amount.setVisibility(View.INVISIBLE);
            pay.setVisibility(View.INVISIBLE);
            layout.setVisibility(View.VISIBLE);
            btn.setVisibility(View.INVISIBLE);
        }

        if(cartList!= null && cartList.size()>0) {
            Global.amount.setText(String.valueOf(Global.amt));

        }

        adapter = new ProductCartAdapter(getActivity(), cartList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        adapter.notifyDataSetChanged();
        return  view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onResume() {
        super.onResume();
        if(Global.amt ==0 || cartList == null) {
            Global.amount.setVisibility(View.INVISIBLE);
            pay.setVisibility(View.INVISIBLE);
            layout.setVisibility(View.VISIBLE);
            btn.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
