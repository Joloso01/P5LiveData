package com.example.p5livedata;

import androidx.lifecycle.LiveData;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import static java.util.concurrent.TimeUnit.SECONDS;

public class Formaciones {

    interface FormacionesListener{
        void cuandoDeLaOrden(String orden);
    }

    Random random = new Random();
    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    ScheduledFuture<?> formando;

    LiveData<String> ordenLiveData = new LiveData<String>() {
        @Override
        protected void onActive() {
            super.onActive();

            iniciarFormaciones(new FormacionesListener() {
                @Override
                public void cuandoDeLaOrden(String orden) {
                    postValue(orden);
                }
            });
        }

        @Override
        protected void onInactive() {
            super.onInactive();

            pararFormacion();
        }
    };

    void iniciarFormaciones(FormacionesListener formacionesListener) {
        if (formando == null || formando.isCancelled()){
            formando = scheduler.scheduleAtFixedRate(new Runnable() {
                int formacion;
                int repeticiones = -1;

                @Override
                public void run() {
                    if (repeticiones <0) {
                        repeticiones = random.nextInt(3)+3;
                        formacion = random.nextInt(5)+1;
                    }
                    formacionesListener.cuandoDeLaOrden("Formación" + formacion + ":" + (repeticiones == 0 ? "CAMBIO" : repeticiones));
                    repeticiones--;
                }
            },0,1,SECONDS);
        }
    }

    void pararFormacion(){
        if (formando != null){
            formando.cancel(true);
        }
    }
}
