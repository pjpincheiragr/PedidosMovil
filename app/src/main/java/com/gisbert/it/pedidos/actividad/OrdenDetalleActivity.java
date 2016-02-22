package com.pluscel.pluscelmovil.actividad;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.pluscel.pluscelmovil.dom.Equipo;
import com.pluscel.pluscelmovil.R;
import com.pluscel.pluscelmovil.dom.OrdenServicio;
import com.pluscel.pluscelmovil.dom.OrdenServicioRef;

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
 * Created by Pablo Pincheira on 10/12/2015.
 */
public class OrdenDetalleActivity extends Activity {


    String url;
    String user;
    String pass;
    OrdenServicio orden;
    OrdenServicioRef ordenRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orden_detalle);

        TextView textViewNumero = (TextView) findViewById(R.id.textView_orden_numero);
        TextView textViewTecnico = (TextView) findViewById(R.id.textView_orden_tecnico);
        TextView textViewCliente = (TextView) findViewById(R.id.textView_orden_cliente);
        TextView textViewEquipo = (TextView) findViewById(R.id.textView_orden_equipo);
        TextView textViewFechaHora = (TextView) findViewById(R.id.textView_orden_fechaHora);
        TextView textViewFalla = (TextView) findViewById(R.id.textView_orden_falla);
        TextView textViewImporte = (TextView) findViewById(R.id.textView_orden_importe);
        TextView textViewComision = (TextView) findViewById(R.id.textView_orden_comision);
        TextView textViewEstado = (TextView) findViewById(R.id.textView_orden_estado);
        TextView textViewGarantia = (TextView) findViewById(R.id.textView_orden_garantia);


        Intent intent = getIntent();
        url =  intent.getStringExtra("url");
        user =  intent.getStringExtra("user");
        pass =  intent.getStringExtra("pass");


        try {
            orden = new TraerOrdenServicioThread().execute().get();
            ordenRef=new TraerOrdenServicioRefThread().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        textViewNumero.setText(orden.getMembers().getNumero().getValue());
        textViewTecnico.setText(orden.getMembers().getTecnico().getValue().getTitle());
        textViewCliente.setText(orden.getMembers().getCliente().getValue().getTitle());
        textViewEquipo.setText(orden.getMembers().getEquipo().getValue().getTitle());
        textViewFechaHora.setText(orden.getMembers().getFechaHora().getValue());
        textViewFalla.setText(orden.getMembers().getFalla().getValue());
        textViewImporte.setText(orden.getMembers().getImporte().getValue());
        textViewComision.setText(orden.getMembers().getComisionTecnico().getValue());
        textViewEstado.setText(orden.getMembers().getEstado().getValue());
        textViewGarantia.setText(orden.getMembers().getGarantia().getValue());

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

    private class TraerOrdenServicioThread extends AsyncTask<Void, Void, OrdenServicio> {
        @Override
        protected OrdenServicio doInBackground(Void... params) {
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
                ResponseEntity<OrdenServicio> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, OrdenServicio.class);
                //return response.getBody();
                OrdenServicio oOrden = response.getBody();
                //Log.v("leido", restLinks.getLinks().size()+"");
                return oOrden;

            } catch (Exception e) {
                Log.e("main_activity", e.getMessage(), e);
            }

            return null;
        }
    }
    public void onClick_Equipos(View view) {

        Intent intent = new Intent("android.intent.action.EQUIPO_DETALLE");

        intent.putExtra("url", ordenRef.getMembers().getEquipo().getValue().getHref());
        intent.putExtra("user", user);
        intent.putExtra("pass", pass);

        startActivity(intent);
    }
    public void onClick_Tecnico(View view) {

        Intent intent = new Intent("android.intent.action.TECNICO_DETALLE");

        intent.putExtra("url", ordenRef.getMembers().getTecnico().getValue().getHref());
        intent.putExtra("user", user);
        intent.putExtra("pass", pass);

        startActivity(intent);
    }
    public void onClick_Cliente(View view) {

        Intent intent = new Intent("android.intent.action.CLIENTE_DETALLE");

        intent.putExtra("url", ordenRef.getMembers().getCliente().getValue().getHref());
        intent.putExtra("user", user);
        intent.putExtra("pass", pass);

        startActivity(intent);
    }
    private class TraerOrdenServicioRefThread extends AsyncTask<Void, Void, OrdenServicioRef> {
        @Override
        protected OrdenServicioRef doInBackground(Void... params) {
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
                ResponseEntity<OrdenServicioRef> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, OrdenServicioRef.class);
                //return response.getBody();
                OrdenServicioRef oOrden = response.getBody();
                //Log.v("leido", restLinks.getLinks().size()+"");
                return oOrden;

            } catch (Exception e) {
                Log.e("main_activity", e.getMessage(), e);
            }

            return null;
        }
    }
}
