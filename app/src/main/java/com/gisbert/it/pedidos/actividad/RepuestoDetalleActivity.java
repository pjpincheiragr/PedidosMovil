package com.pluscel.pluscelmovil.actividad;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.pluscel.pluscelmovil.R;
import com.pluscel.pluscelmovil.dom.Repuesto;

import org.springframework.http.HttpAuthentication;
import org.springframework.http.HttpBasicAuthentication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutionException;

/**
 * Created by Pablo Pincheira on 21/11/2015.
 */
public class RepuestoDetalleActivity extends Activity {



    String url;
    String user;
    String pass;
    Repuesto repuesto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reparacion_detalle);
/*
        TextView textViewTipoRepuesto = (TextView) findViewById(R.id.textView_repuesto_tipoRepuesto);
        TextView textViewModelo = (TextView) findViewById(R.id.textView_repuesto_modelo);
        TextView textViewDescripcion = (TextView) findViewById(R.id.textView_repuesto_descripcion);
        TextView textViewFechaArribo = (TextView) findViewById(R.id.textView_repuesto_fechaArribo);
        TextView textViewCosto = (TextView) findViewById(R.id.textView_repuesto_costo);
        TextView textViewCantidad = (TextView) findViewById(R.id.textView_repuesto_cantidad);
*/
        Intent intent = getIntent();
        url =  intent.getStringExtra("url");
        user =  intent.getStringExtra("user");
        pass =  intent.getStringExtra("pass");


        try {
            repuesto = new TraerRepuestoThread().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
/*

        textViewTipoRepuesto.setText(repuesto.getMembers().getTipoRepuesto().getValue());
        textViewModelo.setText(repuesto.getMembers().getModelo().getValue());
        textViewDescripcion.setText(repuesto.getMembers().getDescripcion().getValue());
        textViewFechaArribo.setText(repuesto.getMembers().getFechaArribo().getValue());
        textViewCosto.setText(repuesto.getMembers().getCosto().getValue());
        textViewCantidad.setText(repuesto.getMembers().getCantidad().getValue());
*/



    }

    private String ordenarFecha(String input){

        String[] fecha = input.split("-");
        String anio = fecha[0];
        String mes = fecha[1];
        String dia = fecha[2];
        return dia + "/" + mes + "/" + anio;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_equipo_detalle, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class TraerRepuestoThread extends AsyncTask<Void, Void, Repuesto> {
        @Override
        protected Repuesto doInBackground(Void... params) {
            try {


                //Services services = null;
                Log.v("ingresando User y Pass", user + " : " + pass);
                // Set the username and password for creating a Basic Auth request
                HttpAuthentication authHeader = new HttpBasicAuthentication(user, pass);
                HttpHeaders requestHeaders = new HttpHeaders();
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);

                Log.v("ingresando URL",url);
                RestTemplate restTemplate = new RestTemplate();

                MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
                converter.getObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

                restTemplate.getMessageConverters().add(converter);

                // Make the HTTP GET request to the Basic Auth protected URL
                ResponseEntity<Repuesto> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, Repuesto.class);

                //return response.getBody();

                Repuesto oRepuesto = response.getBody();

                //Log.v("leido", restLinks.getLinks().size()+"");



                return oRepuesto;

            } catch (Exception e) {
                Log.e("main_activity", e.getMessage(), e);
            }

            return null;
        }
    }
}
