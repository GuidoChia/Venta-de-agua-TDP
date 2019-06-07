package skrb.appprueba.Fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.snackbar.Snackbar;

import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;

import customer.Customer;
import reader.ConcreteCustomerManager;
import reader.ConcreteReader;
import reader.ExcelReader;
import skrb.appprueba.MainActivity;
import skrb.appprueba.R;
import skrb.appprueba.helpers.FileHelper;
import writer.ConcreteWriter;
import writer.ExcelWriter;

public class RecorridoFragment extends Fragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_calcular_recorrido, container, false);
        final MainActivity act = (MainActivity) getActivity();
        Objects.requireNonNull(act.getSupportActionBar()).setTitle("Calcular recorrido");

        Button btn = view.findViewById(R.id.BotonCalcularRecorrido);
        if (ContextCompat.checkSelfPermission(this.getContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            btn.setEnabled(false);
        } else {
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Date today = Calendar.getInstance().getTime();
                    Date lastMonth = getMonthBefore(today);

                    if (lastMonth == null) {
                        return;
                    }

                    ExcelReader reader = ConcreteReader.getInstance();
                    Date[] months = new Date[]{lastMonth, today};
                    Collection<Customer> customers = reader.readCustomersMonth(months, FileHelper.getPath());
                    Collection<Customer> routeCustomers = new ConcreteCustomerManager(customers).getRoute();

                    ExcelWriter writer = ConcreteWriter.getInstance();
                    writer.WriteRoute(routeCustomers, FileHelper.createFileRoute());

                    Snackbar snackbarAgregado = Snackbar.make(view, R.string.msg_recorrido_creado, Snackbar.LENGTH_LONG);
                    snackbarAgregado.show();

                    btn.setEnabled(false);


                }

                private Date getMonthBefore(Date date) {
                    DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                    String dateString = format.format(date);
                    String[] strings = dateString.split("/");
                    String resString;
                    if (Integer.parseInt(strings[1]) == 1) {
                        strings[1] = "12";
                        int year = Integer.parseInt(strings[2]) - 1;
                        strings[2] = String.valueOf(year);
                    } else {
                        int month = Integer.parseInt(strings[1]) - 1;
                        strings[1] = String.valueOf(month);
                    }

                    resString = strings[0] + '/' + strings[1] + '/' + strings[2];

                    try {
                        return format.parse(resString);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return null;

                }
            });
        }

        return view;

    }
}
