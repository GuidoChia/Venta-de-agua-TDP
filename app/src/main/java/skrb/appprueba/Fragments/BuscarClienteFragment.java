package skrb.appprueba.Fragments;

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

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import exceptions.WorkbookException;
import infos.OutputInfo;
import reader.ConcreteReader;
import reader.ExcelReader;
import skrb.appprueba.MainActivity;
import skrb.appprueba.R;
import skrb.appprueba.helpers.fileRW;

public class BuscarClienteFragment extends Fragment {
    private final int FRAGMENT_RESULTADOS = 0;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_buscar_cliente, container, false);

        final MainActivity act = (MainActivity) getActivity();
        act.getSupportActionBar().setTitle("Buscar Cliente");

        Button btn = view.findViewById(R.id.BotonBuscarCliente);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextInputEditText txt = getActivity().findViewById(R.id.InputBuscar);
                Editable nombreEditable = txt.getText();
                String name = nombreEditable.toString();

                ExcelReader reader = ConcreteReader.getInstance();
                File file = fileRW.findFile(name);
                OutputInfo out = null;
                if (file.exists()) {
                    try {
                        out = reader.readInfo(file);
                    } catch (WorkbookException e) {
                        e.printStackTrace();
                        return;
                    }
                    Bundle bnd = new Bundle();

                    bnd.putString("name", name);

                    bnd.putDouble("balance", out.getBalance());

                    DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                    String dateString = format.format(out.getLastDate());
                    bnd.putString("date", dateString);

                    bnd.putInt("twelveBalance",out.getTwelveBalance());

                    bnd.putInt("twentyBalance", out.getTwentyBalance());

                    bnd.putInt("canistersBalance", out.getCanistersBalance());

                    setFragment(FRAGMENT_RESULTADOS, bnd);
                }
                else{
                    //Mostrar Error
                }

            }
        });

        return view;
    }


    public void setFragment(int position, Bundle bnd) {
        MainActivity act = (MainActivity) getActivity();

        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;
        Fragment frag = null;
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
