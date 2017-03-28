package iomango.com.forestdirect.mvp.view.custom;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import iomango.com.forestdirect.R;
import iomango.com.forestdirect.mvp.common.global.Constants;

/**
 * Created by Clelia López on 3/12/17
 */

public class TimePickerEditText
        extends LinearLayout
        implements TimePickerDialog.OnTimeSetListener, DialogInterface.OnCancelListener, View.OnClickListener {

    /**
     * Attributes
     */
    private CustomEditText timeEditText;
    private TimePickerDialog timePickerDialog;
    private Context context;


    public TimePickerEditText(Context context) {
        super(context);
        this.context = context;

        if (!isInEditMode())
            initializeView();
    }

    public TimePickerEditText(Context context, AttributeSet attributes) {
        super(context, attributes);
        this.context = context;

        if (!isInEditMode()) {
            initializeView();
            parseAttributes(attributes);
        }
    }

    private void parseAttributes(AttributeSet attributes) {
        TypedArray typedArray = context.obtainStyledAttributes(attributes, R.styleable.TimePickerEditText);
        String hint = typedArray.getString(R.styleable.TimePickerEditText_hint);
        if (hint != null)
            timeEditText.setHint(hint);

        typedArray.recycle();
    }

    public void initializeView() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View container = inflater.inflate(R.layout.time_picker_edit_text, this, true);
        timeEditText = (CustomEditText) container.findViewById(R.id.time_edit_text_view);
        timeEditText.setFocusableInTouchMode(true);

        timeEditText.setOnClickListener(this);

        timeEditText.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus)
                    timePickerDialog.show(((Activity)getContext()).getFragmentManager(), "TimePickerDialog");
            }
        });

        timePickerDialog = TimePickerDialog.newInstance(this, 0, 0, false);
        timePickerDialog.setSelectableTimes(Constants.Timepoints);
        timePickerDialog.setOnCancelListener(this);
    }

    @Override
    public void onClick(View view) {
        timePickerDialog.show(((Activity)getContext()).getFragmentManager(), "TimePickerDialog");
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        if (minute == 0)
            timeEditText.setText(hourOfDay + ":" + minute + "0");
        else
            timeEditText.setText(hourOfDay + ":" + minute);
    }

    public String getValue() {
        String hour = timeEditText.getValue();
        hour = hour.replace(":", "");
        if (hour.length() == 3)
            hour = "0" + hour;
        return hour;
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        timeEditText.clearFocus();
    }
}
