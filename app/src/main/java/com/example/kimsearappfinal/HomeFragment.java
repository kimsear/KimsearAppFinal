package com.example.kimsearappfinal;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView recyclerView2;
    private MyAdapter adapter;
    private ProductByCategory adapter2;
    private List<MyDataModel> dataList;
    private List<MyDataModel> categoryList;
    private ViewPager2 viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private Handler handler;
    private Runnable runnable;
    private int currentPage = 0;

    private ImageView moreButton;
    private ImageView moreButton2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize ViewPager2 and TabLayout
        viewPager = view.findViewById(R.id.viewPager);
        TabLayout tabLayout = view.findViewById(R.id.tabLayout);

        List<Integer> imageList = initializeImageList();
        viewPagerAdapter = new ViewPagerAdapter(getContext(), imageList);
        viewPager.setAdapter(viewPagerAdapter);

        // Attach TabLayout to ViewPager2
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {}).attach();

        // Set up auto-slide for ViewPager2
        setupAutoSlide();

        // Initialize RecyclerViews
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView2 = view.findViewById(R.id.recyclerView2);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        // Initialize data lists
        initializeData();

        // Set up adapters
        setupAdapters();

        // Initialize and set OnClickListener for "See More" buttons
        moreButton = view.findViewById(R.id.moreButton);
        moreButton2 = view.findViewById(R.id.moreButton2);

        moreButton.setOnClickListener(v -> {
            // Replace the current fragment with the LatestFragment
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new LatestFragment())
                    .addToBackStack(null)
                    .commit();
        });

        moreButton2.setOnClickListener(v -> {
            // Replace the current fragment with the PopularFragment
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new PopularFragment())
                    .addToBackStack(null)
                    .commit();
        });

        return view;
    }

    private List<Integer> initializeImageList() {
        List<Integer> imageList = new ArrayList<>();
        imageList.add(R.drawable.slide1);
        imageList.add(R.drawable.slide2);
        imageList.add(R.drawable.slide3);
        return imageList;
    }

    private void setupAutoSlide() {
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                if (currentPage == viewPagerAdapter.getItemCount()) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
                handler.postDelayed(this, 3000); // Change slide every 3 seconds
            }
        };
        handler.postDelayed(runnable, 3000);
    }

    private void initializeData() {
        // Sample data for first RecyclerView
        dataList = new ArrayList<>();
        List<Integer> imageIds1 = new ArrayList<>();
        imageIds1.add(R.drawable.nike_mercurial_superfly);
        imageIds1.add(R.drawable.nike_mercurial_superfly);
        dataList.add(new MyDataModel("Nike Mercurial", "Built for speed, traction and stability on natural grass fields", imageIds1, 33.33F));

        List<Integer> imageIds2 = new ArrayList<>();
        imageIds2.add(R.drawable.adidas_predator_elite);
        dataList.add(new MyDataModel("Predator-Elite", "Built for speed, traction and stability on natural grass fields", imageIds2, 33.33F));

        List<Integer> imageIds3 = new ArrayList<>();
        imageIds3.add(R.drawable.futur_ultimate);
        dataList.add(new MyDataModel("Future Ultimate", "Built for speed, traction and stability on natural grass fields", imageIds3, 33.33F));

        List<Integer> imageIds4 = new ArrayList<>();
        imageIds4.add(R.drawable.nike_superfly9elite_mercurial_dream_speed);
        dataList.add(new MyDataModel("SuperflyElite", "Built for speed, traction and stability on natural grass fields", imageIds4, 33.33F));

        List<Integer> imageIds5 = new ArrayList<>();
        imageIds5.add(R.drawable.future_ultimatenew);
        dataList.add(new MyDataModel("FutureNew", "Built for speed, traction and stability on natural grass fields", imageIds5, 33.33F));

        // Sample data for second RecyclerView
        categoryList = new ArrayList<>();
        List<Integer> imageIdsCategory1 = new ArrayList<>();
        imageIdsCategory1.add(R.drawable.nike_mercurial_superfly);
        categoryList.add(new MyDataModel("Nike Mercurial", "Built for speed, traction and stability on natural grass fields", imageIdsCategory1, 33.33F));

        List<Integer> imageIdsCategory2 = new ArrayList<>();
        imageIdsCategory2.add(R.drawable.adidas_predator_elite);
        categoryList.add(new MyDataModel("Predator", "Built for speed, traction and stability on natural grass fields", imageIdsCategory2, 33.33F));

        List<Integer> imageIdsCategory3 = new ArrayList<>();
        imageIdsCategory3.add(R.drawable.futur_ultimate);
        categoryList.add(new MyDataModel("Future Ultimate", "Built for speed, traction and stability on natural grass fields", imageIdsCategory3, 33.33F));

        List<Integer> imageIdsCategory4 = new ArrayList<>();
        imageIdsCategory4.add(R.drawable.nike_superfly9elite_mercurial_dream_speed);
        categoryList.add(new MyDataModel("SuperflyElite", "Built for speed, traction and stability on natural grass fields", imageIdsCategory4, 33.33F));

    }

    private void setupAdapters() {
        adapter = new MyAdapter(getContext(), dataList);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(position -> {
            MyDataModel dataModel = dataList.get(position);
            openProductDetail(dataModel);
        });

        adapter2 = new ProductByCategory(getContext(), categoryList);  // Pass context here
        recyclerView2.setAdapter(adapter2);
        adapter2.setOnItemClickListener(position -> {
            MyDataModel dataModel = categoryList.get(position);
            openProductDetail(dataModel);
        });
    }

    private void openProductDetail(MyDataModel dataModel) {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        ProductDetailFragment fragment = new ProductDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", dataModel.getTitle());
        bundle.putString("description", dataModel.getDescription());
        bundle.putFloat("price", dataModel.getPrice());
        bundle.putIntegerArrayList("imageList", new ArrayList<>(dataModel.getImageResIds()));
        fragment.setArguments(bundle);
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        handler.removeCallbacks(runnable); // Stop the auto-slide when the view is destroyed
    }
}
