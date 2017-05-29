package iomango.com.forestdirect.mvp.view.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by clelia_arch on 3/10/17
 */

public class ViewPagerAdapterHome
        extends FragmentPagerAdapter {

    /**
     * Attributes
     */
    private final List<Fragment> fragments = new ArrayList<>();
    private final List<String> titles = new ArrayList<>();
    private Context context;


    public ViewPagerAdapterHome(Context context, FragmentManager manager) {
        super(manager);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    public void addFragment(Fragment fragment, int title) {
        fragments.add(fragment);
        titles.add(context.getString(title));
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
