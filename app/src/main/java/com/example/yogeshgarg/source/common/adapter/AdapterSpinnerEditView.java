package com.example.yogeshgarg.source.common.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yogeshgarg.source.R;
import com.example.yogeshgarg.source.common.helper.FontHelper;
import com.example.yogeshgarg.source.common.requestResponse.Const;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by yogeshgarg on 27/08/17.
 */

public class AdapterSpinnerEditView extends ArrayAdapter<String> {

    ArrayList<HashMap<String, String>> list;
    Context context;

    public AdapterSpinnerEditView(Context context, int txtViewResourceId,
                                  ArrayList<HashMap<String, String>> list) {
        super(context, txtViewResourceId);
        this.context = context;
        this.list = list;

    }

    @Override
    public String getItem(int index) {
        return list.get(index).get(Const.KEY_NAME);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getDropDownView(int position, View cnvtView, ViewGroup prnt) {
        return getCustomViewDropdown(position, cnvtView, prnt);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        /*View mySpinner = inflater.inflate(R.layout.layout_spinner_dropdown, parent, false);*/
        TextView textView = (TextView) View.inflate(context, R.layout.layout_spinner_dropdown, null);

        textView.setTextColor(ContextCompat.getColor(context, R.color.color_black));

        HashMap<String, String> events = list.get(position);

        if (events.get(Const.KEY_NAME) != null)
            textView.setText(events.get(Const.KEY_NAME));


        return textView;
    }

    public View getCustomViewDropdown(int position, View convertView, ViewGroup parent) {


        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        LinearLayout linearLayout = (LinearLayout) View.inflate(context, R.layout.layout_spinner_dropdown_editview, null);

        TextView txtViewType = linearLayout.findViewById(R.id.txtViewType);
        EditText edtTextNumber = linearLayout.findViewById(R.id.edtTextNumber);
        TextView txtViewOff = linearLayout.findViewById(R.id.txtViewOff);

        FontHelper.setFontFace(txtViewType, FontHelper.FontType.FONT_Normal, context);
        FontHelper.setFontFace(txtViewOff, FontHelper.FontType.FONT_Normal, context);
        FontHelper.applyFont(context, edtTextNumber, FontHelper.FontType.FONT_Normal);

        if (position == 0) {
            txtViewType.setText("$");
        }
        if (position == 1) {
            txtViewType.setText("%");
        }


        HashMap<String, String> events = list.get(position);

        if (events.get(Const.KEY_NAME) != null)
            edtTextNumber.setText(events.get(Const.KEY_NAME));

        return edtTextNumber;
    }

    @Override
    public int getCount() {
        return list.size();
    }
}

