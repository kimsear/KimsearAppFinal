package com.example.kimsearappfinal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private Context context;
    private List<CategoryItem> categoryItemList;
    private OnItemClickListener onItemClickListener;

    public CategoryAdapter(Context context, List<CategoryItem> categoryItemList) {
        this.context = context;
        this.categoryItemList = categoryItemList;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_by_category, parent, false);
        return new CategoryViewHolder(view, onItemClickListener);
    }


    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        if (categoryItemList != null && position < categoryItemList.size()) {
            CategoryItem data = categoryItemList.get(position);
            // Set data to views directly here
            holder.categoryTitle.setText(data.getCategoryTitle()); // Assuming getTitle() is a method in your CategoryItem class
            holder.categoryImage.setImageResource(data.getImageResource());
            // Set other data as needed
        }
    }

    @Override
    public int getItemCount() {
        return categoryItemList != null ? categoryItemList.size() : 0;
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        ImageView categoryImage;
        TextView categoryTitle;
        CardView cardView;
        OnItemClickListener onItemClickListener;

        public CategoryViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            categoryImage = itemView.findViewById(R.id.card_background_image);
            categoryTitle = itemView.findViewById(R.id.card_category_title);
            cardView = itemView.findViewById(R.id.cardView);
            onItemClickListener = listener;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && onItemClickListener != null) {
                        onItemClickListener.onItemClick(position);
                    }
                }
            });
        }
    }

}

