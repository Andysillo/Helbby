package com.cabg.helbby.helbby;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


public class RegistroActivity extends AppCompatActivity {

    private static final String IP_REGISTRAR = "https://helbby.000webhostapp.com/PHPhelbby/Registro_INSERT.php";
    LoginButton loginButton2;
    EditText etNombre, etEmail, etPass;
    CallbackManager callbackManager;
    Button buttonRegistrarse;
    RequestQueue mRequest;
    VolleyRP volleyRP;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        volleyRP = VolleyRP.getInstance(this);
        mRequest = volleyRP.getRequestQueue();

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        loginButton2 = (LoginButton) findViewById(R.id.botonFacebook2);
        loginButton2.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                goMainScreen();
            }
            @Override
            public void onCancel() {
            }
            @Override
            public void onError(FacebookException error) {
            }
        });

        etNombre = (EditText) findViewById(R.id.nombreRegistro);
        etEmail = (EditText) findViewById(R.id.EmailRegistro);
        etPass = (EditText) findViewById(R.id.contraseñaRegistro);
        buttonRegistrarse = (Button) findViewById(R.id.registrarse);
        buttonRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrarWebService(getStringET(etNombre),getStringET(etEmail),getStringET(etPass));
            }
        });

    }

    private void registrarWebService(String nombre,String email,String password){

        if(!nombre.isEmpty() &&
                !email.isEmpty() &&
                !password.isEmpty()) {

            HashMap<String, String> hashMapToken = new HashMap<>();
            hashMapToken.put("nombre", nombre);
            hashMapToken.put("email", email);
            hashMapToken.put("password", password);


            JsonObjectRequest solicitud = new JsonObjectRequest(Request.Method.POST, IP_REGISTRAR,
                    new JSONObject(hashMapToken),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject datos) {
                            try {
                                String estado = datos.getString("resultado");
                                if (estado.equalsIgnoreCase("El usuario se registro correctamente")) {
                                    Toast.makeText(RegistroActivity.this, estado, Toast.LENGTH_SHORT).show();
                                    Intent entrar =  new Intent(RegistroActivity.this, HelbbyActivity.class);
                                    startActivity(entrar);
                                    overridePendingTransition(R.anim.trans_zoom_forward_in, R.anim.trans_zoom_forward_out);

                                } else {
                                    Toast.makeText(RegistroActivity.this, estado, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                Toast.makeText(RegistroActivity.this, "No se pudo registrar", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(RegistroActivity.this, "No se pudo registrar", Toast.LENGTH_SHORT).show();
                }
            });
            VolleyRP.addToQueue(solicitud, mRequest, this, volleyRP);
        }else{
            Toast.makeText(this, "Por favor llene todo los campos", Toast.LENGTH_SHORT).show();
        }
    }


    private String getStringET (EditText editTextRegistro){
        return editTextRegistro.getText().toString();
    }


    private void goMainScreen() {
        Intent intent = new Intent(this, HelbbyActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    // Metodo para utilizar el boton de retoceder del celular//
    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        RegistroActivity.this.overridePendingTransition(R.anim.trans_right_in,
                R.anim.trans_right_out);
    }
}