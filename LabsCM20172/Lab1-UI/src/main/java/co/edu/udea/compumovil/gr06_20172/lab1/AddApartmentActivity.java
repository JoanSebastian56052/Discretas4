package co.edu.udea.compumovil.gr06_20172.lab1;

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
import android.widget.EditText;
import android.widget.RadioButton;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.regex.Pattern;

import co.edu.udea.compumovil.gr06_20172.lab1.DBApartment.DBAppApartment;
import co.edu.udea.compumovil.gr06_20172.lab1.DBApartment.DbHelper;
import co.edu.udea.compumovil.gr06_20172.lab1.DBApartment.TableColumnsApartments;
import co.edu.udea.compumovil.gr06_20172.lab1.R;

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

                dbHelper.insertar(DBAppApartment.TABLE_APARTMENTS, values);

                this.finish();

            case R.id.btn_subir:
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
            Snackbar.make(findViewById(R.id.nuevo_apartamento), mensaje, Snackbar.LENGTH_SHORT).show();
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
}
