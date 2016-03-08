package com.gisbert.it.pedidos.actividad;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.gisbert.it.pedidos.R;
import com.gisbert.it.pedidos.dom.PedidoItem;

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

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class PedidoItemDetalleActivity extends Activity {

    /**
     * @Override protected void onCreate(Bundle savedInstanceState) {
     * super.onCreate(savedInstanceState);
     * setContentView(R.layout.activity_equipo_list);
     * }
     * @Override public boolean onCreateOptionsMenu(Menu menu) {
     * // Inflate the menu; this adds items to the action bar if it is present.
     * getMenuInflater().inflate(R.menu.menu_equipo_list, menu);
     * return true;
     * }
     * @Override public boolean onOptionsItemSelected(MenuItem item) {
     * // Handle action bar item clicks here. The action bar will
     * // automatically handle clicks on the Home/Up button, so long
     * // as you specify a parent activity in AndroidManifest.xml.
     * int id = item.getItemId();
     * <p/>
     * //noinspection SimplifiableIfStatement
     * if (id == R.id.action_settings) {
     * return true;
     * }
     * <p/>
     * return super.onOptionsItemSelected(item);
     * }
     **/

    String url;
    String user;
    String pass;
    String estado;
    PedidoItem pedidoItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido_item_detalle);

        TextView muestra_pi = (TextView) findViewById(R.id.pedido_item_es_muestra);
        TextView codigo_pi=(TextView)findViewById(R.id.pedido_item_codigo);
        TextView marca_pi=(TextView)findViewById(R.id.pedido_item_marca);
        TextView cantidad_pi=(TextView)findViewById(R.id.pedido_item_cantidad);
        TextView estado_pi=(TextView)findViewById(R.id.pedido_item_estado);
        TextView observaciones_pi=(TextView)findViewById(R.id.pedido_item_obs);
        Intent intent = getIntent();
        url =  intent.getStringExtra("link");
        user =  intent.getStringExtra("user");
        pass =  intent.getStringExtra("pass");
        estado=intent.getStringExtra("estado");

        try {
            pedidoItem= new FillPedidoItemDetalle().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        String muestra_valor=pedidoItem.getMembers().getMuestra().getValue();
        muestra_pi.setText(muestra_valor);
        String codigo=pedidoItem.getMembers().getCodigo().getValue();
        codigo_pi.setText(codigo);
        String marca=pedidoItem.getMembers().getMarca().getValue().getTitle();
        marca_pi.setText(marca);
        String cantidad=""+(pedidoItem.getMembers().getCantidad().getValue());
        cantidad_pi.setText(cantidad);
        String estado=pedidoItem.getMembers().getEstado().getValue();
        estado_pi.setText(estado);
        String observ=pedidoItem.getMembers().getObservacion().getValue();
        observaciones_pi.setText(observ);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_equipo_list, menu);
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

    private class FillPedidoItemDetalle extends AsyncTask<Void, Void, PedidoItem> {
        @Override
        protected PedidoItem doInBackground(Void...  params) {
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
                UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
                HttpEntity<?> entity = new HttpEntity<>(headers);
                ResponseEntity<PedidoItem> response = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET, requestEntity, PedidoItem.class);
                String some=response.toString();
                PedidoItem pedido = response.getBody();
                return pedido;

            } catch (Exception e) {
                Log.e("main_activity", e.getMessage(), e);
            }

            return null;
        }
    }
    public String loadJSON() {
        String json = null;
        try {
            FileInputStream fis = openFileInput("config.json");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }

            json = sb.toString();

        } catch (FileNotFoundException e) {
            json = "{ \"urlRestful\" : \"\",  \"user\" : \"\",  \"pass\" : \"\",  \"save\" : \"\"}";
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;

    }
    private void mostrarMensaje(CharSequence text) {
        Context context = getApplicationContext();

        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }

    public void onClickButton_EditarEstado(View view) {

        Intent intent = new Intent("android.intent.action.EDITAR_ESTADO");

        intent.putExtra("url", url);
        intent.putExtra("user", user);
        intent.putExtra("pass", pass);

        startActivity(intent);
    }



}
