package app.balotsav.com.balotsavslider.utils;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

import app.balotsav.com.balotsavslider.R;
import app.balotsav.com.balotsavslider.activities.EventRegistrationActivity;
import app.balotsav.com.balotsavslider.model.Event;
import app.balotsav.com.balotsavslider.model.RegisterDetail;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventHolder> {
    View dash;
    Context context;
    ArrayList<Event> event = new ArrayList<>();
    ArrayList<RegisterDetail> registerDetailActivityModels = new ArrayList<>();
    int a;
    private Date today;
    Intent intent;
    Boolean value;

    public EventAdapter(View dashBoard, ArrayList<Event> event, boolean value, Context c) {
        dash = dashBoard;
        this.event = event;
        this.a = 1;
        context = c;
        this.value = value;

    }

    public EventAdapter(View dashBoard, ArrayList registerDetailActivityModels, Context c) {
        dash = dashBoard;
        this.registerDetailActivityModels = registerDetailActivityModels;
        context = c;
        this.a = 2;

        Log.i("hello", "contsructor");
    }

    @NonNull
    @Override
    public EventHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int id = 0;
        if (a == 1) {
            id = R.layout.event;
        } else if (a == 2) {
            id = R.layout.event_register_detail;
        }
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View eventView = inflater.inflate(id, parent, false);
        EventHolder eventHolder = new EventHolder(eventView);
        return eventHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final EventHolder holder, final int position) {


        if (a == 1) {

            holder.tv.setText(event.get(position).getName());
            if(!value) {
                holder.tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (new CheckNetwork(context).isNetworkAvailable()) {
                            intent = new Intent(dash.getContext(), EventRegistrationActivity.class);
                            intent.putExtra("event", event.get(position));
                            dash.getContext().startActivity(intent);
                        }
                        else
                            Toast.makeText(context,R.string.check_connection,Toast.LENGTH_LONG).show();
                    }
                });
            }
            else
                Toast.makeText(context,"Registration closed",Toast.LENGTH_SHORT);
        }
        if (a == 2) {
            holder.jd.setVisibility(View.GONE);
            holder.jb.setVisibility(View.GONE);
            holder.sd.setVisibility(View.GONE);
            holder.sb.setVisibility(View.GONE);
            holder.sjb.setVisibility(View.GONE);
            holder.sjd.setVisibility(View.GONE);
            holder.gridLayout.setVisibility(View.GONE);
            holder.team.setVisibility(View.GONE);
            holder.teamd.setVisibility(View.GONE);
            String name = registerDetailActivityModels.get(position).getEventName();
            int jncount = registerDetailActivityModels.get(position).getJnCount(),
                    subcount = registerDetailActivityModels.get(position).getSubCount(),
                    srcount = registerDetailActivityModels.get(position).getSrCount(),
                    team = registerDetailActivityModels.get(position).getTeam();

            holder.EventName.setText(name);
            holder.jd.setText(Integer.toString(jncount));
            holder.sjd.setText(Integer.toString(subcount));
            holder.sd.setText(Integer.toString(srcount));
            holder.teamd.setText(Integer.toString(team));
            holder.EventName.setVisibility(View.VISIBLE);
            if (team == -99) {
                holder.gridLayout.setVisibility(View.VISIBLE);
                if (jncount == -99 && subcount == -99) {
                    holder.sd.setVisibility(View.VISIBLE);
                    holder.sb.setVisibility(View.VISIBLE);
                }
                if (jncount == -99 && srcount == -99) {
                    holder.sjb.setVisibility(View.VISIBLE);
                    holder.sjd.setVisibility(View.VISIBLE);
                }
                if (subcount == -99 && jncount != -99) {
                    holder.jd.setVisibility(View.VISIBLE);
                    holder.jb.setVisibility(View.VISIBLE);
                    holder.sd.setVisibility(View.VISIBLE);
                    holder.sb.setVisibility(View.VISIBLE);
                }
                if (jncount != -99 && subcount != -99) {
                    holder.jd.setVisibility(View.VISIBLE);
                    holder.jb.setVisibility(View.VISIBLE);
                    holder.sjd.setVisibility(View.VISIBLE);
                    holder.sjb.setVisibility(View.VISIBLE);
                    holder.sd.setVisibility(View.VISIBLE);
                    holder.sb.setVisibility(View.VISIBLE);
                }
            } else {
                holder.team.setVisibility(View.VISIBLE);
                holder.teamd.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (a == 1) {
            count = event.size();
        } else if (a == 2) {
            count = registerDetailActivityModels.size();
        }
        return count;
    }

    public class EventHolder extends RecyclerView.ViewHolder {
        TextView tv;
        LinearLayout gridLayout;
        TextView EventName, jb, sjb, sb, team, jd, sjd, sd, teamd;

        public EventHolder(View itemView) {
            super(itemView);
            if (a == 1) {
                tv = (TextView) itemView.findViewById(R.id.event_text);
            } else if (a == 2) {
                EventName = itemView.findViewById(R.id.event_name);
                gridLayout = itemView.findViewById(R.id.grid);
                jb = itemView.findViewById(R.id.jb);
                jd = itemView.findViewById(R.id.jd);
                sjb = itemView.findViewById(R.id.sjb);
                sjd = itemView.findViewById(R.id.sjd);
                sb = itemView.findViewById(R.id.sb);
                sd = itemView.findViewById(R.id.sd);
                teamd = itemView.findViewById(R.id.teamnodisplay);
                team = itemView.findViewById(R.id.teamdisplay);
            }

        }
    }
}
