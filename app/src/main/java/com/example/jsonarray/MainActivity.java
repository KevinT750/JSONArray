package com.example.jsonarray;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private TextView txtJson;
    private Button btnAceptar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txtJson = findViewById(R.id.txtJson);
        btnAceptar = findViewById(R.id.btnAceptar);

        btnAceptar.setOnClickListener(v -> obtenerMensaje());
    }

    private void obtenerMensaje() {
        String url = "http://169.254.132.108:3000/JSONArray"; // Si usas el emulador

        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            StringBuilder sb = new StringBuilder();

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject usuario = jsonArray.getJSONObject(i);
                                String nombre = usuario.getString("nombre");
                                String correo = usuario.getString("correo");

                                sb.append("Nombre: ").append(nombre)
                                        .append(", Correo: ").append(correo).append("\n");
                            }

                            txtJson.setText(sb.toString());
                        } catch (Exception e) {
                            txtJson.setText("Error al parsear JSON: " + e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        txtJson.setText("Error: " + error.getMessage());
                    }
                });

        queue.add(stringRequest);
    }

}