package app.balotsav.com.balotsavslider;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by chandu on 21/9/18.
 */

public class Logout extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootview=inflater.inflate(R.layout.fragment_rules, container,false);
        // Inflate the layout for this fragment

        //Toast.makeText(this, "hi", Toast.LENGTH_SHORT).show();

        return rootview;
    }
}
