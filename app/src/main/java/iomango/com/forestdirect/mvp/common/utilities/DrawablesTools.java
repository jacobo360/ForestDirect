package iomango.com.forestdirect.mvp.common.utilities;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;


/**
 * Created by clelia_arch on 3/10/17
 */

public class DrawablesTools {

    public static void tintDrawable(Context context, int drawable, int color) {
        Drawable drawableIcon = ContextCompat.getDrawable(context, drawable);
        drawableIcon = DrawableCompat.wrap(drawableIcon);
        if (AndroidTools.getAndroidAPI() >= 21)
            drawableIcon.setColorFilter(ContextCompat.getColor(context, color), PorterDuff.Mode.SRC_IN);
        else {
            DrawableCompat.setTintMode(drawableIcon, PorterDuff.Mode.SRC_IN);
            DrawableCompat.setTint(drawableIcon, ContextCompat.getColor(context, color));
        }
    }
}
