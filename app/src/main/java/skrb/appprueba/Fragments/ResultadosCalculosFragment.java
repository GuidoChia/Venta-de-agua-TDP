package skrb.appprueba.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Objects;

import skrb.appprueba.R;
import skrb.appprueba.R.id;
import skrb.appprueba.R.layout;
import skrb.appprueba.R.string;

public class ResultadosCalculosFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(layout.fragment_resultados_mensuales, container, false);

        TextView txt = view.findViewById(id.text_resultados_mensuales);
        String msg = Objects.requireNonNull(getArguments()).getString("result");

        txt.setText(view.getResources().getString(string.resultado) + msg);

        return view;
    }

}
