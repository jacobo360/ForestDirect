package iomango.com.forestdirect.mvp.view.dialog;

import android.annotation.SuppressLint;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableRow;

import java.util.ArrayList;
import java.util.List;

import iomango.com.forestdirect.R;
import iomango.com.forestdirect.mvp.common.global.Enums.ScreenUnit;
import iomango.com.forestdirect.mvp.common.interfaces.Listener.OnAdvanceOptionsListener;
import iomango.com.forestdirect.mvp.common.interfaces.Listener.OnAmountChangeListener;
import iomango.com.forestdirect.mvp.common.utilities.ScreenTools;
import iomango.com.forestdirect.mvp.model.AdvancedOptionsModel;
import iomango.com.forestdirect.mvp.view.adapter.ChildrenAdapter;
import iomango.com.forestdirect.mvp.view.custom.StepperView;
import iomango.com.forestdirect.mvp.view.custom.spinner.Spinner;
import iomango.com.forestdirect.mvp.view.decorator.DividerItemDecoration;

/**
 * Created by Clelia López on 3/13/17
 */
public class AdvanceOptionsDialog
        extends DialogFragment
        implements DialogInterface.OnShowListener, OnAmountChangeListener, AdapterView.OnItemClickListener {

    /**
     * Attributes
     */
    private Spinner cabinSpinner;
    private Spinner childrenSpinner;
    private StepperView adultsStepperView;
    private StepperView seniorsStepperView;
    private StepperView childrenStepperView;
    private StepperView infantsStepperView;
    private OnAdvanceOptionsListener listener;
    private RecyclerView childrenRecyclerView;
    private ChildrenAdapter childrenAdapter;
    private int oldSize = 1;
    private boolean isForHotels = false;


    public static AdvanceOptionsDialog newInstance() {
        Bundle bundle = new Bundle();
        bundle.putBoolean("isForHotels", true);
        AdvanceOptionsDialog dialog = new AdvanceOptionsDialog();
        dialog.setArguments(bundle);

        return dialog;
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (getArguments() != null)
            isForHotels = getArguments().getBoolean("isForHotels", false);

        // Dialog initialization
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View container = inflater.inflate(R.layout.advanced_options_dialog, null);

        LinearLayout cabinLinear = (LinearLayout) container.findViewById(R.id.cabin_linear_layout);
        TableRow seniorTableRow = (TableRow) container.findViewById(R.id.senior_table_row);
        TableRow childrenTableRow = (TableRow) container.findViewById(R.id.children_table_row);
        TableRow infantsTableRow = (TableRow) container.findViewById(R.id.infant_table_row);
        LinearLayout childrenLinear = (LinearLayout) container.findViewById(R.id.children_linear_layout);

        // Setting spinners and steppers
        adultsStepperView = (StepperView) container.findViewById(R.id.adults_stepper);
        cabinSpinner = (Spinner) container.findViewById(R.id.cabin_spinner);
        seniorsStepperView = (StepperView) container.findViewById(R.id.seniors_step);
        childrenStepperView = (StepperView) container.findViewById(R.id.children_stepper);
        infantsStepperView = (StepperView) container.findViewById(R.id.infants_stepper);

        if (isForHotels) {
            cabinLinear.setVisibility(View.GONE);
            seniorTableRow.setVisibility(View.GONE);
            childrenTableRow.setVisibility(View.GONE);
            infantsTableRow.setVisibility(View.GONE);
            childrenLinear.setVisibility(View.VISIBLE);

            childrenSpinner = (Spinner) container.findViewById(R.id.children_spinner);
            childrenSpinner.getSpinner().setItems(R.array.children_array);
            childrenSpinner.getSpinner().setOnItemClickListener(this);

            childrenRecyclerView = (RecyclerView) container.findViewById(R.id.recycler_children_list);
            childrenRecyclerView.setVisibility(View.VISIBLE);
            childrenRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            childrenRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), R.drawable.divider));
            childrenRecyclerView.setNestedScrollingEnabled(false);
            childrenRecyclerView.setHasFixedSize(false);

            childrenAdapter = new ChildrenAdapter(getContext(), 1);
            childrenRecyclerView.setAdapter(childrenAdapter);

            adultsStepperView.setMinimum(1);
            adultsStepperView.setMaximum(5);
        } else {
            adultsStepperView.setMinimum(1);
            adultsStepperView.setMaximum(6);
        }

        adultsStepperView.setAmount(1);

        seniorsStepperView.setMinimum(0);
        seniorsStepperView.setMaximum(6);

        childrenStepperView.setMinimum(0);
        childrenStepperView.setMaximum(6);

        infantsStepperView.setMinimum(0);
        infantsStepperView.setMaximum(6);

        // Setting listeners
        adultsStepperView.setOnAmountChangeListener(this);
        seniorsStepperView.setOnAmountChangeListener(this);

        builder
            .setView(container)
            .setPositiveButton(R.string.positive_option_label, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    AdvancedOptionsModel model = new AdvancedOptionsModel();
                    model.setAdult(adultsStepperView.getAmount());
                    model.setSenior(seniorsStepperView.getAmount());
                    model.setChildren(childrenStepperView.getAmount());
                    model.setInfant(infantsStepperView.getAmount());
                    model.setCabin(cabinSpinner.getSpinner().getSelectedItem());

                    // Getting ages from holder
                    List<ChildrenAdapter.ViewHolder> holders = childrenAdapter.getHolders();
                    ArrayList<Integer> ages = new ArrayList<>();
                    for (ChildrenAdapter.ViewHolder holder : holders)
                        ages.add(holder.getChildrenStepperView().getAmount());
                    model.setChildrenAges(ages);

                    if (listener != null)
                        listener.updateTextView(model);
                }
            })
            .setNegativeButton(R.string.negative_option_label, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                    if (listener != null)
                        listener.updateTextView(null);
                }
            });

        AlertDialog dialog = builder.create();
        if (isForHotels)
            dialog.setTitle(getContext().getString(R.string.dialog_title_guests_label));
        else
            dialog.setTitle(getContext().getString(R.string.dialog_title_label));

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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int size = Integer.parseInt(childrenSpinner.getSpinner().getSelectedItem());
        if (size >= oldSize) {
            for (int i=0; i < size - oldSize; i++)
                childrenAdapter.addElement();
        } else {
            for (int i=childrenAdapter.getSize() - 1; i >= size; i--)
                childrenAdapter.removeElement(i);
        }
        oldSize = size;
    }

    public boolean isForHotels() {
        return isForHotels;
    }
}
