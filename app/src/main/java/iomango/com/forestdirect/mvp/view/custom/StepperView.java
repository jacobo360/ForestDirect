package iomango.com.forestdirect.mvp.view.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import iomango.com.forestdirect.R;

/**
 * Created by Clelia López on 3/12/17
 */

public class StepperView
        extends LinearLayout
        implements View.OnClickListener {

    /**
     * Attributes
     */
    private Context context;
    private CustomTextView amountTextView;
    private int amount = 0;
    private int minimum = -1;


    public StepperView(Context context) {
        super(context);
        this.context = context;

        initializeView();
    }

    public StepperView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.context = context;

        initializeView();
    }

    public void initializeView() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View container = inflater.inflate(R.layout.stepper_view, this, true);
        amountTextView = (CustomTextView) container.findViewById(R.id.amount_text_view);
        ImageButton removeImageView = (ImageButton) container.findViewById(R.id.remove_button);
        ImageButton addImageView = (ImageButton) container.findViewById(R.id.add_button);

        // Setting listeners
        removeImageView.setOnClickListener(this);
        addImageView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.remove_button:
                if (amount > 0 && Integer.parseInt(amountTextView.getText().toString()) > minimum) {
                    amount--;
                    amountTextView.setText(getContext().getString(R.string.blank, amount));
                }
                break;
            case R.id.add_button:
                amount++;
                amountTextView.setText(getContext().getString(R.string.blank, amount));
                break;
        }
    }

    public void setAmount (int amount) {
        this.amount = amount;
        amountTextView.setText(getContext().getString(R.string.blank, amount));
    }

    public int getAmount() {
        return amount;
    }

    public void setMinimum(int minimum) {
        this.minimum = minimum;
    }
}
