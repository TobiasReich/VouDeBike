package br.dayanelima.voudebike.bikes;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import br.dayanelima.voudebike.IAppNavigation;
import br.dayanelima.voudebike.R;
import br.dayanelima.voudebike.data.Bike;
import br.dayanelima.voudebike.data.database.DataBaseHelper;
import br.dayanelima.voudebike.data.database.IDataBaseWriteCallback;


public class FragmentBikeDetail extends Fragment implements IDataBaseWriteCallback {

    private static final String TAG = FragmentBikeDetail.class.getSimpleName();

    private static final String ARGUMENT_BIKE_ID = "BikeID";

    private IAppNavigation navCallback;

    private int bikeID;

    private DataBaseHelper dbHelper;

    private Bike bike;

    //Views (SHOW)
    LinearLayout showLayout;
    TextView nameTV;
    Button editBikeButton;
    TextView descriptionTV;
    TextView typeTV;
    TextView colorTV;

    // Views (EDIT)
    LinearLayout editLayout;
    EditText nameET;
    EditText descriptionET;
    EditText typeET;
    EditText colorET;
    Button saveBikeButton;

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

        dbHelper = new DataBaseHelper(getActivity());
        bike = dbHelper.getBike(bikeID);
        //bike = dbHelper.getBike(100000); //Testing

        showLayout = root.findViewById(R.id.showLayout);
        nameTV = root.findViewById(R.id.nameTV);
        descriptionTV = root.findViewById(R.id.descriptionTV);
        typeTV = root.findViewById(R.id.typeTV);
        colorTV = root.findViewById(R.id.colorTV);
        editBikeButton = root.findViewById(R.id.editBikeButton);

        editLayout = root.findViewById(R.id.editLayout);
        nameET = root.findViewById(R.id.nameET);
        descriptionET = root.findViewById(R.id.descriptionET);
        typeET = root.findViewById(R.id.typeET);
        colorET = root.findViewById(R.id.colorET);
        saveBikeButton = root.findViewById(R.id.saveBikeButton);

        if (bike.id == Bike.UNSET_ID){
            setViewToEdit();
        } else {
            setViewToShow();
        }

        return root;
    }

    private void setViewToShow() {
        showLayout.setVisibility(View.VISIBLE);
        editLayout.setVisibility(View.GONE);

        nameTV.setText(bike.name);
        colorTV.setText(bike.color);
        typeTV.setText(bike.type);
        descriptionTV.setText(bike.description);
        editBikeButton.setOnClickListener(view ->
                setViewToEdit()
        );
    }

    private void setViewToEdit() {
        Log.d(TAG, "Show the bike editor");
        showLayout.setVisibility(View.GONE);
        editLayout.setVisibility(View.VISIBLE);

        nameET.setText(bike.name);
        colorET.setText(bike.color);
        typeET.setText(bike.type);
        descriptionET.setText(bike.description);
        saveBikeButton.setOnClickListener(view ->{

                // Not all data entered. Can't save bike!
                if (! validateBikeData()) {
                    Toast.makeText(getContext(), R.string.toast_error_bike_no_data, Toast.LENGTH_LONG).show();
                    return;
                }

                setUpdatedData();

                // The bike is a "new" one. Save it in the database
                if (bike.id == Bike.UNSET_ID)
                    dbHelper.insertBike(bike, this);

                // The bike already has an ID. Update the data in the database
                else
                    dbHelper.updateBike(bike, this);
            }
        );
    }

    private void setUpdatedData() {
        bike.name = nameET.getText().toString();
        bike.color = colorET.getText().toString();
        bike.type = typeET.getText().toString();
        bike.description = descriptionET.getText().toString();
    }

    /** Checks if the minimum data for the bike are given.
     *
     * TODO: decide which data are required. Name only? Description? ...
     *
     * @return boolean. true if data is given.
     */
    private boolean validateBikeData(){
        return (nameET.getText().toString().trim().length() > 0
                && descriptionET.getText().toString().trim().length() > 0);
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
    public void dataWritten(boolean success) {
        if (success) {
            Toast.makeText(getContext(), R.string.toast_bike_data_written, Toast.LENGTH_LONG).show();
            navCallback.openBikesList();
        } else
            Toast.makeText(getContext(), R.string.toast_error_bike_data_not_written, Toast.LENGTH_LONG).show();
    }

}
