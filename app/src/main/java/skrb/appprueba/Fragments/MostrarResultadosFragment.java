package skrb.appprueba.Fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

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
        txt.setTypeface(txt.getTypeface(), Typeface.BOLD);
        txt.setText(msg);


        txt = view.findViewById(id.textResultadoSaldo);
        msg = Double.toString(getArguments().getDouble("balance"));
        txt.setText(getResources().getString(string.saldo) + " " + msg);

        txt = view.findViewById(id.textResultadoSaldoDe12);
        msg = Integer.toString(getArguments().getInt("twelveBalance"));
        txt.setText(getResources().getString(string.saldoDe12) + " " + msg);

        txt = view.findViewById(id.textResultadoSaldoDe20);
        msg = Integer.toString(getArguments().getInt("twentyBalance"));
        txt.setText(getResources().getString(string.saldoDe20) + " " + msg);

        txt = view.findViewById(id.textResultadoUltimaCompra);
        msg = getArguments().getString("date");
        txt.setText(getResources().getString(string.ultimaFecha) + " " + msg);

        txt = view.findViewById(id.textResultadoCompradosDe12);
        msg = Integer.toString(getArguments().getInt("twelveBought"));
        txt.setText(getResources().getString(string.comprados_de_12) + " " + msg);

        txt = view.findViewById(id.textResultadoCompradosDe20);
        msg = Integer.toString(getArguments().getInt("twentyBought"));
        txt.setText(getResources().getString(string.comprados_de_20) + " " + msg);

        txt = view.findViewById(id.descripcion);
        msg = getArguments().getString("description");
        if (!TextUtils.isEmpty(msg)) {
            txt.setText(getResources().getString(string.descripcion) + " " + msg);
        } else {
            txt.setVisibility(View.GONE);
        }

        return view;
    }

}
