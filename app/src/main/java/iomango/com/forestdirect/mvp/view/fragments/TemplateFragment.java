package iomango.com.forestdirect.mvp.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import iomango.com.forestdirect.mvp.MVP;
import iomango.com.forestdirect.mvp.common.generic.GenericFragment;
import iomango.com.forestdirect.mvp.common.utilities.Logger;
import iomango.com.forestdirect.mvp.presenter.TemplatePresenterFragment;

/**
 * Created by Clelia López on 03/10/2017
 */
public class TemplateFragment
        extends GenericFragment<MVP.RequiredFragmentMethods, MVP.ProvidedPresenterMethodsFragment, TemplatePresenterFragment>
        implements MVP.RequiredFragmentMethods, View.OnClickListener {

    /**
     * Attributes
     */
    private final String TAG = getClass().getSimpleName();
    private Logger logger = new Logger(TAG);
    /** private ContainerActivity parentActivity; */


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
        /** LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout., container, false); */

        // Initialize parent activity
        /** parentActivity = (ContainerActivity)getActivity(); */

        // Initialize retained fragment state
        /** isRetainedFragment = false; */

        // Instantiate the presenter
        super.onCreate(TemplatePresenterFragment.class, this);

        // Initialize the view components defined in the fragment's layout
        initializeViews();

        /** return linearLayout; */
        return null;
    }

    /**
     * Initialize the Views and GUI widgets.
     */
    private void initializeViews() {

    }

    /**
     * Hook OnClickListener
     */
    @Override
    public void onClick(View view) {
        getPresenter().handleClick(view.getId());
    }
}
