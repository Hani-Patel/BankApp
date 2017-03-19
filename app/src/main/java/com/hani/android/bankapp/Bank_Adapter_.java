package com.hani.android.bankapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class Bank_Adapter_ extends ArrayAdapter {
    private List<BankModel> modelList;
    private LayoutInflater inflater;
    private int resource;
    private Context context;

    public Bank_Adapter_(Context context, int resource, List<BankModel> object) {
        super(context, resource,object);
        modelList = object;

        this.resource = resource;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflater.inflate(resource, parent,false);
        }
        TextView id;
        TextView state;
        TextView bank;
        TextView ifsc;
        TextView mircCord;
        TextView branch;
        TextView contact;
        TextView city;
        TextView district;
        TextView address;

        id = (TextView) convertView.findViewById(R.id.tvId);
        state = (TextView) convertView.findViewById(R.id.tvState);
        bank = (TextView) convertView.findViewById(R.id.tvBank);
        ifsc = (TextView) convertView.findViewById(R.id.tvIfsc);
        mircCord = (TextView) convertView.findViewById(R.id.tvMicrCode);
        branch = (TextView) convertView.findViewById(R.id.tvBranch);
        contact = (TextView) convertView.findViewById(R.id.tvContact);
        city = (TextView) convertView.findViewById(R.id.tvCity);
        address = (TextView) convertView.findViewById(R.id.tvAddress);
        district = (TextView) convertView.findViewById(R.id.tvDistrict);


        id.setText(modelList.get(position).get_id());
        state.setText(modelList.get(position).getSTATE());
        bank.setText(modelList.get(position).getBANK());
        ifsc.setText(modelList.get(position).getIFSC());
        mircCord.setText(modelList.get(position).getMICRCODE());
        branch.setText(modelList.get(position).getBRANCH());
        contact.setText(modelList.get(position).getCONTACT());
        city.setText(modelList.get(position).getCITY());
        district.setText(modelList.get(position).getDISTRICT());
        address.setText(modelList.get(position).getADDRESS());

        return convertView;

    }


}


