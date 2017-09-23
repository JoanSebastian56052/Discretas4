package co.edu.udea.compumovil.gr06_20172.lab1;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import co.edu.udea.compumovil.gr06_20172.lab1.DBApartment.DBAppApartment;
import co.edu.udea.compumovil.gr06_20172.lab1.DBApartment.DbHelper;
import co.edu.udea.compumovil.gr06_20172.lab1.DBApartment.TableColumnsUser;


/**
 * Created by Viviana Londoño on 22/08/2017.
 */

public class Register extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    //Caracteres para validación de correo
    public static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );

    private final static int FOTO_GALERIA = 70;
    private final static int FOTO_CAMARA = 80;

    private Button btnGuardar;
    private Button btnSubir;
    private EditText txtNombre;
    private EditText txtApellido;
    private EditText txtEmail;
    private EditText txtContrasena;
    private EditText txtTelefono;
    private RadioButton rbCamara;
    private RadioButton rbGaleria;
    private RadioButton rbFemale;
    private RadioButton rbMale;
    private DbHelper dbHelper;
    private int mYear;
    private int mMonth;
    private int mDay;
    private EditText txtFecha;
    private AutoCompleteTextView txtCiudad;
    private EditText txtDireccion;
    private byte[] foto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        String[] cities = getResources().getStringArray(R.array.cities_array);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, cities);
        txtCiudad.setAdapter(adapter);
        foto = null;
        btnGuardar = (Button) findViewById(R.id.btnEnviarRegistro);
        btnSubir = (Button) findViewById(R.id.btn_subir);
        txtEmail = (EditText) findViewById(R.id.txtRegisterEmail);
        txtFecha = (EditText) findViewById(R.id.txtRegisterDate);
        txtDireccion = (EditText) findViewById(R.id.txtRegisterAddress);
        txtTelefono = (EditText) findViewById(R.id.txtRegisterPhone);
        txtCiudad = (AutoCompleteTextView) findViewById(R.id.txtRegisterCity);
        txtNombre = (EditText) findViewById(R.id.txtRegisterName);
        txtApellido = (EditText) findViewById(R.id.txtRegisterLastName);
        txtEmail.requestFocus();
        txtContrasena = (EditText) findViewById(R.id.txtRegisterPassConf);
        rbCamara = (RadioButton) findViewById(R.id.rb_camara);
        rbGaleria = (RadioButton) findViewById(R.id.rb_galeria);
        rbFemale =(RadioButton) findViewById(R.id.rbRegisterGenderF);
        rbMale =(RadioButton) findViewById(R.id.rbRegisterGenderM);
        actualizarCampoFecha(obtenerFechaActual());
        setToolbar();

        dbHelper = new DbHelper(this);

        btnGuardar.setOnClickListener(this);
        btnSubir.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnEnviarRegistro:
                boolean existeUsuario;
                String nombre;
                String contrasena;
                String correo;
                String fecha;
                String direccion;
                String ciudad;
                String apellido;
                String telefono;
                String genero = "";
                boolean fechaValida=true;

                // Extraer los datos ingresados
                nombre = txtNombre.getText().toString();
                contrasena = txtContrasena.getText().toString();
                correo = txtEmail.getText().toString();
                fecha = txtFecha.getText().toString();
                apellido = txtApellido.getText().toString();
                direccion = txtDireccion.getText().toString();
                ciudad = txtCiudad.getText().toString();
                telefono = txtTelefono.getText().toString();
                //Obtiene el dato y valida el campo
                if (!validarCampo(telefono,R.string.telefono)){
                    return;
                }
                if (!validarCampo(nombre,R.string.name)){
                    return;
                }
                //Obtiene el dato y valida el campo
                if (!validarCampo(contrasena,R.string.txtpassword)){
                    return;
                }
                if (!validarCampo(contrasena,R.string.confirmarConstraseña)){
                    return;
                }
                //Obtiene el dato y valida el campo
                if (!validarCampo(correo,R.string.email)){
                    return;
                }
                if (!checkEmail(correo)){
                    Snackbar.make(v, getResources().getString(R.string.novalido), Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if (!validarCampo(apellido,R.string.lastname)){
                    return;
                }
                if (!validarCampo(ciudad,R.string.ciudad)){
                    return;
                }
                if (!validarCampo(direccion,R.string.direccion)){
                    return;
                }
                //Obtiene el dato y valida el campo
                if (!validarCampo(fecha,R.string.fechaNacimiento)){
                    return;
                }

                try {
                    //Captura respuesta boolean del método validarFecha
                    fechaValida = validarFecha(obtenerFechaActual());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                //Determina si la fecha es válida
                if (!fechaValida){
                    Snackbar.make(v, getResources().getString(R.string.novalido), Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if(rbFemale.isChecked()) {
                    genero = "Femenino";
                } else if(rbMale.isChecked()) {
                    genero = "Masculino";
                } else {
                    rbFemale.requestFocus();
                    rbMale.requestFocus();
                }
                existeUsuario = dbHelper.consultarUsuarioRegistro(correo);
                if (existeUsuario) {
                    String mensaje = getResources().getString(R.string.novalido);
                    Snackbar.make(v, mensaje, Snackbar.LENGTH_SHORT).show();
                    return;
                } else {
                    // Se prepara el ContentValues para insertar el usuario
                    ContentValues values = new ContentValues();
                    values.put(TableColumnsUser.NOMBRE, nombre);
                    values.put(TableColumnsUser.APELLIDO, apellido);
                    values.put(TableColumnsUser.CONTRASEÑA, contrasena);
                    values.put(TableColumnsUser.EMAIL, correo);
                    values.put(TableColumnsUser.CIUDAD, ciudad);
                    values.put(TableColumnsUser.DATE, fecha);
                    values.put(TableColumnsUser.DIRECCION,direccion);
                    values.put(TableColumnsUser.GENERO, genero);
                    values.put(TableColumnsUser.TELEFONO,telefono);
                    values.put(TableColumnsUser.FOTO, foto);
                    dbHelper.insertar(DBAppApartment.TABLE_USERS, values);

                    String mensaje = getResources().getString(R.string.novalido);
                    Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();

                    Intent intentResult = new Intent();
                    intentResult.putExtra(Login.TAG_USUARIO, nombre);
                    intentResult.putExtra(Login.TAG_CONTRASENA, contrasena);
                    // Activity finished ok, return the data
                    setResult(RESULT_OK, intentResult);
                    this.finish();
                }
                break;
            case R.id.btn_subir:
                    Intent intent = null;
                    int codigo = 0;
                    if (rbCamara.isChecked()) {
                        intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        codigo = FOTO_CAMARA;
                    } else if (rbGaleria.isChecked()) {
                        intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                        codigo = FOTO_GALERIA;
                    }
                    // Verificar si hay aplicaciones disponibles
                    PackageManager packageManager = getPackageManager();
                    List activities = packageManager.queryIntentActivities(intent, 0);
                    boolean isIntentSafe = activities.size() > 0;

                    // Si hay, entonces ejecutamos la actividad
                    if (isIntentSafe) {
                        startActivityForResult(intent, codigo);
                    }

                break;
            case R.id.txtRegisterDate:
                DialogFragment datePickerFragment = new DateDialog();
                datePickerFragment.show(getFragmentManager(), "Date");
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            String mensaje;
            if (requestCode == FOTO_GALERIA) {
                if (data != null) {
                    Uri imagenSeleccionada = data.getData();
                    InputStream is;
                    try {
                        is = getContentResolver().openInputStream(imagenSeleccionada);
                        foto = getBytes(is);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else if (requestCode == FOTO_CAMARA) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                if (photo != null) {
                    photo.compress(Bitmap.CompressFormat.PNG, 100, stream);
                }
            }
        }
    }

    private void setToolbar() {
        //Crea el widget Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.appbar);
        //Referencia la ActionBar como Toolbar
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
    }

    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

    private boolean checkEmail(String email) {
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
    }

    //Verifica si un campo de texto es vacío
    private boolean validarCampo(String campo, int referencia){
        String mensaje;
        boolean retorno = true;
        if (campo.trim().isEmpty()){
            mensaje = getResources().getString(R.string.no_texto)+ ": " + getResources().getString(referencia);
            Snackbar.make(findViewById(R.id.layout_register), mensaje, Snackbar.LENGTH_SHORT).show();
            retorno = false;
        }
        return retorno;
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {

        mYear = year;
        mMonth = monthOfYear+1;
        mDay = dayOfMonth;
        int mDate[] = {mYear,mMonth,mDay};
        actualizarCampoFecha(mDate);
    }

    private void actualizarCampoFecha(int[] date) {
        mYear = date[0];
        mMonth = date[1];
        mDay = date[2];
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(mYear);
        if (mMonth < 9){
            stringBuilder.append("-0");
        }else {
            stringBuilder.append("-");
        }
        stringBuilder.append(mMonth);
        if (mDay < 10){
            stringBuilder.append("-0");
        }else {
            stringBuilder.append("-");
        }
        stringBuilder.append(mDay);
        txtFecha.setText(stringBuilder);
    }

    //Obtiene la fecha actual del dispositivo
    public int[] obtenerFechaActual(){
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        int date [] = {year,month+1,day};
        return date;
    }

    //Verifica que la fecha no sea superior a la actual
    public boolean validarFecha(int fecha[]) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date fechaActual = sdf.parse(fecha[0]+"-"+fecha[1]+"-"+fecha[2]);
        Date fechaIngresada = sdf.parse(mYear+"-"+mMonth+"-"+mDay);

        int i = fechaActual.compareTo(fechaIngresada);
        if (fechaActual.compareTo(fechaIngresada)!=1){
            return true;
        }else {
            return false;
        }
    }
}
