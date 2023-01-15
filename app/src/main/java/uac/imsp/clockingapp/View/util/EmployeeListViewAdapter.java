package uac.imsp.clockingapp.View.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import uac.imsp.clockingapp.Controller.util.Result;
import uac.imsp.clockingapp.R;

public class EmployeeListViewAdapter extends ArrayAdapter<Result> {


    public EmployeeListViewAdapter(Context context, ArrayList<Result> listData) {

        super(context,R.layout.result_item_layout,listData);
    }
    public static class ViewHolder {
        TextView numberView;
        TextView nameView;
        TextView serviceView;
        CircleImageView pictureView;
        TextView statusView;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        String [] status=getContext().getResources().getStringArray(R.array.current_status);
        Bitmap pictureBipmap;

        ViewHolder holder;
        Result result = getItem(position);
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            convertView = layoutInflater.inflate(R.layout.result_item_layout, null);


            holder.numberView = convertView.findViewById(R.id.search_number);
            holder.nameView = convertView.findViewById(R.id.search_name);
            holder.serviceView = convertView.findViewById(R.id.search_service);
            holder.pictureView= convertView.findViewById(R.id.search_cirle_picture);
            holder.statusView= convertView.findViewById(R.id.search_statut);




            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.numberView.setText(String.valueOf(result.getNumber()));
        holder.nameView.setText(result.getName());
        holder.serviceView.setText(result.getService());
        holder.statusView.setText(status[result.getStatus()]);
        if(result.getPicture()!=null) {
            pictureBipmap=getBitMapFromBytes(result.getPicture());
            holder.pictureView.setImageBitmap(pictureBipmap);
        }
        return convertView;

    }
    public static Bitmap getBitMapFromBytes(byte[] array){

        return BitmapFactory.decodeByteArray(array,0,array.length);

    }


}