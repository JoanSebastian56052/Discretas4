package co.edu.udea.compumovil.gr06_20172.lab1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

public class InfoApartmentActivity extends AppCompatActivity {

    private TextView lblNombre;
    private TextView lblDescripcion;
    private TextView lblTipo;
    private TextView lblArea;
    private TextView lblDireccion;
    private TextView lblValor;

    private String nombre;
    private String tipo;
    private String area;
    private String direccion;
    private String descripcion;
    private String valor;

    public static final String TAG_NOMBRE_APARTAMENTO = "nombreApartamento";
    public static final String TAG_TIPO = "tipoApar";
    public static final String TAG_DESCRIPCION = "descripcionApar";
    public static final String TAG_AREA = "areaApar";
    public static final String TAG_DIRECCION = "direccionApar";
    public static final String TAG_VALOR = "valorApar";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_apartment);
        setToolbar();

        lblNombre = (TextView) findViewById(R.id.lbl_nombre_apartamento);
        lblTipo = (TextView) findViewById(R.id.lbl_tipo);
        lblDescripcion = (TextView) findViewById(R.id.lbl_descripcion);
        lblArea = (TextView) findViewById(R.id.lbl_area);
        lblDireccion = (TextView) findViewById(R.id.lbl_direccion);
        lblValor = (TextView) findViewById(R.id.lbl_valor);

        Bundle extras = getIntent().getExtras();

        nombre = extras.getString(InfoApartmentActivity.TAG_NOMBRE_APARTAMENTO);
        tipo = extras.getString(InfoApartmentActivity.TAG_TIPO);
        descripcion = extras.getString(InfoApartmentActivity.TAG_DESCRIPCION);
        area = extras.getString(InfoApartmentActivity.TAG_AREA);
        direccion = extras.getString(InfoApartmentActivity.TAG_DIRECCION);
        valor = extras.getString(InfoApartmentActivity.TAG_VALOR);

        lblNombre.setText(nombre);
        lblDescripcion.setText(descripcion);
        lblTipo.setText(tipo);
        lblArea.setText(area);
        lblDireccion.setText(descripcion);
        lblValor.setText(valor);
    }

    private void setToolbar() {
        //Crea el widget Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.appbar);
        //Referencia la ActionBar como Toolbar
        setSupportActionBar(toolbar);
    }
}
