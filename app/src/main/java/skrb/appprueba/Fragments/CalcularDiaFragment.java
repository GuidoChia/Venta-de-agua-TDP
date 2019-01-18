package skrb.appprueba.Fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import reader.ConcreteCustomerManager;
import reader.ConcreteReader;
import reader.CustomerManager;
import skrb.appprueba.MainActivity;
import skrb.appprueba.R;
import skrb.appprueba.helpers.fileRW;

public class CalcularDiaFragment extends Fragment implements DatePickerDialog.OnDateSetListener {
    private static final int FRAGMENT_RESULTADOS = 0;
    public static final int MIN_YEAR = 1990;
    public static final int MAX_YEAR = 2050;
    @Nullable
    CustomerManager manager;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calcular_dia, container, false);
        final MainActivity act = (MainActivity) getActivity();
        Objects.requireNonNull(act.getSupportActionBar()).setTitle("Calculos diarios");

        initDayButton(view);

        File path = fileRW.getPath();

        Button btn = view.findViewById(R.id.calcular_dinero_dia);
        btn.setOnClickListener(v -> {
            CustomerManager manager = getCustomerManager(view, path);

            Bundle bnd = new Bundle();
            bnd.putString("result", String.valueOf(manager.getPaid()));

            setFragment(FRAGMENT_RESULTADOS, bnd);
        });

        btn = view.findViewById(R.id.calcular_bidones_12_dia);
        btn.setOnClickListener(v -> {
            CustomerManager manager = getCustomerManager(view, path);

            Bundle bnd = new Bundle();
            bnd.putString("result", String.valueOf(manager.getTwelveBought()));

            setFragment(FRAGMENT_RESULTADOS, bnd);
        });

        btn = view.findViewById(R.id.calcular_bidones_20_dia);
        btn.setOnClickListener(v -> {

            CustomerManager manager = getCustomerManager(view, path);

            Bundle bnd = new Bundle();
            bnd.putString("result", String.valueOf(manager.getTwentyBought()));

            setFragment(FRAGMENT_RESULTADOS, bnd);
        });

        btn = view.findViewById(R.id.calcular_total_bidones_dia);
        btn.setOnClickListener(v -> {
            CustomerManager manager = getCustomerManager(view, path);

            Bundle bnd = new Bundle();
            int res = manager.getTwelveBought() + manager.getTwentyBought();
            bnd.putString("result", String.valueOf(res));

            setFragment(FRAGMENT_RESULTADOS, bnd);
        });

        return view;
    }

    @NonNull
    CustomerManager getCustomerManager(View view, File path) {
        if (manager == null) {
            Button btnDia = view.findViewById(R.id.buttonDiaCalcular);
            String dateString = btnDia.getText().toString();
            DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            Date[] date = null;
            try {
                date = new Date[]{format.parse(dateString)};
            } catch (ParseException e) {
                Log.e("Parse error ", e.getStackTrace().toString());
            }

            manager = new ConcreteCustomerManager(ConcreteReader.getInstance().readCostumersDays(date, path));
        }

        return manager;
    }


    private void initDayButton(View view) {
        int day, month, year;
        Calendar actualDate = Calendar.getInstance();
        day = actualDate.get(Calendar.DAY_OF_MONTH);
        month = actualDate.get(Calendar.MONTH);
        year = actualDate.get(Calendar.YEAR);
        final DatePickerDialog dialogFecha = new DatePickerDialog(Objects.requireNonNull(getContext()), this, year, month, day);
        Button botonFecha = view.findViewById(R.id.buttonDiaCalcular);
        botonFecha.setText(day + "/" + (month + 1) + '/' + year);
        botonFecha.setOnClickListener(v -> dialogFecha.show());
    }

    public void setFragment(int position, Bundle bnd) {
        MainActivity act = (MainActivity) getActivity();

        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;
        Fragment frag;
        switch (position) {
            case FRAGMENT_RESULTADOS:
                fragmentManager = Objects.requireNonNull(act).getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                frag = new ResultadosCalculosFragment();
                frag.setArguments(bnd);
                fragmentTransaction.replace(R.id.fragment_resultados_dia, frag);
                fragmentTransaction.commit();
                break;

        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        MainActivity act = (MainActivity) getActivity();
        Button btn = Objects.requireNonNull(act).findViewById(R.id.buttonDiaCalcular);
        btn.setText(dayOfMonth + "/" + (month + 1) + '/' + year);
        manager = null;
    }
}