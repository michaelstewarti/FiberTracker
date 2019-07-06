package py.com.codea.fibertracker;

import android.app.ProgressDialog;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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

import java.util.HashMap;
import java.util.Map;

import py.com.codea.fibertracker.utils.Config;
import py.com.codea.fibertracker.viewobject.CantidadPelos;
import py.com.codea.fibertracker.viewobject.Punto;
import py.com.codea.fibertracker.viewobject.TipoCable;
import py.com.codea.fibertracker.viewobject.TipoManga;
import py.com.codea.fibertracker.viewobject.TipoPoste;
import py.com.codea.fibertracker.adapter.HintArrayAdapter;

public class MainActivity extends AppCompatActivity implements LocationListener {

    private Punto punto;

    Spinner tipoPoste;
    Spinner tipoCable;
    Spinner cantidadPelos;
    Spinner tipoManga;
    EditText cantidadSplitters;

    double longitude = 0D;
    double latitude = 0D;

    ProgressDialog loading = null;
    LocationManager lm = null;

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
                addPunto();
            }
        });

        // Find views
        cantidadSplitters = (EditText) findViewById(R.id.cantidadSplitters);
        tipoPoste = (Spinner) findViewById(R.id.tipoPoste);
        tipoCable = (Spinner) findViewById(R.id.tipoCable);
        cantidadPelos = (Spinner) findViewById(R.id.cantidadPelos);
        tipoManga = (Spinner) findViewById(R.id.tipoManga);

        // Populate spinners
        tipoPoste.setAdapter(new HintArrayAdapter<TipoPoste>(this, R.layout.spinner_item_selected, TipoPoste.values()));
        tipoCable.setAdapter(new HintArrayAdapter<TipoCable>(this, R.layout.spinner_item_selected, TipoCable.values()));
        cantidadPelos.setAdapter(new HintArrayAdapter<CantidadPelos>(this, R.layout.spinner_item_selected, CantidadPelos.values()));
        tipoManga.setAdapter(new HintArrayAdapter<TipoManga>(this, R.layout.spinner_item_selected, TipoManga.values()));

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

        loading = ProgressDialog.show(this,"Agregando punto","Por favor espere");

        // Obtener latitud y longitud decimales
        try {
            lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            if (location != null) {
                longitude = location.getLongitude();
                latitude = location.getLatitude();

                // Crear punto
                punto = new Punto(latitude,
                        longitude,
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

                        // Parametros sobre el punto pasados por HTTP request
                        // Seran usados por el script del google sheet
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

                int socketTimeOut = 50000;

                RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                stringRequest.setRetryPolicy(retryPolicy);

                RequestQueue queue = Volley.newRequestQueue(this);

                queue.add(stringRequest);

            } else {
                lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
                Criteria criteria = new Criteria();
                String bestProvider = String.valueOf(lm.getBestProvider(criteria, true)).toString();
                lm.requestLocationUpdates(bestProvider, 1000, 0, this);
            }



        } catch(SecurityException e) {
            Toast.makeText(this,"Debe dar a la app permiso al GPS",Toast.LENGTH_LONG).show();
        }



    }

    @Override
    public void onLocationChanged(Location location) {
        lm.removeUpdates(this);

        longitude = location.getLongitude();
        latitude = location.getLatitude();


        // Crear punto
        punto = new Punto(latitude,
                longitude,
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

                // Parametros sobre el punto pasados por HTTP request
                // Seran usados por el script del google sheet
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

        int socketTimeOut = 50000;

        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);

        RequestQueue queue = Volley.newRequestQueue(this);

        queue.add(stringRequest);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}

    @Override
    public void onProviderEnabled(String provider) {}

    @Override
    public void onProviderDisabled(String provider) {}
}
