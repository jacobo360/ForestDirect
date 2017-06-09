package iomango.com.forestdirect.mvp.view.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import iomango.com.forestdirect.R;
import iomango.com.forestdirect.mvp.MVP;
import iomango.com.forestdirect.mvp.common.generic.GenericFragment;
import iomango.com.forestdirect.mvp.common.global.Constants;
import iomango.com.forestdirect.mvp.common.interfaces.Listener;
import iomango.com.forestdirect.mvp.common.utilities.Date;
import iomango.com.forestdirect.mvp.common.utilities.DrawablesTools;
import iomango.com.forestdirect.mvp.model.data.HotelModel;
import iomango.com.forestdirect.mvp.presenter.OneWayPresenter;
import iomango.com.forestdirect.mvp.view.activities.SearchActivity;
import iomango.com.forestdirect.mvp.view.custom.CustomButton;
import iomango.com.forestdirect.mvp.view.custom.CustomEditText;
import iomango.com.forestdirect.mvp.view.custom.DatePickerEditText;
import iomango.com.forestdirect.mvp.view.custom.DialogEditText;
import iomango.com.forestdirect.mvp.view.custom.spinner.Spinner;

/**
 * Created by Clelia López on 03/10/2017
 */
public class HotelsFragment
        extends GenericFragment<MVP.RequiredFragmentMethods, MVP.ProvidedPresenterMethodsFragment, OneWayPresenter>
        implements MVP.RequiredFragmentMethods, View.OnClickListener, View.OnFocusChangeListener,
        Listener.OnDateSetListener {

    /**
     * Attributes
     */
    private RelativeLayout containerLayout;
    private CustomEditText destinationEditText;
    private DatePickerEditText checkInPickerEditText;
    private DatePickerEditText checkOutPickerEditText;
    private DialogEditText kindDialogEditText;


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
        containerLayout = (RelativeLayout) inflater.inflate(R.layout.fragment_hotels, container, false);

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
        destinationEditText = (CustomEditText) containerLayout.findViewById(R.id.destination_edit_text);
        checkInPickerEditText = (DatePickerEditText) containerLayout.findViewById(R.id.check_in_edit_text);
        checkOutPickerEditText = (DatePickerEditText) containerLayout.findViewById(R.id.check_out_edit_text);
        kindDialogEditText = (DialogEditText) containerLayout.findViewById(R.id.guests_edit_text);
        CustomButton searchButton =  (CustomButton) containerLayout.findViewById(R.id.search_button);

        destinationEditText.clearFocus();

        // Tinting drawables
        DrawablesTools.tintDrawable(getContext(), R.drawable.ic_event, R.color.colorPrimary);
        DrawablesTools.tintDrawable(getContext(), R.drawable.ic_my_location, R.color.colorPrimary);
        DrawablesTools.tintDrawable(getContext(), R.drawable.ic_filter_outline, R.color.colorPrimary);
        DrawablesTools.tintDrawable(getContext(), R.drawable.ic_hotel, R.color.colorPrimary);

        // Setting listeners
        destinationEditText.setOnClickListener(this);
        destinationEditText.setOnFocusChangeListener(this);
        checkInPickerEditText.setOnDateSetListener(this);
        searchButton.setOnClickListener(this);
    }

    /**
     * Hook OnClickListener
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.destination_edit_text:
                startSearchActivity();
                break;
            /*case R.id.search_button:

               TODO
               SearchModel model = new SearchModel();
                model.setType("OneWay");
                model.setFrom(fromCode);
                model.setTo(toCode);
                model.setDepartureDate(datePickerEditText.getValue());
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



                 // getPresenter().executeNetworkRequest(model);
                 break;
                */
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.SEARCH_ACTIVITY && resultCode == Activity.RESULT_OK) {
            HotelModel location = data.getParcelableExtra("location");
            destinationEditText.setText(location.getCity() + " (" + location.getCode() + ")");
            destinationEditText.clearFocus();
        } else
            destinationEditText.clearFocus();
    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        if (hasFocus)
            startSearchActivity();
    }

    public void startSearchActivity() {
        Intent intent = new Intent(getActivity(), SearchActivity.class);
        intent.putExtra("lookingHotels", true);
        startActivityForResult(intent, Constants.SEARCH_ACTIVITY);
    }

    @Override
    public void updateDate(Date date) {
        checkOutPickerEditText.setMinimumDate(date);
    }
}
