package br.dayanelima.voudebike.bikes;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

import static android.content.ContentValues.TAG;


/** Adapter for the car list recycler view */
public class AdapterBikeList extends RecyclerView.Adapter<AdapterBikeList.ViewHolder> {

    private final IAppNavigation navCallback;
    private final List<Bike> bikes;             // The messages to show

    public AdapterBikeList(IAppNavigation navCallback) {
        this.navCallback = navCallback;
        this.bikes = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.bike_list_item, parent, false));
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
        Bike bike = bikes.get(position);
        holder.bikeNameTV.setText(bike.name);
        holder.rootLayout.setOnClickListener(
                view -> navCallback.openBikeDetails(bike.id)
        );
        holder.descriptionTV.setText(bike.description);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return bikes.size();
    }

    void setBikes(List<Bike> bikes) {
        this.bikes.clear();
        this.bikes.addAll(bikes);
        this.notifyDataSetChanged();
    }

    /* ------------------------------- VIEW_HOLDER ----------------------------------- */

    class ViewHolder extends RecyclerView.ViewHolder {

        final LinearLayout rootLayout;
        final TextView bikeNameTV;
        final TextView descriptionTV;

        ViewHolder(View view) {
            super(view);
            rootLayout = view.findViewById(R.id.rootLayout);
            bikeNameTV = view.findViewById(R.id.bikeNameTV);
            descriptionTV = view.findViewById(R.id.descriptionTV);
        }
    }
}
