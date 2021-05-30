package in.apssdc.attendance.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.SearchView;
import android.widget.TextView;
import java.util.ArrayList;
import in.apssdc.attendance.R;
import in.apssdc.attendance.adapter.ContactAdapter;
import in.apssdc.attendance.model.ContactModel;

public class ContactReportActivity extends AppCompatActivity{

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    SearchView searchView;
    TextView tv;
    ContactAdapter adapter;
    ArrayList<ContactModel> pojos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        recyclerView=(RecyclerView)findViewById(R.id.recycler_view);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        searchView=(SearchView)findViewById(R.id.search_view);
        tv=(TextView)findViewById(R.id.tv_title);
        tv.setText("Search Employee");
        try{
            pojos=(ArrayList<ContactModel>)getIntent().getSerializableExtra("data");
        }catch (Exception e){e.printStackTrace();}
        adapter=new ContactAdapter(pojos,ContactReportActivity.this);
        recyclerView.setAdapter(adapter);

        //search view functionalities
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }
        });
    }
    public void refresh(){
        adapter=new ContactAdapter(pojos,ContactReportActivity.this);
        recyclerView.setAdapter(adapter);
    }
}
