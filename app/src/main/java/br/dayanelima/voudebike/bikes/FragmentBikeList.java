package br.dayanelima.voudebike.bikes;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import br.dayanelima.voudebike.IAppNavigation;
import br.dayanelima.voudebike.R;
import br.dayanelima.voudebike.data.Bike;

public class FragmentBikeList extends Fragment {

    private IAppNavigation navCallback;

    private AdapterBikeList bikeAdapter;

    public FragmentBikeList() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_bikes, container, false);

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
        List<Bike> bikes = new ArrayList<>();
        bikes.add(new Bike());
        bikes.add(new Bike());
        bikes.add(new Bike());
        bikes.add(new Bike());
        bikes.add(new Bike());
        bikes.add(new Bike());
        bikes.add(new Bike());
        bikes.add(new Bike());
        bikes.add(new Bike());
        bikes.add(new Bike());
        bikes.add(new Bike());
        bikes.add(new Bike());
        bikes.add(new Bike());
        bikeAdapter.setBikes(bikes);
    }

}
