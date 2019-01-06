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

        TextView txt = view.findViewById(R.id.textResultado);
        String msg = getArguments().getString("nombre");
        txt.setText(msg);

        return view;
    }

}
