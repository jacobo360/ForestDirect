package iomango.com.forestdirect.mvp.view.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.CheckBox;
import android.widget.DatePicker;

import java.util.Calendar;

import iomango.com.forestdirect.R;
import iomango.com.forestdirect.mvp.MVP;
import iomango.com.forestdirect.mvp.common.generic.GenericFragment;
import iomango.com.forestdirect.mvp.common.utilities.DrawablesTools;
import iomango.com.forestdirect.mvp.presenter.TemplatePresenterFragment;
import iomango.com.forestdirect.mvp.view.custom.CustomEditText;
import iomango.com.forestdirect.mvp.view.custom.CustomTextView;

/**
 * Created by Clelia López on 03/10/2017
 */
public class OneWayFragment
        extends GenericFragment<MVP.RequiredFragmentMethods, MVP.ProvidedPresenterMethodsFragment, TemplatePresenterFragment>
        implements MVP.RequiredFragmentMethods, View.OnClickListener, DatePickerDialog.OnDateSetListener {

    /**
     * Attributes
     */
    private NestedScrollView containerLayout;
    private DatePickerDialog datePickerDialog;


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
        super.onCreate(TemplatePresenterFragment.class, this);

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
        CustomEditText dateEditText = (CustomEditText) containerLayout.findViewById(R.id.date_edit_text);
        CustomEditText kindEditText = (CustomEditText) containerLayout.findViewById(R.id.kind_edit_text);
        CheckBox fromCheckBox = (CheckBox) containerLayout.findViewById(R.id.from_checkbox);
        CheckBox toCheckBox = (CheckBox) containerLayout.findViewById(R.id.to_checkbox);
        CustomTextView moreOptionsCustomTextView = (CustomTextView) containerLayout.findViewById(R.id.more_options_link);

        // Tinting drawables
        DrawablesTools.tintDrawable(getContext(), R.drawable.ic_flight_takeoff, R.color.colorPrimary);
        DrawablesTools.tintDrawable(getContext(), R.drawable.ic_flight_land, R.color.colorPrimary);
        DrawablesTools.tintDrawable(getContext(), R.drawable.ic_event, R.color.colorPrimary);
        DrawablesTools.tintDrawable(getContext(), R.drawable.ic_filter_outline, R.color.colorPrimary);

        // Setting listeners
        fromEditText.setOnClickListener(this);
        toEditText.setOnClickListener(this);
        dateEditText.setOnClickListener(this);
        kindEditText.setOnClickListener(this);
        fromCheckBox.setOnClickListener(this);
        toCheckBox.setOnClickListener(this);
        moreOptionsCustomTextView.setOnClickListener(this);

        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        datePickerDialog = new DatePickerDialog(getActivityContext(), this, year, month, day);
    }

    /**
     * Hook OnClickListener
     */
    @Override
    public void onClick(View view) {
        /*getPresenter().handleClick(view.getId());*/
        switch (view.getId()) {
            case R.id.from_edit_text:
                break;
            case R.id.to_edit_text:
                break;
            case R.id.date_edit_text:
                datePickerDialog.show();
                break;
            case R.id.kind_edit_text:
                break;
            case R.id.from_checkbox:
                break;
            case R.id.to_checkbox:
                break;
            case R.id.more_options_link:
                break;
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

    }
}
