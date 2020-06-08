package edu.iis.mto.testreactor.coffee;

import java.util.Optional;

public class Coffee {

    private Integer waterAmount;
    private Double coffeeWeigthGr;
    private Integer milkAmout;

    public Integer getWaterAmount() {
        return waterAmount;
    }

    public void setWaterAmount(Integer waterAmount) {
        this.waterAmount = waterAmount;
    }

    public Double getCoffeeWeigthGr() {
        return coffeeWeigthGr;
    }

    public void setCoffeeWeigthGr(Double coffeeWeigthGr) {
        this.coffeeWeigthGr = coffeeWeigthGr;
    }

    public Optional<Integer> getMilkAmout() {
        return Optional.ofNullable(milkAmout);
    }

    public void setMilkAmout(Integer milkAmout) {
        this.milkAmout = milkAmout;
    }

}
