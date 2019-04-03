package com.example.l4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private List<User> mDataset;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView firstName, lastName;
        public  MyViewHolder(View view) {
            super(view);
            firstName = view.findViewById(R.id.text_view_first_name);
            lastName = view.findViewById(R.id.text_view_last_name);
        }
    }

    public MyAdapter(List<User> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context)
                .inflate(R.layout.user_text_view, parent, false);

        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        User user = mDataset.get(position);
        holder.firstName.setText(user.firstName);
        holder.lastName.setText(user.lastName);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}