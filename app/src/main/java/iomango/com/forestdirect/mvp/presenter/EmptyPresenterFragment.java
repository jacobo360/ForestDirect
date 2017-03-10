package iomango.com.forestdirect.mvp.presenter;

import iomango.com.forestdirect.mvp.MVP;
import iomango.com.forestdirect.mvp.common.generic.GenericPresenter;
import iomango.com.forestdirect.mvp.model.GlobalModel;

/**
 * Created by Clelia López on 12/9/2015
 */
public class EmptyPresenterFragment
        extends GenericPresenter<MVP.RequiredPresenterMethods, MVP.ProvidedModelMethods, GlobalModel>
        implements MVP.ProvidedPresenterMethodsFragment, MVP.RequiredPresenterMethods {

    @Override
    public void onCreate(MVP.RequiredFragmentMethods view) {

    }

    @SuppressWarnings("unchecked")
    @Override
    public MVP.ProvidedModelMethods getModel() {
        return null;
    }
}
