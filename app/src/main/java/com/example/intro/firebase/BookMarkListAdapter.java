package com.example.intro.firebase;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.intro.R;

import java.util.ArrayList;

public class BookMarkListAdapter extends RecyclerView.Adapter<BookMarkListAdapter.CustomViewHolder> {

    private ArrayList<TotalRecipe> arrayList;
    private FragmentActivity context;//이부분 이상함 oncreate에서 this면 context로 넘어가야하는데 context가 아닌 MainActivity반환

    public BookMarkListAdapter(ArrayList<TotalRecipe> arrayList, FragmentActivity context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_recipe,parent,false);
        CustomViewHolder holder = new CustomViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        Glide.with(holder.itemView)
                .load(arrayList.get(position).getImage())
                .override(40,40)
                .into(holder.iv_Image);
        holder.tv_Context.setText(arrayList.get(position).getTitle());
        holder.tv_Ingredient.setText(arrayList.get(position).getIngredient());
        holder.tv_Context.setTextSize(20);
        holder.tv_Context.setTextColor(Color.BLACK);
    }

    @Override
    public int getItemCount() {
        return (arrayList!=null ? arrayList.size():0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_Image;
        TextView tv_Context;
        TextView tv_Ingredient;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.iv_Image=itemView.findViewById(R.id.iv_image);
            this.tv_Context=itemView.findViewById(R.id.Context);
            this.tv_Ingredient=itemView.findViewById(R.id.Ingredient);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition() ;
                    if (pos != RecyclerView.NO_POSITION) {
                        Intent intent = new Intent(context, DetailList.class);

                        intent.putExtra("Title",arrayList.get(pos).getTitle());

                        context.startActivity(intent);
                        // TODO : use item.
                    }
                }
            });
        }
    }
}