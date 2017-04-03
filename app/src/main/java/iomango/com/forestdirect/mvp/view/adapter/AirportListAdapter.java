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
import iomango.com.forestdirect.mvp.model.data.AirportLocationModel;
import iomango.com.forestdirect.mvp.view.custom.CustomTextView;

/**
 * Created by clelia_arch on 3/8/17
 */
public class AirportListAdapter
        extends RecyclerView.Adapter<AirportListAdapter.ViewHolder> {

    /**
     * Attributes
     */
    private Context context;
    private List<AirportLocationModel> locations;
    private OnLocationSelectedListener listener;


    public AirportListAdapter(Context context, List<AirportLocationModel> locations) {
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
        holder.codeTextView.setText(locations.get(position).getCode());
        holder.locationTextView.setText(locations.get(position).getCity()
                + ", " + locations.get(position).getCountry());

        if (locations.get(position).getIsChild())
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
