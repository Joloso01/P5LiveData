package com.example.p5livedata;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

public class FormacionesViewModel extends AndroidViewModel {
    Formaciones formaciones;
    LiveData<Integer> formacionesLiveData;
    LiveData<String> repeticionLiveData;

    public FormacionesViewModel(@NonNull Application application) {
        super(application);

        formaciones = new Formaciones();

        formacionesLiveData = Transformations.switchMap(formaciones.ordenLiveData, new Function<String, LiveData<Integer>>() {

            String formacionAnterior;

            @Override
            public LiveData<Integer> apply(String orden) {
                String formacion = orden.split(":")[0];

                if (!formacion.equals(formacionAnterior)){
                    formacionAnterior = formacion;
                    int imagen;
                    switch (formacion){
                        case "Formacion1":
                        default:
                            imagen = R.drawable.formacion1;
                            break;
                        case "Formacion2":
                            imagen = R.drawable.formacion2;
                            break;
                        case "Formacion3":
                            imagen = R.drawable.formacion3;
                            break;
                        case "Formacion4":
                            imagen = R.drawable.formacion4;
                            break;
                    }

                    return new MutableLiveData<>(imagen);
                }
                return null;
            }
        });

        repeticionLiveData = Transformations.switchMap(formaciones.ordenLiveData, new Function<String, LiveData<String>>() {
            @Override
            public LiveData<String> apply(String orden) {
                return new MutableLiveData<>(orden.split(":")[1]);
            }
        });
    }
    LiveData<Integer> obtenerFormacion(){
        return formacionesLiveData;
    }

    LiveData<String> obtenerRepeticion(){
        return repeticionLiveData;
    }
}
