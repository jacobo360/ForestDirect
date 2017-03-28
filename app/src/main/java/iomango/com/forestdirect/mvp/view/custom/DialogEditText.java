package iomango.com.forestdirect.mvp.view.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import iomango.com.forestdirect.R;
import iomango.com.forestdirect.mvp.common.interfaces.Listener.OnAdvanceOptionsListener;
import iomango.com.forestdirect.mvp.model.AdvancedOptionsModel;
import iomango.com.forestdirect.mvp.view.dialog.AdvanceOptionsDialog;

/**
 * Created by Clelia López on 3/13/17
 */
public class DialogEditText
        extends LinearLayout
        implements View.OnClickListener, OnAdvanceOptionsListener {

    /**
     * Attributes
     */
    private CustomEditText dialogEditText;
    private AdvanceOptionsDialog advanceOptionsDialog;
    private AdvancedOptionsModel data;
    private Context context;


    public DialogEditText(Context context) {
        super(context);
        this.context = context;

        if (!isInEditMode())
            initializeView();
    }

    public DialogEditText (Context context, AttributeSet attributes) {
        super(context, attributes);
        this.context = context;

        if (!isInEditMode()) {
            initializeView();
            parseAttributes(attributes);
        }
    }

    private void parseAttributes(AttributeSet attributes) {
        TypedArray typedArray = context.obtainStyledAttributes(attributes, R.styleable.DialogEditText);
        String hint = typedArray.getString(R.styleable.DialogEditText_hint);
        if (hint != null)
            dialogEditText.setHint(hint);

        typedArray.recycle();
    }

    private void initializeView() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View container = inflater.inflate(R.layout.dialog_edit_text, this, true);
        dialogEditText = (CustomEditText) container.findViewById(R.id.dialog_edit_text_view);
        dialogEditText.setFocusableInTouchMode(true);
        dialogEditText.setOnClickListener(this);

        advanceOptionsDialog = new AdvanceOptionsDialog();
        advanceOptionsDialog.setOnAdvanceOptionsListener(this);

        dialogEditText.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus)
                    advanceOptionsDialog.show(((AppCompatActivity)getContext())
                            .getSupportFragmentManager(), "AdvancedOptionsDialogFragment");
            }
        });
    }

    @Override
    public void onClick(View view) {
        advanceOptionsDialog.show(((AppCompatActivity)getContext())
                .getSupportFragmentManager(), "AdvancedOptionsDialogFragment");
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void updateTextView(AdvancedOptionsModel model) {
        if (model != null) {
            data = model;
            int total = model.getAdult() + model.getSenior() + model.getChildren() + model.getInfant();
            if (total > 1)
                dialogEditText.setText(total + " Travelers, " + model.getCabin());
            else if (model.getAdult() == 1)
                dialogEditText.setText("1 Adult, " + model.getCabin());
            else if (model.getSenior() == 1)
                dialogEditText.setText("1 Senior, " + model.getCabin());
        } else
            dialogEditText.clearFocus();
    }

    public AdvancedOptionsModel getData() {
        return data;
    }
}