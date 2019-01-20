package skrb.appprueba.Fragments;

import android.R.layout;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.util.Log;
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
import java.util.Objects;

import exceptions.WorkbookException;
import infos.OutputInfo;
import reader.ConcreteReader;
import reader.ExcelReader;
import skrb.appprueba.MainActivity;
import skrb.appprueba.R;
import skrb.appprueba.R.id;
import skrb.appprueba.R.string;
import skrb.appprueba.helpers.fileRW;

import static skrb.appprueba.helpers.fileRW.initClientes;


public class BuscarClienteFragment extends Fragment {
    private static final int FRAGMENT_RESULTADOS = 0;

    private static final String[] CLIENTES = initClientes();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_buscar_cliente, container, false);

        final MainActivity act = (MainActivity) getActivity();
        Objects.requireNonNull(act.getSupportActionBar()).setTitle("Buscar Cliente");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(Objects.requireNonNull(getActivity()),
                layout.simple_dropdown_item_1line, CLIENTES);
        AutoCompleteTextView textView =
                view.findViewById(id.InputBuscar);

        textView.setAdapter(adapter);

        Button btn = view.findViewById(id.BotonBuscarCliente);
        btn.setOnClickListener(v -> {
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
                OutputInfo out;
                if (file.exists()) {
                    try {
                        out = reader.readInfo(file);
                    } catch (WorkbookException e) {
                        Log.e("Workbook error ", e.getClass().toString(), e);
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
        });

        return view;
    }

    private void showError() {
        Bundle bnd = new Bundle();
        bnd.putInt("msg", string.errorBusqueda);
        DialogFragment frag = new ErrorFragment();
        frag.setArguments(bnd);
        frag.show(Objects.requireNonNull(getFragmentManager()), "error");
    }



    private void setFragment(int position, Bundle bnd) {
        MainActivity act = (MainActivity) getActivity();

        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;
        Fragment frag;
        switch (position) {
            case FRAGMENT_RESULTADOS:
                fragmentManager = Objects.requireNonNull(act).getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                frag = new MostrarResultadosFragment();
                frag.setArguments(bnd);
                fragmentTransaction.replace(id.fragment_resultados, frag);
                fragmentTransaction.commit();
                break;
        }
    }
}
