package br.dayanelima.voudebike.customers;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.dayanelima.voudebike.IAppNavigation;
import br.dayanelima.voudebike.R;

public class FragmentCustomersList extends Fragment {

    private IAppNavigation navCallback;

    public FragmentCustomersList() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_clients, container, false);
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
        getActivity().setTitle(getString(R.string.title_fragment_customers_list));
    }


}
