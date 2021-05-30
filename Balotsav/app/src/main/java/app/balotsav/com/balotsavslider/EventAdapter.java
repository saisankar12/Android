package app.balotsav.com.balotsavslider;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventHolder> {
    View dash;Context context;
    ArrayList<Event> event=new ArrayList<>();
    ArrayList<RegisterDetailActivityModel> registerDetailActivityModels=new ArrayList<>();
    int a;
    Intent intent;ProgressDialog progressDialog;
    public EventAdapter(View dashBoard, ArrayList<Event> event,int a,Context c) {
        dash=dashBoard;
        this.event=event;
        this.a=1;
        context=c;
        intent=new Intent(dash.getContext(),EventRegistration.class);
    }
    public EventAdapter(View dashBoard, ArrayList registerDetailActivityModels,Context c) {
        dash=dashBoard;
        this.registerDetailActivityModels=registerDetailActivityModels;
        context=c;
        this.a=2;

        Log.i("hello","contsructor");
    }

    @NonNull
    @Override
    public EventHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int id=0;
        if(a==1){
        id = R.layout.event;}
        else if(a==2){
            id=R.layout.event_register_detail;
        }
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View eventView = inflater.inflate(id, parent, false);
        EventHolder eventHolder = new EventHolder(eventView);
        return eventHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final EventHolder holder, final int position) {

        if(a==1){

            holder.tv.setText(event.get(position).getName());
            holder.tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intent.putExtra("event",event.get(position));
                    Log.i("e.getName",event.get(position).getName());

                    dash.getContext().startActivity(intent);
                }
            });
        }
        if(a==2){
            holder.jd.setVisibility(View.GONE);
            holder.jb.setVisibility(View.GONE);
            holder.sd.setVisibility(View.GONE);
            holder.sb.setVisibility(View.GONE);
            holder.sjb.setVisibility(View.GONE);
            holder.sjd.setVisibility(View.GONE);
            holder.gridLayout.setVisibility(View.GONE);
            holder.team.setVisibility(View.GONE);
            holder.teamd.setVisibility(View.GONE);
            String name=registerDetailActivityModels.get(position).EventName;
             int    jncount=registerDetailActivityModels.get(position).JnCount,
                    subcount=registerDetailActivityModels.get(position).SubCount,
                    srcount=registerDetailActivityModels.get(position).SrCount,
                    team=registerDetailActivityModels.get(position).team;

            holder.EventName.setText(name);
            holder.jd.setText(Integer.toString(jncount));
            holder.sjd.setText(Integer.toString(subcount));
            holder.sd.setText(Integer.toString(srcount));
            holder.teamd.setText(Integer.toString(team));
            holder.EventName.setVisibility(View.VISIBLE);
            if(team==-99){
                holder.gridLayout.setVisibility(View.VISIBLE);
                if(jncount==-99 && subcount==-99) {
                    holder.sd.setVisibility(View.VISIBLE);
                    holder.sb.setVisibility(View.VISIBLE);
                }
                if(jncount==-99 && srcount==-99) {
                    holder.sjb.setVisibility(View.VISIBLE);
                    holder.sjd.setVisibility(View.VISIBLE);
                }
                if(subcount==-99 && jncount!=-99){
                    holder.jd.setVisibility(View.VISIBLE);
                    holder.jb.setVisibility(View.VISIBLE);
                    holder.sd.setVisibility(View.VISIBLE);
                    holder.sb.setVisibility(View.VISIBLE);
                }
                if(jncount!=-99 && subcount!=-99){
                    holder.jd.setVisibility(View.VISIBLE);
                    holder.jb.setVisibility(View.VISIBLE);
                    holder.sjd.setVisibility(View.VISIBLE);
                    holder.sjb.setVisibility(View.VISIBLE);
                    holder.sd.setVisibility(View.VISIBLE);
                    holder.sb.setVisibility(View.VISIBLE);
                }
            }
            else{
                holder.team.setVisibility(View.VISIBLE);
                holder.teamd.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public int getItemCount() {
        int count=0;
        if (a==1){
            count=event.size();}
        else if(a==2){
            count=registerDetailActivityModels.size();
        }
        return count;
    }

    public class EventHolder extends RecyclerView.ViewHolder {
        TextView tv;LinearLayout gridLayout;
        TextView EventName,jb,sjb,sb,team,jd,sjd,sd,teamd;

        public EventHolder(View itemView) {
            super(itemView);
            if(a==1){
                tv=(TextView)itemView.findViewById(R.id.event_text);
            }
            else if(a==2){
                EventName=itemView.findViewById(R.id.event_name);
                gridLayout=itemView.findViewById(R.id.grid);
                jb=itemView.findViewById(R.id.jb);
                jd=itemView.findViewById(R.id.jd);
                sjb=itemView.findViewById(R.id.sjb);
                sjd=itemView.findViewById(R.id.sjd);
                sb=itemView.findViewById(R.id.sb);
                sd=itemView.findViewById(R.id.sd);
                teamd=itemView.findViewById(R.id.teamnodisplay);
                team=itemView.findViewById(R.id.teamdisplay);
            }

        }
    }
}
