package com.verizon.smartfeedback;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


public class LikeFragment extends Fragment{
	   View likeFragmentView;
	   EditText otherReason;
	   CustomerTO customer;
	   Spinner spinner;
	   @Override
	   public View onCreateView(LayoutInflater inflater,
	      ViewGroup container, Bundle savedInstanceState) {
		  likeFragmentView = inflater.inflate(R.layout.like_fragment, null);
		  otherReason = (EditText) likeFragmentView.findViewById(R.id.other_reason);
		  
		  spinner = (Spinner) likeFragmentView.findViewById(R.id.like_dropdown);
		  ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
		        R.array.like_array, android.R.layout.simple_spinner_item);
		  adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		  spinner.setAdapter(adapter);
		  spinner.setOnItemSelectedListener(new SpinnerActivity());
		  
		  customer = new CustomerTO();
		  
		  
		  Button btnNext = (Button) likeFragmentView.findViewById(R.id.btn_next);
		  btnNext.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				customer.setGoodFeedback(spinner.getSelectedItem().toString());
				customer.setGoodComments(otherReason.getText().toString());
				getActivity().getSupportFragmentManager().beginTransaction().replace(android.R.id.content,new DisLikeFragment(customer)).commit();
			}
		  });
		  
		  return likeFragmentView;
	   }	   	   
	   public class SpinnerActivity extends Activity implements OnItemSelectedListener {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			String option = parent.getItemAtPosition(position).toString();
			
			if(option.equalsIgnoreCase("Choose one..")){
				otherReason.setVisibility(View.GONE);
			}else{
				otherReason.setVisibility(View.VISIBLE);
				if(option.equalsIgnoreCase("other"))
					otherReason.setHint("Please enter what you like");
				else
					otherReason.setHint("Additional Comments (if any)");
			}
				
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub
			
		}
		    
		}
	   
}