package com.example.younotif;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class NotifList extends ListFragment{
	private ArrayList<HashMap<String, String>> listItems = new ArrayList<HashMap<String, String>>();
	private HashMap<String, String> itemMap = new HashMap<String, String>();
	private SimpleAdapter adapter;
	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        
        itemMap.put("titre", "Nouvelle notification:");
		itemMap.put("description", "Arduino");
		itemMap.put("img", String.valueOf(R.drawable.ic_action_forward));

		
		listItems.add(itemMap);
		itemMap.put("titre", "Nouvelle notification:");
		itemMap.put("description", "Arduino!");
		itemMap.put("img", String.valueOf(R.drawable.ic_action_forward));
		listItems.add(itemMap);
		adapter = new SimpleAdapter(this.getActivity(), listItems,
				R.layout.list_item_layout, new String[] { "titre","description","img",
						 }, new int[] {  R.id.titre,
						R.id.description,R.id.img });
		setListAdapter(adapter);
		
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
    	
    }

	public ArrayList<HashMap<String, String>> getListItems() {
		return listItems;
	}

	public void setListItems(ArrayList<HashMap<String, String>> listItems) {
		this.listItems = listItems;
	}

	public SimpleAdapter getAdapter() {
		return adapter;
	}

	public void setAdapter(SimpleAdapter adapter) {
		this.adapter = adapter;
	}
    
}
