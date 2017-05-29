package iomango.com.forestdirect.mvp.view.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import iomango.com.forestdirect.R;
import iomango.com.forestdirect.mvp.view.custom.CustomTextView;


/**
 * Created by Clelia López on 9/14/16
 */
public class ViewPagerAdapterMain
        extends FragmentPagerAdapter {

    /**
     * Attributes
     */
    private final List<Fragment> fragments = new ArrayList<>();
    private final List<String> titles = new ArrayList<>();
    private final List<Integer> icons = new ArrayList<>();
    private Context context;


    public ViewPagerAdapterMain(Context context, FragmentManager manager) {
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

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

    public void addFragment(Fragment fragment, int title, int icon) {
        fragments.add(fragment);
        titles.add(context.getString(title));
        icons.add(icon);
    }

    @SuppressLint("InflateParams")
    public View getTabView(int position) {
        View tab = LayoutInflater.from(context).inflate(R.layout.tab_item, null);
        CustomTextView tabText = (CustomTextView) tab.findViewById(R.id.title_text_view);
        ImageView tabImage = (ImageView) tab.findViewById(R.id.icon_image_view);
        tabText.setText(titles.get(position));
        tabImage.setBackgroundResource(icons.get(position));
        if (position == 0)
            tab.setSelected(true);
        return tab;
    }
}