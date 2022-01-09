package com.pum.simpleweatherapp;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CityLabAdapter extends RecyclerView.Adapter<CityLabAdapter.CityViewHolder> {

    private CityLab mCityLab;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    public CityLabAdapter(Context context)
    {
        this.mInflater = LayoutInflater.from(context);
        this.mCityLab = CityLab.get(context);
    }

    public class CityViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView tvCity;
        public CityLabAdapter mAdapter;

        public CityViewHolder(@NonNull View itemView, CityLabAdapter adapter)
        {
            super(itemView);
            this.tvCity = itemView.findViewById(R.id.tvCity);
            itemView.setOnClickListener(this);
            this.mAdapter = adapter;
        }

        @Override
        public void onClick(View view)
        {
            if (mClickListener != null)
            {
                mClickListener.onItemClick(view, getAdapterPosition());
            }
        }
    }

    @NonNull
    @Override
    public CityLabAdapter.CityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = mInflater.inflate(R.layout.recycleview_row, parent, false);
        return new CityViewHolder(view, this);
    }

    @NonNull
    @Override
    public void onBindViewHolder(@NonNull CityLabAdapter.CityViewHolder viewHolder, int position)
    {
        City current = mCityLab.getCities().get(position);
        viewHolder.tvCity.setText(current.getCityName());
    }

    @Override
    public int getItemCount()
    {
        return mCityLab.getCities().size();
    }

    public City getItem(int id)
    {
        return mCityLab.getCities().get(id);
    }

    public void setClickListener(ItemClickListener itemClickListener)
    {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener
    {
        void onItemClick(View view, int position);
    }
}
