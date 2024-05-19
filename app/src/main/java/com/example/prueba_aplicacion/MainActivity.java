package com.example.prueba_aplicacion;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText ednombre, edapellido, eddireccion;
    Button registrarse;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ednombre = findViewById(R.id.ednombre);
        edapellido = findViewById(R.id.edapellido);
        eddireccion = findViewById(R.id.eddireccion);
        registrarse = findViewById(R.id.registrarse);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Cargando");

        registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarDatos();
            }
        });
    }

    private void registrarDatos() {
        String nombre = ednombre.getText().toString().trim();
        String apellido = edapellido.getText().toString().trim();
        String direccion = eddireccion.getText().toString().trim();

        if (nombre.isEmpty()) {
            Toast.makeText(this, "Ingrese nombre", Toast.LENGTH_SHORT).show();
        } else if (apellido.isEmpty()) {
            Toast.makeText(this, "Ingrese apellido", Toast.LENGTH_SHORT).show();
        } else if (direccion.isEmpty()) {
            Toast.makeText(this, "Ingrese dirección", Toast.LENGTH_SHORT).show();
        } else {
            progressDialog.show();
            StringRequest request = new StringRequest(Request.Method.POST, "http://192.168.10.1/proyecto_libreria/insertar.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.equalsIgnoreCase("Información insertada")) {
                                Toast.makeText(MainActivity.this, "Correcto", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("nombre", nombre);
                    params.put("apellido", apellido);
                    params.put("direccion", direccion);
                    return params;
                }
            };
            // Add the request to the RequestQueue (assuming you have a singleton for the request queue)
            MySingleton.getInstance(this).addToRequestQueue(request);
        }
    }
}
