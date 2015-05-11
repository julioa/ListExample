package com.sample.listexample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by julioa on 6/05/15.
 */
public class InfoAdapter  extends ArrayAdapter<JSONObject> {
    private final LayoutInflater mInflater;

    public InfoAdapter(Context context, int id) {
        super(context, id);
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setData(List<JSONObject> data) {
        clear();
        if (data != null) {
            addAll(data);
        }
    }

    /**
     * Populate new items in the list.
     */
    @Override public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        if (convertView == null) {
            view = mInflater.inflate(R.layout.item_layout, parent, false);
        } else {
            view = convertView;
        }

        JSONObject item = getItem(position);
        String title = null;
        title = item.optString("title");
        if (title == null) {
            title =  item.optString("title");
        }
        if (title == null) {
            title = "Sin Título";
        }
        ((TextView) view.findViewById(R.id.firstLine)).setText(title);

        return view;
    }
}
