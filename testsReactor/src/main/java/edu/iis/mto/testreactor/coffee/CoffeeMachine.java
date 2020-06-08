package edu.iis.mto.testreactor.coffee;

import java.util.Objects;

public class CoffeeMachine {

    private final Grinder grinder;
    private final MilkProvider milkProvider;
    private final CoffeeReceipes receipes;

    public CoffeeMachine(Grinder grinder, MilkProvider milkProvider, CoffeeReceipes receipes) {
        this.grinder = Objects.requireNonNull(grinder, "ginder == null");
        this.milkProvider = Objects.requireNonNull(milkProvider, "milkProvider == null");
        this.receipes = Objects.requireNonNull(receipes, "receipes == null");
    }

    public Coffee make(CoffeOrder order) {
        grindCoffee(order.getSize());
        Coffee coffee = create(order);
        addMilk(order, coffee);
        return coffee;
    }

    private void addMilk(CoffeOrder order, Coffee coffee) {
        if (isMilkCoffee(order.getType())) {
            int milkAmount = receipes.getReceipe(order.getType())
                                     .getMilkAmount();
            milkProvider.heat();
            milkProvider.pour(milkAmount);
            coffee.setMilkAmout(milkAmount);
        }
    }

    private boolean isMilkCoffee(CoffeType type) {
        return receipes.getReceipe(type)
                       .withMilk();
    }

    private void grindCoffee(CoffeeSize coffeeSize) {
        if (!grinder.grind(coffeeSize)) {
            throw new NoCoffeeBeansException();
        }
    }

    private Coffee create(CoffeOrder order) {
        Coffee coffee = new Coffee();
        CoffeeReceipe receipe = receipes.getReceipe(order.getType());
        if (Objects.isNull(receipe)) {
            throw new IllegalArgumentException("no receipe for order " + order);
        }
        Integer waterAmount = receipe.getWaterAmount(order.getSize());
        coffee.setWaterAmount(waterAmount);
        return coffee;
    }
}
