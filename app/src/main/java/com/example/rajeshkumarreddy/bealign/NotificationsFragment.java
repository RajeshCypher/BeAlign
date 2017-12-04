package com.example.rajeshkumarreddy.bealign;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.wang.avi.AVLoadingIndicatorView;


public class NotificationsFragment extends Fragment {

    RecyclerView recyclerView;
    DatabaseReference databaseReference;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_notifications,container,false);

        databaseReference= FirebaseDatabase.getInstance().getReference().child("Notifications");
        recyclerView=(RecyclerView)v.findViewById(R.id.notifiRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Notifications,NotificationHolder> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Notifications,NotificationHolder>(
                Notifications.class,
                R.layout.notification_row,
                NotificationHolder.class,
                databaseReference
        ){
            @Override
            protected void populateViewHolder(NotificationHolder viewHolder, Notifications model, int position) {
                viewHolder.setTitle(model.getTitle());
                viewHolder.setDesc(model.getDesc());
            }
        };

        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    public static class NotificationHolder extends RecyclerView.ViewHolder{
        View mView;

        public NotificationHolder(View itemView) {
            super(itemView);
            mView=itemView;
        }

        public void setTitle(String title){
            TextView feedTitle=(TextView)mView.findViewById(R.id.notifiTitle);
            feedTitle.setText(title);
        }

        public void setDesc(String desc){
            TextView feedDesc=(TextView)mView.findViewById(R.id.notifiDesc);
            feedDesc.setText(desc);
        }
    }
}
