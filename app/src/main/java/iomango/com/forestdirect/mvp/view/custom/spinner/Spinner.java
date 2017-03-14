package iomango.com.forestdirect.mvp.view.custom.spinner;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.ArrayList;

import iomango.com.forestdirect.R;
import iomango.com.forestdirect.mvp.common.global.Enums.CustomTypeface;
import iomango.com.forestdirect.mvp.common.utilities.FontTools;
import iomango.com.forestdirect.mvp.view.custom.TriangleShape;


/**
 * Created by Clelia López on 2/27/16
 */
public class Spinner
        extends LinearLayout {

    /**
     * Attributes
     */
    private MaterialSpinner materialSpinner;
    private TriangleShape triangleShape;
    private View lineView;

    // Spinner
    private int textSize;
    private int textColor;
    private int backgroundColor;
    private Drawable backgroundDrawable = null;
    private int visibleItems;
    private int itemsOverSpinner;
    private CustomTypeface typeface = null;
    private int arrowColor = -1;
    private int lineColor = -1;
    private Mode mode;

    // Item
    private int itemTextColor;
    private int itemTextSize;
    private int itemSelectedColor;
    private int itemSelectedDrawable;

    // List
    private int listBackgroundColor;
    private int listBackgroundDrawable;

    private Context context;
    private boolean initializedByParsing = false;

    private boolean isItemSelectedColorSet = false;
    private boolean isItemSelectedDrawableSet = false;
    private boolean isListBackgroundColorSet = false;
    private boolean isListBackgroundDrawableSet = false;


    public enum Mode {
        TEXT, BUTTON, SIMPLE
    }

    /**
     * Class Constructor
     */
    public Spinner(Context context) {
        super(context);
        this.context = context;

        mode = Mode.TEXT;

        initializeViews();
    }

    public Spinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        initializedByParsing = true;

        parseAttributes(attrs);

        initializeViews();
    }

    private void parseAttributes(AttributeSet attributes) {
        TypedArray typedArray = context.obtainStyledAttributes(attributes, R.styleable.Spinner);

        // Spinner
        String spinnerMode = typedArray.getString(R.styleable.Spinner_mode);

        textSize = typedArray.getInteger(R.styleable.Spinner_textSize, 15);
        textColor = typedArray.getColor(R.styleable.Spinner_textColor, ContextCompat.getColor(context, R.color.grey_dark));
        backgroundColor = typedArray.getColor(R.styleable.Spinner_backgroundColor, ContextCompat.getColor(context, R.color.white));
        backgroundDrawable = typedArray.getDrawable(R.styleable.Spinner_backgroundDrawable);
        visibleItems = typedArray.getInteger(R.styleable.Spinner_visibleItems, 4);
        itemsOverSpinner = typedArray.getInteger(R.styleable.Spinner_itemsOverSpinner, 1);

        // Item
        itemTextSize = typedArray.getInteger(R.styleable.Spinner_itemTextSize, 15);
        itemTextColor = typedArray.getResourceId(R.styleable.Spinner_itemTextColor, R.color.grey_dark);
        itemSelectedColor = typedArray.getResourceId(R.styleable.Spinner_itemSelectedColor, R.color.colorPrimary);
        itemSelectedDrawable = typedArray.getResourceId(R.styleable.Spinner_itemSelectedDrawable, R.drawable.bg_dialog);
        if(itemSelectedColor != R.color.colorPrimary)
            isItemSelectedColorSet = true;
        if(itemSelectedDrawable != R.drawable.bg_dialog)
            isItemSelectedDrawableSet = true;

        // List
        listBackgroundColor = typedArray.getResourceId(R.styleable.Spinner_listBackgroundColor, R.color.white);
        if(listBackgroundColor != R.color.white)
            isListBackgroundColorSet = true;
        listBackgroundDrawable = typedArray.getResourceId(R.styleable.Spinner_listBackgroundDrawable, R.drawable.bg_dialog);
        if(listBackgroundDrawable != R.drawable.bg_dialog)
            isListBackgroundDrawableSet = true;

        String spinnerArrowColor = typedArray.getString(R.styleable.Spinner_arrowColor);
        String spinnerLineColor = typedArray.getString(R.styleable.Spinner_lineColor);

        if(spinnerArrowColor != null)
            arrowColor = Color.parseColor(spinnerArrowColor);
        if(spinnerLineColor != null)
            lineColor =  Color.parseColor(spinnerLineColor);

        mode = Mode.BUTTON;
        if(spinnerMode != null) {
            switch (Integer.parseInt(spinnerMode)) {
                case 0: mode = Mode.TEXT; break;
                case 1: mode = Mode.BUTTON; break;
                case 2: mode = Mode.SIMPLE; break;
            }
        }


        String spinnerTypeface = typedArray.getString(R.styleable.Spinner_typeface);
        if (spinnerTypeface != null)
            typeface = CustomTypeface.getEnum(Integer.parseInt(spinnerTypeface));
        typeface = CustomTypeface.ROBOTO_REGULAR;

        typedArray.recycle();
    }

    /**
     * Initialize the Views and GUI widgets.
     */
    private void initializeViews() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout view = (LinearLayout) inflater.inflate(R.layout.material_spinner, this, true);
        FrameLayout containerFrameLayout = (FrameLayout) view.getChildAt(0);
        materialSpinner = (MaterialSpinner) containerFrameLayout.getChildAt(0);
        triangleShape = (TriangleShape) containerFrameLayout.getChildAt(1);
        lineView = containerFrameLayout.getChildAt(2);

        if(initializedByParsing) {

            // Spinner
            materialSpinner.setTextSize(textSize);
            materialSpinner.setTextColor(textColor);
            materialSpinner.setBackgroundColor(backgroundColor);
            materialSpinner.setVisibleItemNumber(visibleItems);
            materialSpinner.setVerticalItemsOffset(itemsOverSpinner);
            triangleShape.setColor(arrowColor);
            if (backgroundDrawable != null)
                materialSpinner.setBackground(backgroundDrawable);
            if (typeface != null)
                materialSpinner.setTypeFace(typeface);

            if(arrowColor != -1)
                setArrowColor(arrowColor);
            else {
                arrowColor = ContextCompat.getColor(context, R.color.grey_dark);
                setArrowColor(arrowColor);
            }

            if(lineColor != -1)
                setLineColor(lineColor);
            else {
                lineColor = ContextCompat.getColor(context, R.color.grey_dark);
                setLineColor(lineColor);
            }

            setMode(mode);

            // Item
            materialSpinner.setItemTextSize(itemTextSize);
            materialSpinner.setItemTextColor(itemTextColor);
            if(isItemSelectedColorSet)
                materialSpinner.setItemBackgroundColor(itemSelectedColor);
            if(isItemSelectedDrawableSet && !isItemSelectedColorSet)
                materialSpinner.setItemBackgroundDrawable(itemSelectedDrawable);

            // List
            if(isListBackgroundColorSet)
                materialSpinner.setPopUpBackgroundColor(listBackgroundColor);
            if(isListBackgroundDrawableSet && !isListBackgroundColorSet)
                materialSpinner.setPopupBackgroundDrawable(listBackgroundDrawable);
        }
    }

    /**
     * View Constructor
     */
    private View buildView(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        FrameLayout view = (FrameLayout) inflater.inflate(R.layout.material_spinner, null);
        replaceView(view.getChildAt(0), materialSpinner);
        replaceView(view.getChildAt(1), triangleShape);
        replaceView(view.getChildAt(2), lineView);

        return view;
    }

    private static ViewGroup getParent(View view) {
        return (ViewGroup)view.getParent();
    }

    private static void remove_View(View view) {
        ViewGroup parent = getParent(view);
        if(parent != null) {
            parent.removeView(view);
        }
    }

    private static void replaceView(View currentView, View newView) {
        ViewGroup parent = getParent(currentView);
        if(parent == null)
            return;
        final int index = parent.indexOfChild(currentView);
        remove_View(currentView);
        remove_View(newView);
        parent.addView(newView, index);
    }

    /**
     * Return material design spinner
     * @return - inflated spinner
     */
    public MaterialSpinner getSpinner() {
        return materialSpinner;
    }

    /**
     * Replace material spinner object for another one
     * @param spinner - material spinner object
     */
    private void setMaterialSpinner(MaterialSpinner spinner) {
        materialSpinner = spinner;
    }

    /**
     * Set line color
     * @param color - color code
     */
    public void setLineColor(int color) {
        lineView.setBackgroundColor(color);
    }

    /**
     * Set arrow color
     * @param color - color code
     */
    public void setArrowColor(int color) {
        triangleShape.setColor(color);
    }

    /**
     * Set spinner mode
     * @param mode - mode of field (text, button, simple)
     */
    public void setMode(Mode mode) {
        switch (mode) {
            case TEXT:
                lineView.setVisibility(VISIBLE);
                break;
            case BUTTON:
                lineView.setVisibility(GONE);
                break;
            case SIMPLE:
                lineView.setVisibility(GONE);
                triangleShape.setVisibility(GONE);
                break;
        }
    }

    public static class Builder
            extends MaterialSpinner.Builder {

        MaterialSpinner innerSpinner;
        Spinner spinner;
        Context context;

        ArrayList<String> items = null;
        int array = -1;

        int arrowColor = -1;
        int lineColor = -1;
        Mode spinnerMode = null;


        /**
         * Constructor
         *
         * @param context - User class context
         */
        public Builder(Context context) {
            super(context);
            this.context = context;
            spinner = new Spinner(context);
        }

        /**
         * Constructor
         *
         * @param context - User class context
         * @param array - array resource id on String.xml
         */
        public Builder(Context context, int array) {
            super(context);
            this.context = context;
            spinner = new Spinner(context);
            this.array = array;
        }

        /**
         * Constructor
         *
         * @param context - User class context
         * @param array - ArrayList collection
         */
        public Builder(Context context, ArrayList<String> array) {
            super(context);
            this.context = context;
            spinner = new Spinner(context);
            items = array;
        }

        public View build() {
            super.build();

            // Line
            if(lineColor != -1)
                spinner.setLineColor(lineColor);

            // Arrow
            if(arrowColor != -1)
                spinner.setArrowColor(arrowColor);

            // Mode
            if(spinnerMode != null)
                spinner.setMode(spinnerMode);

            innerSpinner = getMaterialSpinner(spinner.getSpinner());

            // Array
            if(array != -1)
                innerSpinner.setItems(array);
            if(items != null)
                innerSpinner.setItems(items);

            spinner.setMaterialSpinner(innerSpinner);

            return spinner.buildView(context);
        }

        // Line
        public Builder setLineColor(int color) {
            lineColor = color;
            return this;
        }


        // Arrow
        public Builder setArrowColor(int color) {
            arrowColor = color;
            return this;
        }

        // Mode
        public Builder setMode(Mode mode) {
            spinnerMode = mode;
            return this;
        }
    }
}
