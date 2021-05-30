package app.balotsav.com.balotsavslider;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;


/**
 * A simple {@link Fragment} subclass.
 */
public class RulesFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview=inflater.inflate(R.layout.fragment_rules, container,false);
        // Inflate the layout for this fragment

        //Toast.makeText(this, "hi", Toast.LENGTH_SHORT).show();
        try {

            PDFView pdfView = rootview.findViewById(R.id.pdfView1);
            //File file = new File("res/raw/brochure.pdf");
            pdfView.fromAsset("brochure.pdf").load();
        }catch (Exception e){
            Log.i("brochure","brochure view problem");
        }

        return rootview;
    }

}