package com.example.intro.database;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.intro.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RecipeList extends AppCompatActivity {
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<TotalRecipe> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipelist);

        //상태바 제거
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //액션바 사라지게 하기
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Intent intent = getIntent();
        String searchtag = intent.getStringExtra("taglist");
        System.out.println(searchtag);


        database= FirebaseDatabase.getInstance();//파이어베이스 데이터베이스 연동
        recyclerView=findViewById(R.id.RecipeListView);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>();

        database= FirebaseDatabase.getInstance();
        databaseReference=database.getReference("data");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot Snapshot) {
                arrayList.clear();
                for(DataSnapshot snapshot : Snapshot.getChildren())
                {
                    TotalRecipe totalRecipe=snapshot.getValue(TotalRecipe.class);
                    if (totalRecipe.getTitle().contains(searchtag)) {
                        arrayList.add(totalRecipe);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("MainActivity", String.valueOf(error.toException()));

            }
        });
        adapter = new RecipeListAdapter(arrayList,this);
        recyclerView.setAdapter(adapter);
    }
}
