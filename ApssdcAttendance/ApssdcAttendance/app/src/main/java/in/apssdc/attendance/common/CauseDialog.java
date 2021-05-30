package in.apssdc.attendance.common;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import in.apssdc.attendance.R;

public class CauseDialog extends DialogFragment{

	EditText et_cause;
	Button btn_submit;
	ForgetCommunicator communicator;

	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.cause_dialog, container, false);
		et_cause=(EditText) view.findViewById(R.id.cause);
		btn_submit=(Button)view.findViewById(R.id.submit);

		btn_submit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(et_cause.getText().toString().equalsIgnoreCase("")){
					Toast.makeText(getActivity(),"enter cause",Toast.LENGTH_SHORT).show();
				}else{
					communicator.onDialogMessage(et_cause.getText().toString());
					dismiss();
				}
			}
		});
		return view;
	}
	public interface ForgetCommunicator
	{
		public void onDialogMessage(String message);
	}
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		communicator=(ForgetCommunicator)activity;
	}
}
