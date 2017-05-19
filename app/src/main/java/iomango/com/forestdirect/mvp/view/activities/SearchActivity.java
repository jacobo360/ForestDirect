package iomango.com.forestdirect.mvp.view.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

import iomango.com.forestdirect.R;
import iomango.com.forestdirect.mvp.MVP;
import iomango.com.forestdirect.mvp.common.generic.GenericActivity;
import iomango.com.forestdirect.mvp.common.global.Constants;
import iomango.com.forestdirect.mvp.common.global.Enums;
import iomango.com.forestdirect.mvp.common.interfaces.Listener.OnLocationSelectedListener;
import iomango.com.forestdirect.mvp.common.utilities.DrawablesTools;
import iomango.com.forestdirect.mvp.model.SearchActivityModel;
import iomango.com.forestdirect.mvp.model.data.AirportLocationModel;
import iomango.com.forestdirect.mvp.model.data.HotelLocationModel;
import iomango.com.forestdirect.mvp.presenter.SearchActivityPresenter;
import iomango.com.forestdirect.mvp.view.adapter.AirportListAdapter;
import iomango.com.forestdirect.mvp.view.adapter.HotelListAdapter;
import iomango.com.forestdirect.mvp.view.custom.CustomEditText;
import iomango.com.forestdirect.mvp.view.decorator.DividerItemDecoration;

/**
 * Created by Clelia López on 08/12/2015
 */
public class SearchActivity
        extends GenericActivity<MVP.RequiredActivityMethods, MVP.ProvidedPresenterMethodsActivity, SearchActivityPresenter>
        implements MVP.RequiredActivityMethods, View.OnClickListener, OnLocationSelectedListener {

    /**
     * Attributes
     */
    private CustomEditText editText;
    private RecyclerView defaultRecyclerView;
    private RecyclerView locationsRecyclerView;
    private RecyclerView hotelsRecyclerView;
    private AirportListAdapter airportListAdapter;
    private HotelListAdapter locationListAdapter;
    private HotelListAdapter hotelListAdapter;
    private boolean lookingHotels = false;


    /**
     * This hook method is called when the Activity is instantiated.
     *
     * @param savedInstanceState saved previous state, it may be null
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Intent intent = getIntent();
        if (intent != null)
            lookingHotels = intent.getBooleanExtra("lookingHotels", false);

        // Instantiate the presenter
        super.onCreate(SearchActivityPresenter.class, this);

        // Initialize all view components defined in the activity's layout
        initializeViews();
    }

    /**
     * Initialize the Views and GUI widgets.
     */
    private void initializeViews() {
        editText = (CustomEditText) findViewById(R.id.search_edit_text);
        ImageButton backImageButton = (ImageButton) findViewById(R.id.back_image_button);
        ImageButton clearImageButton = (ImageButton) findViewById(R.id.clear_image_button);
        defaultRecyclerView = (RecyclerView) findViewById(R.id.recycler_location_list);

        if (lookingHotels) {
            defaultRecyclerView.setVisibility(View.GONE);
            locationsRecyclerView = (RecyclerView) findViewById(R.id.recycler_location);
            hotelsRecyclerView = (RecyclerView) findViewById(R.id.recycler_hotels);
        }

        // Tinting icons
        DrawablesTools.tintDrawable(this, R.drawable.ic_clear, R.color.grey_500);
        DrawablesTools.tintDrawable(this, R.drawable.ic_arrow_back, R.color.grey_500);

        setDialog(findViewById(R.id.dialog));

        // Setting listeners
        clearImageButton.setOnClickListener(this);
        backImageButton.setOnClickListener(this);

        // Recycler set up
        defaultRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        defaultRecyclerView.addItemDecoration(new DividerItemDecoration(this, R.drawable.divider));
        defaultRecyclerView.setHasFixedSize(false);

        if (lookingHotels) {
            locationsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            locationsRecyclerView.addItemDecoration(new DividerItemDecoration(this, R.drawable.divider));
            locationsRecyclerView.setHasFixedSize(false);

            hotelsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            hotelsRecyclerView.addItemDecoration(new DividerItemDecoration(this, R.drawable.divider));
            hotelsRecyclerView.setHasFixedSize(false);
        }

        // Text watcher functionality
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence text, int start, int count, int after) {
                // no-op
            }

            @Override
            public void onTextChanged(CharSequence text, int start, int before, int count) {
                // no-op
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (lookingHotels) {
                    if (editable.toString().length() > 0)
                        getPresenter().executeNetworkRequest(new SearchActivityModel(editable.toString(), true));
                    else {
                        if (locationListAdapter != null && hotelListAdapter != null) {
                            locationListAdapter.clear();
                            hotelListAdapter.clear();
                        }
                    }
                } else {
                    if (editable.toString().length() > 0)
                        getPresenter().executeNetworkRequest(new SearchActivityModel(editable.toString()));
                    else {
                        if (airportListAdapter != null)
                            airportListAdapter.clear();
                    }
                }
            }
        });
    }

    /**
     * Called when the user clicks a button to perform some action
     *
     * @param view Indicates the view component pressed by the user
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.clear_image_button:
                editText.setText("");
                break;
            case R.id.back_image_button:
                setResult(Constants.CLEAR_ACTIVITY);
                finish();
                break;
            default:
                getPresenter().handleClick(view.getId());
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> void updateView(T data) {
        List<HotelLocationModel> locationList = (List<HotelLocationModel>)data;

        if (lookingHotels) {
            List<HotelLocationModel> locations = new ArrayList<>();
            List<HotelLocationModel> hotels = new ArrayList<>();
            for (HotelLocationModel location : locationList) {
                if (location.getHotelCode() != null)
                    hotels.add(location);
                else
                    locations.add(location);
            }

            locationListAdapter = new HotelListAdapter(this, locations);
            hotelListAdapter = new HotelListAdapter(this, hotels);

            locationListAdapter.setOnLocationSelectedListener(this);
            hotelListAdapter.setOnLocationSelectedListener(this);

            locationsRecyclerView.setAdapter(locationListAdapter);
            hotelsRecyclerView.setAdapter(hotelListAdapter);

            dismissDialog(Enums.DialogType.TIME_UNDETERMINED);
        } else {
            airportListAdapter = new AirportListAdapter(this, (List<AirportLocationModel>)data);
            airportListAdapter.setOnLocationSelectedListener(this);
            dismissDialog(Enums.DialogType.TIME_UNDETERMINED);
            defaultRecyclerView.setAdapter(airportListAdapter);
        }
    }

    @Override
    public <T> void updateLocation(T location) {
        Intent intent = new Intent();
        if (lookingHotels)
            intent.putExtra("location", (HotelLocationModel) location);
        else
            intent.putExtra("location", (AirportLocationModel) location);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}
