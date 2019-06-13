package skrb.appprueba.tasks;

import android.view.View;

import java.io.File;
import java.util.Collection;
import java.util.Date;

import customer.Customer;
import reader.ConcreteCustomerManager;
import reader.ConcreteReader;
import skrb.appprueba.helpers.FileHelper;
import skrb.appprueba.interfaces.Updatable;

public class MonthCustomerTask extends TemplateCustomerTask {
    public MonthCustomerTask(Updatable observer, View view) {
        super(observer, view);
    }

    @Override
    Collection<Customer> getCustomers(Date[] date) {
        return ConcreteReader.getInstance().readCustomersMonth(date, FileHelper.getPath());
    }
}
