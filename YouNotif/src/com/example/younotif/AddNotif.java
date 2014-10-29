package com.example.younotif;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddNotif extends Fragment implements OnClickListener{
	private Button btn;
	private View v;
	public Notifications notifications;
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        // Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.add_notif_layout, container, false);
		v = view;
		   btn = (Button) view.findViewById(R.id.btnAdd);
		   
		   btn.setOnClickListener(this); 
		   
        return view;
    }
	
	public void onClick(View vo) {
		String group = ((Spinner)v.findViewById(R.id.spGroup)).getSelectedItem().toString();;
		String title = ((EditText) v.findViewById(R.id.txtTitle)).getText().toString();
		String type = ((Spinner)v.findViewById(R.id.spType)).getSelectedItem().toString();
		String beginD =((EditText) v.findViewById(R.id.txtBeginDate)).getText().toString();
		String endD = ((EditText)v.findViewById(R.id.txtEndDate)).getText().toString();
		String day = ((Spinner)v.findViewById(R.id.spDay)).getSelectedItem().toString();
		String content = ((EditText)v.findViewById(R.id.txtContent)).getText().toString();
		Notification toAdd = new Notification(group,title,beginD, endD, day, content);
		
		toAdd.setType(type);
		toAdd.createItemMap();
        notifications.addNotification(toAdd);
		
    }
}
