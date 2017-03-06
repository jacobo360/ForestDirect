package iomango.com.forestdirect.mvp.common.utilities;

import android.os.Build;

/**
 * Created by Clelia López on 9/22/16
 */

public class AndroidTools {

    public static int getAndroidAPI() {
        int version;
        switch (Build.VERSION.RELEASE.substring(0,3)) {
            case "4.1": version = 16; break; // Jelly Bean
            case "4.2": version = 17; break; // Jelly Bean
            case "4.3": version = 18; break; // Jelly Bean
            case "4.4": version = 19; break; // KitKat
            case "5.0": version = 21; break; // Lollipop
            case "5.1": version = 22; break; // Lollipop
            case "6.0": version = 23; break; // Marshmallow
            case "7.0": version = 23; break; // Nougat
            default: version = -1; break;
        }
        return version;
    }
}
