package co.edu.udea.compumovil.gr06_20172.lab1;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Viviana Londoño on 24/08/2017.
 */

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */
public class InformationFragment extends Fragment {

    public static final String TAG_USER = "user";
    private ImageView ivPerfil;
    private TextView lblName;
    private TextView lblLastName;
    private TextView lblDireccion;
    private TextView lblTelefono;
    private TextView lblCiudad;
    private TextView lblFecha;
    private TextView lblCorreo;
    private DbHelper dbHelper;

    public InformationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_information, container, false);

        String usuario = getArguments().getString(TAG_USER);

        ivPerfil = (ImageView) view.findViewById(R.id.viewProfile);
        lblName = (TextView) view.findViewById(R.id.viewName);
        lblCorreo = (TextView) view.findViewById(R.id.viewEmail);

        dbHelper = new DbHelper(getContext());
        Usuario usuarioDB = dbHelper.consultarUsuario(usuario);
        if (usuarioDB.getFoto() != null) {
            ivPerfil.setImageBitmap(BitmapFactory.decodeByteArray(usuarioDB.getFoto(), 0, usuarioDB.getFoto().length));
        }
        lblName.setText(usuarioDB.getUsuario());
        lblCorreo.setText(usuarioDB.getEmail());

        // Inflate the layout for this fragment
        return view;
    }

}
