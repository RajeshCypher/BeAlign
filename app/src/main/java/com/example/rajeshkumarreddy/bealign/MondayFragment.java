package com.example.rajeshkumarreddy.bealign;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.wang.avi.AVLoadingIndicatorView;

import me.toptas.fancyshowcase.FancyShowCaseView;
import me.toptas.fancyshowcase.OnViewInflateListener;


public class MondayFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MondayFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MondayFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MondayFragment newInstance(String param1, String param2) {
        MondayFragment fragment = new MondayFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    AVLoadingIndicatorView avLoadingIndicatorView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_monday, container, false);

        databaseReference= FirebaseDatabase.getInstance().getReference().child("cse6a").child("timetable").child("mon");
        recyclerView=(RecyclerView)v.findViewById(R.id.MonRecyclerview);
        avLoadingIndicatorView=(AVLoadingIndicatorView)v.findViewById(R.id.MonAVI);
        avLoadingIndicatorView.show();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();


        FirebaseRecyclerAdapter<Timetable,TimetableHolder> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Timetable,TimetableHolder>(
                Timetable.class,
                R.layout.timetable_row,
                MondayFragment.TimetableHolder.class,
                databaseReference
        ){
            @Override
            protected void populateViewHolder(TimetableHolder viewHolder, Timetable model, int position) {
                viewHolder.setPeriod(model.getPeriod());
                viewHolder.setTime(model.getTime());
                avLoadingIndicatorView.hide();
                if (model.getPeriod().equals("Break")||model.getPeriod().equals("Lunch")){
                    viewHolder.itemView.setBackgroundColor(Color.parseColor("#F5B4CA"));
                    TextView tableProf=(TextView)viewHolder.itemView.findViewById(R.id.TTProf);
                    TextView tableLoc=(TextView)viewHolder.itemView.findViewById(R.id.TTLoc);
                    TextView tableQuote=(TextView)viewHolder.itemView.findViewById(R.id.TTQuote);
                    tableQuote.setVisibility(View.VISIBLE);
                    tableLoc.setVisibility(View.GONE);
                    tableProf.setVisibility(View.GONE);
                    if (model.getPeriod().equals("Break"))
                        tableQuote.setText("Have Snacks!");
                    if (model.getPeriod().equals("Lunch"))
                        tableQuote.setText("Have a nice Meal!");
                }
            }
        };

        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }


    public static class TimetableHolder extends RecyclerView.ViewHolder{
        View tView;

        public TimetableHolder(View itemView) {
            super(itemView);
            tView=itemView;
        }


        public void setPeriod(String period){
            TextView tablePeriod=(TextView)tView.findViewById(R.id.TTPeriod);
            //tablePeriod.setTypeface(Typeface.createFromAsset());
            tablePeriod.setText(period);
        }

        public void setTime(String time){
            TextView tableTime=(TextView)tView.findViewById(R.id.TTTime);
            tableTime.setText(time);
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
