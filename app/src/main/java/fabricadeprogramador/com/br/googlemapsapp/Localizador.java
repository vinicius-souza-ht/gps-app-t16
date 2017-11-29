package fabricadeprogramador.com.br.googlemapsapp;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;

/**
 * Created by viniciuspodi on 28/11/17.
 */

public class Localizador {

    private Context context;

    public Localizador(Context context){
        this.context = context;
    }

    public LatLng buscarCoordenada(String endereco){

        Geocoder geocoder =  new Geocoder(context);

        try{
            List<Address> enderecos = geocoder.getFromLocationName(endereco, 1);
            Address enderecoLocalizado = enderecos.get(0);

            double latitude = enderecoLocalizado.getLatitude();
            double longitude = enderecoLocalizado.getLongitude();

            return new LatLng(latitude, longitude);
        }catch (IOException e){
            return null;
        }
    }
}
