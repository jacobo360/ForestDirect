package iomango.com.forestdirect.mvp.view.custom;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import iomango.com.forestdirect.R;
import iomango.com.forestdirect.mvp.common.interfaces.Listener.OnDateSetListener;
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
    private Date date;
    private OnDateSetListener listener;
    private String hint;
    private int id;


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
            parseAttributes(attributes);
            initializeView();
        }
    }

    private void parseAttributes(AttributeSet attributes) {
        TypedArray typedArray = context.obtainStyledAttributes(attributes, R.styleable.DatePickerEditText);
        hint = typedArray.getString(R.styleable.DatePickerEditText_hint);
        typedArray.recycle();
    }

    public void initializeView() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View container = inflater.inflate(R.layout.date_picker_edit_text, this, true);
        dateEditText = (CustomEditText) container.findViewById(R.id.date_edit_text_view);
        ImageButton clearImageButton = (ImageButton) container.findViewById(R.id.clear_image_button);
        clearImageButton.setOnClickListener(this);
        dateEditText.setFocusableInTouchMode(true);
        dateEditText.setOnClickListener(this);

        if (hint != null)
            dateEditText.setHint(hint);

        dateEditText.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus)
                    datePickerDialog.show();
            }
        });

        date = new Date();
        datePickerDialog = new DatePickerDialog(context, this, date.getYear(), date.getMonth(), date.getDay());
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.setOnCancelListener(this);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        date = new Date(year, month, dayOfMonth);
        if (date.isValid()) {
            dateEditText.setText(date.toString());
            dateEditText.setError(null);
        } else {
            dateEditText.setText("");
            dateEditText.setError(context.getString(R.string.date_error));
        }
        if (listener != null)
            listener.updateDate(date, id);
    }

    public String getValue() {
        return dateEditText.getText().toString().trim();
    }

    public Date getDate() {
        return date;
    }

    public void setMinimumDate(Date date) {
        datePickerDialog = new DatePickerDialog(context, this, date.getYear(), date.getMonth(), date.getDay());
        datePickerDialog.getDatePicker().setMinDate(date.getCalendar().getTimeInMillis());
        this.date = date;
    }

    public void setOnDateSetListener(OnDateSetListener listener, int id) {
        this.listener = listener;
        this.id = id;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.clear_image_button:
                dateEditText.setText("");
                dateEditText.clearFocus();
                break;
            default:
                datePickerDialog.show();
                break;
        }
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        dateEditText.clearFocus();
    }

    public void setText(String value) {
        dateEditText.setText(value);
    }
}
