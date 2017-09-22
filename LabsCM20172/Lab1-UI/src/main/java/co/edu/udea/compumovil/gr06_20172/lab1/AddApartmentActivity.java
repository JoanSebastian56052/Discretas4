package co.edu.udea.compumovil.gr06_20172.lab1;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
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

public class AddApartmentActivity extends AppCompatActivity implements View.OnClickListener {
    private final static int FOTO_GALERIA = 90;
    private final static int FOTO_CAMARA = 100;

    private EditText txtNombre;
    private EditText txtDescripcion;
    private EditText txtTipo;
    private EditText txtArea;
    private EditText txtDireccion;
    private EditText txtValor;
    private Button btnCrearCarrera;
    private byte[] foto;
    private DbHelper dbHelper;
    private int mYear;
    private int mMonth;
    private RadioButton rbCamara;
    private RadioButton rbGaleria;
    private int mDay;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_apartment);

        setToolbar();
        foto = null;

        txtNombre = (EditText) findViewById(R.id.NombreApartamento);
        txtNombre.requestFocus();
        txtDescripcion = (EditText) findViewById(R.id.DescripciónApartamento);
        txtTipo = (EditText) findViewById(R.id.tipoApartamento);
        txtArea = (EditText) findViewById(R.id.AreaApartamento);
        txtDireccion = (EditText) findViewById(R.id.addressApartamento);
        txtValor = (EditText) findViewById(R.id.valorApartamento);
        btnCrearCarrera = (Button) findViewById(R.id.buttonApartamento);
        rbCamara = (RadioButton) findViewById(R.id.rb_camara);
        rbGaleria = (RadioButton) findViewById(R.id.rb_galeria);
        actualizarCampoFecha(obtenerFechaActual());
        btnCrearCarrera.setOnClickListener(this);
        dbHelper = new DbHelper(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonApartamento:
                String nombreApartamento;
                String tipoApartamento;
                String descripcionApartamento;
                String areaApartamento;
                String direccionApartamento;
                String valorApartamento;
                boolean fechaValida=true;

                // Extraer los datos ingresados
                nombreApartamento = txtNombre.getText().toString();
                tipoApartamento = txtTipo.getText().toString();
                descripcionApartamento = txtDescripcion.getText().toString();
                areaApartamento = txtArea.getText().toString();
                direccionApartamento = txtDireccion.getText().toString();
                valorApartamento = txtValor.getText().toString();

                //Obtiene el dato y valida el campo
                if (!validarCampo(nombreApartamento,R.string.nombreApartamento)){
                    return;
                }
                //Obtiene el dato y valida el campo
                if (!validarCampo(tipoApartamento,R.string.tipo)){
                    return;
                }
                //Obtiene el dato y valida el campo
                if (!validarCampo(descripcionApartamento,R.string.descripcion)){
                    return;
                }
                //Obtiene el dato y valida el campo
                if (!validarCampo(areaApartamento,R.string.area)){
                    return;
                }

                //Obtiene el dato y valida el campo
                if (!validarCampo(direccionApartamento,R.string.direccion)){
                    return;
                }
                //Obtiene el dato y valida el campo
                if (!validarCampo(valorApartamento,R.string.valor)){
                    return;
                }

                // Se prepara el ContentValues para insertar la carrera
                ContentValues values = new ContentValues();
                values.put(TableColumnsApartments.NOMBRE, nombreApartamento);
                values.put(TableColumnsApartments.TIPO, tipoApartamento);
                values.put(TableColumnsApartments.DESCRIPCION, descripcionApartamento);
                values.put(TableColumnsApartments.AREA, areaApartamento);
                values.put(TableColumnsApartments.DIRECCION, direccionApartamento);
                values.put(TableColumnsApartments.VALOR, valorApartamento);
                values.put(TableColumnsApartments.FOTO, foto);

                dbHelper.insertar(DBAppRun.TABLE_EVENTS, values);

                String mensaje = getResources().getString(R.string.carrera_creada);
                Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();

                this.finish();

            case R.id.btn_subir:

                if (!rbNoFoto.isChecked()) {
                    Intent intent = null;
                    int codigo = 0;
                    if (rbCamara.isChecked()) {
                        intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        codigo = FOTO_CAMARA;
                    } else if (rbGaleria.isChecked()) {
                        intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
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
                } else {
                    foto = null;
                }
                break;

            case R.id.txt_fecha_carrera:
                DialogFragment datePickerFragment = new DateDialog();
                datePickerFragment.show(getFragmentManager(), "Date");
                break;
        }
    }

    private void setToolbar() {
        //Crea el widget Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.appbar);
        //Referencia la ActionBar como Toolbar
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            //Atributos de la Toolbar
            ab.setTitle(R.string.title_nueva_carrera);
            ab.setIcon(R.mipmap.logo_grupo5);
        }
    }

    //Verifica si un campo de texto es vacío
    private boolean validarCampo(String campo, int referencia){
        String mensaje;
        boolean retorno = true;
        if (campo.trim().isEmpty()){
            mensaje = getResources().getString(R.string.no_texto)+ ": " + getResources().getString(referencia);
            Snackbar.make(findViewById(R.id.nuevo_evento), mensaje, Snackbar.LENGTH_SHORT).show();
            retorno = false;
        }
        return retorno;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            String mensaje;
            if (requestCode == FOTO_GALERIA) {
                if (data != null) {
                    Uri imagenSeleccionada = data.getData();
                    InputStream is = null;
                    try {
                        is = getContentResolver().openInputStream(imagenSeleccionada);
                        foto = getBytes(is);
                        mensaje = getResources().getString(R.string.foto_cargada);
                        Snackbar.make(btnSubirImagen, mensaje, Snackbar.LENGTH_LONG).show();
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
                mensaje = getResources().getString(R.string.foto_cargada);
                Snackbar.make(btnSubirImagen, mensaje, Snackbar.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        mYear = year;
        mMonth = monthOfYear+1;
        mDay = dayOfMonth;
        int mDate[] = {mYear,mMonth,mDay};
        actualizarCampoFecha(mDate);
    }

    private boolean checkEmail(String email) {
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
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
    // Actualiza la fecha en el TextView
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
