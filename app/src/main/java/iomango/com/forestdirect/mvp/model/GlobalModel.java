package iomango.com.forestdirect.mvp.model;

import android.content.Context;

import iomango.com.forestdirect.mvp.MVP;
import iomango.com.forestdirect.mvp.common.generic.GenericModel;


/**
  * Created by Clelia López on 04/12/2015.
 */
public class GlobalModel
        extends GenericModel
        implements MVP.ProvidedModelMethods {


    public GlobalModel() {
        // no-op
    }

    public GlobalModel(Context context) {
        super(context);
    }
}
