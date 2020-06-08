package edu.iis.mto.testreactor.coffee;

public interface Grinder {

    boolean canGrindFor(CoffeeSize size);

    /**
     * @param size
     *            - size of the coffee
     * @return weight of grid coffee in grams
     */
    double grind(CoffeeSize size);

}
