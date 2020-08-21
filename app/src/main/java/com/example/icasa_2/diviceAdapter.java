package com.example.icasa_2;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;


         /*       Representa el punto de unión entre firebase y el elemento lista. Extiende de FirebaseRecyclerAdapter
                y como elementos necesarios se pasa la clase del objeto se se podrá en los ítems y la clase que
                representa el holder .
*/

public class diviceAdapter extends FirestoreRecyclerAdapter<dives_card,diviceAdapter.diviceHolder> {

    private  OnItemClickListener listener;

    Activity activity;

    public diviceAdapter(@NonNull FirestoreRecyclerOptions<dives_card> options, Activity activity) {
        super(options);
        this.activity = activity;
    }

    @Override
    protected void onBindViewHolder(@NonNull diviceHolder holder, int position, @NonNull dives_card model) {

        DocumentSnapshot modelDocument = getSnapshots().getSnapshot(holder.getAdapterPosition());

       final String id = modelDocument.getId();



        holder.textViewid.setText(String.valueOf(model.getid()));
        holder.textViewNoContrato.setText(model.getnocontrato());
        holder.textViewConsumo.setText(model.getconsumo());
        holder.textViewDireccion.setText(model.getdireccion());
        holder.textViewColonia.setText(model.getcolonia());
        holder.textViewMunicipio.setText(model.getmunicipio());
        holder.textViewfecha_corte.setText(model.getfecha_corte());
        holder.textViewfecha_instalacion.setText(model.getfecha_instalacion());
        holder.textViewMesEnero.setText(model.getmesEnero());
        holder.textViewMesFebrero.setText(model.getmesFebrero());
        holder.textViewMesMarzo.setText(model.getmesMarzo());
        holder.textViewMesAbril.setText(model.getmesAbril());
        holder.textViewMesMayo.setText(model.getmesMayo());
        holder.textViewMesJunio.setText(model.getmesJunio());
        holder.textViewMesJulio.setText(model.getmesJulio());
        holder.textViewMesAgosto.setText(model.getmesAgosto());
        holder.textViewMesSeptiembre.setText(model.getmesSeptiembre());
        holder.textViewMesOctubre.setText(model.getmesOctubre());
        holder.textViewMesNoviembre.setText(model.getmesNoviembre());
        holder.textViewMesDiciembre.setText(model.getmesDiciembre());

        holder.buttonActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String recu_Collection = ScanCodeActivity.dispositivos.getText().toString();
                Intent intent = new Intent(activity, EditarActivity.class);
                intent.putExtra("userId",recu_Collection);
                intent.putExtra("DispositivoId",id);
                activity.startActivity(intent);
            }
        });

    }

    @NonNull
    @Override
    public diviceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item,parent,false);
        return new diviceHolder(v);
    }

    public void deleteItem(int position){
        getSnapshots().getSnapshot(position).getReference().delete();


    }

    /*   diviceHolder
    Representa el elemento que se renderizará en el elemento de lista.
    Extentiende de RecyclerView.ViewHolder y como mínimo en constructor tendrá
    como parámetro el layout que se utilizará. Adicionalmente se pasará como
    parámetro cualquier cosa que sea necesaria. En este constructor se
    inicializarán todos los elementos del layout
*/

    class diviceHolder extends RecyclerView.ViewHolder{
        TextView textViewid;
        TextView textViewNoContrato;
        TextView textViewConsumo;
        TextView textViewDireccion;
        TextView textViewColonia;
        TextView textViewMunicipio;
        TextView textViewfecha_corte;
        TextView textViewfecha_instalacion;
        TextView textViewMesEnero, textViewMesFebrero, textViewMesMarzo, textViewMesAbril, textViewMesMayo, textViewMesJunio,
                 textViewMesJulio, textViewMesAgosto, textViewMesSeptiembre, textViewMesOctubre, textViewMesNoviembre, textViewMesDiciembre;


        Button buttonActualizar;


        public diviceHolder(@NonNull View itemView) {
            super(itemView);
            textViewid = itemView.findViewById(R.id.text_view_ID);
            textViewNoContrato = itemView.findViewById(R.id.text_view_NoContrato);
            textViewConsumo = itemView.findViewById(R.id.text_view_Consumo);
            textViewDireccion = itemView.findViewById(R.id.text_view_direccion);
            textViewColonia = itemView.findViewById(R.id.text_view_colonia);
            textViewMunicipio = itemView.findViewById(R.id.text_view_municipio);
            textViewfecha_corte = itemView.findViewById(R.id.text_view_fecha_corte);
            textViewfecha_instalacion = itemView.findViewById(R.id.text_view_fecha_instalacion);
            textViewMesEnero = itemView.findViewById(R.id.text_view_Mes_Enero);
            textViewMesFebrero = itemView.findViewById(R.id.text_view_Mes_Febrero);
            textViewMesMarzo = itemView.findViewById(R.id.text_view_Mes_Marzo);
            textViewMesAbril = itemView.findViewById(R.id.text_view_Mes_Abril);
            textViewMesMayo = itemView.findViewById(R.id.text_view_Mes_Mayo);
            textViewMesJunio = itemView.findViewById(R.id.text_view_Mes_Junio);
            textViewMesJulio = itemView.findViewById(R.id.text_view_Mes_Julio);
            textViewMesAgosto = itemView.findViewById(R.id.text_view_Mes_Agosto);
            textViewMesSeptiembre = itemView.findViewById(R.id.text_view_Mes_Septiembre);
            textViewMesOctubre = itemView.findViewById(R.id.text_view_Mes_Octubre);
            textViewMesNoviembre = itemView.findViewById(R.id.text_view_Mes_Noviembre);
            textViewMesDiciembre = itemView.findViewById(R.id.text_view_Mes_Dicembre);

            buttonActualizar = itemView.findViewById(R.id.buttonEdit);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null){
                        listener.onItemClick(getSnapshots().getSnapshot(position),position);
                    }

                }
            });


        }
    }

    public  interface  OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    public  void  setOnItemClickListener(OnItemClickListener listener){
    this.listener = listener;

    }
}