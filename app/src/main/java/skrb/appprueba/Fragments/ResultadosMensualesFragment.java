package skrb.appprueba.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Calendar;

import skrb.appprueba.R;

public class ResultadosMensualesFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_resultados_mensuales, container, false);

        TextView txt = view.findViewById(R.id.text_resultados_mensuales);
        String msg = getArguments().getString("result");

        txt.setText(view.getResources().getString(R.string.resultado_mensual) + msg);

        return view;
    }

}
