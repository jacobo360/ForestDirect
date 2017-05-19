package iomango.com.forestdirect.mvp.view.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import iomango.com.forestdirect.R;

/**
 * Created by Clelia López on 5/18/17
 */
public class SplashDialog
        extends DialogFragment {

    @SuppressLint("InflateParams")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Dialog initialization
        View dialog = inflater.inflate(R.layout.dialog_splash, null);
        Context context = dialog.getContext();

        Window window = getDialog().getWindow();
        if (window != null) {

            // Setting basic properties
            setCancelable(false);
            setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Translucent_NoTitleBar);

            // Setting background color
            window.setBackgroundDrawable(new ColorDrawable(
                    ContextCompat.getColor(context, R.color.black)));
        }

        return dialog;
    }
}
