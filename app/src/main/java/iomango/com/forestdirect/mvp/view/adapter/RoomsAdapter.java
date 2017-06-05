package iomango.com.forestdirect.mvp.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import iomango.com.forestdirect.R;
import iomango.com.forestdirect.mvp.model.data.GuestModel;
import iomango.com.forestdirect.mvp.view.custom.CustomTextView;
import iomango.com.forestdirect.mvp.view.custom.DialogEditText;

/**
 * Created by Clelia López on 5/30/17
 */
public class RoomsAdapter
        extends RecyclerView.Adapter<RoomsAdapter.ViewHolder> {

    /**
     * Attributes
     */
    private Context context;
    private List<GuestModel> guests;
    private int size = 1;
    private int minimumSize = 1;
    private int maximumSize = 9;


    public RoomsAdapter(Context context, List<GuestModel> guests) {
        this.context = context;
        this.guests = guests;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View convertView = inflater.inflate(R.layout.room_list_item, parent, false);
        return new ViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.roomTextView.setText(holder.roomTextView.getText() + " " + (position + 1));
    }

    @Override
    public int getItemCount() {
        return guests.size();
    }

    private void addElement() {
        if (size < maximumSize) {
            size++;
            notifyItemInserted(size);
        }
    }

    private void removeElement(int position) {
        if (size > minimumSize) {
            size--;
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, size);
        }
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
        CustomTextView roomTextView;
        DialogEditText guessDialogEditText;


        ViewHolder(View view) {
            super(view);
            roomTextView = (CustomTextView) view.findViewById(R.id.room_text_view);
            guessDialogEditText = (DialogEditText) view.findViewById(R.id.guests_edit_text);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            // if (listener != null)
                // listener.updateLocation(locations.get(position));
        }
    }
}
