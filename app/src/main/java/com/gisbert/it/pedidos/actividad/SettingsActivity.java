package com.pluscel.pluscelmovil.actividad;

/**
 * Created by Pablo Pincheira on 28/09/2015.
 */

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.pluscel.pluscelmovil.R;
import com.pluscel.pluscelmovil.dom.GestionConfigRepositorio;
import com.pluscel.pluscelmovil.dom.GestionConfig;

public class SettingsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        final EditText editTextUrl = (EditText)findViewById(R.id.editText_settings_url);
        Button buttonSave = (Button)findViewById(R.id.button_guardar);
        final Activity activity = this;

        final GestionConfigRepositorio gestionConfigRepositorio = new GestionConfigRepositorio();
        final GestionConfig config = gestionConfigRepositorio.recuperarConfiguracion(activity);

        editTextUrl.setText(config.getUrlRestful());

        buttonSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                final GestionConfig config = gestionConfigRepositorio.recuperarConfiguracion(activity);
                config.setUrlRestful(editTextUrl.getText().toString());
                gestionConfigRepositorio.guardarConfiguracion(activity, config);

                finish();
            }
        });

    }

}
