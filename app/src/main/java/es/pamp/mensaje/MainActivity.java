package es.pamp.mensaje;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IntDef;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.logging.LogRecord;

public class MainActivity extends AppCompatActivity {

    DatePickerDialog muestraFecha;
    DatePickerDialog.OnDateSetListener onDateListener;
    TimePickerDialog muestraHora;
    TimePickerDialog.OnTimeSetListener onTimeListener;
    Context context = this;
    Calendar calendario = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonPrueba = (Button) findViewById(R.id.button);
        buttonPrueba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast toast = Toast.makeText(context, "Mensaje Toast", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER | Gravity.CENTER, 0, 0);
                toast.show();

            }
        });

        Button botonFecha = (Button) findViewById(R.id.botonFecha);
        botonFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lanzarDatepicker();
            }
        });

        Button botonHora = (Button) findViewById(R.id.botonHora);
        botonHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lanzarTimePicker();
            }
        });
        Button botonSalir = (Button) findViewById(R.id.botonSalir);
        botonSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarSalir();
            }
        });
        Button botonProgreso = (Button) findViewById(R.id.botonProgreso);
        botonProgreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarProgreso();
            }
        });
        Button botonLogin = (Button) findViewById(R.id.botonLogin);
        botonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarLogin();
            }
        });


    }
    private void lanzarTimePicker(){
        onTimeListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Toast toast = Toast.makeText(getApplicationContext(), "Hora: " + hourOfDay + " Minutos: " + minute, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER | Gravity.CENTER, 0, 0);
                toast.show();
            }
        };

        muestraHora = new TimePickerDialog(context, onTimeListener,calendario.get(Calendar.HOUR_OF_DAY),calendario.get(Calendar.MINUTE),true);
        muestraHora.show();
    };

    private void lanzarDatepicker() {
        onDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Toast toast = Toast.makeText(getApplicationContext(), "Día: " + dayOfMonth + " Mes: " + (month + 1) + " Año: " + year, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER | Gravity.CENTER, 0, 0);
                toast.show();
            }
        };
        muestraFecha = new DatePickerDialog(context, onDateListener, 2017, 4, 22);
        muestraFecha.show();
    };
    private void mostrarSalir(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle("Salir");
        alertDialogBuilder.setMessage("¿Desea cerrar la aplicación?");
        alertDialogBuilder.setPositiveButton("Salir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MainActivity.this.finish();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    };
    private void mostrarProgreso() {
        final ProgressDialog barraProgreso = new ProgressDialog(context);
        barraProgreso.setMax(100);
        barraProgreso.setTitle("Barra de progreso");
        barraProgreso.setMessage("Cargando...");
        barraProgreso.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        barraProgreso.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (barraProgreso.getProgress() < barraProgreso.getMax()) {
                        Thread.sleep(200);
                    }
                    barraProgreso.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        Handler handle = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                barraProgreso.incrementProgressBy(1);
            }
        };
    }
    private void mostrarLogin(){
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.custom);
        dialog.setTitle("Login");
        dialog.show();

        Button dialogoAceptar = (Button) dialog.findViewById(R.id.botonAceptar);
        dialogoAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, Main2Activity.class);
                String usuario =  ((EditText)dialog.findViewById(R.id.user)).getText().toString();
                String pass = ((EditText)dialog.findViewById(R.id.pass)).getText().toString();
                if (!usuario.equals("") && !pass.equals("") && usuario.equals(pass)){
                    startActivity(i);
                    finish();
                }else{
                    Toast toast = Toast.makeText(getApplicationContext(), "Contraseña incorrecta", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER | Gravity.CENTER, 0, 0);
                    toast.show();
                }


            }
        });
        Button dialogoCancelar = (Button) dialog.findViewById(R.id.botonCancelar);
        dialogoCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });


    }
}

