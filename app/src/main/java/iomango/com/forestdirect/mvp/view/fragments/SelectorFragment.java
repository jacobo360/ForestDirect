package iomango.com.forestdirect.mvp.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.LinearLayout;

import iomango.com.forestdirect.R;
import iomango.com.forestdirect.mvp.MVP;
import iomango.com.forestdirect.mvp.common.generic.GenericFragment;
import iomango.com.forestdirect.mvp.presenter.TemplatePresenterFragment;
import iomango.com.forestdirect.mvp.view.activities.MainActivity;
import iomango.com.forestdirect.mvp.view.custom.CustomRadioButton;

/**
 * Created by Clelia López on 03/10/2017
 */
public class SelectorFragment
        extends GenericFragment<MVP.RequiredFragmentMethods, MVP.ProvidedPresenterMethodsFragment, TemplatePresenterFragment>
        implements MVP.RequiredFragmentMethods, View.OnClickListener {

    /**
     * Attributes
     */
    private MainActivity parentActivity;
    private LinearLayout linearLayout;


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
        linearLayout = (LinearLayout) inflater.inflate(R.layout.fragment_selector, container, false);

        // Initialize parent activity
        parentActivity = (MainActivity) getActivity();

        // Setting fragment
        parentActivity.placeFragment(R.id.fragment, new OneWayFragment());

        // Initialize retained fragment state
        isRetainedFragment = false;

        // Instantiate the presenter
        super.onCreate(TemplatePresenterFragment.class, this);

        // Initialize the view components defined in the fragment's layout
        initializeViews();

        return linearLayout;
    }

    /**
     * Initialize the Views and GUI widgets.
     */
    private void initializeViews() {
        CustomRadioButton oneWayRadioButton = (CustomRadioButton) linearLayout.findViewById(R.id.one_way_radio_button);
        CustomRadioButton roundTripRadioButton = (CustomRadioButton) linearLayout.findViewById(R.id.round_trip_radio_button);
        CustomRadioButton multiCityRadioButton = (CustomRadioButton) linearLayout.findViewById(R.id.multi_city_radio_button);

        // Setting listeners
        oneWayRadioButton.setOnClickListener(this);
        roundTripRadioButton.setOnClickListener(this);
        multiCityRadioButton.setOnClickListener(this);
    }

    /**
     * Hook OnClickListener
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.one_way_radio_button:
                parentActivity.replaceFragment(R.id.fragment, new OneWayFragment());
                break;
            case R.id.round_trip_radio_button:
                parentActivity.replaceFragment(R.id.fragment, new RoundTripFragment());
                break;
            case R.id.multi_city_radio_button:
                parentActivity.replaceFragment(R.id.fragment, new MultiCityFragment());
                break;
        }
    }
}
