package py.com.codea.fibertracker;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import py.com.codea.fibertracker.utils.Config;
import py.com.codea.fibertracker.viewobject.CantidadPelos;
import py.com.codea.fibertracker.viewobject.Punto;
import py.com.codea.fibertracker.viewobject.TipoCable;
import py.com.codea.fibertracker.viewobject.TipoManga;
import py.com.codea.fibertracker.viewobject.TipoPoste;

public class MainActivity extends AppCompatActivity {

    private Punto punto;

    EditText x;
    EditText y;
    Spinner tipoPoste;
    Spinner tipoCable;
    Spinner cantidadPelos;
    Spinner tipoManga;
    EditText cantidadSplitters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();
                addPunto();
            }
        });

        // Find views
        x = (EditText) findViewById(R.id.x);
        y = (EditText) findViewById(R.id.y);
        cantidadSplitters = (EditText) findViewById(R.id.cantidadSplitters);
        tipoPoste = (Spinner) findViewById(R.id.tipoPoste);
        tipoCable = (Spinner) findViewById(R.id.tipoCable);
        cantidadPelos = (Spinner) findViewById(R.id.cantidadPelos);
        tipoManga = (Spinner) findViewById(R.id.tipoManga);

        // Populate spinners
        tipoPoste.setAdapter(new ArrayAdapter<TipoPoste>(this, android.R.layout.simple_spinner_item, TipoPoste.values()));
        tipoCable.setAdapter(new ArrayAdapter<TipoCable>(this, android.R.layout.simple_spinner_item, TipoCable.values()));
        cantidadPelos.setAdapter(new ArrayAdapter<CantidadPelos>(this, android.R.layout.simple_spinner_item, CantidadPelos.values()));
        tipoManga.setAdapter(new ArrayAdapter<TipoManga>(this, android.R.layout.simple_spinner_item, TipoManga.values()));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    private void   addPunto() {

        final ProgressDialog loading = ProgressDialog.show(this,"Agregando punto","Por favor espere");

        // Obtener punto
        punto = new Punto(Float.parseFloat(x.getText().toString()),
                Float.parseFloat(y.getText().toString()),
                (TipoPoste) tipoPoste.getSelectedItem(),
                (TipoCable) tipoCable.getSelectedItem(),
                (CantidadPelos) cantidadPelos.getSelectedItem(),
                (TipoManga) tipoManga.getSelectedItem(),
                Integer.parseInt(cantidadSplitters.getText().toString()));

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.APP_SCRIPT_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        loading.dismiss();
                        //Toast.makeText(AddItem.this,response,Toast.LENGTH_LONG).show();
                        //Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        //startActivity(intent);
                        //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        //        .setAction("Action", null).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                //here we pass params
                params.put("action","addPunto");
                params.put("x",punto.getX().toString());
                params.put("y",punto.getY().toString());
                params.put("tipoPoste",punto.getTipoPoste().val);
                params.put("tipoCable",punto.getTipoCable().val);
                params.put("cantidadPelos",punto.getCantidadPelos().val);
                params.put("tipoManga",punto.getTipoManga().val);
                params.put("cantidadSplitters",punto.getCantidadSplitters().toString());

                return params;
            }
        };

        int socketTimeOut = 50000;// u can change this .. here it is 50 seconds

        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);

        RequestQueue queue = Volley.newRequestQueue(this);

        queue.add(stringRequest);


    }

}
