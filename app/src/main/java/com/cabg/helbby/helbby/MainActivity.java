package com.cabg.helbby.helbby;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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


public class MainActivity extends AppCompatActivity {

    private static String IP = "https://helbby.000webhostapp.com/PHPhelbby/Login_GETEMAIL.php?email=";
    TextView textViewHelbby;
    Button registrarse, recuperar, ingresar;
    LoginButton loginButton1;
    CallbackManager callbackManager;
    EditText etEmail, etPassword;
    RequestQueue mRequest;
    VolleyRP volleyRP;
    String EMAIL = "";
    String PASSWORD = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        volleyRP = VolleyRP.getInstance(this);
        mRequest = volleyRP.getRequestQueue();

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        loginButton1 = (LoginButton) findViewById(R.id.botonFacebook1);
        loginButton1.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
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

        etEmail = (EditText) findViewById(R.id.emailLogin);
        etPassword = (EditText) findViewById(R.id.passwordLogin);

        ingresar = (Button) findViewById(R.id.ingresar);
        ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verificarLogin(etEmail.getText().toString().toLowerCase(),
                        etPassword.getText().toString().toLowerCase());
            }
        });


        // Cambiar fuente texview helbby //
        textViewHelbby = (TextView) findViewById(R.id.helbby);
        Typeface fuente = Typeface.createFromAsset(getAssets(), "fonts/Anydore.otf");
        textViewHelbby.setTypeface(fuente);

        registrarse = (Button) findViewById(R.id.registro);
        registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentRegistrarse = new Intent(MainActivity.this, RegistroActivity.class);
                startActivity(intentRegistrarse);
                overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
            }
        });

        recuperar = (Button) findViewById(R.id.olvidarContra);
        recuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentRecuperar = new Intent(MainActivity.this, RecuperarActivity.class);
                startActivity(intentRecuperar);
                overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
            }
        });


    }

    public void solicitudJSON(String URL){
        JsonObjectRequest solicitud = new JsonObjectRequest(URL, null, new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject datos) {verificarPassword(datos);
            }
        },new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Ocurrio un error", Toast.LENGTH_SHORT).show();
            }
        });
        VolleyRP.addToQueue(solicitud, mRequest, this, volleyRP);
    }

    public void verificarLogin(String email, String password){
        EMAIL =  email;
        PASSWORD = password;
        solicitudJSON(IP + email);

    }

    public void verificarPassword(JSONObject datos){
        try {
            String estado = datos.getString("resultado");
            if (estado.equals("ConsultaCorrecta")){

                JSONObject jsonDatos = new JSONObject(datos.getString("datos"));
                String email = jsonDatos.getString("email");
                String password = jsonDatos.getString("password");

                if (email.equals(EMAIL) && password.equals(PASSWORD)){
                    Toast.makeText(this, "Usted se ha logeado correctamente",Toast.LENGTH_SHORT).show();
                    Intent entrar =  new Intent(this, HelbbyActivity.class);
                    startActivity(entrar);
                    overridePendingTransition(R.anim.trans_zoom_forward_in, R.anim.trans_zoom_forward_out);
                }else {
                    etPassword.setError("La contraseña es incorrecta");
                }

            }else
                Toast.makeText(this, estado , Toast.LENGTH_SHORT).show();

        } catch (JSONException e) {

        }
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
}