package skrb.appprueba.tasks;

import android.os.AsyncTask;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import customer.Customer;
import reader.ConcreteCustomerManager;
import reader.ConcreteReader;
import reader.CustomerManager;
import skrb.appprueba.helpers.FileHelper;
import skrb.appprueba.interfaces.Updatable;

public abstract class TemplateCustomerTask extends AsyncTask<String, Void, CustomerManager> {
    WeakReference<Updatable> observer;

    /*
    AL HACER EL NEW TENDRIA QUE TENER POR PARAMETRO UNA INTERFACE QUE TENGA UN UPDATE O ALGO ASI
    ESTA INTERFACE LA TIENEN QUE IMPLEMENTAR LOS FRAGMENTS QUE USAN ESTA CLASE
    */
    public TemplateCustomerTask(Updatable observer){
        this.observer = new WeakReference<>(observer);
    }


    @Override
    public CustomerManager doInBackground(String... strings){
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date[] date = null;
        try {
            date = new Date[]{format.parse(strings[0])};
        } catch (ParseException e) {
            Log.e("Parse error ", e.getClass().toString(), e);
        }

        return new ConcreteCustomerManager(getCustomers(date));
    }

    abstract Collection<Customer> getCustomers(Date[] date);

    @Override
    protected void onPostExecute(CustomerManager customerManager){
        observer.get().onUpdate(customerManager);
    }



}
