package iomango.com.forestdirect.mvp.view.fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.lang.ref.WeakReference;

import iomango.com.forestdirect.R;
import iomango.com.forestdirect.mvp.MVP;
import iomango.com.forestdirect.mvp.common.generic.GenericFragment;
import iomango.com.forestdirect.mvp.presenter.TemplatePresenterFragment;
import iomango.com.forestdirect.mvp.view.activities.MainActivity;
import iomango.com.forestdirect.mvp.view.adapter.ViewPagerAdapterHome;

/**
 * Created by Clelia López on 03/10/2017
 */
public class HomeFragment
        extends GenericFragment<MVP.RequiredFragmentMethods, MVP.ProvidedPresenterMethodsFragment, TemplatePresenterFragment>
        implements MVP.RequiredFragmentMethods, View.OnClickListener {

    /**
     * Attributes
     */
    private LinearLayout linearLayout;
    private ViewPager viewPager;
    private MainActivity parentActivity;


    /**
     * Hook method called to set up the fragment's user interface. It returns a View object,
     * that is given to the hosting activity to install it into its view hierarchy.
     *
     * @param container view parent of the fragment in the activity, therefore its container
     * @param savedInstanceState object that contains saved state information.
     * @return View object returned by the inflation process
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        linearLayout = (LinearLayout) inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize parent activity
        parentActivity = (MainActivity) getActivity();

        // Initialize retained fragment state
        isRetainedFragment = false;

        // Instantiate the presenter
        super.onCreate(TemplatePresenterFragment.class, this);

        // Initialize the view components defined in the fragment's layout
        initializeViews();

        return linearLayout;
    }

    /**
     * Initialize the Views and GUI widgets.
     */
    private void initializeViews() {
        viewPager = (ViewPager) linearLayout.findViewById(R.id.view_pager_home);
        setViewPager();

        TabLayout tabLayout = (TabLayout) linearLayout.findViewById(R.id.tab_layout_home);
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
        ViewPagerAdapterHome adapter = new ViewPagerAdapterHome(getContext(), parentActivity.getSupportFragmentManager());
        adapter.addFragment(new SelectorFragment(), R.string.flight_label);
        adapter.addFragment(new HotelsFragment(), R.string.hotel_label);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);
    }

    /**
     * Hook OnClickListener
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

        TabLayoutOnPageChangeListener(TabLayout tabLayout) {
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
        }

        @SuppressWarnings("ConstantConditions")
        @Override
        public void onPageSelected(int position) {
            final TabLayout tabLayout = tabLayoutWeakReference.get();
            if (tabLayout != null)
                tabLayout.getTabAt(position).select();
        }
    }
}
