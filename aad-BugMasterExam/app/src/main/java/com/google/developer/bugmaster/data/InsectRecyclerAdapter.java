package com.google.developer.bugmaster.data;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.developer.bugmaster.InsectDetailsActivity;
import com.google.developer.bugmaster.R;

import java.util.ArrayList;

/**
 * RecyclerView adapter extended with project-specific required methods.
 */

public class InsectRecyclerAdapter extends
        RecyclerView.Adapter<InsectRecyclerAdapter.InsectHolder> {
    Context context;
    ArrayList scientific_Name;
    String colors[];
    String sortOrders;
    public InsectRecyclerAdapter(Context context, ArrayList scientificName,String[] colors,String sortOrders)
    {
        this.context = context;
        this.scientific_Name= scientificName;
        this.colors=colors;
        this.sortOrders = sortOrders;
    }

    /* ViewHolder for each insect item */
    public class InsectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public GradientDrawable gradientDrawable;
        TextView danger_level,insect_name,scientific_name;
        LinearLayout layout;
        public InsectHolder(View itemView) {
            super(itemView);
            danger_level = (TextView)itemView.findViewById(R.id.dangerlevel);
            insect_name = (TextView)itemView.findViewById(R.id.friendlyname);
            scientific_name = (TextView)itemView.findViewById(R.id.scientificName);
            gradientDrawable=(GradientDrawable)danger_level.getBackground();
            layout=(LinearLayout)itemView.findViewById(R.id.rowlinearlayout);
        }

        @Override
        public void onClick(View v) {
        }
    }

    private Cursor mCursor;

    @Override
    public InsectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rowdata,parent,false);
        return new InsectHolder(view);
    }

    @Override
    public void onBindViewHolder(InsectHolder holder, int position) {
        Insect insect=getItem(position);
        final String insect_name = insect.name;
        final String scientificName = insect.scientificName;
        final int dangerLevel = insect.dangerLevel;
        final String img_asset=insect.imageAsset;
        final String classfication=insect.classification;
        holder.insect_name.setText(insect_name);
        holder.scientific_name.setText(scientificName);
        holder.danger_level.setText(""+dangerLevel);

        switch (dangerLevel){
            case 1:
                holder.gradientDrawable.setColor(Color.parseColor(colors[0]));
                break;
            case 2:
                holder.gradientDrawable.setColor(Color.parseColor(colors[1]));
                break;
            case 3:
                holder.gradientDrawable.setColor(Color.parseColor(colors[2]));
                break;
            case 4:
                holder.gradientDrawable.setColor(Color.parseColor(colors[3]));
                break;
            case 5:
                holder.gradientDrawable.setColor(Color.parseColor(colors[4]));
                break;
            case 6:
                holder.gradientDrawable.setColor(Color.parseColor(colors[5]));
                break;
            case 7:
                holder.gradientDrawable.setColor(Color.parseColor(colors[6]));
                break;
            case 8:
                holder.gradientDrawable.setColor(Color.parseColor(colors[7]));
                break;
            case 9:
                holder.gradientDrawable.setColor(Color.parseColor(colors[8]));
                break;
            case 10:
                holder.gradientDrawable.setColor(Color.parseColor(colors[9]));
                break;
            default:
        }
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context,InsectDetailsActivity.class);
                i.putExtra("name",insect_name);
                i.putExtra("scientific_name",scientificName);
                i.putExtra("image",img_asset);
                i.putExtra("classification",classfication);
                i.putExtra("rating",dangerLevel);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return scientific_Name.size();
    }

    /**
     * Return the {@link Insect} represented by this item in the adapter.
     *
     * @param position Adapter item position.
     *
     * @return A new {@link Insect} filled with this position's attributes
     *
     * @throws IllegalArgumentException if position is out of the adapter's bounds.
     */
    public Insect getItem(int position) {
        DatabaseManager databaseManager = new DatabaseManager(context);
        mCursor = databaseManager.queryAllInsects(sortOrders);
        if (position < 0 || position >= getItemCount())
        {
            throw new IllegalArgumentException("Item position is out of adapter's range");
        }
        else if (mCursor.moveToPosition(position))
        {
            return new Insect(mCursor);
        }
        return null;
    }
}
