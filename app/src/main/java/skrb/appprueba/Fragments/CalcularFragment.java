package skrb.appprueba.Fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.content.ContextCompat;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;

import com.whiteelephant.monthpicker.MonthPickerDialog;

import java.io.File;
import java.lang.ref.WeakReference;
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
import skrb.appprueba.R.id;
import skrb.appprueba.R.layout;
import skrb.appprueba.helpers.FileHelper;
import skrb.appprueba.interfaces.Updatable;
import skrb.appprueba.tasks.MonthCustomerTask;
import skrb.appprueba.tasks.TemplateCustomerTask;

import static skrb.appprueba.helpers.Constants.BOTON_DINERO;
import static skrb.appprueba.helpers.Constants.BOTON_TOTAL_12;
import static skrb.appprueba.helpers.Constants.BOTON_TOTAL_20;
import static skrb.appprueba.helpers.Constants.BOTON_TOTAL_BIDONES;

public class CalcularFragment extends Fragment implements Updatable {
    private static final int FRAGMENT_RESULTADOS = 0;
    private static final int MIN_YEAR = 1990;
    private static final int MAX_YEAR = 2050;

    private int lastPressed;

    @Nullable
    private CustomerManager manager;
    private WeakReference<View> viewReference;
    private TemplateCustomerTask currentTask;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(layout.fragment_calcular, container, false);
        viewReference = new WeakReference<>(view);

        final MainActivity act = (MainActivity) getActivity();
        Objects.requireNonNull(act.getSupportActionBar()).setTitle("Calculos mensuales");

        initMonthButton(view);

        Button btnDinero = view.findViewById(id.calcular_dinero_mensual);
        Button btn12 = view.findViewById(id.calcular_bidones_12);
        Button btn20 = view.findViewById(id.calcular_bidones_20);
        Button btnTot = view.findViewById(id.calcular_total_bidones);
        CheckBox checkBox = view.findViewById(id.checkbox_lista_mes);

        if (ContextCompat.checkSelfPermission(this.getContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            btn12.setEnabled(false);
            btn20.setEnabled(false);
            btnDinero.setEnabled(false);
            btnTot.setEnabled(false);
        } else {
            btnDinero.setOnClickListener(v -> {
                lastPressed = BOTON_DINERO;
                onClickedButton();
            });

            btn12.setOnClickListener(v -> {
                lastPressed = BOTON_TOTAL_12;
                onClickedButton();
            });

            btn20.setOnClickListener(v -> {
                lastPressed = BOTON_TOTAL_20;
                onClickedButton();
            });

            btnTot.setOnClickListener(v -> {
                lastPressed = BOTON_TOTAL_BIDONES;
                onClickedButton();
            });
        }

        return view;
    }


    private void initMonthButton(View view) {
        int month, year;
        Calendar actualDate = Calendar.getInstance();
        month = actualDate.get(Calendar.MONTH);
        year = actualDate.get(Calendar.YEAR);

        Button btn = view.findViewById(id.buttonMesAño);
        btn.setText((month + 1) + "/" + year);

        btn.setOnClickListener(v -> {
            MonthPickerDialog.Builder builder;
            builder = new MonthPickerDialog.Builder(getContext(), (selectedMonth, selectedYear) -> {
                btn.setText((selectedMonth + 1) + "/" + selectedYear);
                manager = null;
            }, year, month);

            builder.setActivatedMonth(month)
                    .setMinYear(MIN_YEAR)
                    .setActivatedYear(year)
                    .setMaxYear(MAX_YEAR)
                    .setMinMonth(Calendar.JANUARY)
                    .setTitle(getString(R.string.elija_mes))
                    .setMonthRange(Calendar.JANUARY, Calendar.DECEMBER)
                    .build()
                    .show();
        });
    }

    private void setFragment(int position, Bundle bnd) {
        MainActivity act = (MainActivity) getActivity();

        if (act != null) {

            FragmentManager fragmentManager;
            FragmentTransaction fragmentTransaction;
            Fragment frag;
            switch (position) {
                case FRAGMENT_RESULTADOS:
                    fragmentManager = Objects.requireNonNull(act).getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    frag = new ResultadosCalculosFragment();
                    frag.setArguments(bnd);
                    fragmentTransaction.replace(id.fragment_resultados_mes, frag);
                    fragmentTransaction.commit();
                    break;

            }
        }
    }

    @Override
    public void onUpdate(CustomerManager manager) {
        this.manager = manager;
        currentTask = null;
        View view = viewReference.get();
        if (view != null) {
            ProgressBar pb = view.findViewById(R.id.progress_calcular_mes);
            pb.setVisibility(View.GONE);
            updateResultText();
        }
    }

    private void updateResultText() {
        View parent = viewReference.get();
        if (parent != null) {
            CheckBox checkBox = parent.findViewById(id.checkbox_lista_mes);

            Bundle bnd = new Bundle();

            if (checkBox.isChecked()) {
                bnd.putString("list", manager.toString());
            } else {
                bnd.putString("list", "");
            }

            double res = 0;
            String stringRes = "";

            switch (lastPressed) {
                case BOTON_DINERO:
                    res = manager.getPaid();
                    stringRes = String.valueOf(res);
                    break;
                case BOTON_TOTAL_12:
                    res = manager.getTwelveBought();
                    stringRes = String.valueOf((int) res);
                    break;
                case BOTON_TOTAL_20:
                    res = manager.getTwentyBought();
                    stringRes = String.valueOf((int) res);
                    break;
                case BOTON_TOTAL_BIDONES:
                    res = manager.getTwentyBought() + manager.getTwelveBought();
                    stringRes = String.valueOf((int) res);
            }


            bnd.putString("result", stringRes);

            setFragment(FRAGMENT_RESULTADOS, bnd);
        }
    }

    @Override
    public void onPreExecute() {
        View view = viewReference.get();

        if (view != null) {
            ProgressBar pb = view.findViewById(R.id.progress_calcular_mes);
            pb.setVisibility(View.VISIBLE);
        }
    }

    private void onClickedButton() {
        View view = viewReference.get();
        if (view != null) {
            Button btnMes = view.findViewById(id.buttonMesAño);
            String dateString = "1/" + btnMes.getText().toString();

            if (manager == null) {
                if (currentTask == null) {
                    currentTask = new MonthCustomerTask(this, view);
                    currentTask.execute(dateString);
                }
            } else {
                updateResultText();
            }
        }
    }
}
