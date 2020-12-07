package com.example.notepad;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;

import java.io.File;
import java.util.ArrayList;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.MyViewHolder> {
    private ArrayList<NoteItem> mDataset;
    private Context context;
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;
        public ImageView image;
        //public MyViewHolder(TextView v) {
        public MyViewHolder(View v) {
            super(v);
            //textView = v;
            textView = (TextView) v.findViewById(R.id.text_id);
            image = (ImageView) v.findViewById(R.id.image_id);
        }
    }


    public NoteAdapter(ArrayList<NoteItem> myDataset, Context context) {
        mDataset = myDataset;
        this.context = context;
    }


    @Override
    public NoteAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String title = mDataset.get(position).title;
        String styledTitle = "<big><b><font color='#333333'>"+title+"</font></b></big><br/>";
        String content = "";
        if (mDataset.get(position).content.length() > 15) {
            content = mDataset.get(position).content.substring(0,15);
        } else {
            content = mDataset.get(position).content;
        }
/*        System.out.println("br");
        System.out.println(content);*/
        String sytledContent = "<small><b><font color='#D3D3D3'>"+content+"</font></b></small>";
        String output = styledTitle + sytledContent;
        holder.textView.setText(Html.fromHtml(output));
        holder.image.setImageResource(R.drawable.ic_delete_black_24dp);

        final int passPosition = position;
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NoteItem newNote = mDataset.get(passPosition);
                Intent intent = new Intent(context, writeNote.class);
                intent.putExtra("newNote", newNote);
                context.startActivity(intent);
            }
        });
        final Context passContext = this.context;
        final NoteAdapter passNoteAdapter = this;
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NoteItem newNote = mDataset.get(passPosition);
                File file = passContext.getFileStreamPath(newNote.fileName + "Title");
                File file2 = passContext.getFileStreamPath(newNote.fileName + "Content");
                file.delete();
                file2.delete();
                mDataset.remove(passPosition);
                passNoteAdapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
