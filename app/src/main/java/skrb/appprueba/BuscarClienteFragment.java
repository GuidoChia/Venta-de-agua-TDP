package skrb.appprueba;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class BuscarClienteFragment extends Fragment {
    private final int FRAGMENT_RESULTADOS=0;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final View view = inflater.inflate(R.layout.fragment_buscar_cliente, container, false);

        final MainActivity act = (MainActivity) getActivity();
        act.getSupportActionBar().setTitle("Buscar Cliente");

        Button btn = view.findViewById(R.id.BotonBuscarCliente);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextInputEditText txt=getActivity().findViewById(R.id.InputBuscar);
                Editable nombre = txt.getText();
                Bundle bnd = new Bundle();
                bnd.putString("nombre",nombre.toString());
                setFragment(FRAGMENT_RESULTADOS,bnd);
            }
        });

        return view;
    }


    public void setFragment(int position, Bundle bnd) {
        MainActivity act = (MainActivity) getActivity();

        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;
        Fragment frag= null;
        switch (position) {
            case FRAGMENT_RESULTADOS:
                fragmentManager = act.getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                frag = new MostrarResultadosFragment();
                frag.setArguments(bnd);
                fragmentTransaction.replace(R.id.fragment_resultados, frag);
                fragmentTransaction.commit();
                break;
        }
    }
}
