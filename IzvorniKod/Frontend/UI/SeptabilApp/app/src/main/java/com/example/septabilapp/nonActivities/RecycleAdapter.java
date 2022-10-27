package com.example.septabilapp.nonActivities;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.septabilapp.R;

import java.util.ArrayList;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.MyViewHolder> {

    private ArrayList<Document> documentList;
    private OnDocumentListener mOnDocumentListener;
    public ArrayList<MyViewHolder> myViewHolders = new ArrayList<>();


    public RecycleAdapter(ArrayList<Document> documentList, OnDocumentListener mOnDocumentListener){
        this.documentList=documentList;
        this.mOnDocumentListener=mOnDocumentListener;
    }

    public MyViewHolder getView(int p) {
        return myViewHolders.get(p);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private TextView documentIDText;
        private TextView CheckIfsignedText;
        OnDocumentListener onDocumentListener;
        View view;

        public MyViewHolder( View view, OnDocumentListener onDocumentListener){
            super(view);
            documentIDText=view.findViewById(R.id.documentIDTextView);
            CheckIfsignedText=view.findViewById(R.id.CheckIfsignedTextView);
            this.onDocumentListener = onDocumentListener;

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }



        @Override
        public void onClick(View v) {
            System.out.println("On click Run");
            onDocumentListener.onDocumentClick((getAdapterPosition()));

            if(v.getBackground()==null){
                v.setBackgroundColor(Color.GREEN);
            }
            else if(((ColorDrawable)v.getBackground()).getColor()==-1){
                v.setBackgroundColor(Color.GREEN);
            }

            else{
                v.setBackgroundColor(Color.WHITE);
                System.out.println(((ColorDrawable)v.getBackground()).getColor());
            }


        }

        @Override
        public boolean onLongClick(View v) {
            onDocumentListener.onDocumentLongClick((getAdapterPosition()));
            System.out.println("Long click detected in RecycleViewer");

            return true;
        }
    }

    @NonNull
    @Override
    public RecycleAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_items,parent,false);
        return new MyViewHolder(itemView, mOnDocumentListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleAdapter.MyViewHolder holder, int position) {
        String documentID = documentList.get(position).getID();
        Boolean isItSigned = documentList.get(position).getSigned();
        holder.documentIDText.setText(documentID);

        if(isItSigned==Boolean.TRUE){
            holder.CheckIfsignedText.setText("Signed");
        }

        myViewHolders.add(position,holder);
    }

    @Override
    public int getItemCount() {
        return documentList.size();
    }


    public  interface OnDocumentListener{
        void onDocumentClick(int position);
        boolean onDocumentLongClick(int position);
    }


}
