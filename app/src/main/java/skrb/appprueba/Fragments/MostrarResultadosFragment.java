package skrb.appprueba.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Objects;

import skrb.appprueba.R.id;
import skrb.appprueba.R.layout;
import skrb.appprueba.R.string;

public class MostrarResultadosFragment extends Fragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(layout.fragment_mostrar_resultados, container, false);

        TextView txt = view.findViewById(id.textResultadoNombre);
        String msg = Objects.requireNonNull(getArguments()).getString("name");
        txt.setText(getResources().getString(string.nombre) + msg);

        txt = view.findViewById(id.textResultadoSaldo);
        msg = Double.toString(getArguments().getDouble("balance"));
        txt.setText(getResources().getString(string.saldo) + msg);

        txt = view.findViewById(id.textResultadoSaldoDe12);
        msg = Integer.toString(getArguments().getInt("twelveBalance"));
        txt.setText(getResources().getString(string.saldoDe12) + msg);

        txt = view.findViewById(id.textResultadoSaldoDe20);
        msg = Integer.toString(getArguments().getInt("twentyBalance"));
        txt.setText(getResources().getString(string.saldoDe20) + msg);

        txt = view.findViewById(id.textResultadoUltimaCompra);
        msg = getArguments().getString("date");
        txt.setText(getResources().getString(string.ultimaFecha) + msg);

        return view;
    }

}
