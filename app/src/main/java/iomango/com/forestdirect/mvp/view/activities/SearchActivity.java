package iomango.com.forestdirect.mvp.view.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageButton;

import java.util.List;

import iomango.com.forestdirect.R;
import iomango.com.forestdirect.mvp.MVP;
import iomango.com.forestdirect.mvp.common.generic.GenericActivity;
import iomango.com.forestdirect.mvp.model.MainActivityModel;
import iomango.com.forestdirect.mvp.model.data.LocationModel;
import iomango.com.forestdirect.mvp.presenter.SearchActivityPresenter;
import iomango.com.forestdirect.mvp.view.adapter.LocationListAdapter;
import iomango.com.forestdirect.mvp.view.custom.CustomEditText;
import iomango.com.forestdirect.mvp.view.decorator.DividerItemDecoration;

/**
 * Created by Clelia López on 08/12/2015
 */
public class SearchActivity
        extends GenericActivity<MVP.RequiredActivityMethods, MVP.ProvidedPresenterMethodsActivity, SearchActivityPresenter>
        implements MVP.RequiredActivityMethods, View.OnClickListener {

    /**
     * Attributes
     */
    private CustomEditText editText;
    private RecyclerView recyclerView;
    LocationListAdapter adapter;


    /**
     * This hook method is called when the Activity is instantiated.
     *
     * @param savedInstanceState saved previous state, it may be null
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Instantiate the presenter
        super.onCreate(SearchActivityPresenter.class, this);

        // Initialize all view components defined in the activity's layout
        initializeViews();
    }

    /**
     * Initialize the Views and GUI widgets.
     */
    private void initializeViews() {
        editText = (CustomEditText) findViewById(R.id.search_edit_text);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_location_list);
        ImageButton clearImageButton = (ImageButton) findViewById(R.id.clear_image_button);
        ImageButton backImageButton = (ImageButton) findViewById(R.id.back_image_button);

        setDialog(findViewById(R.id.dialog));

        // Setting listeners
        clearImageButton.setOnClickListener(this);
        backImageButton.setOnClickListener(this);

        // Recycler set up
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, R.drawable.divider));
        recyclerView.setHasFixedSize(false);

        // Text watcher functionality
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence text, int start, int count, int after) {
                // no-op
            }

            @Override
            public void onTextChanged(CharSequence text, int start, int before, int count) {
                // no-op
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() > 0)
                    getPresenter().executeNetworkRequest(new MainActivityModel(editable.toString()));
                else
                    adapter.clear();
            }
        });
    }

    /**
     * Called when the user clicks a button to perform some action
     *
     * @param view Indicates the view component pressed by the user
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.clear_image_button:
                editText.setText("");
                break;
            case R.id.back_image_button:
                finish();
                break;
            default:
                getPresenter().handleClick(view.getId());
        }
    }

    @Override
    public <T> void updateView(T data) {
        adapter = new LocationListAdapter(this, (List<LocationModel>)data);
        // adapter.setPortfolioListItemListener(this);
        recyclerView.setAdapter(adapter);
    }
}
