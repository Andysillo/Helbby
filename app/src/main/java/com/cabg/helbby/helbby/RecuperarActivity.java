package com.cabg.helbby.helbby;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class RecuperarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar);
    }

    // Metodo para utilizar el boton de retoceder del celular//
    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        RecuperarActivity.this.overridePendingTransition(R.anim.trans_left_in,
                R.anim.trans_left_out);
    }
}