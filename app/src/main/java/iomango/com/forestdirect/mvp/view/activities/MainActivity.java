package iomango.com.forestdirect.mvp.view.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.lang.ref.WeakReference;

import iomango.com.forestdirect.R;
import iomango.com.forestdirect.mvp.MVP;
import iomango.com.forestdirect.mvp.common.generic.GenericActivity;
import iomango.com.forestdirect.mvp.presenter.MainActivityPresenter;
import iomango.com.forestdirect.mvp.view.adapter.ViewPagerAdapter;
import iomango.com.forestdirect.mvp.view.fragments.EmptyFragment;
import iomango.com.forestdirect.mvp.view.fragments.OneWayFragment;

/**
 * Created by Clelia López on 03/10/2016
 */
public class MainActivity
        extends GenericActivity<MVP.RequiredActivityMethods, MVP.ProvidedPresenterMethodsActivity, MainActivityPresenter>
        implements MVP.RequiredActivityMethods, View.OnClickListener {

    /**
     * Attributes
     */
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ViewPagerAdapter adapter;


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
        super.onCreate(MainActivityPresenter.class, this);

        // Initialize all view components defined in the activity's layout
        initializeViews();
    }

    /**
     * Initialize the Views and GUI widgets.
     */
    private void initializeViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setToolbar(toolbar, false);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        setViewPager();

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        if(viewPager != null) {
            viewPager.clearOnPageChangeListeners();
            viewPager.addOnPageChangeListener(new TabLayoutOnPageChangeListener(tabLayout));
        }
    }

    /**
     * Specifies which fragments will be contained on the {@param viewpager} and sets its adapter.
     */
    private void setViewPager() {
        adapter = new ViewPagerAdapter(this, getSupportFragmentManager());
        adapter.addFragment(new OneWayFragment(), R.string.flight_label);
        adapter.addFragment(new EmptyFragment(), R.string.hotel_label);
        viewPager.setAdapter(adapter);
    }


    /**
     * Called when the user clicks a button to perform some action
     *
     * @param view Indicates the view component pressed by the user
     */
    @Override
    public void onClick(View view) {
        getPresenter().handleClick(view.getId());
    }

    /**
     * Custom OnPageChangeListener
     */
    private class TabLayoutOnPageChangeListener
            implements ViewPager.OnPageChangeListener {

        /**
         * Attributes
         */
        private final WeakReference<TabLayout> tabLayoutWeakReference;
        private int previousScrollState;
        private int scrollState;


        public TabLayoutOnPageChangeListener(TabLayout tabLayout) {
            tabLayoutWeakReference = new WeakReference<>(tabLayout);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            previousScrollState = scrollState;
            scrollState = state;
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            final TabLayout tabLayout = tabLayoutWeakReference.get();
            if (tabLayout != null) {
                final boolean updateText = (scrollState == ViewPager.SCROLL_STATE_DRAGGING)
                        || (scrollState == ViewPager.SCROLL_STATE_SETTLING
                        && previousScrollState == ViewPager.SCROLL_STATE_DRAGGING);
                tabLayout.setScrollPosition(position, positionOffset, updateText);
            }
            /*if (position == 0)
                floatingActionButton.setVisibility(View.VISIBLE);
            else
                floatingActionButton.setVisibility(View.GONE);*/
        }

        @SuppressWarnings("ConstantConditions")
        @Override
        public void onPageSelected(int position) {
            final TabLayout tabLayout = tabLayoutWeakReference.get();
            if (tabLayout != null)
                tabLayout.getTabAt(position).select();

            /*
                if (position == 0)
                    floatingActionButton.setVisibility(View.VISIBLE);
                else
                    floatingActionButton.setVisibility(View.GONE);
            }*/
        }
    }
}
