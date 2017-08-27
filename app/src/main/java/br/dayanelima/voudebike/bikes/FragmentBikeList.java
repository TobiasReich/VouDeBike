package br.dayanelima.voudebike.bikes;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import br.dayanelima.voudebike.IAppNavigation;
import br.dayanelima.voudebike.R;
import br.dayanelima.voudebike.data.Bike;
import br.dayanelima.voudebike.data.database.DataBaseHelper;

public class FragmentBikeList extends Fragment {

    private static final String TAG = FragmentBikeList.class.getSimpleName();

    private IAppNavigation navCallback;

    private AdapterBikeList bikeAdapter;

    private DataBaseHelper dbHelper;

    private Button addBikeButton;

    public FragmentBikeList() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DataBaseHelper(getActivity());
        Log.d(TAG, "On Create");
    }

    @Override
    public void onStop() {
        super.onStop();
        dbHelper.closeDB();
        Log.d(TAG, "On Stop");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_bikes, container, false);

        // Button for adding a random bike.
        // FIXME: This opens the detail view for creating a new one.
        // This is just for showing and testing
        addBikeButton = root.findViewById(R.id.addBikeButton);
        addBikeButton.setOnClickListener(view -> {
                Bike bike = new Bike();
                bike.name = "My Bike " + Math.random();
                dbHelper.insertBike(bike);
                loadBikes();
            }
        );

        // RecyclerView (List) with Bikes
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        RecyclerView bikeListRecyclerView = root.findViewById(R.id.bikeListRecyclerView);
        bikeListRecyclerView.setHasFixedSize(true);
        bikeListRecyclerView.setLayoutManager(mLinearLayoutManager);

        bikeAdapter = new AdapterBikeList(navCallback);
        bikeListRecyclerView.setAdapter(bikeAdapter);

        loadBikes();

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

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(getString(R.string.title_fragment_bikes_list));
    }

    private void loadBikes() {
        List<Bike> bikes = dbHelper.getAllBikes(false);
        Log.i(TAG, "Bikes loaded: " + bikes.size());
        bikeAdapter.setBikes(bikes);
    }

}
