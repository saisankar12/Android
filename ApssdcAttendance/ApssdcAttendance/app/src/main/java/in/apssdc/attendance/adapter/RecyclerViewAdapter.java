package in.apssdc.attendance.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

import in.apssdc.attendance.R;
import in.apssdc.attendance.common.DataModel;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    /*onBindViewHolder() method is used to display the items at a specified position
      in the recyclerView.that items may be anything like TextView,ImageView,Button etc.*.
      who is supplying that items to the onBindViewHolder method.the answer is
      onCreateViewHolder() method.this method takes the views from RecyclerView.ViewHolder
      class via Model class and giving to the onBindViewHolder along with description
      of item views and its positions.Then onBindViewHolder method takes the data and display
      that items at the given specified positions.*/

    private ClickListener clickListener;
    Context context;
    List<DataModel> list = new ArrayList<DataModel>();

    public RecyclerViewAdapter(Context context, List<DataModel> list) {
        this.context = context;
        this.list = list;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //LayoutInflater inflater=(LayoutInflater)parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.individual_row, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        DataModel dataModel = list.get(position);
        holder.itemText.setText(dataModel.getTitle());
        holder.itemIcon.setImageResource(dataModel.getIconid());
    }

    /*this method gives the count of How many Individual items you want to display
    on the RecyclerView*/

    @Override
    public int getItemCount() {
        return list.size();
    }

   /* public void delete(int position)
    {
        list.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,list.size());
    }*/

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        /*super constructor takes the object of view as a input and given
        back to onCreateViewHolder() method.*/
        TextView itemText;
        ImageView itemIcon;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemText = (TextView) itemView.findViewById(R.id.list_text);
            itemIcon = (ImageView) itemView.findViewById(R.id.list_icon);
            itemView.setOnClickListener(this);

         /* itemIcon.setOnLongClickListener(new View.OnLongClickListener()
          {
              @Override
              public boolean onLongClick(View v) {
                  delete(getAdapterPosition());
                  return true;
              }
          });*/


        }

        @Override
        public void onClick(View view) {

           /* if (itemText.getText().toString().equalsIgnoreCase("BasicDetails")) {
                context.startActivity(new Intent(context, BasicDetails.class));
            } else if (itemText.getText().toString().equalsIgnoreCase("Academic")) {
                context.startActivity(new Intent(context, AcademicActivity.class));
            } else if (itemText.getText().toString().equalsIgnoreCase("Examinations")) {
                context.startActivity(new Intent(context, ExaminationsActivity.class));
            } else if (itemText.getText().toString().equalsIgnoreCase("Events")) {
                context.startActivity(new Intent(context, EventsActivity.class));
            } else if (itemText.getText().toString().equalsIgnoreCase("Services")) {
                context.startActivity(new Intent(context, ServicesActivity.class));
            } else if (itemText.getText().toString().equalsIgnoreCase("Infrastructure")) {
                context.startActivity(new Intent(context, InfrastructureActivity.class));
            } else if (itemText.getText().toString().equalsIgnoreCase("Supporting Staff")) {
                context.startActivity(new Intent(context, SupportingStaffActivity.class));
            } else {
                context.startActivity(new Intent(context, ResearchActivity.class));
            }*/

            if(clickListener!=null)
            {
                clickListener.itemClicked(view,getPosition());
            }
        }
    }

    public interface ClickListener
    {
        public void itemClicked(View view, int position);

    }
    public  void setClickListener(ClickListener clickListener)
    {
        this.clickListener=clickListener;
    }
}


