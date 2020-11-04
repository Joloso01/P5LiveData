package com.example.p5livedata;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.p5livedata.databinding.FragmentFormacionesBinding;


public class FormacionesFragment extends Fragment {
    private FragmentFormacionesBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return (binding = FragmentFormacionesBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FormacionesViewModel formacionesViewModel= new ViewModelProvider(this).get(FormacionesViewModel.class);

        formacionesViewModel.obtenerFormacion().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer formacion) {
                Glide.with(FormacionesFragment.this).load(formacion).into(binding.formacion);
            }
        });

        formacionesViewModel.obtenerRepeticion().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String repeticion) {
                if(repeticion.equals("CAMBIO")){
                    binding.cambio.setVisibility(View.VISIBLE);
                }else {
                    binding.cambio.setVisibility(View.GONE);
                }
                binding.repeticion.setText(repeticion);
            }
        });
    }
}