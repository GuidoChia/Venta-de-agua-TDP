package skrb.appprueba.tasks;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import java.lang.ref.WeakReference;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import customer.Customer;
import reader.ConcreteCustomerManager;
import reader.CustomerManager;
import skrb.appprueba.interfaces.Updatable;

public abstract class TemplateCustomerTask extends AsyncTask<String, Void, CustomerManager> {
    protected WeakReference<Updatable> observerReference;
    protected WeakReference<View> viewReference;

    public TemplateCustomerTask(Updatable observer, View view) {
        this.observerReference = new WeakReference<>(observer);
        this.viewReference = new WeakReference<>(view);
    }


    @Override
    public CustomerManager doInBackground(String... strings) {
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
    protected void onPostExecute(CustomerManager customerManager) {
        Updatable observer = observerReference.get();

        if (observer != null) {
            observer.onUpdate(customerManager);
        }
    }

    protected void onPreExecute(){
        Updatable observer =observerReference.get();

        if(observer!=null){
            observer.onPreExecute();
        }
    }


}
