package iomango.com.forestdirect.mvp.view.dialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import iomango.com.forestdirect.R;
import iomango.com.forestdirect.mvp.view.custom.StepperView;

/**
 * Created by Clelia López on 3/13/17
 */

public class AdvanceOptionsDialog
        extends DialogFragment {

    // TODO: add model

    /**
     * Attributes
     */
    private StepperView adultsStepperView;
    private StepperView seniorsStepperView;
    private StepperView childrenStepperView;
    private StepperView infantsStepperView;


    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Dialog initialization
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View container = inflater.inflate(R.layout.advanced_options_dialog, null);
        adultsStepperView = (StepperView) container.findViewById(R.id.adults_stepper);
        seniorsStepperView = (StepperView) container.findViewById(R.id.seniors_step);
        childrenStepperView = (StepperView) container.findViewById(R.id.children_stepper);
        infantsStepperView = (StepperView) container.findViewById(R.id.infants_stepper);

        builder.setView(inflater.inflate(R.layout.advanced_options_dialog, null))

            .setPositiveButton(R.string.negative_option_label, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    System.out.print("Adults value: " + adultsStepperView.getAmount());
                    System.out.print("Adults value: " + seniorsStepperView.getAmount());
                    System.out.print("Adults value: " + childrenStepperView.getAmount());
                    System.out.print("Adults value: " + infantsStepperView.getAmount());
                }
            })

            .setNegativeButton(R.string.negative_option_label, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });

        return builder.create();
    }
}
