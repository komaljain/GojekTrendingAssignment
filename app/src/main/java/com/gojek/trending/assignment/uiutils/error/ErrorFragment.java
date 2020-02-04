package com.gojek.trending.assignment.uiutils.error;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.gojek.trending.assignment.BR;
import com.gojek.trending.assignment.R;
import com.gojek.trending.assignment.application.TrendingApplication;
import com.gojek.trending.assignment.databinding.ErrorBinding;

import javax.inject.Inject;

public class ErrorFragment extends Fragment {

    @Inject
    ErrorPresenterImpl errorPresenter;

    private ErrorBinding binding;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        ((TrendingApplication) getActivity().getApplication()).getAppComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.error, container, false);
        binding.setVariable(BR.presenter, errorPresenter);
        View view = binding.getRoot();
        return view;
    }
}
