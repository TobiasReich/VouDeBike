package br.dayanelima.voudebike.customers;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.dayanelima.voudebike.IAppNavigation;
import br.dayanelima.voudebike.R;
import br.dayanelima.voudebike.data.Bike;
import br.dayanelima.voudebike.data.Client;


/** Adapter for the car list recycler view */
public class AdapterClientList extends RecyclerView.Adapter<AdapterClientList.ViewHolder> {

    private final IAppNavigation navCallback;
    private final List<Client> clients;             // The messages to show

    public AdapterClientList(IAppNavigation navCallback) {
        this.navCallback = navCallback;
        this.clients = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.client_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        fillViewElements(position, holder);
    }

    /** Fills the View Elements with the specific car
     *
     * @param position position of the element
     * @param holder ViewHolder to fill
     */
    private void fillViewElements(int position, ViewHolder holder) {
        Client client = clients.get(position);
        holder.clientNameTV.setText(client.name);
        /*holder.rootLayout.setOnClickListener(
            view -> navCallback.openBikeDetails(bike.id)
        );
        holder.descriptionTV.setText(bike.description);*/
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return clients.size();
    }

    void setBikes(List<Client> clients) {
        this.clients.clear();
        this.clients.addAll(clients);
        this.notifyDataSetChanged();
    }



    /* ------------------------------- VIEW_HOLDER ----------------------------------- */

    class ViewHolder extends RecyclerView.ViewHolder {

        final LinearLayout rootLayout;
        final TextView clientNameTV;
        //final TextView descriptionTV;

        ViewHolder(View view) {
            super(view);
            rootLayout = view.findViewById(R.id.rootLayout);
            clientNameTV = view.findViewById(R.id.clientNameTV);
            //descriptionTV = view.findViewById(R.id.descriptionTV);
        }
    }
}
