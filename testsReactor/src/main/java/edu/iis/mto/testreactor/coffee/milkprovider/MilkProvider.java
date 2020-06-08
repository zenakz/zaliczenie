package edu.iis.mto.testreactor.coffee.milkprovider;

public interface MilkProvider {

    void heat() throws MilkProviderException;

    void pour(int milkAmount) throws MilkProviderException;

}
