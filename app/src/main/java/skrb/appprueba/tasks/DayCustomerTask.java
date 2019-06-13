package skrb.appprueba.tasks;

import android.view.View;

import java.util.Collection;
import java.util.Date;

import customer.Customer;
import reader.ConcreteReader;
import skrb.appprueba.helpers.FileHelper;
import skrb.appprueba.interfaces.Updatable;

public class DayCustomerTask extends TemplateCustomerTask {
    public DayCustomerTask(Updatable observer, View view) {
        super(observer, view);
    }

    @Override
    Collection<Customer> getCustomers(Date[] date) {
        return ConcreteReader.getInstance().readCustomersDays(date, FileHelper.getPath());
    }
}
