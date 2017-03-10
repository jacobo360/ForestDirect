package iomango.com.forestdirect.mvp.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import iomango.com.forestdirect.R;
import iomango.com.forestdirect.mvp.MVP;
import iomango.com.forestdirect.mvp.common.generic.GenericFragment;
import iomango.com.forestdirect.mvp.presenter.EmptyPresenterFragment;

/**
 * Created by Clelia López on 08/12/2015
 */
public class EmptyFragment
        extends GenericFragment<MVP.RequiredFragmentMethods, Void, EmptyPresenterFragment>
        implements MVP.RequiredFragmentMethods {

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

        // Initialize retained fragment state
        isRetainedFragment = false;

        return inflater.inflate(R.layout.fragment_empty, container, false);
    }
}
