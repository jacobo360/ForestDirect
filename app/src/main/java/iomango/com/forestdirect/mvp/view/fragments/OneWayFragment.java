package iomango.com.forestdirect.mvp.view.fragments;

import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.CheckBox;
import android.widget.TableRow;

import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import iomango.com.forestdirect.R;
import iomango.com.forestdirect.mvp.MVP;
import iomango.com.forestdirect.mvp.common.generic.GenericFragment;
import iomango.com.forestdirect.mvp.common.utilities.DrawablesTools;
import iomango.com.forestdirect.mvp.model.GlobalModel;
import iomango.com.forestdirect.mvp.model.data.AirlineModel;
import iomango.com.forestdirect.mvp.presenter.OneWayPresenter;
import iomango.com.forestdirect.mvp.view.custom.CustomEditText;
import iomango.com.forestdirect.mvp.view.custom.CustomTextView;
import iomango.com.forestdirect.mvp.view.custom.DatePickerEditText;
import iomango.com.forestdirect.mvp.view.custom.DialogEditText;
import iomango.com.forestdirect.mvp.view.custom.spinner.Spinner;

/**
 * Created by Clelia López on 03/10/2017
 */
public class OneWayFragment
        extends GenericFragment<MVP.RequiredFragmentMethods, MVP.ProvidedPresenterMethodsFragment, OneWayPresenter>
        implements MVP.RequiredFragmentMethods, View.OnClickListener {

    /**
     * Attributes
     */
    private NestedScrollView containerLayout;
    private TableRow departureTimeTextView;
    private TableRow departureTimeEditText;
    private TableRow returnTimeTextView;
    private TableRow returnTimeEditText;
    private TableRow preferredAirlineTextView;
    private TableRow preferredAirlineSpinner;
    private Spinner airlinesSpinner;
    private boolean moreOptionsIsVisible = false;


    /**
     * Hook method called to set up the fragment's user interface. It returns a View object,
     * that is given to the hosting activity to install it into its view hierarchy.
     *
     * @param container view parent of the fragment in the activity, therefore its container
     * @param savedInstanceState object that contains saved state information.
     * @return View object returned by the inflation process
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        containerLayout = (NestedScrollView) inflater.inflate(R.layout.fragment_one_way, container, false);

        // Initialize retained fragment state
        isRetainedFragment = false;

        // Instantiate the presenter
        super.onCreate(OneWayPresenter.class, this);

        // Initialize the view components defined in the fragment's layout
        initializeViews();

        return containerLayout;
    }

    /**
     * Initialize the Views and GUI widgets.
     */
    private void initializeViews() {

        // Initializing views
        CustomEditText fromEditText = (CustomEditText) containerLayout.findViewById(R.id.from_edit_text);
        CustomEditText toEditText = (CustomEditText) containerLayout.findViewById(R.id.to_edit_text);
        DatePickerEditText dateEditText = (DatePickerEditText) containerLayout.findViewById(R.id.departure_date_edit_text);
        DialogEditText kindEditText = (DialogEditText) containerLayout.findViewById(R.id.kind_edit_text);
        CheckBox fromCheckBox = (CheckBox) containerLayout.findViewById(R.id.from_checkbox);
        CheckBox toCheckBox = (CheckBox) containerLayout.findViewById(R.id.to_checkbox);
        CustomTextView moreOptionsCustomTextView = (CustomTextView) containerLayout.findViewById(R.id.more_options_link);
        departureTimeTextView = (TableRow) containerLayout.findViewById(R.id.departure_table_label);
        departureTimeEditText = (TableRow) containerLayout.findViewById(R.id.departure_table_edit_text);
        returnTimeTextView = (TableRow) containerLayout.findViewById(R.id.return_table_label);
        returnTimeEditText = (TableRow) containerLayout.findViewById(R.id.return_table_edit_text);
        preferredAirlineTextView = (TableRow) containerLayout.findViewById(R.id.airline_table_label);
        preferredAirlineSpinner = (TableRow) containerLayout.findViewById(R.id.airline_table_spinner);
        airlinesSpinner = (Spinner) containerLayout.findViewById(R.id.airline_spinner);

        // Tinting drawables
        DrawablesTools.tintDrawable(getContext(), R.drawable.ic_flight_takeoff, R.color.colorPrimary);
        DrawablesTools.tintDrawable(getContext(), R.drawable.ic_flight_land, R.color.colorPrimary);
        DrawablesTools.tintDrawable(getContext(), R.drawable.ic_event, R.color.colorPrimary);
        DrawablesTools.tintDrawable(getContext(), R.drawable.ic_filter_outline, R.color.colorPrimary);
        DrawablesTools.tintDrawable(getContext(), R.drawable.ic_access_time, R.color.colorPrimary);
        DrawablesTools.tintDrawable(getContext(), R.drawable.ic_flight, R.color.colorPrimary);

        // Setting listeners
        fromEditText.setOnClickListener(this);
        toEditText.setOnClickListener(this);
        dateEditText.setOnClickListener(this);
        kindEditText.setOnClickListener(this);
        fromCheckBox.setOnClickListener(this);
        toCheckBox.setOnClickListener(this);
        moreOptionsCustomTextView.setOnClickListener(this);

        GlobalModel model = getPresenter().getModel();
        List<AirlineModel> airlines = model.loadJsonDatabase(
                new TypeToken<ArrayList<AirlineModel>>(){}, "Data/airlines");

        Iterator<AirlineModel> iterator = airlines.iterator();
        while (iterator.hasNext()) {
            AirlineModel airline = iterator.next();
            if (!airline.isActive())
                iterator.remove();
        }

        ArrayList<String> airlineNames = model.createStringList(airlines, "AirLineName");
        airlinesSpinner.getSpinner().setItems(airlineNames);
    }

    /**
     * Hook OnClickListener
     */
    @Override
    public void onClick(View view) {
        /*getPresenter().handleClick(view.getId());*/
        switch (view.getId()) {
            case R.id.from_checkbox:
                break;
            case R.id.to_checkbox:
                break;
            case R.id.more_options_link:
                if (moreOptionsIsVisible) {
                    departureTimeTextView.setVisibility(View.GONE);
                    departureTimeEditText.setVisibility(View.GONE);
                    returnTimeTextView.setVisibility(View.GONE);
                    returnTimeEditText.setVisibility(View.GONE);
                    preferredAirlineTextView.setVisibility(View.GONE);
                    preferredAirlineSpinner.setVisibility(View.GONE);
                            moreOptionsIsVisible = false;
                } else {
                    departureTimeTextView.setVisibility(View.VISIBLE);
                    departureTimeEditText.setVisibility(View.VISIBLE);
                    returnTimeTextView.setVisibility(View.VISIBLE);
                    returnTimeEditText.setVisibility(View.VISIBLE);
                    preferredAirlineTextView.setVisibility(View.VISIBLE);
                    preferredAirlineSpinner.setVisibility(View.VISIBLE);
                    moreOptionsIsVisible = true;
                }
                break;
        }
    }
}