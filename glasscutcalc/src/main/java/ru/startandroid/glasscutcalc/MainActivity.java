package ru.startandroid.glasscutcalc;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import static java.lang.Float.parseFloat;
import static java.lang.Math.round;
import static ru.startandroid.glasscutcalc.R.id.rb_no;

public class MainActivity extends Activity implements View.OnClickListener {

    final int MENU_RESET_ID = 1;
    final int MENU_PREF_ID = 2;
    final int MENU_QUIT_ID = 3;

    EditText et_ps;
    EditText et_len;
    EditText et_width;
    EditText et_ch;

    RadioGroup rg_dop;
    Button btnsms;
    Button btnCalc;
    TextView tvResult;
    TextView tv_base;
    TextView tv_hole;
    TextView tv_dop;

    SharedPreferences sPref;
    public static final String PRC_HOLE_ = "prc_hole_";
    public static final String PRC_SHL_ = "prc_shl_";
    public static final String PRC_FASK_ = "prc_fask_";
    float prc_hole=10; //стоимость ответрстия в %% от стоимости заготовки
    float prc_shl=25; //стоимость шлифовки в %% от стоимости заготовки
    float prc_fask=50; //стоимость фаски в %% от стоимости заготовки

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc);

        // находим элементы
        et_ps = (EditText) findViewById(R.id.et_ps);
        et_len = (EditText) findViewById(R.id.et_len);
        et_width = (EditText) findViewById(R.id.et_width);
        et_ch = (EditText) findViewById(R.id.et_ch);
        rg_dop = (RadioGroup) findViewById(R.id.rg_dop);
        btnsms = (Button) findViewById(R.id.btnsms);
        btnCalc = (Button) findViewById(R.id.btnCalc);
        tvResult = (TextView) findViewById(R.id.tvResult);
        tv_base = (TextView) findViewById(R.id.tv_base);
        tv_hole = (TextView) findViewById(R.id.tv_hole);
        tv_dop = (TextView) findViewById(R.id.tv_dop);

        // прописываем обработчик
        btnCalc.setOnClickListener(this);
        btnsms.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {



        // определяем нажатую кнопку и выполняем соответствующую операцию
        switch (v.getId()) {
            case R.id.btnCalc:
                //*******

                // TODO Auto-generated method stub
                float len_ = 0;
                float width_ = 0;
                float ps_ = 0;
                float ch_ = 0;
                float st_det = 0;
                float result = 0;

                // Проверяем поля на пустоту
                if (TextUtils.isEmpty(et_len.getText().toString())
                        || TextUtils.isEmpty(et_width.getText().toString())
                        || TextUtils.isEmpty(et_ps.getText().toString())
                        ) {
                    Toast.makeText(this, "Не указаны основные параметры", Toast.LENGTH_SHORT).show();
                    // надо попробовать выделять эти элементы цветом
                    //et_ps.setBackgroundColor(Color.RED);
                    return;
                }


                // читаем EditText и заполняем переменные числами
                len_ = parseFloat(et_len.getText().toString());
                width_ = parseFloat(et_width.getText().toString());
                ps_ = parseFloat(et_ps.getText().toString());
                if (!TextUtils.isEmpty(et_ch.getText().toString())) {
                    ch_ = parseFloat(et_ch.getText().toString());
                }
                //******


                tvResult.setText("");
                tv_base.setText("");
                tv_hole.setText("");
                tv_dop.setText("");

                tv_base.setText(String.valueOf(round((len_ * width_) * ps_ / 1000000)));
                st_det = parseFloat(tv_base.getText().toString());
                result = st_det;

                //добавляем стоимость отверстий
                if (ch_ != 0) {
                    result = result + (st_det * ch_ * prc_hole / 100);
                    tv_hole.setText(String.valueOf(round(st_det * ch_ * prc_hole / 100)));
                }

                // добавляем стоимость дополнительной обработки в %% от стоимости заготовки
                float dopobr = 0;
                switch (rg_dop.getCheckedRadioButtonId()) {
                    case rb_no:
                        dopobr = 0;
                        break;
                    case R.id.rb_shl:
                        dopobr = prc_shl;
                        break;
                    case R.id.rb_fask:
                        dopobr = prc_fask;
                        break;
                }
                if (dopobr != 0) {
                    result = result + (st_det * dopobr / 100);
                    tv_dop.setText(String.valueOf(round(st_det * dopobr / 100)));
                }
                tvResult.setText(String.valueOf(round(result)));
                break;
            case R.id.btnsms:
                et_ps.setBackgroundColor(Color.RED);
                break;
            default:
                break;
        }

        // формируем строку вывода
//        tvResult.setText(String.valueOf(round(result)));
    }

    // создание меню
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, MENU_RESET_ID, 0, "Очистить");
        menu.add(0, MENU_PREF_ID, 0, "Настройки");
        menu.add(0, MENU_QUIT_ID, 0, "Выход");
        return super.onCreateOptionsMenu(menu);
    }

    // обработка нажатий на пункты меню
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MENU_RESET_ID:
                // очищаем поля
                et_len.setText("");
                et_ps.setText("");
                et_width.setText("");
                et_ch.setText("");
                rg_dop.check(rb_no);
                tvResult.setText("");
                tv_base.setText("");
                tv_hole.setText("");
                tv_dop.setText("");
                break;
            case MENU_PREF_ID:
                // переход в настройки
                Intent intent = new Intent(this, PrefActivity.class);
                startActivity(intent);
                break;
            case MENU_QUIT_ID:
                // выход из приложения
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    void loadPref() {
        sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        prc_hole = Float.parseFloat(sPref.getString(PRC_HOLE_, "10"));
        prc_shl = Float.parseFloat(sPref.getString(PRC_SHL_, "25"));
        prc_fask = Float.parseFloat(sPref.getString(PRC_FASK_, "50"));
    }

    protected void onResume() {
        super.onResume();
        loadPref();
    }


}
