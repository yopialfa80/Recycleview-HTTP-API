package com.github.recycleviewhttp;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.recycleviewhttp.CRUD.delete.HTTP_delete;
import com.github.recycleviewhttp.CRUD.edit.Edit;
import com.github.recycleviewhttp.CRUD.edit.HTTP_edit;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<User> contactList;
    private ArrayList<User> contactListFiltered;
    public Activity activity;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView userName, userStats;
        public ImageView userPicture, edit, delete;

        public MyViewHolder(View view) {
            super(view);
            edit = view.findViewById(R.id.edit);
            delete = view.findViewById(R.id.delete);
            userPicture = view.findViewById(R.id.userPicture);
            userName = view.findViewById(R.id.userName);
            userStats = view.findViewById(R.id.userStats);
        }
    }

    public RecycleAdapter(Activity activity, Context context, ArrayList<User> contactList) {
        this.context = context;
        this.contactList = contactList;
        this.contactListFiltered = contactList;
        this.activity = activity;
    }

    @Override
    public RecycleAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user, parent, false);

        return new RecycleAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecycleAdapter.MyViewHolder holder, final int position) {
        final User contact = contactListFiltered.get(position);

        Glide.with(context)
                .load(contact.getPicture())
                .into(holder.userPicture);

        holder.userName.setText(contact.getName());
        holder.userStats.setText(contact.getStats());

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Edit.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("id", contact.getId());
                intent.putExtra("name", contact.getName());
                intent.putExtra("stats", contact.getStats());
                intent.putExtra("picture", contact.getPicture());
                context.startActivity(intent);
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = contact.getId();
                HTTP_delete http_delete = new HTTP_delete(activity);
                http_delete.execute(id);
            }
        });
    }

    @Override
    public int getItemCount() {
        return contactListFiltered.size();
    }

    public interface ContactsAdapterListener {
        void onContactSelected(User contact);
    }

    public interface CallbackInterface{

        void onHandleSelection(int position, String text);
    }


}