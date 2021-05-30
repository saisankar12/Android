package in.apssdc.attendance.fragments;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import in.apssdc.attendance.R;
import in.apssdc.attendance.activities.HomeActivity;
import in.apssdc.attendance.activities.LoginActivity;
import in.apssdc.attendance.adapter.RecyclerViewAdapter;
import in.apssdc.attendance.common.DataModel;


public class NavigationDrawerFragment extends Fragment implements RecyclerViewAdapter.ClickListener{

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.drawer_list);
        adapter = new RecyclerViewAdapter(getActivity(),getData());
        adapter.setClickListener(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        fragmentManager=getFragmentManager();
        return v;
    }
    public static List<DataModel> getData()
    {
        int[] icons={R.drawable.attendance, R.drawable.worklog, R.drawable.nav_logout};
        String[] titles = {"Attendance","Work Log", "Logout"};
        int i = 0;
        List<DataModel> data_list = new ArrayList<DataModel>();
        for (String lables : titles) {
            DataModel dataModel = new DataModel(icons[i], lables);
            data_list.add(dataModel);
            i++;
        }
        return data_list;
    }
    public void setUp(DrawerLayout drawerLayout, Toolbar toolbar) {
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.action_open, R.string.action_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            //called when the drawer is closed
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
            //called during the drawer is sliding from left to right
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
         /*syncState() method is used to Synchronize the state of the drawer indicator*/
                mDrawerToggle.syncState();
            }
        });
    }
    @Override
    public void itemClicked(View view, int position) {

        mDrawerLayout.closeDrawers();
        fragmentTransaction = fragmentManager.beginTransaction();
        if (position == 0) {
            AttendanceFragment attend = new AttendanceFragment();
            fragmentTransaction.replace(R.id.frag_container, attend);
        } else if (position == 1) {
            WorkLogFragment work_log = new WorkLogFragment();
            fragmentTransaction.replace(R.id.frag_container, work_log);
        } else if (position == 2) {
            Intent i=new Intent();
            i.setComponent(new ComponentName((HomeActivity)getActivity(),LoginActivity.class));
            startActivity(i);
        }
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
