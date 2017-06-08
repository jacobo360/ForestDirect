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
    private int size = 1;
    private int minimumSize = 1;
    private int maximumSize = 9;
    private List<RoomsAdapter.ViewHolder> holders  = new ArrayList<>();


    public RoomsAdapter(Context context, int size) {
        this.context = context;
        this.size = size;

        for (int i=1; i<size; i++)
            addElement();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View convertView = inflater.inflate(R.layout.room_list_item, parent, false);
        return new ViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.roomNumberTextView.setText(String.valueOf(position + 1));

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
        CustomTextView roomNumberTextView;
        DialogEditText guessDialogEditText;


        ViewHolder(View view) {
            super(view);
            roomNumberTextView = (CustomTextView) view.findViewById(R.id.room_number_text_view);
            guessDialogEditText = (DialogEditText) view.findViewById(R.id.guests_edit_text);
        }

        public DialogEditText getGuessDialogEditText() {
            return guessDialogEditText;
        }
    }
}
