package ru.startandroid.glasscutcalc;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;

import static ru.startandroid.glasscutcalc.MainActivity.PRC_FASK_;
import static ru.startandroid.glasscutcalc.MainActivity.PRC_HOLE_;
import static ru.startandroid.glasscutcalc.MainActivity.PRC_SHL_;

public class PrefActivity extends Activity {

    SharedPreferences sPref;
    //final String PRC_HOLE_ = "prc_hole_";
    //final String PRC_SHL_ = "prc_shl_";
    //final String PRC_FASK_ = "prc_fask_";
    EditText et_prchole;
    EditText et_prcshl;
    EditText et_prcfask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pref);

        // находим элементы
        et_prchole = (EditText) findViewById(R.id.et_prchole);
        et_prcshl = (EditText) findViewById(R.id.et_prcshl);
        et_prcfask = (EditText) findViewById(R.id.et_prcfask);
        sPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
    }

    void saveText() {
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(PRC_HOLE_,et_prchole.getText().toString());
        ed.putString(PRC_SHL_,et_prcshl.getText().toString());
        ed.putString(PRC_FASK_, et_prcfask.getText().toString());
        ed.apply();
    }

    void loadText() {
        et_prchole.setText(sPref.getString(PRC_HOLE_,"0"));
        et_prcshl.setText(sPref.getString(PRC_SHL_,"0"));
        et_prcfask.setText(sPref.getString(PRC_FASK_, "0"));
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveText();
    }
    @Override
    protected void onResume() {
        super.onResume();
        loadText();
    }


}


