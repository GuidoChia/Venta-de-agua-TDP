package skrb.appprueba;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.AsyncTaskLoader;
import android.view.View;
import android.widget.ProgressBar;

import java.lang.ref.WeakReference;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import customer.Customer;
import reader.ConcreteCustomerManager;
import reader.ConcreteReader;
import reader.ExcelReader;
import skrb.appprueba.helpers.fileRW;
import writer.ConcreteWriter;
import writer.ExcelWriter;

public class RouteTask extends AsyncTask<Void, Void, Void> {
    private WeakReference<View> viewReference;

    public RouteTask(View view) {
        super();
        viewReference = new WeakReference<>(view);
    }


    @Override
    protected void onPreExecute() {
        ProgressBar pb = viewReference.get().findViewById(R.id.progress_recorrido);
        pb.setVisibility(View.VISIBLE);

    }

    @Override
    protected void onPostExecute(Void param) {
        if (viewReference.get() != null) {
            ProgressBar pb = viewReference.get().findViewById(R.id.progress_recorrido);
            pb.setVisibility(View.INVISIBLE);
            Snackbar snackbarAgregado = Snackbar.make(viewReference.get(), R.string.msg_recorrido_creado, Snackbar.LENGTH_LONG);
            snackbarAgregado.show();
        }
    }

    @Override
    public Void doInBackground(Void... paramams) {
        Date today = Calendar.getInstance().getTime();
        Date lastMonth = getMonthBefore(today);
        if (lastMonth == null) {
            return null;
        }

        ExcelReader reader = ConcreteReader.getInstance();
        Date[] months = new Date[]{lastMonth, today};
        Collection<Customer> customers = reader.readCostumersMonth(months, fileRW.getPath());
        Collection<Customer> routeCustomers = new ConcreteCustomerManager(customers).getRoute();

        ExcelWriter writer = ConcreteWriter.getInstance();
        writer.WriteRoute(routeCustomers, fileRW.createFileRoute());

        return null;
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
}
