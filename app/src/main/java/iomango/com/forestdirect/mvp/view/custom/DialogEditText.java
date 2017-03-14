package iomango.com.forestdirect.mvp.view.custom;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import iomango.com.forestdirect.R;
import iomango.com.forestdirect.mvp.view.dialog.AdvanceOptionsDialog;

/**
 * Created by Clelia López on 3/13/17
 */
public class DialogEditText
        extends LinearLayout
        implements View.OnClickListener {

    /**
     * Attributes
     */
    private CustomEditText dialogEditText;
    private AdvanceOptionsDialog advanceOptionsDialog;
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

        if (!isInEditMode())
            initializeView();
    }

    public void initializeView() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View container = inflater.inflate(R.layout.dialog_edit_text, this, true);
        dialogEditText = (CustomEditText) container.findViewById(R.id.dialog_edit_text_view);
        dialogEditText.setFocusableInTouchMode(true);
        dialogEditText.setOnClickListener(this);

        advanceOptionsDialog = new AdvanceOptionsDialog();

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
}