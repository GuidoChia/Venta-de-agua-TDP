package skrb.appprueba.tasks;

import android.view.View;

import java.util.Collection;
import java.util.Date;

import customer.Customer;
import reader.ConcreteReader;
import skrb.appprueba.helpers.FileHelper;
import skrb.appprueba.interfaces.Updatable;

public class YearCustomerTask extends TemplateCustomerTask {


    public YearCustomerTask(Updatable observer, View view) {
        super(observer, view);
    }

    @Override
    Collection<Customer> getCustomers(Date[] date) {
        return ConcreteReader.getInstance().readCustomersYear(date, FileHelper.getPath());
    }


}
