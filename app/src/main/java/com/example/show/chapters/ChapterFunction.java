package com.example.show.chapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChapterFunction {

    private static final String TAG = "Firebase Read";
    Context context;
    RecyclerView recyclerView;
    String uid;
    ArrayList<ChapterJavaBean> arrayList = new ArrayList<>();

    public ChapterFunction(Context context, RecyclerView recyclerView, String uid) {
        this.context = context;
        this.recyclerView = recyclerView;
        this.uid = uid;
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection("userData")
//          .whereEqualTo("name", "jeff")
            .get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {
                            ChapterJavaBean data = queryDocumentSnapshot.toObject(ChapterJavaBean.class);
                            Log.d(TAG, "onComplete: "+ queryDocumentSnapshot.getId() +" ==> "+ data.name +"\t" + data.uid);
                            arrayList.add(data);
                        }
                        upData();
                    }
                }
            });
    }

    private void upData(){
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        ChapterAdapter adapter = new ChapterAdapter(context,arrayList);
        recyclerView.setAdapter(adapter);
    }
}
