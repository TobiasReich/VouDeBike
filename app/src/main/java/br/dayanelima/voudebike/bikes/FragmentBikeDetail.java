package br.dayanelima.voudebike.bikes;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.dayanelima.voudebike.IAppNavigation;
import br.dayanelima.voudebike.R;


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
        return inflater.inflate(R.layout.fragment_bike_detail, container, false);
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
