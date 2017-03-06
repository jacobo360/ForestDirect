package iomango.com.forestdirect.mvp.common.global;

import android.Manifest;
import android.support.v4.util.Pair;

/**
 * Created by Clelia lópez on 9/11/16
 */
public class Constants {

    /**
     * Permission constants
     */
    public static final int READ_EXTERNAL_STORAGE = 10;
    public static final int WRITE_EXTERNAL_STORAGE = 20;
    public static final int CAMERA = 30;
    public static final int ALL = 40;

    public static final Pair<String,Integer> READ_PERMISSION =
            new Pair<>(Manifest.permission.READ_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE);


    public static final Pair<String,Integer> WRITE_PERMISSION =
            new Pair<>(Manifest.permission.WRITE_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE);

    public static final Pair<String,Integer> CAMERA_PERMISSION =
            new Pair<>(Manifest.permission.CAMERA, CAMERA);
}
