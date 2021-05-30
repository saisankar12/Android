package com.example.saisankar.roomdatabase;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class DeleteFragment extends Fragment {


    public DeleteFragment() {
        // Required empty public constructor
    }

    EditText deleedit;
    Button del;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_delete, container, false);
        deleedit=v.findViewById(R.id.delete);
        del=v.findViewById(R.id.del);

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id= Integer.parseInt(deleedit.getText().toString());
                User  user=new User();
                user.setId(id);
                MainActivity.myAppDatabase.myDao().deleteUser(user);
                Toast.makeText(getActivity(), "User Delete Sucessfully", Toast.LENGTH_SHORT).show();
                deleedit.setText("");
            }
        });

        return v;
    }

}
