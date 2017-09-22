package co.edu.udea.compumovil.gr06_20172.lab1;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ApartmentFragment extends ListFragment implements View.OnClickListener{

    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ImageButton fab;
    private DbHelper dbHelper;

    public ApartmentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_apartment, container, false);
        dbHelper = new DbHelper(getContext());

        List<Apartment> listaCarreras;
        listaCarreras = dbHelper.consultarApartamentos();

        // Obtener el Recycler
        recycler = (RecyclerView) view.findViewById(R.id.reciclador);
        recycler.setHasFixedSize(true);

        // Usar un administrador para LinearLayout
        layoutManager = new LinearLayoutManager(getActivity());
        recycler.setLayoutManager(layoutManager);

        // Crear un nuevo adaptador
        adapter = new AdapterApartments(getContext(),listaCarreras);
        adapter.notifyDataSetChanged();
        recycler.setAdapter(adapter);

        // Obtener el botón flotante
        fab = (ImageButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.fab:
                intent = new Intent(getActivity(),AddApartmentActivity.class);
                getActivity().startActivity(intent);
                break;
        }
    }
}
