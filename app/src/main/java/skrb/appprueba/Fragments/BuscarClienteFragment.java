package skrb.appprueba.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

import exceptions.WorkbookException;
import infos.OutputInfo;
import reader.ConcreteReader;
import reader.ExcelReader;
import skrb.appprueba.MainActivity;
import skrb.appprueba.R;
import skrb.appprueba.helpers.fileRW;

import static skrb.appprueba.helpers.fileRW.initClientes;


public class BuscarClienteFragment extends Fragment {
    private final int FRAGMENT_RESULTADOS = 0;

    private final static String[] CLIENTES = initClientes();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_buscar_cliente, container, false);

        final MainActivity act = (MainActivity) getActivity();
        act.getSupportActionBar().setTitle("Buscar Cliente");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, CLIENTES);
        AutoCompleteTextView textView =
                view.findViewById(R.id.InputBuscar);

        textView.setAdapter(adapter);

        Button btn = view.findViewById(R.id.BotonBuscarCliente);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AutoCompleteTextView txt = getActivity().findViewById(R.id.InputBuscar);
                Editable nombreEditable = txt.getText();
                String name = nombreEditable.toString();

                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);

                View focus = getActivity().getCurrentFocus();
                if (focus != null) {
                    imm.hideSoftInputFromWindow(focus.getWindowToken(), 0);
                }

                if (name.length() == 0) {
                    showError();
                } else {
                    ExcelReader reader = ConcreteReader.getInstance();
                    File file = fileRW.findFileRead(name);
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

                        bnd.putInt("twelveBalance", out.getTwelveBalance());

                        bnd.putInt("twentyBalance", out.getTwentyBalance());

                        bnd.putInt("canistersBalance", out.getCanistersBalance());

                        setFragment(FRAGMENT_RESULTADOS, bnd);
                    } else {
                        showError();
                    }
                }
            }
        });

        return view;
    }

    private void showError() {
        Bundle bnd = new Bundle();
        bnd.putInt("msg", R.string.errorBusqueda);
        ErrorFragment frag = new ErrorFragment();
        frag.setArguments(bnd);
        frag.show(getFragmentManager(), "error");
    }



    private void setFragment(int position, Bundle bnd) {
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
