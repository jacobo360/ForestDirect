package iomango.com.forestdirect.mvp.view.fragments;

import android.app.Activity;
import android.content.Intent;
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
import iomango.com.forestdirect.mvp.common.global.Constants;
import iomango.com.forestdirect.mvp.common.interfaces.Listener;
import iomango.com.forestdirect.mvp.common.utilities.Date;
import iomango.com.forestdirect.mvp.common.utilities.DrawablesTools;
import iomango.com.forestdirect.mvp.model.AdvancedOptionsModel;
import iomango.com.forestdirect.mvp.model.GlobalModel;
import iomango.com.forestdirect.mvp.model.data.AirlineModel;
import iomango.com.forestdirect.mvp.model.data.AirportLocationModel;
import iomango.com.forestdirect.mvp.model.data.AirportSearchModel;
import iomango.com.forestdirect.mvp.presenter.OneWayPresenter;
import iomango.com.forestdirect.mvp.view.activities.MainActivity;
import iomango.com.forestdirect.mvp.view.activities.SearchActivity;
import iomango.com.forestdirect.mvp.view.custom.CustomButton;
import iomango.com.forestdirect.mvp.view.custom.CustomEditText;
import iomango.com.forestdirect.mvp.view.custom.CustomTextView;
import iomango.com.forestdirect.mvp.view.custom.DatePickerEditText;
import iomango.com.forestdirect.mvp.view.custom.DialogEditText;
import iomango.com.forestdirect.mvp.view.custom.TimePickerEditText;
import iomango.com.forestdirect.mvp.view.custom.spinner.Spinner;

/**
 * Created by Clelia López on 03/10/2017
 */
public class RoundTripFragment
        extends GenericFragment<MVP.RequiredFragmentMethods, MVP.ProvidedPresenterMethodsFragment, OneWayPresenter>
        implements MVP.RequiredFragmentMethods, View.OnClickListener, View.OnFocusChangeListener,
        Listener.OnDateSetListener {

    /**
     * Attributes
     */
    private NestedScrollView containerLayout;
    private CustomEditText fromEditText;
    private CustomEditText toEditText;
    private CheckBox fromCheckBox;
    private CheckBox toCheckBox;
    private CheckBox flexibleDatesCheckBox;
    private DatePickerEditText departureDatePickerEditText;
    private DatePickerEditText returnDatePickerEditText;
    private DialogEditText kindDialogEditText;
    private TimePickerEditText departureTimeEditText;
    private TimePickerEditText returnTimeEditText;
    private TableRow departureTextTableRow;
    private TableRow departureEditTableRow;
    private TableRow returnTextTableRow;
    private TableRow returnEditTableRow;
    private TableRow airlineTextTableRow;
    private TableRow airlineSpinnerTableRow;
    private Spinner airlinesSpinner;
    private boolean moreOptionsIsVisible = false;
    private boolean isFromActive = false;
    private List<AirlineModel> airlines;
    private String fromCode = "";
    private String toCode = "";


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
        containerLayout = (NestedScrollView) inflater.inflate(R.layout.fragment_round_trip, container, false);

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
        fromEditText = (CustomEditText) containerLayout.findViewById(R.id.from_edit_text);
        toEditText = (CustomEditText) containerLayout.findViewById(R.id.to_edit_text);
        fromCheckBox = (CheckBox) containerLayout.findViewById(R.id.from_checkbox);
        toCheckBox = (CheckBox) containerLayout.findViewById(R.id.to_checkbox);
        departureDatePickerEditText = (DatePickerEditText) containerLayout.findViewById(R.id.departure_date_edit_text);
        returnDatePickerEditText = (DatePickerEditText) containerLayout.findViewById(R.id.return_date_edit_text);
        flexibleDatesCheckBox = (CheckBox) containerLayout.findViewById(R.id.flexible_dates_checkbox);
        kindDialogEditText = (DialogEditText) containerLayout.findViewById(R.id.kind_edit_text);
        departureTimeEditText = (TimePickerEditText) containerLayout.findViewById(R.id.departure_time_edit_text);
        returnTimeEditText = (TimePickerEditText) containerLayout.findViewById(R.id.return_time_edit_text);
        CustomTextView moreOptionsCustomTextView = (CustomTextView) containerLayout.findViewById(R.id.more_options_link);
        departureTextTableRow = (TableRow) containerLayout.findViewById(R.id.departure_table_label);
        departureEditTableRow = (TableRow) containerLayout.findViewById(R.id.departure_table_edit_text);
        returnTextTableRow = (TableRow) containerLayout.findViewById(R.id.return_table_label);
        returnEditTableRow = (TableRow) containerLayout.findViewById(R.id.return_table_edit_text);
        airlineTextTableRow = (TableRow) containerLayout.findViewById(R.id.airline_table_label);
        airlineSpinnerTableRow = (TableRow) containerLayout.findViewById(R.id.airline_table_spinner);
        airlinesSpinner = (Spinner) containerLayout.findViewById(R.id.airline_spinner);
        CustomButton searchButton =  (CustomButton) containerLayout.findViewById(R.id.search_button);

        fromEditText.clearFocus();
        toEditText.clearFocus();

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
        fromEditText.setOnFocusChangeListener(this);
        toEditText.setOnFocusChangeListener(this);
        departureDatePickerEditText.setOnDateSetListener(this, -1);
        moreOptionsCustomTextView.setOnClickListener(this);
        searchButton.setOnClickListener(this);

        // Loading data and setting up spinner
        GlobalModel model = getPresenter().getModel();
        airlines = model.loadJsonDatabase(new TypeToken<ArrayList<AirlineModel>>(){}, "Data/airlines");
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
        switch (view.getId()) {
            case R.id.from_edit_text:
                startSearchActivity();
                isFromActive = true;
                break;
            case R.id.to_edit_text:
                startSearchActivity();
                isFromActive = false;
                break;
            case R.id.more_options_link:
                if (moreOptionsIsVisible) {
                    departureTextTableRow.setVisibility(View.GONE);
                    departureEditTableRow.setVisibility(View.GONE);
                    returnTextTableRow.setVisibility(View.GONE);
                    returnEditTableRow.setVisibility(View.GONE);
                    airlineTextTableRow.setVisibility(View.GONE);
                    airlineSpinnerTableRow.setVisibility(View.GONE);
                    moreOptionsIsVisible = false;
                } else {
                    departureTextTableRow.setVisibility(View.VISIBLE);
                    departureEditTableRow.setVisibility(View.VISIBLE);
                    returnTextTableRow.setVisibility(View.VISIBLE);
                    returnEditTableRow.setVisibility(View.VISIBLE);
                    airlineTextTableRow.setVisibility(View.VISIBLE);
                    airlineSpinnerTableRow.setVisibility(View.VISIBLE);
                    moreOptionsIsVisible = true;
                }
                break;
            case R.id.search_button:
                AirportSearchModel model = new AirportSearchModel();
                model.setType("Round");
                model.setFrom(fromCode);
                model.setIncludeFrom(fromCheckBox.isChecked() ? "1" : "0");
                model.setTo(toCode);
                model.setIncludeTo(toCheckBox.isChecked() ? "1" : "0");
                model.setDepartureDate(departureDatePickerEditText.getValue());
                model.setArriveDate(returnDatePickerEditText.getValue());
                model.setIncludeFlexibleDates(flexibleDatesCheckBox.isChecked() ? "on": null);
                AdvancedOptionsModel data = kindDialogEditText.getData();
                if (data != null) {
                    switch (data.getCabin()) {
                        case "Economy":
                            model.setCabin("M");
                            break;
                        case "Premium Economy":
                            model.setCabin("Y");
                            break;
                        case "Business":
                            model.setCabin("C");
                            break;
                        case "First":
                            model.setCabin("F");
                            break;
                        default:
                            model.setCabin("");
                    }
                    model.setAdult(String.valueOf(data.getAdult()));
                    model.setSenior(String.valueOf(data.getSenior()));
                    model.setChild(String.valueOf(data.getChildren()));
                    model.setLapInfant(String.valueOf(data.getInfant()));
                }
                if (moreOptionsIsVisible) {
                    int selected = airlinesSpinner.getSpinner().getSelectedPosition();
                    model.setDepartureTime(departureTimeEditText.getValue());
                    model.setArriveTime(returnTimeEditText.getValue());
                    model.setAirline(airlines.get(selected).getAirLineCode());
                } else {
                    model.setDepartureTime("");
                    model.setArriveTime("");
                    model.setAirline("");
                }

                if (model.isValid() && model.getTotalPassengers() <= 6)
                    getPresenter().executeNetworkRequest(model);
                else
                    getParentActivity(MainActivity.class).displaySnackBar(containerLayout,
                        R.string.passenger_error, R.string.close_label, this);

                break;
            case android.support.design.R.id.snackbar_action:
                kindDialogEditText.setText("");
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.SEARCH_ACTIVITY && resultCode == Activity.RESULT_OK) {
            AirportLocationModel location = data.getParcelableExtra("location");
            if (isFromActive) {
                fromCode = location.getCode();
                fromEditText.setText(location.getCity() + " (" + location.getCode() + ")");
                fromEditText.clearFocus();
            } else {
                toCode = location.getCode();
                toEditText.setText(location.getCity() + " (" + location.getCode() + ")");
                toEditText.clearFocus();
            }
        } else {
            fromEditText.clearFocus();
            toEditText.clearFocus();
        }
    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        switch (view.getId()) {
            case R.id.from_edit_text:
                if (hasFocus) {
                    startSearchActivity();
                    isFromActive = true;
                }
                break;
            case R.id.to_edit_text:
                if (hasFocus) {
                    startSearchActivity();
                    isFromActive = false;
                }
                break;
        }
    }

    public void startSearchActivity() {
        Intent intent = new Intent(getActivity(), SearchActivity.class);
        startActivityForResult(intent, Constants.SEARCH_ACTIVITY);
    }

    @Override
    public void updateDate(Date date, int id) {
        returnDatePickerEditText.setMinimumDate(date);
    }
}
