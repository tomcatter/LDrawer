package com.ikimuhendis.ldrawer.sample;

import com.ikimuhendis.ldrawer.ActionBarDrawerToggle;
import com.ikimuhendis.ldrawer.DrawerArrowDrawable;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

public class MainActivity extends Activity {
	
	 private ActionBarDrawerToggle mDrawerToggle;
	 private DrawerArrowDrawable drawerArrow;
	 private ListView mDrawerList;
	 private boolean drawerArrowColor;
	 
	 DrawerLayout drawer;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		drawer = (DrawerLayout) inflater.inflate(R.layout.decor,null); // "null" is important.
		// HACK: "steal" the first child of decor view
		ViewGroup decor = (ViewGroup) getWindow().getDecorView();
		View child = decor.getChildAt(0);
		decor.removeView(child);
		FrameLayout container = (FrameLayout) drawer.findViewById(R.id.container); // This is the container we
		mDrawerList = (ListView) drawer.findViewById(R.id.navdrawer);
		container.addView(child);

		// Make the drawer replace the first child
		decor.addView(drawer);

		// Do what you want to do.......
		final ActionBar ab = getActionBar();
	    ab.setDisplayHomeAsUpEnabled(true);
	    ab.setHomeButtonEnabled(true);
	    
	    drawerArrow = new DrawerArrowDrawable(this) {
            @Override
            public boolean isLayoutRtl() {
                return false;
            }
        };
        mDrawerToggle = new ActionBarDrawerToggle(this, drawer,
            drawerArrow, R.string.drawer_open,
            R.string.drawer_close) {

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }
        };
        drawer.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();


        String[] values = new String[]{
            "Stop Animation (Back icon)",
            "Stop Animation (Home icon)",
            "Start Animation",
            "Change Color",
            "GitHub Page",
            "Share",
            "Rate"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
            android.R.layout.simple_list_item_1, android.R.id.text1, values);
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                switch (position) {
                    case 0:
                        mDrawerToggle.setAnimateEnabled(false);
                        drawerArrow.setProgress(1f);
                        break;
                    case 1:
                        mDrawerToggle.setAnimateEnabled(false);
                        drawerArrow.setProgress(0f);
                        break;
                    case 2:
                        mDrawerToggle.setAnimateEnabled(true);
                        mDrawerToggle.syncState();
                        break;
                    case 3:
                        if (drawerArrowColor) {
                            drawerArrowColor = false;
                            drawerArrow.setColor(getResources().getColor(R.color.ldrawer_color));
                        } else {
                            drawerArrowColor = true;
                            drawerArrow.setColor(getResources().getColor(R.color.drawer_arrow_second_color));
                        }
                        mDrawerToggle.syncState();
                        break;
                    case 4:
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/IkiMuhendis/LDrawer"));
                        startActivity(browserIntent);
                        break;
                    case 5:
                        Intent share = new Intent(Intent.ACTION_SEND);
                        share.setType("text/plain");
                        share.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        share.putExtra(Intent.EXTRA_SUBJECT,
                            getString(R.string.app_name));
                        share.putExtra(Intent.EXTRA_TEXT, getString(R.string.app_description) + "\n" +
                            "GitHub Page :  https://github.com/IkiMuhendis/LDrawer\n" +
                            "Sample App : https://play.google.com/store/apps/details?id=" +
                            getPackageName());
                        startActivity(Intent.createChooser(share,
                            getString(R.string.app_name)));
                        break;
                    case 6:
                        String appUrl = "https://play.google.com/store/apps/details?id=" + getPackageName();
                        Intent rateIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(appUrl));
                        startActivity(rateIntent);
                        break;
                }

            }
        });
	}
	
	   @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	        if (item.getItemId() == android.R.id.home) {
	            if (drawer.isDrawerOpen(mDrawerList)) {
	            	drawer.closeDrawer(mDrawerList);
	            } else {
	            	drawer.openDrawer(mDrawerList);
	            }
	        }
	        return super.onOptionsItemSelected(item);
	    }

	    @Override
	    protected void onPostCreate(Bundle savedInstanceState) {
	        super.onPostCreate(savedInstanceState);
	        mDrawerToggle.syncState();
	    }

	    @Override
	    public void onConfigurationChanged(Configuration newConfig) {
	        super.onConfigurationChanged(newConfig);
	        mDrawerToggle.onConfigurationChanged(newConfig);
	    }
}
