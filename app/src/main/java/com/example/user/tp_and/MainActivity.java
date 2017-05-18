package com.example.user.tp_and;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void maMethode(View sender){
        EditText et = (EditText)findViewById(R.id.edit_euros);
        TextView tv = (TextView)findViewById(R.id.text_euros);
        tv.setText(et.getText().toString());
    }

}
