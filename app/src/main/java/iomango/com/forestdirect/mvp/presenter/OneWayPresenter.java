package iomango.com.forestdirect.mvp.presenter;

import android.content.Context;

import java.lang.ref.WeakReference;

import iomango.com.forestdirect.mvp.MVP;
import iomango.com.forestdirect.mvp.common.generic.GenericPresenter;
import iomango.com.forestdirect.mvp.model.GlobalModel;


/**
 * Created by Clelia López on 12/9/2015
 */
public class OneWayPresenter
        extends GenericPresenter<MVP.RequiredPresenterMethods, MVP.ProvidedModelMethods, GlobalModel>
        implements MVP.ProvidedPresenterMethodsFragment, MVP.RequiredPresenterMethods {

    /**
     * Attributes
     */
    private WeakReference<MVP.RequiredFragmentMethods> view;
    private Context context;


    /**
     * Hook method called when a new instance of this presenter is created.
     *
     * @param view A reference to the View layer.
     */
    @Override
    public void onCreate(MVP.RequiredFragmentMethods view) {
        // Initialized the defined WeakReference
        this.view = new WeakReference<>(view);

        // Invoke the special onCreate() method in GenericPresenter to instantiate the model
        super.onCreate(GlobalModel.class, this);

        // Initialize context
        context = this.view.get().getActivityContext();
    }

    /**
     * Called when the user clicks a button to perform some action
     *
     * @param viewId Indicates the id of the button pressed by the user
     */
    @Override
    public void handleClick(int viewId) {

    }

    @SuppressWarnings("unchecked")
    @Override
    public MVP.ProvidedModelMethods getModel() {
        return new GlobalModel(context);
    }
}
