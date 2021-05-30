package app.balotsav.com.balotsavslider;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class ResultsFragment extends Fragment {




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        ActionBar actionBar = ((DashBoard)getActivity()).getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.results));
        actionBar.setDefaultDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        View rootview=inflater.inflate(R.layout.fragment_registered_events, container,false);
        // Inflate the layout for this fragment

        //Toast.makeText(this, "hi", Toast.LENGTH_SHORT).show();

        return rootview;
    }

}
