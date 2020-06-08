package edu.iis.mto.testreactor.coffee;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

import java.util.Optional;

import edu.iis.mto.testreactor.coffee.milkprovider.MilkProvider;
import edu.iis.mto.testreactor.coffee.milkprovider.MilkProviderException;

public class CoffeeMachine {

    private final Grinder grinder;
    private final MilkProvider milkProvider;
    private final CoffeeReceipes receipes;

    public CoffeeMachine(Grinder grinder, MilkProvider milkProvider, CoffeeReceipes receipes) {
        this.grinder = requireNonNull(grinder, "ginder == null");
        this.milkProvider = requireNonNull(milkProvider, "milkProvider == null");
        this.receipes = requireNonNull(receipes, "receipes == null");
    }

    public Coffee make(CoffeOrder order) {
        double coffeWeightGr = grindCoffee(order.getSize());
        Coffee coffee = create(order, coffeWeightGr);
        addMilk(order, coffee);
        return coffee;
    }

    private double grindCoffee(CoffeeSize coffeeSize) {
        if (!grinder.canGrindFor(coffeeSize)) {
            throw new NoCoffeeBeansException();
        }
        return grinder.grind(coffeeSize);
    }

    private Coffee create(CoffeOrder order, double coffeeWeightGr) {
        CoffeeReceipe receipe = getReceipe(order.getType());
        Coffee coffee = new Coffee();
        coffee.setCoffeeWeigthGr(coffeeWeightGr);
        coffee.setWaterAmount(getWaterAmount(order, receipe));
        return coffee;
    }

    private void addMilk(CoffeOrder order, Coffee coffee) {
        if (isMilkCoffee(order.getType())) {
            try {
                int milkAmount = getReceipe(order.getType()).getMilkAmount();
                milkProvider.heat();
                milkProvider.pour(milkAmount);
                coffee.setMilkAmout(milkAmount);
            } catch (MilkProviderException e) {
                coffee.setMilkAmout(0);
            }
        }
    }

    private CoffeeReceipe getReceipe(CoffeType type) {
        Optional<CoffeeReceipe> receipe = receipes.getReceipe(type);
        if (receipe.isEmpty()) {
            throw new UnsupportedCoffeeException(type);
        }
        return receipe.get();
    }

    private boolean isMilkCoffee(CoffeType type) {
        return getReceipe(type).withMilk();
    }

    private int getWaterAmount(CoffeOrder order, CoffeeReceipe receipe) {
        Integer waterAmount = receipe.getWaterAmount(order.getSize());
        if (isNull(waterAmount)) {
            throw new UnsupportedCoffeeSizeException(order.getType(), order.getSize());
        }
        return waterAmount;
    }
}
