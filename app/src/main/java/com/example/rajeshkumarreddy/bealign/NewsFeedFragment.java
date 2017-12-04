package com.example.rajeshkumarreddy.bealign;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.wang.avi.AVLoadingIndicatorView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import me.toptas.fancyshowcase.FancyShowCaseView;
import me.toptas.fancyshowcase.OnViewInflateListener;


public class NewsFeedFragment extends Fragment {

    ShimmerFrameLayout shimmerFrameLayout;
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    EditText search;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_news_feed,container,false);

        shimmerFrameLayout=(ShimmerFrameLayout)v.findViewById(R.id.facebookShimmer);
        shimmerFrameLayout.setTilt(0);
        shimmerFrameLayout.setDuration(1000);
        shimmerFrameLayout.startShimmerAnimation();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Feeds");
        recyclerView=(RecyclerView)v.findViewById(R.id.feedRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        search=(EditText)v.findViewById(R.id.searchFeed);

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Feed,FeedHolder> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Feed,FeedHolder>(
                Feed.class,
                R.layout.feed_row,
                FeedHolder.class,
                databaseReference
        ){
            @Override
            protected void populateViewHolder(FeedHolder viewHolder, Feed model, int position) {
                viewHolder.setTitle(model.getTitle());
                viewHolder.setDesc(model.getDesc());
                viewHolder.setImage(getActivity(),model.getImage());

                shimmerFrameLayout.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }
        };

        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    public static class FeedHolder extends RecyclerView.ViewHolder{
        View mView;

        public FeedHolder(View itemView) {
            super(itemView);

            mView=itemView;
        }

        public void setTitle(String title){
            TextView feedTitle=(TextView)mView.findViewById(R.id.feedTitle);
            feedTitle.setText(title);
        }

        public void setDesc(String desc){
            TextView feedDesc=(TextView)mView.findViewById(R.id.feedDesc);
            feedDesc.setText(desc);
        }

        public void setImage(Context ctx,String image){
            ImageView feedImage=(ImageView)mView.findViewById(R.id.feedImage);
            Picasso.with(ctx).load(image).into(feedImage, new Callback() {
                @Override
                public void onSuccess() {
                    AVLoadingIndicatorView ballClip=(AVLoadingIndicatorView)mView.findViewById(R.id.ballClip);
                    ballClip.hide();
                    ImageButton delete=(ImageButton)mView.findViewById(R.id.backgroundView);
                    delete.setVisibility(View.VISIBLE);
                }

                @Override
                public void onError() {

                }
            });
        }
    }
}
