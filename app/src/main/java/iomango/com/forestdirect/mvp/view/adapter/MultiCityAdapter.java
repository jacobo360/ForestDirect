package iomango.com.forestdirect.mvp.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

import iomango.com.forestdirect.R;
import iomango.com.forestdirect.mvp.common.interfaces.Listener;
import iomango.com.forestdirect.mvp.common.interfaces.Listener.OnAdapterErrorListener;
import iomango.com.forestdirect.mvp.common.interfaces.Listener.OnMultiCityActionListener;
import iomango.com.forestdirect.mvp.common.utilities.Date;
import iomango.com.forestdirect.mvp.view.custom.CustomButton;
import iomango.com.forestdirect.mvp.view.custom.CustomEditText;
import iomango.com.forestdirect.mvp.view.custom.CustomTextView;
import iomango.com.forestdirect.mvp.view.custom.DatePickerEditText;
import iomango.com.forestdirect.mvp.view.custom.DialogEditText;

import static iomango.com.forestdirect.mvp.common.global.Constants.FROM;
import static iomango.com.forestdirect.mvp.common.global.Constants.TO;

/**
 * Created by Clelia López on 3/17/17
 */

public class MultiCityAdapter
        extends RecyclerView.Adapter<MultiCityAdapter.ViewHolder>
        implements Listener.OnDateSetListener {

    /**
     * Attributes
     */
    private Context context;
    private int size;
    private int minimumSize;
    private int maximumSize;
    private OnMultiCityActionListener listener;
    private OnAdapterErrorListener errorListener;
    private List<MultiCityAdapter.ViewHolder> holders  = new ArrayList<>();
    private int lastUpdated = -1;
    private Date currentDate;


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
            convertView = inflater.inflate(R.layout.multicity_footer, parent, false);
        return new MultiCityAdapter.ViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (holder.fromEditText != null && holder.toEditText != null) {
            holder.fromEditText.setHint(FROM[position]);
            holder.toEditText.setHint(TO[position]);
        }

        if (holder.departureDateEditText != null) {
            holder.departureDateEditText.setOnDateSetListener(this, position);

            if (currentDate != null && position == lastUpdated + 1)
                holder.departureDateEditText.setMinimumDate(currentDate);
        }

        holders.add(position, holder);
    }

    @Override
    public int getItemCount() {
        return size;
    }

    @Override
    public int getItemViewType(int position) {
        if (position  == (size - 1))
            return R.layout.multicity_footer;
        else
            return R.layout.multicity_list_item;
    }

    private void addElement() {
        if (size < maximumSize) {
            size++;
            notifyItemInserted(size);
            holders.size();
        }
    }

    private void removeElement(int position) {
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

    public void setOnMultiCityActionListener(OnMultiCityActionListener listener) {
        this.listener = listener;
    }

    public void setOnAdapterErrorListener(OnAdapterErrorListener errorListener) {
        this.errorListener = errorListener;
    }

    @Override
    public void updateDate(Date date, int id) {
        if (id == lastUpdated + 1) {
            if (id < 6) {
                currentDate = date;
                lastUpdated++;
                DatePickerEditText dateEditText = holders.get(id + 1).departureDateEditText;
                if (dateEditText != null)
                    dateEditText.setMinimumDate(date);
            }
        } else if (errorListener != null) {
            lastUpdated = -1;
            errorListener.reportError();
        }
    }


    /**
     * View holder class for list item
     */
    public class ViewHolder
            extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnFocusChangeListener {

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
        DialogEditText dialogEditText;


        ViewHolder(View view) {
            super(view);
            switch (view.getId()) {
                case R.id.footer_container:
                    searchButton = (CustomButton) view.findViewById(R.id.search_button);
                    addTextView = (CustomTextView) view.findViewById(R.id.add_flights_link);
                    dialogEditText = (DialogEditText) view.findViewById(R.id.kind_edit_text);
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
                    fromEditText.setOnClickListener(this);
                    toEditText.setOnClickListener(this);
                    deleteImageButton.setOnClickListener(this);
                    fromEditText.setOnFocusChangeListener(this);
                    toEditText.setOnFocusChangeListener(this);
            }
        }

        public CustomEditText getFromEditText() {
            return fromEditText;
        }

        public CheckBox getFromCheckbox() {
            return fromCheckbox;
        }

        public CustomEditText getToEditText() {
            return toEditText;
        }

        public CheckBox getToCheckbox() {
            return toCheckbox;
        }

        public DatePickerEditText getDepartureDateEditText() {
            return departureDateEditText;
        }

        public DialogEditText getDialogEditText() {
            return dialogEditText;
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            switch (view.getId()) {
                case R.id.from_edit_text:
                    if (listener != null)
                        listener.onFromEditTextClicked((CustomEditText)view
                                .findViewById(R.id.from_edit_text));
                    break;
                case R.id.to_edit_text:
                    if (listener != null)
                        listener.onToEditTextClicked((CustomEditText)view
                                .findViewById(R.id.to_edit_text));
                    break;
                case R.id.delete_image_button:
                    removeElement(position);
                    break;
                case R.id.add_flights_link:
                    addElement();
                    break;
                case R.id.search_button:
                    if (listener != null)
                        listener.onSearchButtonClicked();
                    break;
            }
        }

        @Override
        public void onFocusChange(View view, boolean hasFocus) {
            switch (view.getId()) {
                case R.id.from_edit_text:
                    if (hasFocus) {
                        if (listener != null)
                            listener.onFromEditTextClicked((CustomEditText)view
                                    .findViewById(R.id.from_edit_text));
                    }
                    break;
                case R.id.to_edit_text:
                    if (hasFocus) {
                        if (listener != null)
                            listener.onToEditTextClicked((CustomEditText)view
                                    .findViewById(R.id.to_edit_text));
                    }
                    break;
            }
        }
    }
}
