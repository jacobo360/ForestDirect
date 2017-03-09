package iomango.com.forestdirect.mvp.view.activities;

import android.os.Bundle;
import android.view.View;

import iomango.com.forestdirect.mvp.MVP;
import iomango.com.forestdirect.mvp.common.generic.GenericActivity;
import iomango.com.forestdirect.mvp.common.utilities.Logger;
import iomango.com.forestdirect.mvp.presenter.TemplatePresenterActivity;

/**
 * Created by Clelia López on 08/12/2015
 */
public class TemplateActivity
        extends GenericActivity<MVP.RequiredActivityMethods, MVP.ProvidedPresenterMethodsActivity, TemplatePresenterActivity>
        implements MVP.RequiredActivityMethods, View.OnClickListener {

    /**
     * Attributes
     */
    private final String TAG = getClass().getSimpleName();
    private final Logger logger = new Logger(TAG);


    /**
     * This hook method is called when the Activity is instantiated.
     *
     * @param savedInstanceState saved previous state, it may be null
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /** setContentView(R.layout.); */

        /** Retrieve building information
        Intent intent = getIntent();
        object = intent.getParcelableExtra("object"); */

        /** Instantiate the presenter
        super.onCreate(TemplatePresenterActivity.class, this); */

        // Initialize all view components defined in the activity's layout
        initializeViews();
    }

    /**
     * Initialize the Views and GUI widgets.
     */
    private void initializeViews() {
        /**
         toolbar = (Toolbar)findViewById(R.id.toolbar);
         setToolbar(toolbar, false);
         */
    }

    @Override
    public void onClick(View view) {
        forwardClick(view);
    }

    /**
     * Called when the user clicks a button to perform some action
     *
     * @param view Indicates the view component pressed by the user
     */
    public void forwardClick(View view) {
        getPresenter().handleClick(view.getId());
    }
}
