package iomango.com.forestdirect.mvp.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.lang.ref.WeakReference;

import iomango.com.forestdirect.R;
import iomango.com.forestdirect.mvp.MVP;
import iomango.com.forestdirect.mvp.common.generic.GenericActivity;
import iomango.com.forestdirect.mvp.common.global.Enums.DialogType;
import iomango.com.forestdirect.mvp.common.interfaces.Listener.ExecutorListener;
import iomango.com.forestdirect.mvp.common.managers.FutureTaskManager;
import iomango.com.forestdirect.mvp.presenter.MainActivityPresenter;

import iomango.com.forestdirect.mvp.view.adapter.ViewPagerAdapterMain;
import iomango.com.forestdirect.mvp.view.dialog.SplashDialog;
import iomango.com.forestdirect.mvp.view.fragments.EmptyFragment;
import iomango.com.forestdirect.mvp.view.fragments.HomeFragment;
import iomango.com.forestdirect.mvp.view.fragments.WebViewFragment;

/**
 * Created by Clelia López on 03/10/2016
 */
public class MainActivity
        extends GenericActivity<MVP.RequiredActivityMethods, MVP.ProvidedPresenterMethodsActivity, MainActivityPresenter>
        implements MVP.RequiredActivityMethods, View.OnClickListener, ExecutorListener {

    /**
     * Attributes
     */
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ViewPagerAdapterMain adapter;

    /**
     * Vector drawable support
     */
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }


    /**
     * This hook method is called when the Activity is instantiated.
     *
     * @param savedInstanceState saved previous state, it may be null
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Displaying splash dialog
        setDialog(new SplashDialog());
        displayDialog(DialogType.SPLASH);
        FutureTaskManager.executeAfter(this, "splash", 4, false);

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
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayShowTitleEnabled(false);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        setViewPager();

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        setTabLayout();

        if(viewPager != null) {
            viewPager.clearOnPageChangeListeners();
            viewPager.addOnPageChangeListener(new TabLayoutOnPageChangeListener(tabLayout));
        }
    }

    /**
     * Specifies which fragments will be contained on the {@param viewpager} and sets its adapter.
     */
    private void setViewPager() {
        adapter = new ViewPagerAdapterMain(this, getSupportFragmentManager());

        adapter.addFragment(new HomeFragment(), R.string.search_label, R.drawable.bg_tab_home);

        adapter.addFragment(WebViewFragment.newInstance(getString(R.string.profile_url)),
                R.string.profile_label, R.drawable.bg_tab_profile);

        adapter.addFragment(WebViewFragment.newInstance(getString(R.string.my_bookings_url)),
                R.string.my_bookings_label, R.drawable.bg_tab_bookmark);

        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);
    }

    /**
     * Customizes each tab individually
     */
    private void setTabLayout() {
        if (tabLayout != null) {
            tabLayout.setupWithViewPager(viewPager);

            for (int i = 0; i < tabLayout.getTabCount(); i++) {
                TabLayout.Tab tab = tabLayout.getTabAt(i);
                if (tab != null)
                    tab.setCustomView(adapter.getTabView(i));
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * Called when the user clicks a button to perform some action
     *
     * @param view Indicates the view component pressed by the user
     */
    @Override
    public void onClick(View view) {
        // no-op
    }

    @Override
    public void execute(String name) {
        dismissDialog(DialogType.SPLASH);
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
