package com.example.younotif;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;

public class ListItemAdapter extends SimpleAdapter {
	private List<String> colors = new ArrayList<String>(Arrays.asList(
			"#2980b9", "#e67e22", "#27ae60", "#c0392b", "#f1c40f"));

	public ListItemAdapter(Context context,
			List<? extends Map<String, ?>> data, int resource, String[] from,
			int[] to) {
		super(context, data, resource, from, to);
		// TODO Auto-generated constructor stub
		
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		
		 View itemView = null;

		    if (convertView == null) {
		        LayoutInflater inflater = (LayoutInflater) parent.getContext()
		            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		        itemView = inflater.inflate(R.layout.list_item_layout, null);
		    } else {
		        itemView = convertView;
		    }
		    if (itemView instanceof LinearLayout) {
				LinearLayout l = (LinearLayout) itemView;
				FrameLayout fchild = (FrameLayout) l.getChildAt(0);
				LinearLayout searched = (LinearLayout) fchild.getChildAt(0);

				searched.setBackgroundColor(Color.parseColor(colors.get(position
						% (colors.size()))));
			}
		    // play with itemView

		return super.getView(position, itemView, parent);

	}

}
