package skrb.appprueba.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import skrb.appprueba.R;

public class MostrarResultadosFragment extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final View view = inflater.inflate(R.layout.fragment_mostrar_resultados, container, false);

        TextView txt = view.findViewById(R.id.textResultadoNombre);
        String msg = getArguments().getString("name");
        txt.setText(getResources().getString(R.string.nombre)+msg);

        txt = view.findViewById(R.id.textResultadoSaldo);
        msg = Double.toString(getArguments().getDouble("balance"));
        txt.setText(getResources().getString(R.string.saldo)+msg);

        txt = view.findViewById(R.id.textResultadoSaldoDe12);
        msg = Integer.toString(getArguments().getInt("twelveBalance"));
        txt.setText(getResources().getString(R.string.saldoDe12)+msg);

        txt = view.findViewById(R.id.textResultadoSaldoDe20);
        msg = Integer.toString(getArguments().getInt("twentyBalance"));
        txt.setText(getResources().getString(R.string.saldoDe20)+msg);

        txt = view.findViewById(R.id.textResultadoUltimaCompra);
        msg = getArguments().getString("date");
        txt.setText(getResources().getString(R.string.ultimaFecha)+msg);

        return view;
    }

}
