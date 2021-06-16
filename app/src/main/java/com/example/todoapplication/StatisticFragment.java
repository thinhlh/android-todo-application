package com.example.todoapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.todoapplication.databinding.FragmentStatisticBinding;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import org.jetbrains.annotations.NotNull;

public class StatisticFragment extends Fragment implements View.OnClickListener {
    private StatisticViewModel viewModel;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(StatisticViewModel.class);
        FragmentStatisticBinding fragmentStatisticBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_statistic, container, false);
        fragmentStatisticBinding.signOutButton.setOnClickListener(this);
        fragmentStatisticBinding.setLifecycleOwner(this.getViewLifecycleOwner());
        viewModel.getCountTodoLiveData().observe(getViewLifecycleOwner(), countTodo -> {
            fragmentStatisticBinding.numberOfTotalTodo.setText("Tổng số lượng TODO: " + countTodo.getTotalTodo());
            fragmentStatisticBinding.completedTodo.setText("Số lượng TODO đã hoàn thành: " + countTodo.getCompletedTodo());
        });
        return fragmentStatisticBinding.getRoot();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.sign_out_button) {
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
            GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(getContext(), gso);
            mGoogleSignInClient.signOut().addOnCompleteListener(task -> {
                LoginManager.getInstance().logOut();
                UserRepo.uid = null;
                startActivity(new Intent(getContext(), MainActivity.class));
            });
        }
    }
}
