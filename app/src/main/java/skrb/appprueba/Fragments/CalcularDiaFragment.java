package skrb.appprueba.Fragments;

import android.Manifest;
import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.ProgressBar;

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
import skrb.appprueba.helpers.FileHelper;
import skrb.appprueba.interfaces.Updatable;
import skrb.appprueba.tasks.DayCustomerTask;
import skrb.appprueba.tasks.TemplateCustomerTask;
import skrb.appprueba.tasks.YearCustomerTask;

import static skrb.appprueba.helpers.Constants.BOTON_DINERO;
import static skrb.appprueba.helpers.Constants.BOTON_TOTAL_12;
import static skrb.appprueba.helpers.Constants.BOTON_TOTAL_20;
import static skrb.appprueba.helpers.Constants.BOTON_TOTAL_BIDONES;

public class CalcularDiaFragment extends Fragment implements DatePickerDialog.OnDateSetListener, Updatable {
    private static final int FRAGMENT_RESULTADOS = 0;
    private boolean isStopped = false;
    private WeakReference<View> viewReference;
    private int lastPressed;
    @Nullable
    private CustomerManager manager;
    private TemplateCustomerTask currentTask;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calcular_dia, container, false);
        viewReference = new WeakReference<>(view);

        final MainActivity act = (MainActivity) getActivity();
        Objects.requireNonNull(act.getSupportActionBar()).setTitle("Calculos diarios");

        initDayButton(view);


        Button btnDinero = view.findViewById(R.id.calcular_dinero_dia);
        Button btnTot = view.findViewById(R.id.calcular_total_bidones_dia);
        Button btn12 = view.findViewById(R.id.calcular_bidones_12_dia);
        Button btn20 = view.findViewById(R.id.calcular_bidones_20_dia);
        CheckBox checkBox = view.findViewById(R.id.checkbox_lista_dia);

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

    @NonNull
    private CustomerManager getCustomerManager(View view, File path) {
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

            manager = new ConcreteCustomerManager(ConcreteReader.getInstance().readCustomersDays(date, path));
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

    private void setFragment(int position, Bundle bnd) {
        MainActivity act = (MainActivity) getActivity();

        if (act != null && !isStopped) {
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
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        MainActivity act = (MainActivity) getActivity();
        Button btn = Objects.requireNonNull(act).findViewById(R.id.buttonDiaCalcular);
        btn.setText(dayOfMonth + "/" + (month + 1) + '/' + year);
        manager = null;
    }

    @Override
    public void onUpdate(CustomerManager manager) {
        this.manager = manager;
        currentTask = null;
        View view = viewReference.get();
        if (view != null) {
            ProgressBar pb = view.findViewById(R.id.progress_calcular_dia);
            pb.setVisibility(View.GONE);
            updateResultText();
        }
    }

    @Override
    public void onPreExecute() {
        View view = viewReference.get();

        if (view != null) {
            ProgressBar pb = view.findViewById(R.id.progress_calcular_dia);
            pb.setVisibility(View.VISIBLE);
        }
    }

    private void onClickedButton() {
        View view = viewReference.get();
        if (view != null) {
            Button btnDia = view.findViewById(R.id.buttonDiaCalcular);
            String dateString = btnDia.getText().toString();

            if (manager == null) {
                if (currentTask == null) {
                    currentTask = new DayCustomerTask(this, view);
                    currentTask.execute(dateString);
                }
            } else {
                updateResultText();
            }
        }
    }

    private void updateResultText() {
        View parent = viewReference.get();
        if (parent != null) {
            CheckBox checkBox = parent.findViewById(R.id.checkbox_lista_dia);

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
    public void onStop(){
        isStopped = true;
        super.onStop();
    }

    @Override
    public void onResume(){
        isStopped = false;
        super.onResume();
    }
}
