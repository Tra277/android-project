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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject.R;
import com.example.androidproject.adapter.TrafficSignAdapter;
import com.example.androidproject.model.TrafficSign;
import com.example.androidproject.viewmodel.SignListViewModel;

public class SignListFragment extends Fragment implements TrafficSignAdapter.OnItemClickListener {

    private SignListViewModel viewModel;
    private RecyclerView rvSignList;
    private TrafficSignAdapter adapter;
    private int categoryId;

    public SignListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            categoryId = SignListFragmentArgs.fromBundle(getArguments()).getCategoryId();
        }

        rvSignList = view.findViewById(R.id.rvSignList);
        rvSignList.setLayoutManager(new GridLayoutManager(getContext(), 2)); // 2 columns

        viewModel = new ViewModelProvider(this).get(SignListViewModel.class);
        viewModel.signs.observe(getViewLifecycleOwner(), signs -> {
            adapter = new TrafficSignAdapter(signs, this);
            rvSignList.setAdapter(adapter);
        });

        viewModel.loadTrafficSignsByCategory(categoryId);
    }

    @Override
    public void onItemClick(TrafficSign sign) {
        SignListFragmentDirections.ActionSignListFragmentToSignDetailFragment action =
                SignListFragmentDirections.actionSignListFragmentToSignDetailFragment(sign.getId());
        Navigation.findNavController(requireView()).navigate(action);
    }
}


