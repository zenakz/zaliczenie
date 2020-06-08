package edu.iis.mto.testreactor.coffee;

import java.util.Optional;

public interface CoffeeReceipes {

    Optional<CoffeeReceipe> getReceipe(CoffeType type);
}
