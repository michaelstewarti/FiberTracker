package py.com.codea.fibertracker.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import py.com.codea.fibertracker.R;

public class HintArrayAdapter<T> extends ArrayAdapter {

    public HintArrayAdapter(Context context, int resource, Object[] objects) {
        super(context, resource, objects);
        setDropDownViewResource(R.layout.spinner_dropdown_item);
    }

    @Override
    public boolean isEnabled(int position) {
        return position != 0;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view = super.getDropDownView(position, convertView, parent);
        TextView tv = (TextView) view;
        if (position == 0) {
            tv.setTextColor(Color.GRAY);
        } else {
            tv.setTextColor(Color.BLACK);
        }
        return view;
    }
}
