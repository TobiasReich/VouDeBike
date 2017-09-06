package br.dayanelima.voudebike.clients;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.dayanelima.voudebike.R;
import br.dayanelima.voudebike.data.Client;
import br.dayanelima.voudebike.data.database.DataBaseHelper;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentClientDetail#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentClientDetail extends Fragment {

    private static final String PARAM_CLIENT_ID = "param_ID";

    private int clientID;
    private DataBaseHelper dbHelper;

    Client client;

    public FragmentClientDetail() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param id client id
     * @return A new instance of fragment FragmentClientDetail.
     */
    public static FragmentClientDetail newInstance(int id) {
        FragmentClientDetail fragment = new FragmentClientDetail();
        Bundle args = new Bundle();
        args.putInt(PARAM_CLIENT_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            clientID = getArguments().getInt(PARAM_CLIENT_ID);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        dbHelper = new DataBaseHelper(getActivity());
        client = dbHelper.getClient(clientID);

        View root = inflater.inflate(R.layout.fragment_client_detail, container, false);

        TextView clientNameTV = root.findViewById(R.id.clientNameTV);
        clientNameTV.setText(client.name);

        TextView clientPhoneTV = root.findViewById(R.id.clientPhoneTV);
        clientPhoneTV.setText(client.phone);

        TextView clientEmailTV = root.findViewById(R.id.clientEmailTV);
        clientEmailTV.setText(client.email);

        TextView clientAddressTV = root.findViewById(R.id.clientAddressTV);
        clientAddressTV.setText(client.address);



        return root;
    }


    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(client.name);
    }
}
