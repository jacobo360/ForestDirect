package iomango.com.forestdirect.mvp.common.global;

import android.Manifest;
import android.support.v4.util.Pair;

import com.wdullaer.materialdatetimepicker.time.Timepoint;

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

    /**
     * Requests codes
     */
    public static int SEARCH_ACTIVITY = 50;
    public static int CLEAR_ACTIVITY = 60;

    public static Timepoint Timepoints[] = new Timepoint[] {
        new Timepoint(0, 0), new Timepoint(0, 30),
        new Timepoint(1, 0), new Timepoint(1, 30),
        new Timepoint(2, 0), new Timepoint(2, 30),
        new Timepoint(3, 0), new Timepoint(3, 30),
        new Timepoint(4, 0), new Timepoint(4, 30),
        new Timepoint(5, 0), new Timepoint(5, 30),
        new Timepoint(6, 0), new Timepoint(6, 30),
        new Timepoint(7, 0), new Timepoint(7, 30),
        new Timepoint(8, 0), new Timepoint(8, 30),
        new Timepoint(9, 0), new Timepoint(9, 30),
        new Timepoint(10, 0), new Timepoint(10, 30),
        new Timepoint(11, 0), new Timepoint(11, 30),
        new Timepoint(12, 0), new Timepoint(12, 30),
        new Timepoint(13, 0), new Timepoint(13, 30),
        new Timepoint(14, 0), new Timepoint(14, 30),
        new Timepoint(15, 0), new Timepoint(15, 30),
        new Timepoint(16, 0), new Timepoint(16, 30),
        new Timepoint(17, 0), new Timepoint(17, 30),
        new Timepoint(18, 0), new Timepoint(18, 30),
        new Timepoint(19, 0), new Timepoint(19, 30),
        new Timepoint(20, 0), new Timepoint(20, 30),
        new Timepoint(21, 0), new Timepoint(21, 30),
        new Timepoint(22, 0), new Timepoint(22, 30),
        new Timepoint(23, 0), new Timepoint(23, 30)
    };
}
