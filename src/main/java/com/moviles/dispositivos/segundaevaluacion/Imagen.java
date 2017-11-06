package com.moviles.dispositivos.segundaevaluacion;

import android.graphics.Bitmap;

/**
 * Created by Nels on 04/11/2017.
 */

public class Imagen {
    public String ruta;
    public Bitmap imagen;

    public Imagen(String ruta, Bitmap imagen) {
        this.ruta = ruta;
        this.imagen = imagen;
    }

/*public String getRuta() {
        return Ruta;
    }

    public void setRuta(String ruta) {
        Ruta = ruta;
    }

    public String getBitmap() {
        return Bitmap;
    }

    public void setBitmap(String bitmap) {
        Bitmap = bitmap;
    }*/
}
