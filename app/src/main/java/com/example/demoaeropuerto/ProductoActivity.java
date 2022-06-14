package com.example.demoaeropuerto;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.demoaeropuerto.databinding.ActivityProductoBinding;

import org.json.JSONException;
import org.json.JSONObject;

public class ProductoActivity extends AppCompatActivity {

    private ActivityProductoBinding binding;
    private RequestQueue colaPeticiones;
    private JSONObject parametros;
    private final String URL_BASE = "http://192.168.56.1/univalleDemoB";
    private String endPoint = "/insertarProducto.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_producto);
        binding = ActivityProductoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertarDatosEndPoint();
            }
        });
    }

    private void insertarDatosEndPoint() {
        obtenerInformacion();
        JsonObjectRequest peticionInsercionDatos = new JsonObjectRequest(
                Request.Method.POST,
                URL_BASE+endPoint,
                parametros,
                response -> {
                    try {
                        if (response.get("respuesta").toString().equals("ok")){
                            Toast.makeText(ProductoActivity.this, "Registro guardado correctamente", Toast.LENGTH_SHORT).show();
                            binding.etCodigoProducto.setText("");
                            binding.etDescripcion.setText("");
                            binding.etPrecio.setText("");
                        }
                        else {
                            Toast.makeText(ProductoActivity.this, "Algo salió mal : (", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    Toast.makeText(ProductoActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
        );
        colaPeticiones.add(peticionInsercionDatos);
    }

    private void obtenerInformacion() {
        parametros = new JSONObject();
        try {
            parametros.put("codigo", binding.etCodigoProducto.getText().toString());
            parametros.put("descripcion", binding.etDescripcion.getText().toString());
            parametros.put("precio", Integer.parseInt(binding.etPrecio.getText().toString()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}