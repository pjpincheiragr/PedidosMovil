package com.pluscel.pluscelmovil.actividad;

/**
 * Created by Pablo Pincheira on 11/10/2015.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.view.View;
import org.springframework.http.HttpAuthentication;
import org.springframework.http.HttpBasicAuthentication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.pluscel.pluscelmovil.R;
import com.pluscel.pluscelmovil.dom.*;
import com.pluscel.pluscelmovil.serv.*;
public class ServicesActivity  extends Activity  {

    String url;
    String user;
    String pass;
    Services services;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);

        //ListView listview = (ListView) findViewById(R.id.listView_service);

        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        user = intent.getStringExtra("user");
        pass = intent.getStringExtra("pass");
/*
        //Textview Titulo
        TextView title = (TextView) findViewById(R.id.textView_title);
        title.setText( title.getText() + ": " + user);
*/
        //Captura del evento Click del boton
         Button button_Equipment = (Button) findViewById(R.id.button_Equipment);
    }



    public void onClickButton_Equipment(View  view) {

                Intent intent = new Intent("android.intent.action.EQUIPO_LIST");

                intent.putExtra("url", url);
                intent.putExtra("user", user);
                intent.putExtra("pass", pass);

                startActivity(intent);
            }

    public void onClickButton_ListCustomers(View  view) {

        Intent intent = new Intent("android.intent.action.CLIENTE_LIST");

        intent.putExtra("url", url);
        intent.putExtra("user", user);
        intent.putExtra("pass", pass);

        startActivity(intent);
    }


    public void onClickButton_prueba(View  view) {

        Intent intent = new Intent("android.intent.action.EQUIPOS2");

        intent.putExtra("url", url);
        intent.putExtra("user", user);
        intent.putExtra("pass", pass);

        startActivity(intent);
    }

    private class FillListOfServicesThread extends AsyncTask<Void, Void, Services> {
        @Override
        protected Services doInBackground(Void... params) {
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
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

                // Make the HTTP GET request to the Basic Auth protected URL
                ResponseEntity<RestLinks> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, RestLinks.class);

                //return response.getBody();

                RestLinks restLinks = response.getBody();

                Log.v("leido", restLinks.getLinks().size()+"");

                //Buscar los servicios

                for (RestLink restlink : restLinks.getLinks()){

                    if (restlink.getRel().equals("urn:org.restfulobjects:rels/services")){
                        Log.v("Servicios Encontrados en", restlink.getHref());


                        // Make the HTTP GET request to the Basic Auth protected URL
                        ResponseEntity<Services> response2 = restTemplate.exchange(restlink.getHref(), HttpMethod.GET, requestEntity, Services.class);

                        services = response2.getBody();

                        Log.v("leido", services.getValue().size()+"");

                        //Buscar los servicios

                        int arraySize = services.getValue().size();
                        IsisService[] serviceArray = new IsisService[arraySize];
                        for (int i=0; i< arraySize;i++){
                            serviceArray[i] = services.getValue().get(i);
                            Log.v("Servicios Encontrados", serviceArray[i].getTitle());
                        }

                    }



                }

                return services;

            } catch (Exception e) {
                Log.e("main_activity", e.getMessage(), e);
            }

            return null;
        }
    }

}


