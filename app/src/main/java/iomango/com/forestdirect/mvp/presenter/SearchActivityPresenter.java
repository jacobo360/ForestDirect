package iomango.com.forestdirect.mvp.presenter;

import android.content.Context;

import java.lang.ref.WeakReference;

import iomango.com.forestdirect.mvp.MVP;
import iomango.com.forestdirect.mvp.common.generic.GenericPresenter;
import iomango.com.forestdirect.mvp.common.global.Enums.DialogType;
import iomango.com.forestdirect.mvp.common.interfaces.Listener.OnNetworkResponseListener;
import iomango.com.forestdirect.mvp.model.GlobalModel;
import iomango.com.forestdirect.mvp.model.netwotk.NetworkRequest;
import iomango.com.forestdirect.mvp.model.request.QueryLocation;

/**
 * Created by Clelia López on 12/9/2015
 */
public class SearchActivityPresenter
        extends GenericPresenter<MVP.RequiredPresenterMethods, MVP.ProvidedModelMethods, GlobalModel>
        implements MVP.ProvidedPresenterMethodsActivity, MVP.RequiredPresenterMethods, OnNetworkResponseListener {

    /**
     * Attributes
     */
    private WeakReference<MVP.RequiredActivityMethods> view;
    private Context context;


    /**
     * Hook method called when a new instance of this presenter is created.
     *
     * @param view A reference to the View layer.
     */
    @Override
    public void onCreate(MVP.RequiredActivityMethods view) {
        // Initialized the defined WeakReference
        this.view = new WeakReference<>(view);

        // Invoke the special onCreate() method in GenericPresenter to instantiate the model
        super.onCreate(GlobalModel.class, this);

        context = this.view.get().getActivityContext();
    }

    /**
     * Called when the user clicks a button to perform some action
     *
     * @param viewId Indicates the id of the button pressed by the user
     */
    @Override
    public void handleClick(int viewId) {
        // no-op
    }

    @Override
    public <M> void executeNetworkRequest(M model) {
        NetworkRequest request = new QueryLocation<>(model, this);
        request.performNetworkRequest();
        view.get().displayDialog(DialogType.TIME_UNDETERMINED);
    }

    @SuppressWarnings("unchecked")
    @Override
    public MVP.ProvidedModelMethods getModel() {
        return new GlobalModel(context);
    }

    @Override
    public <T> void processResponse(T response) {
        view.get().dismissDialog(DialogType.TIME_UNDETERMINED);
        view.get().updateView(response);
    }
}
