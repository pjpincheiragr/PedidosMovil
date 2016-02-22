package com.pluscel.pluscelmovil.actividad;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.pluscel.pluscelmovil.R;
import com.pluscel.pluscelmovil.dom.Falla;
import com.pluscel.pluscelmovil.dom.FallaEquipoTecnico;
import com.pluscel.pluscelmovil.dom.FallaEquipoTecnicoRef;
import com.pluscel.pluscelmovil.dom.OrdenServicio;
import com.pluscel.pluscelmovil.dom.Tecnico;
import com.pluscel.pluscelmovil.serv.OrdenServicios;


import org.springframework.http.HttpAuthentication;
import org.springframework.http.HttpBasicAuthentication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.concurrent.ExecutionException;

/**
 * Created by Pablo Pincheira on 10/12/2015.
 */
public class ReparacionDetalleActivity extends Activity {


    String url;
    String user;
    String pass;
    FallaEquipoTecnico fallaE;
    FallaEquipoTecnicoRef fallaERef;
    Falla falla;

   OrdenServicio numeroDeOrden;

     Tecnico tecnico;
    DatoTipoFalla tipoDeFalla;

    public class DatoString {
        String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public class DatoTipoFalla {

        DatoTitle value;
        public DatoTitle getValue() {return value;}
        public void setValue(DatoTitle value) {
            this.value = value;
        }

        public class DatoTitle {
            String title;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reparacion_detalle);

        TextView textViewNumero = (TextView) findViewById(R.id.textView_reparacion_numero);
        TextView textViewTecnico = (TextView) findViewById(R.id.textView_reparacion_tecnico);
        TextView textViewFechaHora = (TextView) findViewById(R.id.textView_reparacion_fechaHora);
        TextView textViewFalla = (TextView) findViewById(R.id.textView_reparacion_falla);
        TextView textViewFallaDescripcion = (TextView) findViewById(R.id.textView_reparacion_fallaDescripcion);
        TextView textViewEstado = (TextView) findViewById(R.id.textView_reparacion_estado);


        Intent intent = getIntent();
        url =  intent.getStringExtra("url");
        user =  intent.getStringExtra("user");
        pass =  intent.getStringExtra("pass");
        try {
            fallaE = new TraerFallaEThread().execute().get();
            fallaERef=new TraerFallaERefThread().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        textViewNumero.setText(fallaE.getMembers().getOrdenServicio().getValue().getTitle());
        textViewTecnico.setText(fallaE.getMembers().getTecnico().getValue().getTitle());
        textViewFechaHora.setText(fallaE.getMembers().getFechaHora().getValue());
        textViewEstado.setText(fallaE.getMembers().getEstado().getValue());
        intent.putExtra("url", fallaERef.getMembers().getFalla().getValue().getHref());
        intent.putExtra("user", user);
        intent.putExtra("pass", pass);

        Intent intentFalla = getIntent();
        url =  intentFalla.getStringExtra("url");
        user =  intentFalla.getStringExtra("user");
        pass =  intentFalla.getStringExtra("pass");


        try {
            falla = new TraerFallaThread().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        textViewFalla.setText(falla.getMembers().getTipoFalla().getValue().getTitle());
        textViewFallaDescripcion.setText(falla.getMembers().getDescripcion().getValue());

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






    private class IngresarFallaEEThread extends AsyncTask<Void, Void, FallaEquipoTecnico> {
        @Override
        protected FallaEquipoTecnico doInBackground(Void... params) {
            try {

                //Services services = null;
                Log.v("ingresando User y Pass", user + " : " + pass);
                // Set the username and password for creating a Basic Auth request
                HttpAuthentication authHeader = new HttpBasicAuthentication(user, pass);
                HttpHeaders requestHeaders = new HttpHeaders();
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);
                Log.v("ingresando URL", url);
                RestTemplate restTemplate = new RestTemplate();
                MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
                converter.getObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                restTemplate.getMessageConverters().add(converter);

                HttpHeaders headers = new HttpHeaders();
                headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
                UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                 .queryParam("numeroDeOrden", numeroDeOrden)
                        .queryParam("fecha", "01/01/2015")
                .queryParam("tecnico", tecnico)
                .queryParam("tipoDeFalla", tipoDeFalla)
                .queryParam("fallaDescripcion", "lalalallalalal")
                .queryParam("estado", "SIN_REVIZAR");
                HttpEntity<?> entity = new HttpEntity<>(headers);

                ResponseEntity<FallaEquipoTecnico> response = restTemplate.exchange(builder.build().encode().toUri(),HttpMethod.POST, requestEntity, FallaEquipoTecnico.class);
                FallaEquipoTecnico oFalla = response.getBody();

                return oFalla;

            } catch (Exception e) {
                Log.e("main_activity", e.getMessage(), e);
            }

            return null;
        }
    }

    private class TraerFallaEThread extends AsyncTask<Void, Void, FallaEquipoTecnico> {
        @Override
        protected FallaEquipoTecnico doInBackground(Void... params) {
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
                ResponseEntity<FallaEquipoTecnico> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, FallaEquipoTecnico.class);
                //return response.getBody();
                FallaEquipoTecnico oFalla = response.getBody();

                return oFalla;

            } catch (Exception e) {
                Log.e("main_activity", e.getMessage(), e);
            }

            return null;
        }
    }
    public void onClick_Orden(View view) {

        Intent intent = new Intent("android.intent.action.ORDEN_DETALLE");

        intent.putExtra("url", fallaERef.getMembers().getOrdenServicio().getValue().getHref());
        intent.putExtra("user", user);
        intent.putExtra("pass", pass);

        startActivity(intent);
    }
    public void onClick_Tecnico(View view) {

        Intent intent = new Intent("android.intent.action.TECNICO_DETALLE");

        intent.putExtra("url", fallaERef.getMembers().getTecnico().getValue().getHref());
        intent.putExtra("user", user);
        intent.putExtra("pass", pass);

        startActivity(intent);
    }

    private class TraerFallaERefThread extends AsyncTask<Void, Void, FallaEquipoTecnicoRef> {
        @Override
        protected FallaEquipoTecnicoRef doInBackground(Void... params) {
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
                ResponseEntity<FallaEquipoTecnicoRef> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, FallaEquipoTecnicoRef.class);
                //return response.getBody();
                FallaEquipoTecnicoRef oFallaRef = response.getBody();
                //Log.v("leido", restLinks.getLinks().size()+"");
                return oFallaRef;

            } catch (Exception e) {
                Log.e("main_activity", e.getMessage(), e);
            }

            return null;
        }
    }


    private class TraerFallaThread extends AsyncTask<Void, Void, Falla> {
        @Override
        protected Falla doInBackground(Void... params) {
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
                ResponseEntity<Falla> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, Falla.class);
                //return response.getBody();
                Falla oFalla = response.getBody();
                //Log.v("leido", restLinks.getLinks().size()+"");
                return oFalla;
            } catch (Exception e) {
                Log.e("main_activity", e.getMessage(), e);
            }

            return null;
        }
    }
}
