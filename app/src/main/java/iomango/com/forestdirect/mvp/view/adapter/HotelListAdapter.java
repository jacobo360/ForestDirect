package iomango.com.forestdirect.mvp.view.adapter;

import android.content.Context;
import android.support.v4.widget.Space;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import iomango.com.forestdirect.R;
import iomango.com.forestdirect.mvp.common.interfaces.Listener.OnLocationSelectedListener;
import iomango.com.forestdirect.mvp.model.data.HotelLocationModel;
import iomango.com.forestdirect.mvp.view.custom.CustomTextView;

/**
 * Created by Clelia López on 3/8/17
 */
public class HotelListAdapter
        extends RecyclerView.Adapter<HotelListAdapter.ViewHolder> {

    /**
     * Attributes
     */
    private Context context;
    private List<HotelLocationModel> locations;
    private OnLocationSelectedListener listener;


    public HotelListAdapter(Context context, List<HotelLocationModel> locations) {
        this.context = context;
        this.locations = locations;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View convertView = inflater.inflate(R.layout.location_list_item, parent, false);
        return new ViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.codeTextView.setText(locations.get(position).getCityCode());
        holder.locationTextView.setText(locations.get(position).getTitle());

        if (locations.get(position).isChild() == 1)
            holder.space.setVisibility(View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    public void clear() {
        int size = locations.size();
        locations.clear();
        notifyItemRangeRemoved(0, size);
    }

    public void setOnLocationSelectedListener(OnLocationSelectedListener listener) {
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
        CustomTextView codeTextView;
        CustomTextView locationTextView;
        Space space;


        ViewHolder(View view) {
            super(view);
            codeTextView = (CustomTextView)view.findViewById(R.id.code_text_view);
            locationTextView = (CustomTextView)view.findViewById(R.id.location_text_view);
            space = (Space)view.findViewById(R.id.space);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if (listener != null)
                listener.updateLocation(locations.get(position));
        }
    }
}
