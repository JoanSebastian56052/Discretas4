package co.edu.udea.compumovil.gr06_20172.lab1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


/**
 * Created by Viviana Londoño on 21/08/2017.
 */

public class Login extends AppCompatActivity implements View.OnClickListener{
    private EditText txtUsuario;
    private EditText txtContrasena;
    private Button btnEntrar;
    private Button btnRegistro;
    private DbHelper dbHelper;

    public static final String TAG_USUARIO = "Usuario";
    public static final String TAG_CONTRASENA = "Contrasena";
    public static final String TAG_PREFERENCIAS = "PreferenciasUsuario";
    public static final String TAG_USUARIO_DEFECTO = "usuarioDefecto";
    public static int REQUEST_CODE = 5;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences preferencias = getSharedPreferences(TAG_PREFERENCIAS, Context.MODE_PRIVATE);
        String usuario = preferencias.getString(TAG_USUARIO, TAG_USUARIO_DEFECTO);
        if (TAG_USUARIO_DEFECTO.compareTo(usuario) == 0) {
            setToolbar();

            dbHelper = new DbHelper(this);

            btnEntrar = (Button) findViewById(R.id.btnIngresar);
            btnRegistro = (Button) findViewById(R.id.btnregistrar);
            txtUsuario = (EditText) findViewById(R.id.txtLoginEmail);
            txtUsuario.requestFocus();
            txtContrasena = (EditText) findViewById(R.id.txtLoginPassword);
            btnRegistro.setOnClickListener(this);
            btnEntrar.setOnClickListener(this);
        }else{
            Intent intentEntrar = new Intent(this, MainActivity.class);
            intentEntrar.putExtra(TAG_USUARIO,usuario);
            startActivity(intentEntrar);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            if (data.hasExtra(Login.TAG_USUARIO)) {
                txtUsuario.setText(data.getExtras().getString(Login.TAG_USUARIO));
            }
            if (data.hasExtra(Login.TAG_CONTRASENA)) {
                txtContrasena.setText(data.getExtras().getString(Login.TAG_CONTRASENA));
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnIngresar:
                String usuario;
                String contrasena;
                boolean existeUsuario;
                // Extraer los datos ingresados
                usuario = txtUsuario.getText().toString();
                contrasena = txtContrasena.getText().toString();

                //Obtiene el dato y valida el campo
                if (!validarCampo(usuario,R.string.txtusuario)){
                    return;
                }
                //Obtiene el dato y valida el campo
                if (!validarCampo(contrasena,R.string.txtpassword)){
                    return;
                }
                existeUsuario = dbHelper.consultarUsuarioInicio(usuario,contrasena);
                if (!existeUsuario) {
                    String mensaje = getResources().getString(R.string.no_usuario);
                    Snackbar.make(v, mensaje, Snackbar.LENGTH_LONG).show();
                    return;
                } else {
                    SharedPreferences preferencias = getSharedPreferences(TAG_PREFERENCIAS, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferencias.edit();
                    editor.putString(TAG_USUARIO, usuario);
                    editor.commit();
                    Intent intentEntrar = new Intent(this, MainActivity.class);
                    intentEntrar.putExtra(TAG_USUARIO,usuario);
                    startActivity(intentEntrar);
                    this.finish();
                }
                break;

            case R.id.btnregistrar:
                txtUsuario.setText(null);
                txtContrasena.setText(null);
                Intent intentRegistro = new Intent(this, Register.class);
                startActivityForResult(intentRegistro, REQUEST_CODE);
                break;
        }
    }

    private void setToolbar() {
        //Crea el widget Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.appbar);
        //Referencia la ActionBar como Toolbar
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
    }

    //Verifica si un campo de texto es vacío
    private boolean validarCampo(String campo, int referencia){
        String mensaje;
        boolean retorno = true;
        if (campo.trim().isEmpty()){
            mensaje = getResources().getString(R.string.no_texto)+ ": " + getResources().getString(referencia);
            Snackbar.make(findViewById(R.id.layout_login), mensaje, Snackbar.LENGTH_SHORT).show();
            retorno = false;
        }
        return retorno;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();

    }
}
