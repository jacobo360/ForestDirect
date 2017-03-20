package iomango.com.forestdirect.mvp.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;

import iomango.com.forestdirect.R;
import iomango.com.forestdirect.mvp.common.interfaces.Listener.OnMultiCityActionListener;
import iomango.com.forestdirect.mvp.common.utilities.DrawablesTools;
import iomango.com.forestdirect.mvp.view.custom.CustomButton;
import iomango.com.forestdirect.mvp.view.custom.CustomEditText;
import iomango.com.forestdirect.mvp.view.custom.CustomTextView;
import iomango.com.forestdirect.mvp.view.custom.DatePickerEditText;

/**
 * Created by clelia_arch on 3/17/17
 */

public class MultiCityAdapter
        extends RecyclerView.Adapter<MultiCityAdapter.ViewHolder> {

    /**
     * Attributes
     */
    private Context context;
    private int size;
    private int minimumSize;
    private int maximumSize;
    private OnMultiCityActionListener listener;


    public MultiCityAdapter(Context context, int size, int minimumSize, int maximumSize) {
        this.context = context;
        this.size = size + 1;
        this.minimumSize = minimumSize + 1;
        this.maximumSize = maximumSize + 1;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View convertView;
        if (viewType == R.layout.multicity_list_item)
            convertView = inflater.inflate(R.layout.multicity_list_item, parent, false);
        else
            convertView = inflater.inflate(R.layout.footer_button, parent, false);
        return new MultiCityAdapter.ViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DrawablesTools.tintDrawable(context, R.drawable.ic_clear, R.color.grey_500);
    }

    @Override
    public int getItemCount() {
        return size;
    }

    @Override
    public int getItemViewType(int position) {
        if (position  == (size -1))
            return R.layout.footer_button;
        else
            return R.layout.multicity_list_item;
    }

    private void removeElement(int position) {
        if (size > minimumSize) {
            size--;
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, size);
        }
    }

    public void addElement() {
        if (size < maximumSize) {
            size++;
            notifyItemInserted(size);
        }
    }

    public void setOnMultiCityActionListener(OnMultiCityActionListener listener) {
        this.listener = listener;
    }

    /**
     * View holder class for list item
     */
    class ViewHolder
            extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        /**
         * Attributes
         */
        CustomEditText fromEditText;
        CustomEditText toEditText;
        CheckBox fromCheckbox;
        CheckBox toCheckbox;
        DatePickerEditText departureDateEditText;
        ImageButton deleteImageButton;

        CustomButton searchButton;
        CustomTextView addTextView;


        ViewHolder(View view) {
            super(view);
            switch (view.getId()) {
                case R.id.footer_container:
                    searchButton = (CustomButton) view.findViewById(R.id.search_button);
                    addTextView = (CustomTextView) view.findViewById(R.id.add_flights_link);
                    searchButton.setOnClickListener(this);
                    addTextView.setOnClickListener(this);
                    break;
                case R.id.multi_city_container:
                    fromEditText = (CustomEditText) view.findViewById(R.id.from_edit_text);
                    toEditText = (CustomEditText) view.findViewById(R.id.to_edit_text);
                    fromCheckbox = (CheckBox) view.findViewById(R.id.from_checkbox);
                    toCheckbox = (CheckBox) view.findViewById(R.id.to_checkbox);
                    departureDateEditText = (DatePickerEditText) view.findViewById(R.id.departure_date_edit_text);
                    deleteImageButton = (ImageButton) view.findViewById(R.id.delete_image_button);
                    deleteImageButton.setOnClickListener(this);
            }
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            switch (view.getId()) {
                case R.id.delete_image_button:
                    removeElement(position);
                    break;
                case R.id.add_flights_link:
                    if (listener != null)
                        listener.onAddFlightPressed();
                    break;
                case R.id.search_button:
                    if (listener != null)
                        listener.onSearchPressed();
                    break;
            }
        }
    }
}
