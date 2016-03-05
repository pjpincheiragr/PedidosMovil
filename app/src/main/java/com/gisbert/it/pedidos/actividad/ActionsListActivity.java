package com.gisbert.it.pedidos.actividad;

/**
 * Created by Pablo Pincheira on 09/11/2015.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.fasterxml.jackson.databind.DeserializationFeature;

import org.springframework.http.HttpAuthentication;
import org.springframework.http.HttpBasicAuthentication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutionException;


import com.gisbert.it.pedidos.R;
import com.gisbert.it.pedidos.dom.Actions;

public class ActionsListActivity extends Activity {
    String serviceurl;
    String serviceTitle;
    String user;
    String pass;
    Actions actions;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actions_list);

        Intent intent = getIntent();
        serviceurl =  intent.getStringExtra("serviceUrl");
        serviceTitle = intent.getStringExtra("serviceTitle");
        user =  intent.getStringExtra("user");
        pass =  intent.getStringExtra("pass");

        //Textview Titulo
        TextView title = (TextView) findViewById(R.id.actionlist_title);
        title.setText( title.getText() + ": " + user + ": " + serviceTitle);

        //llamar al thread que devuelve una lista de servicios de Isis
        try {
            actions = new FillListOfServicesThread().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.actions_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class FillListOfServicesThread extends AsyncTask<Void, Void, Actions> {
        @Override
        protected Actions doInBackground(Void... params) {
            try {

                Log.v("ingresando User y Pass", user + " : " + pass);
                // Set the username and password for creating a Basic Auth request
                HttpAuthentication authHeader = new HttpBasicAuthentication(user, pass);
                HttpHeaders requestHeaders = new HttpHeaders();
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);

                Log.v("ingresando URL",serviceurl);
                RestTemplate restTemplate = new RestTemplate();

                MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
                converter.getObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

                restTemplate.getMessageConverters().add(converter);

                // Make the HTTP GET request to the Basic Auth protected URL
                ResponseEntity<Actions> response = restTemplate.exchange(serviceurl, HttpMethod.GET, requestEntity, Actions.class);

                Actions actions = response.getBody();


                //Log.v("Acciones encontradas", actions.getMembers().getId().getLinks().get(0).getHref()   +"");

                Log.v("Acciones encontradas", actions.getMembers().getCreate().getLinks().get(0).getHref());

                return null;

            } catch (Exception e) {
                Log.e("main_activity", e.getMessage(), e);
            }

            return null;
        }
    }
}
