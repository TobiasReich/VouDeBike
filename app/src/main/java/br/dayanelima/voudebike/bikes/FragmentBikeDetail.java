package br.dayanelima.voudebike.bikes;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.dayanelima.voudebike.IAppNavigation;
import br.dayanelima.voudebike.R;
import br.dayanelima.voudebike.data.Bike;
import br.dayanelima.voudebike.data.database.DataBaseHelper;


public class FragmentBikeDetail extends Fragment {

    private static final String ARGUMENT_BIKE_ID = "BikeID";

    private IAppNavigation navCallback;

    private int bikeID;

    public FragmentBikeDetail() {
        // Required empty public constructor
    }

    public static FragmentBikeDetail newInstance(int bikeID) {
        FragmentBikeDetail fragment = new FragmentBikeDetail();
        Bundle args = new Bundle();
        args.putInt(ARGUMENT_BIKE_ID, bikeID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            bikeID = getArguments().getInt(ARGUMENT_BIKE_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_bike_detail, container, false);

        DataBaseHelper dbHelper = new DataBaseHelper(getActivity());
        Bike bike = dbHelper.getBike(bikeID);

        TextView titleTV = root.findViewById(R.id.titleTV);
        titleTV.setText(bike.name);
        return root;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof  IAppNavigation)
            navCallback = (IAppNavigation) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        navCallback = null;
    }


}
