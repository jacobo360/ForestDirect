package iomango.com.forestdirect.mvp.view.dialog;

import android.annotation.SuppressLint;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import iomango.com.forestdirect.R;
import iomango.com.forestdirect.mvp.common.global.Enums.ScreenUnit;
import iomango.com.forestdirect.mvp.common.interfaces.Listener.OnAdvanceOptionsListener;
import iomango.com.forestdirect.mvp.common.interfaces.Listener.OnAmountChangeListener;
import iomango.com.forestdirect.mvp.common.utilities.DrawablesTools;
import iomango.com.forestdirect.mvp.common.utilities.ScreenTools;
import iomango.com.forestdirect.mvp.model.AdvancedOptionsModel;
import iomango.com.forestdirect.mvp.view.custom.StepperView;
import iomango.com.forestdirect.mvp.view.custom.spinner.Spinner;

/**
 * Created by Clelia López on 3/13/17
 */
public class AdvanceOptionsDialog
        extends DialogFragment
        implements DialogInterface.OnShowListener, OnAmountChangeListener {

    /**
     * Attributes
     */
    private Spinner cabinSpinner;
    private StepperView adultsStepperView;
    private StepperView seniorsStepperView;
    private StepperView childrenStepperView;
    private StepperView infantsStepperView;
    private OnAdvanceOptionsListener listener;


    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Dialog initialization
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View container = inflater.inflate(R.layout.advanced_options_dialog, null);
        cabinSpinner = (Spinner) container.findViewById(R.id.cabin_spinner);
        adultsStepperView = (StepperView) container.findViewById(R.id.adults_stepper);
        seniorsStepperView = (StepperView) container.findViewById(R.id.seniors_step);
        childrenStepperView = (StepperView) container.findViewById(R.id.children_stepper);
        infantsStepperView = (StepperView) container.findViewById(R.id.infants_stepper);

        adultsStepperView.setAmount(1);
        adultsStepperView.setMinimum(1);

        // Setting listeners
        adultsStepperView.setOnAmountChangeListener(this);
        seniorsStepperView.setOnAmountChangeListener(this);

        // Tinting drawables
        DrawablesTools.tintDrawable(getContext(), R.drawable.ic_add, R.color.white);
        DrawablesTools.tintDrawable(getContext(), R.drawable.ic_remove, R.color.white);

        builder
            .setView(container)
            .setTitle(getContext().getString(R.string.dialog_title_label))
            .setPositiveButton(R.string.positive_option_label, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    AdvancedOptionsModel model = new AdvancedOptionsModel();
                    model.setAdult(adultsStepperView.getAmount());
                    model.setSenior(seniorsStepperView.getAmount());
                    model.setChildren(childrenStepperView.getAmount());
                    model.setInfant(infantsStepperView.getAmount());
                    model.setCabin(cabinSpinner.getSpinner().getSelectedItem());

                    if (listener != null)
                        listener.updateTextView(model);
                }
            })
            .setNegativeButton(R.string.negative_option_label, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });

        AlertDialog dialog = builder.create();
        dialog.setOnShowListener(this);


        return dialog;
    }

    @Override
    public void onShow(DialogInterface dialog) {
        Window view = ((AlertDialog)dialog).getWindow();

        if (view != null) {
            // Setting size
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.copyFrom(view.getAttributes());
            int margin = ScreenTools.convertValueTo(getContext(), 15, ScreenUnit.PX);
            int width = (int)ScreenTools.getScreenWidth(getContext(), ScreenUnit.PX);
            width = width - 2*margin;
            layoutParams.width = width;
            view.setAttributes(layoutParams);

            // Setting resource background
            view.setBackgroundDrawableResource(R.drawable.bg_dialog);

            // Setting button font colors
            int colorPrimary = ContextCompat.getColor(getContext(), R.color.colorPrimary);

            Button negativeButton = ((AlertDialog)dialog).getButton(DialogInterface.BUTTON_NEGATIVE);
            negativeButton.setTextColor(colorPrimary);
            negativeButton.invalidate();

            Button positiveButton = ((AlertDialog)dialog).getButton(DialogInterface.BUTTON_POSITIVE);
            positiveButton.setTextColor(colorPrimary);
            positiveButton.invalidate();
        }
    }

    public void setOnAdvanceOptionsListener(OnAdvanceOptionsListener listener) {
        this.listener = listener;
    }

    @Override
    public void amountIncrease(int viewId, int amount) {
        switch (viewId) {
            case R.id.adults_stepper:
                if (amount > 0) {
                    adultsStepperView.setMinimum(1);
                    seniorsStepperView.setMinimum(0);
                }
                break;
            case R.id.seniors_step:
                if (amount > 0) {
                    adultsStepperView.setMinimum(0);
                    seniorsStepperView.setMinimum(1);
                }
                break;
        }
    }

    @Override
    public void amountDecrease(int viewId, int amount) {
        // no-op
    }
}
