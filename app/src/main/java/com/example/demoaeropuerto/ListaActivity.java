package com.example.demoaeropuerto;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.usb.UsbEndpoint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.demoaeropuerto.databinding.ActivityListaBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListaActivity extends AppCompatActivity {

    //Atributos

    private ActivityListaBinding binding;
    private ArrayList<Producto> productos = new ArrayList<>();
    private RequestQueue colaPeticiones;
    private final String URL_BASE = "http://192.168.56.1/univalleDemoB";
    private String endPoint = "/productos.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //En java tienen que contener los datos en un formato amigable para el lenguaje
        //Estructuras como un Array o una Lista

        //Inicializamos la cola de peticiones

        colaPeticiones = Volley.newRequestQueue(this);
        binding.btnPeticion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                peticionServicioWeb();
            }
        });
        binding.btnMostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarListaProductos();
            }
        });
    }

    private void mostrarListaProductos() {
        String mensaje = "";
        for (Producto p: productos) {
            mensaje += p.toString();
        }
        binding.txtListaProductos.setText(mensaje);
    }

    private void peticionServicioWeb() {
        JsonArrayRequest peticionInformacion = new JsonArrayRequest(
                Request.Method.GET,
                URL_BASE+endPoint,
                null,
                response -> {
                    if (response.length() > 0) {
                        Log.d("TAG",response.toString());
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject objeto = new JSONObject(response.get(i).toString());
                                Producto producto = new Producto(
                                        objeto.getInt("id"),
                                        objeto.getString("codigo"),
                                        objeto.getString("descripcion"),
                                        objeto.getInt("precio"),
                                        objeto.getInt("habilitado")
                                );
                                productos.add(producto);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                error -> {
                    Toast.makeText(ListaActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                }
        );
        colaPeticiones.add(peticionInformacion);
    }
}