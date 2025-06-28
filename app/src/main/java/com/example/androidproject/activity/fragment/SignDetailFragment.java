package com.example.androidproject.activity.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.androidproject.R;
import com.example.androidproject.viewmodel.SignDetailViewModel;

public class SignDetailFragment extends Fragment {

    private SignDetailViewModel viewModel;
    private ImageView ivSignImage;
    private TextView tvSignCode;
    private TextView tvSignName;
    private TextView tvSignDescription;
    private int signId;

    public SignDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            signId = SignDetailFragmentArgs.fromBundle(getArguments()).getSignId();
        }

        ivSignImage = view.findViewById(R.id.ivSignImage);
        tvSignCode = view.findViewById(R.id.tvSignCode);
        tvSignName = view.findViewById(R.id.tvSignName);
        tvSignDescription = view.findViewById(R.id.tvSignDescription);

        viewModel = new ViewModelProvider(this).get(SignDetailViewModel.class);
        viewModel.sign.observe(getViewLifecycleOwner(), sign -> {
            if (sign != null) {
                tvSignCode.setText(sign.getCode());
                tvSignName.setText(sign.getName());
                tvSignDescription.setText(sign.getDescription());

                int imageResId = getContext().getResources().getIdentifier(
                        sign.getImagePath(), "drawable", getContext().getPackageName());
                if (imageResId != 0) {
                    Glide.with(this)
                            .load(imageResId)
                            .into(ivSignImage);
                } else {
                    Glide.with(this)
                            .load(R.drawable.ic_launcher_foreground) // Placeholder image
                            .into(ivSignImage);
                }
            }
        });

        viewModel.loadTrafficSign(signId);
    }
}


