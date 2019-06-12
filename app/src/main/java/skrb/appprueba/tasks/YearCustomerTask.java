package skrb.appprueba.tasks;

import java.util.Collection;
import java.util.Date;

import customer.Customer;
import reader.ConcreteReader;
import reader.ExcelReader;
import skrb.appprueba.helpers.FileHelper;
import skrb.appprueba.interfaces.Updatable;

public class YearCustomerTask extends TemplateCustomerTask {


    public YearCustomerTask(Updatable observer) {
        super(observer);
    }

    @Override
    Collection<Customer> getCustomers(Date[] date) {
        return ConcreteReader.getInstance().readCustomersYear(date, FileHelper.getPath());
    }
}
