package br.dayanelima.voudebike.clients;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

import br.dayanelima.voudebike.IAppNavigation;
import br.dayanelima.voudebike.R;
import br.dayanelima.voudebike.data.Client;
import br.dayanelima.voudebike.data.database.DataBaseHelper;

public class FragmentClientsList extends Fragment {

    private IAppNavigation navCallback;
    private AdapterClientList clientAdapter;
    private Button addClientButton;

    private DataBaseHelper dbHelper;

    public FragmentClientsList() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DataBaseHelper(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_clients, container, false);

        // Button for adding a random bike.
        // FIXME: This opens the detail view for creating a new one.
        // This is just for showing and testing
        addClientButton = root.findViewById(R.id.addClientButton);
        addClientButton.setOnClickListener(view -> {
                    Client client = new Client();
                    client.name = "ABCDE " + Math.random();
                    dbHelper.insertClient(client, null);
                    loadClients();
                }
        );

        // RecyclerView (List) with Bikes
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        RecyclerView clientListRecyclerView = root.findViewById(R.id.clientsListRecyclerView);
        clientListRecyclerView.setHasFixedSize(true);
        clientListRecyclerView.setLayoutManager(mLinearLayoutManager);

        clientAdapter = new AdapterClientList(navCallback);
        clientListRecyclerView.setAdapter(clientAdapter);

        loadClients();

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
        getActivity().setTitle(getString(R.string.title_fragment_customers_list));
    }

    private void loadClients() {
        List<Client> clients = dbHelper.getAllClients(false);
        clientAdapter.setBikes(clients);
    }

}
