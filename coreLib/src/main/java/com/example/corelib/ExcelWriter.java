package com.example.corelib;

/**
 * The interface that represents a writer of BuyInfo in an excel file
 * @author Guido Chia
 */
public interface ExcelWriter {
    /**
     * Writes a new buy with the info given in a BuyInfo.
     * If the customer is new, it creates the correspondent file.
     * @param info BuyInfo object that contains the info of the buy
     */
    void WriteBuy(BuyInfo info);
}