package skrb.appprueba.interfaces;

import reader.CustomerManager;

public interface Updatable {
    void onUpdate(CustomerManager manager);
    void onPreExecute();
}
