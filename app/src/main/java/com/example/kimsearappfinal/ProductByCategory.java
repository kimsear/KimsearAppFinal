package com.example.kimsearappfinal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProductByCategory extends RecyclerView.Adapter<ProductByCategory.ViewHolder> {

    private List<MyDataModel> categoryList;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public ProductByCategory(Context context, List<MyDataModel> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MyDataModel item = categoryList.get(position);
        holder.bind(item, onItemClickListener, context);
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView titleTextView;
        TextView descriptionTextView;
        TextView productPrice;
        private ImageView addCart;
        private ImageView favoriteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.card_image);
            titleTextView = itemView.findViewById(R.id.card_title);
            descriptionTextView = itemView.findViewById(R.id.card_description);
            productPrice = itemView.findViewById(R.id.productPriceTextView);
            addCart = itemView.findViewById(R.id.addCartButton);
            favoriteButton = itemView.findViewById(R.id.favoriteButton);
        }

        public void bind(MyDataModel dataModel, final OnItemClickListener listener, final Context context) {
            if (!dataModel.getImageResIds().isEmpty()) {
                imageView.setImageResource(dataModel.getImageResIds().get(0)); // Display the first image
            } else {
                // Handle the case where there are no images
                // You might want to set a placeholder image or hide the ImageView
                imageView.setImageResource(R.drawable.nike_superfly9elite_mercurial_dream_speed); // Assume there's a placeholder image
            }

            titleTextView.setText(dataModel.getTitle());
            descriptionTextView.setText(dataModel.getDescription());
            productPrice.setText(String.format("$%.2f", dataModel.getPrice()));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });

            favoriteButton.setOnClickListener(v -> {
                boolean isSelected = v.isSelected();
                v.setSelected(!isSelected);
                ((ImageView) v).setColorFilter(isSelected ? Color.BLACK : Color.RED);
            });

            addCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isUserLoggedIn(context)) {
                        addToCart(dataModel);
                    } else {
                        navigateToLogin(context);
                    }
                }
            });
        }

        private boolean isUserLoggedIn(Context context) {
            SharedPreferences sharedPref = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
            return sharedPref.getBoolean("logged_in", false);
        }

        private void navigateToLogin(Context context) {
            Intent intent = new Intent(context, LoginActivity.class);
            context.startActivity(intent);
        }

        private void addToCart(MyDataModel dataModel) {
            // Implement the logic to add the item to the cart
            // For example, you can store the item information in a database or shared preferences
            // This is a placeholder for your cart-adding logic
        }
    }
}
