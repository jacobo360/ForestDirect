package iomango.com.forestdirect.mvp.view.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.AutoCompleteTextView;

import iomango.com.forestdirect.R;
import iomango.com.forestdirect.mvp.common.utilities.FontTools;

/**
 * Created by Clelia López on 9/12/16
 */
public class CustomAutoComplete
        extends AutoCompleteTextView {

    /**
     * Attributes
     */
    private boolean isValid;


    public CustomAutoComplete(Context context) {
        super(context);
        if (!isInEditMode())
            setDefaultTypeface(context);
    }

    public CustomAutoComplete(Context context, AttributeSet attributes) {
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
        TypedArray typedArray = context.obtainStyledAttributes(attributes, R.styleable.CustomAutoComplete);

        String textViewTypeface = typedArray.getString(R.styleable.CustomAutoComplete_typeface);
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
}
