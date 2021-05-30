package in.apssdc.attendance.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import in.apssdc.attendance.R;
import in.apssdc.attendance.activities.ContactReportActivity;
import in.apssdc.attendance.model.ContactModel;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> implements Filterable{

    ArrayList<ContactModel> adapter_list;
    ContactReportActivity contactReportActivity;
    ArrayList<ContactModel> mFilteredList;
    Context ctx;

    public ContactAdapter(ArrayList<ContactModel> adapter_list,Context ctx){
          this.adapter_list=adapter_list;
          this.ctx=ctx;
          contactReportActivity=(ContactReportActivity) ctx;
          this.mFilteredList=adapter_list;
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.individual_report,parent,false);
        ContactViewHolder reportViewHolder=new ContactViewHolder(view,contactReportActivity);
        return reportViewHolder;
    }
    @Override
    public void onBindViewHolder(ContactViewHolder holder, int position) {
        holder.iv_phone.setImageResource(R.drawable.phone_icon);
        holder.iv_email.setImageResource(R.drawable.ic_email);
        holder.tv_name.setText(mFilteredList.get(position).getEmpName());
        holder.tv_phone.setText(mFilteredList.get(position).getEmpPhone());
        holder.tv_email.setText(mFilteredList.get(position).getEmpEmail());
    }
    @Override
    public int getItemCount() {
        return mFilteredList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    mFilteredList = adapter_list;
                }else{
                    ArrayList<ContactModel> filteredList = new ArrayList<>();
                    for (ContactModel c : adapter_list) {
                        if (c.getEmpName().toLowerCase().contains(charString) || c.getEmpPhone().toLowerCase().contains(charString)|| c.getEmpEmail().toLowerCase().contains(charString)) {
                            filteredList.add(c);
                        }
                    }
                    mFilteredList = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredList;
                return filterResults;
            }
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredList = (ArrayList<ContactModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder{

        ContactReportActivity contactReportActivity;
        ImageView iv_phone,iv_email;
        TextView tv_name;
        TextView tv_email;
        TextView tv_phone;

        public ContactViewHolder(View itemView,final ContactReportActivity contactReportActivity) {
            super(itemView);
            tv_name=(TextView)itemView.findViewById(R.id.tv_name);
            tv_phone=(TextView)itemView.findViewById(R.id.tv_phone);
            tv_email=(TextView)itemView.findViewById(R.id.tv_email);
            iv_phone=(ImageView)itemView.findViewById(R.id.ic_phone);
            iv_email=(ImageView)itemView.findViewById(R.id.ic_email);
            this.contactReportActivity=contactReportActivity;

            //phone
            iv_phone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent();
                    i.setAction(Intent.ACTION_CALL);
                    Uri.Builder builder = new Uri.Builder();
                    builder.path(tv_phone.getText().toString());
                    builder.scheme("tel");
                    i.setData(builder.build());
                    contactReportActivity.startActivity(i);
                    contactReportActivity.refresh();
                }
            });

            //email
            iv_email.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent();
                    i.setAction(Intent.ACTION_SEND);
                    i.putExtra(Intent.EXTRA_EMAIL, new String[]{tv_email.getText().toString()});
                    i.setType("message/rfc822");
                    contactReportActivity.startActivity(Intent.createChooser(i, "Select any Email Client"));
                    contactReportActivity.refresh();
                }
            });
        }
    }
}
