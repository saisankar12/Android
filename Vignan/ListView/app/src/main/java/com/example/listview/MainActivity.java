package com.example.listview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    ListView lv;
    String[] names={"Kishan","Lavanya","NithyaSai","Suresh",
                "Nikhil","Charan","Syam","Aditya","Mani","Harsha Vardhan"
                ,"Murali","Tejasree"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv=findViewById(R.id.list);

        ArrayAdapter<String> namesAdapter=
                new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1,names);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent,
                                    View view, int position, long id) {
                Toast.makeText(MainActivity.this, ""+position,
                        Toast.LENGTH_SHORT).show();
            }
        });
        lv.setAdapter(namesAdapter);

    }
}
