package iomango.com.forestdirect.mvp.common.utilities;

import android.content.Context;
import android.util.DisplayMetrics;

import iomango.com.forestdirect.mvp.common.global.Enums.ScreenUnit;

/**
 * Created by Clelia López on 3/8/16
 */
public class ScreenTools {

    /**
     * @param value in dp or px
     * @return - equivalent number in pixels or dp
     */
    public static int convertValueTo(Context context, int value, ScreenUnit unit) {
        int result = 0;
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        switch (unit) {
            // value is in pixels
            case DP:
                result = (int)((value / displayMetrics.density) + 0.5);
                break;
            // value is in dp
            case PX:
                result = (int)((value * displayMetrics.density) + 0.5);
                break;
        }
        return result;
    }

    /**
     * @return - width resolution in pixels or dp
     */
    public static float getScreenWidth(Context context, ScreenUnit unit) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float result = 0;
        switch (unit) {
            case PX:
                result = displayMetrics.widthPixels;
                break;
            case DP:
                result = displayMetrics.widthPixels / displayMetrics.density;
                break;
        }
        return result;
    }

    /**
     * @return - height resolution in pixels or dp
     */
    public static float getScreenHeight(Context context, ScreenUnit unit) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float result = 0;
        switch (unit) {
            case PX:
                result = displayMetrics.heightPixels;
                break;
            case DP:
                result = displayMetrics.heightPixels / displayMetrics.density;
                break;
        }
        return result;
    }

    public static float getDensityIndex(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }
}
