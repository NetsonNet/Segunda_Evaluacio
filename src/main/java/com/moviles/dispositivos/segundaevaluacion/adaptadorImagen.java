package com.moviles.dispositivos.segundaevaluacion;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Nels on 04/11/2017.
 */

public class adaptadorImagen extends ArrayAdapter<Imagen> {
    Animation anim_combinado;

    Animation anim_zoomIN;
    Animation anim_zoomOut;

    Boolean   es_zoomIN;


    public adaptadorImagen(Context context, List<Imagen> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Obteniendo el dato
        Imagen imagen = getItem(position);
        //Inicializacion del layout correspodiente al item
        if(convertView== null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.item, parent,false);
        }
        TextView lblruta= (TextView) convertView.findViewById(R.id.lblNombre);
        ImageView image= (ImageView) convertView.findViewById(R.id.imgProfile);

        // mostrar los datos
        lblruta.setText(imagen.ruta);
        image.setImageBitmap(imagen.imagen);
        // return la convertview
        return  convertView;
    }


}
