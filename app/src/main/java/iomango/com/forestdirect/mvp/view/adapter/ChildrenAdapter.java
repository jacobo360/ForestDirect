package iomango.com.forestdirect.mvp.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import iomango.com.forestdirect.R;
import iomango.com.forestdirect.mvp.view.custom.CustomTextView;
import iomango.com.forestdirect.mvp.view.custom.StepperView;

/**
 * Created by Clelia López on 6/7/17
 */
public class ChildrenAdapter
        extends RecyclerView.Adapter<ChildrenAdapter.ViewHolder> {

    /**
     * Attributes
     */
    private Context context;
    private int size = 1;
    private int minimumSize = 1;
    private int maximumSize = 5;
    private List<ChildrenAdapter.ViewHolder> holders  = new ArrayList<>();


    public ChildrenAdapter(Context context, int size) {
        this.context = context;
        this.size = size;

        for (int i=1; i<size; i++)
            addElement();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View convertView = inflater.inflate(R.layout.child_list_item, parent, false);
        return new ChildrenAdapter.ViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.childrenNumberTextView.setText(String.valueOf(position + 1));

        holders.add(position, holder);
    }

    @Override
    public int getItemCount() {
        return size;
    }

    public void addElement() {
        if (size < maximumSize) {
            size++;
            notifyItemInserted(size);
        }
    }

    public void removeElement(int position) {
        if (size > minimumSize) {
            size--;
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, size);
        }
    }

    public int getSize() {
        return size;
    }

    public List<ViewHolder> getHolders() {
        return holders;
    }

    /**
     * View holder class for list item
     */
    public class ViewHolder
            extends RecyclerView.ViewHolder {

        /**
         * Attributes
         */
        CustomTextView childrenNumberTextView;
        StepperView childrenStepperView;


        ViewHolder(View view) {
            super(view);
            childrenNumberTextView = (CustomTextView) view.findViewById(R.id.child_number_text_view);
            childrenStepperView = (StepperView) view.findViewById(R.id.children_stepper);
            childrenStepperView.setMinimum(2);
            childrenStepperView.setMaximum(11);
        }

        public StepperView getChildrenStepperView() {
            return childrenStepperView;
        }
    }
}
