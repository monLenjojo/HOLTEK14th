package com.example.show.chapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.show.R;

import java.util.ArrayList;

public class ChapterAdapter extends RecyclerView.Adapter<ChapterAdapter.ChapterHolder> {

    Context context;
    ArrayList<ChapterJavaBean> arrayList;

    public ChapterAdapter(Context context, ArrayList arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ChapterHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1,viewGroup,false);
        return new ChapterHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChapterHolder chapterHolder, int i) {
        chapterHolder.chapter.setText(arrayList.get(i).name);
        chapterHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ChapterHolder extends RecyclerView.ViewHolder {
        TextView chapter;
        public ChapterHolder(@NonNull View itemView) {
            super(itemView);
            chapter = itemView.findViewById(android.R.id.text1);
        }
    }
}
