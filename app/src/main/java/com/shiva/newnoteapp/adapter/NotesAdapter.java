package com.shiva.newnoteapp.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.shiva.newnoteapp.R;
import com.shiva.newnoteapp.activities.MainActivity;
import com.shiva.newnoteapp.activities.NoteDetails;
import com.shiva.newnoteapp.database.NotesDB;
import com.shiva.newnoteapp.entities.Notes;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.Viewholder> {

    List<Notes> allnotes;
    ExecutorService executorService;


    public NotesAdapter(List<Notes> allnotes) {
        this.allnotes = allnotes;
        executorService = Executors.newSingleThreadExecutor();
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.notesitems , parent , false);
        return new Viewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

        holder.title.setText(allnotes.get(position).getNoteTitle());
        holder.body.setText(allnotes.get(position).getNoteBody());
        holder.CreatedDate.setText(allnotes.get(position).getCreatedTime());
        holder.del_btn.setOnClickListener(v ->{
            executorService.execute(() -> {
                NotesDB.getDB(holder.itemView.getContext()).notesDao().DeleteNote(allnotes.get(position).getKey());
                allnotes.remove(position);
                ((Activity) holder.itemView.getContext()).runOnUiThread(() -> {
                    notifyDataSetChanged();
                    ((MainActivity) holder.itemView.getContext()).onItemDeleted();
                });
            });
        });

        holder.listitem.setOnClickListener(v ->{

            Intent intent = new Intent(holder.body.getContext() , NoteDetails.class);
            intent.putExtra("ITEM_LIST", (Serializable) allnotes);
            intent.putExtra("ITEM_KEY", allnotes.get(position).getKey());
            intent.putExtra("ITEM_POSITION", position);
            holder.itemView.getContext().startActivity(intent);
            ((MainActivity) holder.itemView.getContext()).finish();

        });
    }

    @Override
    public int getItemCount() {
        return allnotes.size();
    }



   public static class Viewholder extends RecyclerView.ViewHolder{

        TextView title , body , CreatedDate;
        ImageButton del_btn;
        CardView listitem;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.Titlehead);
            body = itemView.findViewById(R.id.Titlebody);
            CreatedDate = itemView.findViewById(R.id.Createdtime);
            del_btn = itemView.findViewById(R.id.deletbtn);
            listitem = itemView.findViewById(R.id.item);

        }
    }
}
