package skrb.appprueba.Fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Objects;

import skrb.appprueba.R.id;
import skrb.appprueba.R.layout;
import skrb.appprueba.R.string;

public class ResultadosCalculosFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(layout.fragment_resultados_mensuales, container, false);

        TextView res = view.findViewById(id.text_resultados_mensuales);
        TextView list = view.findViewById(id.text_lista_resultados);

        String msg = Objects.requireNonNull(getArguments()).getString("result");
        res.setText(view.getResources().getString(string.resultado) + msg);

        msg = Objects.requireNonNull(getArguments()).getString("list");
        list.setText(msg);

        return view;
    }

}
