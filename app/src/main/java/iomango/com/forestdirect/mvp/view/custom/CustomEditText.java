package iomango.com.forestdirect.mvp.view.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.EditText;

import iomango.com.forestdirect.R;
import iomango.com.forestdirect.mvp.common.utilities.DrawablesTools;
import iomango.com.forestdirect.mvp.common.utilities.FontTools;


/**
 * Custom TextView with attributes font and typeface
 *
 * Created by Clelia López on 8/27/16
 */
public class CustomEditText
        extends EditText {

    /**
     * Attributes
     */
    private boolean isValid;


    public CustomEditText(Context context) {
        super(context);
        if (!isInEditMode())
            setDefaultTypeface(context);
    }

    public CustomEditText(Context context, AttributeSet attributes) {
        super(context, attributes);
        if (!isInEditMode())
            parseAttributes(context, attributes);
    }

    /**
     * Parse view attributes typeface
     *
     * @param context view context
     * @param attributes attribute set
     */
    public void parseAttributes(Context context, AttributeSet attributes) {
        TypedArray typedArray = context.obtainStyledAttributes(attributes, R.styleable.CustomTextView);

        String textViewTypeface = typedArray.getString(R.styleable.CustomEditText_typeface);
        if (textViewTypeface != null) {
            String path = FontTools.getFontTypeface(context, Integer.parseInt(textViewTypeface));
            setTypeFace(context, path);
        }

        typedArray.recycle();
    }

    /**
     * Set typeface
     *
     *  @param path relative path to font typeface
     */
    public void setTypeFace(Context context, String path) {
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), path);
        if (typeface != null)
            setTypeface(typeface);
    }

    /**
     * Set default typeface
     *
     * @param context view context
     */
    public void setDefaultTypeface(Context context) {
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), FontTools.getDefaultFontType(context));
        if (typeface != null)
            setTypeface(typeface);
    }

    /**
     * Method used to customize the default error icon
     */
    public void setError(CharSequence error) {
        Drawable icon;
        DrawablesTools.tintDrawable(getContext(), R.drawable.ic_circle_check, R.color.white);
        if (error == null) {
            setCompoundDrawables(null, null, null, null);
            icon = ContextCompat.getDrawable(getContext(), R.drawable.ic_circle_check);
            icon.setBounds(0, 0, icon.getIntrinsicWidth() - 10, icon.getIntrinsicHeight() - 10);
            setCompoundDrawables(null, null, icon, null);
            setError(null, icon);
        } else {
            icon = ContextCompat.getDrawable(getContext(), R.drawable.ic_circle_cancel);
            icon.setBounds(0, 0, icon.getIntrinsicWidth() - 10, icon.getIntrinsicHeight() - 10);
            setCompoundDrawables(null, null, icon, null);
            setError(error, icon);
        }
    }

    public String getValue() {
        return getText().toString().trim();
    }
}
