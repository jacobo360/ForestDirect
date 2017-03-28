package iomango.com.forestdirect.mvp.view.custom;

import android.app.DatePickerDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;

import iomango.com.forestdirect.R;
import iomango.com.forestdirect.mvp.common.utilities.Date;

/**
 * Created by Clelia López on 3/12/17
 */

public class DatePickerEditText
        extends LinearLayout
        implements DatePickerDialog.OnDateSetListener, DialogInterface.OnCancelListener, View.OnClickListener {

    /**
     * Attributes
     */
    private CustomEditText dateEditText;
    private DatePickerDialog datePickerDialog;
    private Context context;


    public DatePickerEditText(Context context) {
        super(context);
        this.context = context;

        if (!isInEditMode())
            initializeView();
    }

    public DatePickerEditText(Context context, AttributeSet attributes) {
        super(context, attributes);
        this.context = context;

        if (!isInEditMode()) {
            initializeView();
            parseAttributes(attributes);
        }
    }

    private void parseAttributes(AttributeSet attributes) {
        TypedArray typedArray = context.obtainStyledAttributes(attributes, R.styleable.DatePickerEditText);
        String hint = typedArray.getString(R.styleable.DatePickerEditText_hint);
        if (hint != null)
            dateEditText.setHint(hint);

        typedArray.recycle();
    }

    public void initializeView() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View container = inflater.inflate(R.layout.date_picker_edit_text, this, true);
        dateEditText = (CustomEditText) container.findViewById(R.id.date_edit_text_view);
        dateEditText.setFocusableInTouchMode(true);

        dateEditText.setOnClickListener(this);

        dateEditText.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus)
                    datePickerDialog.show();
            }
        });

        Date date = new Date();
        datePickerDialog = new DatePickerDialog(context, this, date.getYear(), date.getMonth(), date.getDay());
        datePickerDialog.setOnCancelListener(this);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Date date = new Date(year, month, dayOfMonth);
        if (date.isValid()) {
            dateEditText.setText(date.toString());
            dateEditText.setError(null);
        } else {
            dateEditText.setError(context.getString(R.string.date_error));
            dateEditText.setText("");
        }
    }

    @Override
    public void onClick(View view) {
        datePickerDialog.show();
    }

    public String getValue() {
        return dateEditText.getText().toString().trim();
    }


    @Override
    public void onCancel(DialogInterface dialog) {
        dateEditText.clearFocus();
    }
}
