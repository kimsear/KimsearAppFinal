package com.example.kimsearappfinal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FavoriteFragment extends Fragment {

    private RecyclerView recyclerView;
    private ProductByCategory  adapter;
    private List<MyDataModel> categoryList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        // Initialize data lists
        initializeData();

        // Set up adapters
        setupAdapters();

        return view;
    }

    private void initializeData() {
        categoryList = new ArrayList<>();

        // Sample data for RecyclerView
        List<Integer> imageIds1 = new ArrayList<>();
        imageIds1.add(R.drawable.nike_mercurial_superfly);
        imageIds1.add(R.drawable.nike_mercurial_superfly);
        categoryList.add(new MyDataModel("Nike Mercurial", "Built for speed, traction and stability on natural grass fields", imageIds1, 33.33F));

        List<Integer> imageIds2 = new ArrayList<>();
        imageIds2.add(R.drawable.futur_ultimate);
        categoryList.add(new MyDataModel("Future Ultimate", "Built for speed, traction and stability on natural grass fields", imageIds2, 33.33F));

        List<Integer> imageIds3 = new ArrayList<>();
        imageIds3.add(R.drawable.adidas_predator_elite);
        categoryList.add(new MyDataModel("Predator Elite", "Built for speed, traction and stability on natural grass fields", imageIds3, 33.33F));

        List<Integer> imageIds4 = new ArrayList<>();
        imageIds4.add(R.drawable.future_ultimatenew);
        categoryList.add(new MyDataModel("FutureNew", "Built for speed, traction and stability on natural grass fields ", imageIds4, 33.33F));

        List<Integer> imageIds5 = new ArrayList<>();
        imageIds5.add(R.drawable.nike_superfly9elite_mercurial_dream_speed);
        categoryList.add(new MyDataModel("SuperflyElite", "Built for speed, traction and stability on natural grass fields", imageIds5, 33.33F));
    }

    private void setupAdapters() {
        adapter = new ProductByCategory(getContext(), categoryList);
        recyclerView.setAdapter(adapter);

        // Handle item click
        adapter.setOnItemClickListener(position -> {
            MyDataModel dataModel = categoryList.get(position);
            openProductDetail(dataModel);
        });
    }

    private void openProductDetail(MyDataModel dataModel) {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        ProductDetailFragment fragment = new ProductDetailFragment();

        // Pass data to ProductDetailFragment
        Bundle bundle = new Bundle();
        bundle.putString("title", dataModel.getTitle());
        bundle.putString("description", dataModel.getDescription());
        bundle.putFloat("price", dataModel.getPrice());
        bundle.putIntegerArrayList("imageList", new ArrayList<>(dataModel.getImageResIds()));
        fragment.setArguments(bundle);

        // Replace fragment and add to back stack
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}