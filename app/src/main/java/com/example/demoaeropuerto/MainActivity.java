package com.example.demoaeropuerto;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.demoaeropuerto.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    //Librería que van a usar para comunicarse con sus EndPoints del sistema WEB
    //VOLLEY
    //Volley utiliza una cola de peticiones para tratar todas las solicitudes
    //que se hagan vio HTTP en orden de llegada sin que puedan llegar a perder
    private RequestQueue colaPeticiones;
    //Configurar la URL a la que vas a hacer la petición
    private final String URL_BASE = "http://192.168.56.1/univalleDemoB";
    private String endPoint = "/consulta.php";
    private String queryParams = "?llave=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //Inicializar la cola de peticiones
        colaPeticiones = Volley.newRequestQueue(this);
        binding.btnCargarDatos.setOnClickListener(view -> {
            peticionEndPointConsulta();
        });
    }

    private void peticionEndPointConsulta() {
        //Cuando sxe hace peticiones VOLLEY trabaj con dos estructuras
        //que pueden ser lo que te responde o como te lo trae la información del EndPoint
        //se llaman: JsonArrayRequest y JsonObjetcRequest
        //JsonArrayRequest: trabaja con peticiones que sabe que van a devolver un array de Json
        JsonArrayRequest peticionProducto = new JsonArrayRequest(
                Request.Method.GET,
                URL_BASE + endPoint + queryParams + binding.etCodigo.getText().toString(),
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (response.length() > 0) {
                            try {
                                Log.d("TAG", response.toString());
                                //Cada elemento de esa respuesta -> resoibse se lo trata
                                //como un objeto JSON --> JSONObject
                                //respuesta -> '[{"codigo":"ex112"}]' String que tiene formato de JSON
                                //"21" -> 21 algo parecido a cuando parsean una cadena en número
                                //Cadena String en formato JSON la puede parsear en un objeto JSON
                                JSONObject objetoProducto = new JSONObject(response.get(0).toString());
                                binding.txtDenominacion.setText(objetoProducto.getString("descripcion"));
                                binding.txtPrecio.setText(objetoProducto.getString("precio"));
                            }
                            catch (Exception e) {
                                e.getStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
        //Adicionar tu petición a la cola de peticiones
        colaPeticiones.add(peticionProducto);
        //JsonObjectRequest: trabaja con peticiones que sabe que van a devolver un objeto Json
        /*JsonArrayRequest peticionProducto = new JsonArrayRequest(
                Request.Method.GET,
                url+queryParams+2,
                null
        );*/
    }


}