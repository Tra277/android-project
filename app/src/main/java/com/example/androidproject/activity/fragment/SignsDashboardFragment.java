package com.example.androidproject.activity.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject.R;
import com.example.androidproject.adapter.TrafficSignCategoryAdapter;
import com.example.androidproject.model.TrafficSignCategory;
import com.example.androidproject.viewmodel.SignsDashboardViewModel;

public class SignsDashboardFragment extends Fragment implements TrafficSignCategoryAdapter.OnItemClickListener {

    private SignsDashboardViewModel viewModel;
    private RecyclerView rvSignCategories;
    private TrafficSignCategoryAdapter adapter;

    public SignsDashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signs_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvSignCategories = view.findViewById(R.id.rvSignCategories);
        rvSignCategories.setLayoutManager(new LinearLayoutManager(getContext()));

        viewModel = new ViewModelProvider(this).get(SignsDashboardViewModel.class);
        viewModel.categories.observe(getViewLifecycleOwner(), categories -> {
            adapter = new TrafficSignCategoryAdapter(categories, this);
            rvSignCategories.setAdapter(adapter);
        });

        viewModel.loadTrafficSignCategories();
    }

    @Override
    public void onItemClick(TrafficSignCategory category) {
        SignsDashboardFragmentDirections.ActionSignsDashboardFragmentToSignListFragment action =
                SignsDashboardFragmentDirections.actionSignsDashboardFragmentToSignListFragment(category.getId());
        Navigation.findNavController(requireView()).navigate(action);
    }
}


