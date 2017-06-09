package iomango.com.forestdirect.mvp.view.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.RelativeLayout;

import java.util.List;

import iomango.com.forestdirect.R;
import iomango.com.forestdirect.mvp.MVP;
import iomango.com.forestdirect.mvp.common.generic.GenericFragment;
import iomango.com.forestdirect.mvp.common.global.Constants;
import iomango.com.forestdirect.mvp.common.interfaces.Listener;
import iomango.com.forestdirect.mvp.common.utilities.DrawablesTools;
import iomango.com.forestdirect.mvp.model.AdvancedOptionsModel;
import iomango.com.forestdirect.mvp.model.data.AirportModel;
import iomango.com.forestdirect.mvp.model.data.MultiCityModel;
import iomango.com.forestdirect.mvp.presenter.OneWayPresenter;
import iomango.com.forestdirect.mvp.view.activities.MainActivity;
import iomango.com.forestdirect.mvp.view.activities.SearchActivity;
import iomango.com.forestdirect.mvp.view.adapter.MultiCityAdapter;
import iomango.com.forestdirect.mvp.view.custom.CustomEditText;
import iomango.com.forestdirect.mvp.view.decorator.DividerItemDecoration;
import jp.wasabeef.recyclerview.animators.LandingAnimator;

/**
 * Created by Clelia López on 03/10/2017
 */
public class MultiCityFragment
        extends GenericFragment<MVP.RequiredFragmentMethods, MVP.ProvidedPresenterMethodsFragment, OneWayPresenter>
        implements MVP.RequiredFragmentMethods, Listener.OnMultiCityActionListener, View.OnClickListener {

    /**
     * Attributes
     */
    private RelativeLayout relativeLayout;
    private MultiCityAdapter adapter;
    private RecyclerView recyclerView;
    private boolean isFromActive = false;
    private CustomEditText fromEditText;
    private CustomEditText toEditText;
    private String fromCode;
    private String toCode;


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
        relativeLayout = (RelativeLayout) inflater.inflate(R.layout.fragment_multicity, container, false);

        // Initialize retained fragment state
        isRetainedFragment = false;

        // Instantiate the presenter
        super.onCreate(OneWayPresenter.class, this);

        // Initialize the view components defined in the fragment's layout
        initializeViews();

        return relativeLayout;
    }

    /**
     * Initialize the Views and GUI widgets.
     */
    private void initializeViews() {
        // Tinting drawables
        DrawablesTools.tintDrawable(getContext(), R.drawable.ic_add, R.color.white);

        // Recycler set up
        recyclerView = (RecyclerView) relativeLayout.findViewById(R.id.steps_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new LandingAnimator());
        recyclerView.getItemAnimator().setAddDuration(200);
        recyclerView.addItemDecoration(new DividerItemDecoration(activityContext, R.drawable.divider));
        recyclerView.setHasFixedSize(false);

        adapter = new MultiCityAdapter(getContext(), 2, 2, 6);
        adapter.setOnMultiCityActionListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.SEARCH_ACTIVITY && resultCode == Activity.RESULT_OK) {
            AirportModel location = data.getParcelableExtra("location");
            if (isFromActive) {
                fromCode = location.getCode();
                fromEditText.setText(location.getMunicipality() + " (" + location.getCode() + ")");
                fromEditText.clearFocus();
            } else {
                toCode = location.getCode();
                toEditText.setText(location.getMunicipality() + " (" + location.getCode() + ")");
                toEditText.clearFocus();
            }
        } else {
            if (fromEditText != null)
                fromEditText.clearFocus();
            if (toEditText != null)
                toEditText.clearFocus();
        }
    }

    public void startSearchActivity() {
        Intent intent = new Intent(getActivity(), SearchActivity.class);
        startActivityForResult(intent, Constants.SEARCH_ACTIVITY);
    }

    @Override
    public void onFromEditTextClicked(CustomEditText editText) {
        fromEditText = editText;
        isFromActive = true;
        startSearchActivity();
    }

    @Override
    public void onToEditTextClicked(CustomEditText editText) {
        toEditText = editText;
        isFromActive = false;
        startSearchActivity();
    }

    @Override
    public void onAddFlightClicked() {
        adapter.addElement();
    }

    @Override
    public void onSearchButtonClicked() {
        List<MultiCityAdapter.ViewHolder> holders = adapter.getHolders();
        MultiCityModel model = new MultiCityModel();
        int total = adapter.getSize();

        MultiCityAdapter.ViewHolder view;
        for (int i = 0; i < total - 1; i++) {
            view = holders.get(i);
            model.addFrom(view.getFromEditText().getValue());
            model.addIncludeFrom(view.getFromCheckbox().isChecked() ? "1" : "0");
            model.addTo(view.getToEditText().getValue());
            model.addIncludeTo(view.getToCheckbox().isChecked() ? "1" : "0");
            model.addDate(view.getDepartureDateEditText().getValue());
        }
        view = holders.get(total - 1);
        AdvancedOptionsModel data = view.getDialogEditText().getData();
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


            if (model.isValid() && model.getTotalPassengers() <= 6)
                getPresenter().executeNetworkRequest(model);
            else
                getParentActivity(MainActivity.class).displaySnackBar(relativeLayout,
                        R.string.passenger_error, R.string.close_label, this);
        }
    }

    @Override
    public void onClick(View view) {
        List<MultiCityAdapter.ViewHolder> holders = adapter.getHolders();
        int total = adapter.getSize();
        MultiCityAdapter.ViewHolder holder = holders.get(total - 1);
        holder.getDialogEditText().setText("");
    }
}
