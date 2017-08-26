package br.dayanelima.voudebike.bike;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.dayanelima.voudebike.IAppNavigation;
import br.dayanelima.voudebike.R;

public class FragmentBikeList extends Fragment {

    private IAppNavigation navCallback;

    public FragmentBikeList() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bikes, container, false);
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


}
