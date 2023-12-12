package com.example.vinifyveneto.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.example.vinifyveneto.R;

import java.util.List;

public class CardViewAdapter extends ArrayAdapter<CardView> {

    public CardViewAdapter(@NonNull Context context, @NonNull List<CardView> cardViews){
        super(context, 0, cardViews);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        CardView cardView = getItem(position);

        if(convertView == null){
            convertView = cardView;
        }

        return convertView;
    }
}
