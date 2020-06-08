package edu.iis.mto.testreactor.coffee;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import edu.iis.mto.testreactor.coffee.milkprovider.MilkProvider;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

@ExtendWith(MockitoExtension.class) class CoffeeMachineTest {

    @Mock private Grinder grinder;
    @Mock private MilkProvider milkProvider;
    @Mock private CoffeeReceipes coffeeReceipes;

    private CoffeeMachine coffeeMachine;

    private Map<CoffeeSize, Integer> waterAmounts = new HashMap<>();

    @BeforeEach void setUp() {
        coffeeMachine = new CoffeeMachine(grinder, milkProvider, coffeeReceipes);
        waterAmounts.put(CoffeeSize.DOUBLE, 10);
    }

    @Test void properMilklessOrderShouldResultInMilklessCoffee() {
        CoffeeSize size = CoffeeSize.DOUBLE;
        CoffeType anyType = CoffeType.CAPUCCINO;
        CoffeOrder coffeOrder = CoffeOrder.builder().withSize(size).withType(anyType).build();
        setFunctions(size, anyType);

        Coffee result = coffeeMachine.make(coffeOrder);

        assertEquals(0, result.getMilkAmout());
    }

    @Test void properOrderShouldHaveCorrectWater() {
        CoffeeSize size = CoffeeSize.DOUBLE;
        CoffeType anyType = CoffeType.CAPUCCINO;
        CoffeOrder coffeOrder = CoffeOrder.builder().withSize(size).withType(anyType).build();
        setFunctions(size, anyType);

        Coffee result = coffeeMachine.make(coffeOrder);

        assertEquals(10, result.getWaterAmount());
    }

    @Test public void itCompiles() {
        assertThat(true, Matchers.equalTo(true));
    }

    @Test void properOrderShouldCallGrinderAndReceipes() {
        CoffeeSize size = CoffeeSize.DOUBLE;
        CoffeType anyType = CoffeType.CAPUCCINO;
        CoffeOrder coffeOrder = CoffeOrder.builder().withSize(size).withType(anyType).build();
        setFunctions(size, anyType);
        Coffee result = coffeeMachine.make(coffeOrder);

        verify(grinder).canGrindFor(size);
        verify(coffeeReceipes, times(2)).getReceipe(anyType);
    }

    @Test void ungrindableSizeShouldReturnError() {
        CoffeeSize size = CoffeeSize.DOUBLE;
        CoffeType anyType = CoffeType.CAPUCCINO;
        CoffeOrder coffeOrder = CoffeOrder.builder().withSize(size).withType(anyType).build();
        when(grinder.canGrindFor(size)).thenReturn(false);

        assertThrows(NoCoffeeBeansException.class, () -> {
            coffeeMachine.make(coffeOrder);
        });
    }


    private void setFunctions(CoffeeSize size, CoffeType type) {
        when(grinder.canGrindFor(size)).thenReturn(true);
        when(grinder.grind(size)).thenReturn(5d);
        CoffeeReceipe receipe = CoffeeReceipe.builder().withMilkAmount(0).withWaterAmounts(waterAmounts).build();
        when(coffeeReceipes.getReceipe(type)).thenReturn(receipe);
    }

}
