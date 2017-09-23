package co.edu.udea.compumovil.gr06_20172.lab1;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import co.edu.udea.compumovil.gr06_20172.lab1.DBApartment.DbHelper;
import co.edu.udea.compumovil.gr06_20172.lab1.Model.Usuario;

/**
 * Created by Viviana Londoño on 24/08/2017.
 */

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */
public class InformationFragment extends Fragment {

    public static final String TAG_EMAIL = "email";
    private ImageView ivPerfil;
    private TextView lblName;
    private TextView lblLastName;
    private TextView lblDireccion;
    private TextView lblTelefono;
    private TextView lblCiudad;
    private TextView lblFecha;
    private TextView lblCorreo;
    private TextView lblGenero;
    private DbHelper dbHelper;

    public InformationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_information, container, false);

        String usuario = getArguments().getString(TAG_EMAIL);

        ivPerfil = (ImageView) view.findViewById(R.id.viewProfile);
        lblName = (TextView) view.findViewById(R.id.viewName);
        lblLastName = (TextView) view.findViewById(R.id.viewLastName);
        lblCorreo = (TextView) view.findViewById(R.id.viewEmail);
        lblTelefono = (TextView) view.findViewById(R.id.viewPhone);
        lblDireccion = (TextView) view.findViewById(R.id.viewAddress);
        lblCiudad = (TextView) view.findViewById(R.id.viewCity);
        lblFecha =(TextView) view.findViewById(R.id.viewDate);
        lblGenero =(TextView) view.findViewById(R.id.viewGender);
        dbHelper = new DbHelper(getContext());
        Usuario usuarioDB = dbHelper.consultarUsuario(usuario);
        if (usuarioDB.getFoto() != null) {
            ivPerfil.setImageBitmap(BitmapFactory.decodeByteArray(usuarioDB.getFoto(), 0, usuarioDB.getFoto().length));
        }
        lblName.setText(usuarioDB.getUsuario());
        lblLastName.setText((usuarioDB.getApellido()));
        lblCorreo.setText(usuarioDB.getEmail());
        lblTelefono.setText(usuarioDB.getTelefono());
        lblFecha.setText(usuarioDB.getFechaNacimiento());
        lblCiudad.setText(usuarioDB.getCiudad());
        lblDireccion.setText(usuarioDB.getDireccion());
        lblGenero.setText(usuarioDB.getGenero());
        // Inflate the layout for this fragment
        return view;
    }

}
