package com.pluscel.pluscelmovil.actividad;

/**
 * Created by Pablo Pincheira on 14/11/2015.
 */


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.ImageView;

import com.fasterxml.jackson.databind.DeserializationFeature;



import org.springframework.http.HttpAuthentication;
import org.springframework.http.HttpBasicAuthentication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import java.util.concurrent.ExecutionException;

import com.pluscel.pluscelmovil.R;
import com.pluscel.pluscelmovil.dom.Equipo;



public class EquipoDetalleActivity extends Activity{



    String url;
    String user;
    String pass;
    Equipo equipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipo_detalle);

        TextView textViewMarca = (TextView) findViewById(R.id.textView_equipo_marca);
        TextView textViewModelo = (TextView) findViewById(R.id.textView_equipo_modelo);
        TextView textViewImei = (TextView) findViewById(R.id.textView_equipo_imei);
        ImageView  ImagenEquipo = (ImageView) findViewById(R.id.imagen_equipo);
        Bitmap loadedImage;
        Intent intent = getIntent();
        url =  intent.getStringExtra("url");
        user =  intent.getStringExtra("user");
        pass =  intent.getStringExtra("pass");


        try {
            equipo = new TraerEquipoThread().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        textViewMarca.setText(equipo.getMembers().getMarca().getValue().getTitle());
        textViewModelo.setText(equipo.getMembers().getModelo().getValue().getTitle());
        textViewImei.setText(equipo.getMembers().getImei().getValue());
     //   ImagenEquipo.setImageResource(convertirRutaEnId(equipo.getMembers().getAttachment().getValue().getValue()));



    }
/*
    private int convertirRutaEnId(String nombre){
        Context context = getContext();
        return context.getResources()
                .getIdentifier(nombre, "drawable", context.getPackageName());
    }*/
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

    private class TraerEquipoThread extends AsyncTask<Void, Void, Equipo> {
        @Override
        protected Equipo doInBackground(Void... params) {
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
                ResponseEntity<Equipo> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, Equipo.class);

                //return response.getBody();

                Equipo oEquipo = response.getBody();

                //Log.v("leido", restLinks.getLinks().size()+"");



                return oEquipo;

            } catch (Exception e) {
                Log.e("main_activity", e.getMessage(), e);
            }

            return null;
        }
    }
}
