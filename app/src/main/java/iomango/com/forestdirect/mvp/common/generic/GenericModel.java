package iomango.com.forestdirect.mvp.common.generic;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import iomango.com.forestdirect.mvp.MVP;
import iomango.com.forestdirect.mvp.common.managers.SharedPreferenceManager;

/**
 * Created by Clelia López on 9/25/16
 */
public abstract class GenericModel
        implements MVP.ProvidedModelMethods {

    /**
     * Attributes
     */
    protected Context context;
    protected WeakReference<MVP.RequiredPresenterMethods> presenter;


    /**
     * Default constructor required by framework
     */
    protected GenericModel() {
        // no-op
    }

    /**
     * Parameter constructor
     *
     * @param context application/activity context
     **/
    public GenericModel(Context context) {
        this.context = context;
    }

    /**
     * Hook method called when a new instance of a Model is created.
     *
     * @param presenter a reference to the Presenter layer
     */
    @Override
    public void onCreate(MVP.RequiredPresenterMethods presenter) {
        this.presenter = new WeakReference<>(presenter);
    }

    /**
     * Hook method called to shutdown the Model layer
     *
     * @param isChangingConfiguration True if a runtime configuration triggered the onDestroy call
     */
    @Override
    public void onDestroy(boolean isChangingConfiguration) {
        // no-op
    }

    /**
     * Saves any value on a general shared preference file
     *
     * @param key chosen key name
     * @param value chosen value type
     */
    @Override
    public <T> void saveOnSharedPreference(String key, T value) {
        SharedPreferenceManager.putValue(context, key, value);
    }

    /**
     * Retrieves value from the general shared preference file
     *
     * @param key chosen key name
     * @param defaultValue expected value if no associated value exist
     */
    @Override
    public <T> T getFromSharedPreference(Class<T> classType, String key, T defaultValue) {
        return SharedPreferenceManager.getValue(context, classType, key, defaultValue);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public <T> List<T> loadJsonDatabase(TypeToken<ArrayList<T>> typeToken, String path) {
        String json = null;
        try {
            InputStream inputStream = context.getAssets().open(path + ".json");
            int size = inputStream.available();
            byte buffer[] = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return new Gson().fromJson(json, typeToken.getType());
    }

    @SuppressWarnings("TryWithIdenticalCatches")
    public <T> ArrayList<String> createStringList(List<T> list, String fieldName) {
        ArrayList<String> result = new ArrayList<>();
        Field field;
        for (T object : list) {
            try {
                field = object.getClass().getDeclaredField(fieldName);
                field.setAccessible(true);
                result.add((String)field.get(object));
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        Collections.sort(result);
        return result;
    }
}
