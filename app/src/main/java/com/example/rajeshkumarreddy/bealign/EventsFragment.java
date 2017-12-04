package com.example.rajeshkumarreddy.bealign;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.ClipboardManager;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import es.dmoral.toasty.Toasty;


public class EventsFragment extends Fragment {

    ShimmerFrameLayout shimmerFrameLayout;
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    AVLoadingIndicatorView avLoadingIndicatorView;
    TextView fetching;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_events,container,false);

        avLoadingIndicatorView=(AVLoadingIndicatorView)v.findViewById(R.id.eventsAVI);
        fetching=(TextView)v.findViewById(R.id.eventsFetching);
        avLoadingIndicatorView.show();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Events");
        recyclerView=(RecyclerView)v.findViewById(R.id.eventRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity(),LinearLayoutManager.HORIZONTAL,false));

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Event,EventHolder> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Event,EventHolder>(
                Event.class,
                R.layout.event_row,
                EventHolder.class,
                databaseReference
        ){
            @Override
            protected void populateViewHolder(EventHolder viewHolder, Event model, int position) {
                viewHolder.setTitle(model.getTitle());
                viewHolder.setDesc(model.getDesc());
                viewHolder.setDate(model.getDate());
                viewHolder.setLink(getActivity(),model.getLink());

                avLoadingIndicatorView.hide();
                fetching.setVisibility(View.GONE);
            }
        };

        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    public static class EventHolder extends RecyclerView.ViewHolder{
        View mView;

        public EventHolder(View itemView) {
            super(itemView);
            mView=itemView;
        }

        public void setTitle(String title){
            TextView eventTitle=(TextView)mView.findViewById(R.id.eventTitle);
            eventTitle.setText(title);
        }

        public void setDesc(String desc){
            TextView eventDesc=(TextView)mView.findViewById(R.id.eventDesc);
            eventDesc.setText(desc);
        }

        public void setDate(String date){
            TextView eventDate=(TextView)mView.findViewById(R.id.eventDate);
            eventDate.setText("Date : "+date);
        }

        public void setLink(final Context ctx, final String link){
            Button eventLink=(Button) mView.findViewById(R.id.eventLink);
            eventLink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        URL url=new URL(link);
                        ctx.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url.toString())));
                    } catch (MalformedURLException e) {

                        final AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                        builder.setTitle("Invalid URL!");
                        builder.setMessage("The URL link provided by staff was invalid. If you still want to proceed with the link, tap COPY button below to copy the url into your clipboard, so that you can paste it in your browser.");
                        builder.setPositiveButton("Copy", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ClipboardManager clipboardManager=(ClipboardManager)ctx.getSystemService(Context.CLIPBOARD_SERVICE);
                                ClipData clip = ClipData.newPlainText("Event URL",link );
                                clipboardManager.setPrimaryClip(clip);
                                Toasty.success(ctx,"Link copied to clipboard",Toast.LENGTH_LONG,true).show();
                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });

                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }

                }
            });
            eventLink.setText("Learn More");
        }
    }
}
