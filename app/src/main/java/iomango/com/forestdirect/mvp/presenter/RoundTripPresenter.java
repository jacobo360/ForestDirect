package iomango.com.forestdirect.mvp.presenter;

import android.content.Context;
import android.content.Intent;

import java.lang.ref.WeakReference;

import iomango.com.forestdirect.mvp.MVP;
import iomango.com.forestdirect.mvp.common.generic.GenericPresenter;
import iomango.com.forestdirect.mvp.common.interfaces.Listener.OnNetworkResponseListener;
import iomango.com.forestdirect.mvp.model.GlobalModel;
import iomango.com.forestdirect.mvp.model.netwotk.NetworkRequest;
import iomango.com.forestdirect.mvp.model.request.QueryFlight;
import iomango.com.forestdirect.mvp.view.activities.WebViewActivity;


/**
 * Created by Clelia López on 12/9/2015
 */
public class RoundTripPresenter
        extends GenericPresenter<MVP.RequiredPresenterMethods, MVP.ProvidedModelMethods, GlobalModel>
        implements MVP.ProvidedPresenterMethodsFragment, MVP.RequiredPresenterMethods, OnNetworkResponseListener {

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

    @Override
    public <T> void executeNetworkRequest(T model) {
        NetworkRequest request = new QueryFlight<>(model, this);
        request.performNetworkRequest();
    }


    @Override
    public <T> void processResponse(T response) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra("source", (String)response);
        context.startActivity(intent);
    }

    @SuppressWarnings("unchecked")
    @Override
    public MVP.ProvidedModelMethods getModel() {
        return new GlobalModel(context);
    }
}
