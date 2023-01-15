package uac.imsp.clockingapp.View.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import uac.imsp.clockingapp.Controller.util.ServiceResuslt;
import uac.imsp.clockingapp.R;

public class ServiceListViewAdapter extends ArrayAdapter<ServiceResuslt> {


	public ServiceListViewAdapter(Context context, ArrayList<ServiceResuslt> listData) {

		super(context,R.layout.service_result_item_layout,listData);
	}
	public static class ViewHolder {
		TextView idView;
	 	TextView nameView;

	}
	//public EditText name;
	/*public EditText getServiceNameEditText(int position, View convertView,
										   ViewGroup parent){

		getView(position,convertView,parent);
		return name;
	}*/



	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;
		ServiceResuslt result = getItem(position);
		if (convertView == null) {
			holder = new ViewHolder();
			LayoutInflater layoutInflater = LayoutInflater.from(getContext());
			convertView = layoutInflater.inflate(R.layout.service_result_item_layout,null);


			holder.idView = convertView.findViewById(R.id.service_id);
			holder.nameView = convertView.findViewById(R.id.service_name);


			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.idView.setText(String.valueOf(result.getId()));
		holder.nameView.setText(result.getName());
		//name=holder.nameView;

		return convertView;

	}


}