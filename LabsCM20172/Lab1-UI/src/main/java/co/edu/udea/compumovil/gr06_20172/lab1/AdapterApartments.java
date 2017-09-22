package co.edu.udea.compumovil.gr06_20172.lab1;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by joluditru on 18/03/2016.
 */
public class AdapterApartments extends RecyclerView.Adapter<AdapterApartments.EventosViewHolder> {

    private List<Apartment> listaCarreras;
    private Context context;

    public AdapterApartments(Context context, List<Apartment> listaCarreras) {
        this.context = context;
        this.listaCarreras = listaCarreras;
    }

    @Override
    public int getItemCount() {
        return listaCarreras.size();
    }

    @Override
    public EventosViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.card_carrera,viewGroup,false);
        EventosViewHolder eventosViewHolder = new EventosViewHolder(v);
        return new EventosViewHolder(v);
    }

    @Override
    public void onBindViewHolder(EventosViewHolder viewHolder, int i) {

        viewHolder.position = i;

        viewHolder.nombreApartamento.setText(listaCarreras.get(i).getNombre());
        viewHolder.tipoApartamento.setText(listaCarreras.get(i).getTipo());
        viewHolder.descripcionApartamento.setText(listaCarreras.get(i).getDescripcion());

        //Convertir imagen byte[] a Bitmap
        byte[] byteArray;
        byteArray = listaCarreras.get(i).getFoto();
        if (byteArray != null){
            Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            viewHolder.imagenEvento.setImageBitmap(bmp);
        }
    }


    ///////////////////////////////////////////////////////////////////////////////////////

    class EventosViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        //Campos de cada item
        public TextView nombreApartamento;
        public TextView tipoApartamento;
        public TextView descripcionApartamento;
        public ImageView imagenEvento;

        public int position;

        public EventosViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            nombreApartamento = (TextView) itemView.findViewById(R.id.lbl_nombre_apartamento);
            tipoApartamento = (TextView) itemView.findViewById(R.id.lbl_tipo);
            descripcionApartamento = (TextView) itemView.findViewById(R.id.lbl_descripcion);
            imagenEvento = (ImageView) itemView.findViewById(R.id.lbl_ImageApartment);
        }

        @Override
        public void onClick(View v) {
            Intent intentInfo;
            intentInfo = getDatos();
            context.startActivity(intentInfo);
        }

        public Intent getDatos(){
            Intent intent = new Intent(context,InfoApartmentActivity.class);
            intent.putExtra(InfoApartmentActivity.TAG_NOMBRE_APARTAMENTO,nombreApartamento.getText());
            intent.putExtra(InfoApartmentActivity.TAG_TIPO,tipoApartamento.getText());
            intent.putExtra(InfoApartmentActivity.TAG_DESCRIPCION,descripcionApartamento.getText());
            intent.putExtra(InfoApartmentActivity.TAG_AREA, listaCarreras.get(position).getDescripcion());
            intent.putExtra(InfoApartmentActivity.TAG_DIRECCION, listaCarreras.get(position).getDireccion());
            intent.putExtra(InfoApartmentActivity.TAG_VALOR, listaCarreras.get(position).getValor());
            return intent;
        }
    }
}
