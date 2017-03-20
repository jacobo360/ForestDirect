package iomango.com.forestdirect.mvp.view.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.RelativeLayout;

import iomango.com.forestdirect.R;
import iomango.com.forestdirect.mvp.MVP;
import iomango.com.forestdirect.mvp.common.generic.GenericFragment;
import iomango.com.forestdirect.mvp.common.interfaces.Listener;
import iomango.com.forestdirect.mvp.common.utilities.DrawablesTools;
import iomango.com.forestdirect.mvp.presenter.TemplatePresenterFragment;
import iomango.com.forestdirect.mvp.view.adapter.MultiCityAdapter;
import iomango.com.forestdirect.mvp.view.decorator.DividerItemDecoration;

/**
 * Created by Clelia López on 03/10/2017
 */
public class MultiCityFragment
        extends GenericFragment<MVP.RequiredFragmentMethods, MVP.ProvidedPresenterMethodsFragment, TemplatePresenterFragment>
        implements MVP.RequiredFragmentMethods, Listener.OnMultiCityActionListener {

    /**
     * Attributes
     */
    private RelativeLayout relativeLayout;
    private MultiCityAdapter adapter;


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
        super.onCreate(TemplatePresenterFragment.class, this);

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
        RecyclerView recyclerView = (RecyclerView) relativeLayout.findViewById(R.id.steps_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(activityContext, R.drawable.divider));
        recyclerView.setHasFixedSize(false);

        adapter = new MultiCityAdapter(getContext(), 2, 2, 6);
        adapter.setOnMultiCityActionListener(this);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onSearchPressed() {

    }

    @Override
    public void onAddFlightPressed() {
        adapter.addElement();
    }
}
