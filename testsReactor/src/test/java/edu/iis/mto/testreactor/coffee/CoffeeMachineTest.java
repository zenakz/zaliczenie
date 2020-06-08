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

@ExtendWith(MockitoExtension.class)
class CoffeeMachineTest {

    @Mock
    private Grinder grinder;
    @Mock
    private MilkProvider milkProvider;
    @Mock
    private CoffeeReceipes coffeeReceipes;

    private CoffeeMachine coffeeMachine;

    private Map<CoffeeSize, Integer>waterAmounts = new HashMap<>();
    @BeforeEach
    void setUp(){
        coffeeMachine = new CoffeeMachine(grinder,milkProvider,coffeeReceipes);
        waterAmounts.put(CoffeeSize.DOUBLE,10);
    }

    @Test
    void properMilklessOrderShouldResultInMilklessCoffee(){
        CoffeeSize unrelevant = CoffeeSize.DOUBLE;
        CoffeType anyType = CoffeType.CAPUCCINO;
        CoffeOrder coffeOrder = CoffeOrder.builder().withSize(unrelevant).withType(anyType).build();
        setFunctions(unrelevant,anyType);

        Coffee result = coffeeMachine.make(coffeOrder);

        assertEquals(0,result.getMilkAmout());
    }

    @Test
    public void itCompiles() {
        assertThat(true, Matchers.equalTo(true));
    }


    @Test
    void properOrderShouldCallGrinderAndReceipes(){
        CoffeeSize unrelevant = CoffeeSize.DOUBLE;
        CoffeType anyType = CoffeType.CAPUCCINO;
        CoffeOrder coffeOrder = CoffeOrder.builder().withSize(unrelevant).withType(anyType).build();
        setFunctions(unrelevant, anyType);
        Coffee result = coffeeMachine.make(coffeOrder);

        verify(grinder).canGrindFor(unrelevant);
        verify(coffeeReceipes, times(2)).getReceipe(anyType);
    }

    @Test
    void ungrindableSizeShouldReturnInError(){
        CoffeeSize unrelevant = CoffeeSize.DOUBLE;
        CoffeType anyType = CoffeType.CAPUCCINO;
        CoffeOrder coffeOrder = CoffeOrder.builder().withSize(unrelevant).withType(anyType).build();
        when(grinder.canGrindFor(unrelevant)).thenReturn(false);
        assertThrows(NoCoffeeBeansException.class, ()->{
            coffeeMachine.make(coffeOrder);
        });
    }

    private void setFunctions(CoffeeSize size, CoffeType type){
        CoffeeSize unrelevant = CoffeeSize.DOUBLE;
        CoffeType anyType = CoffeType.CAPUCCINO;
        CoffeOrder coffeOrder = CoffeOrder.builder().withSize(unrelevant).withType(anyType).build();
        when(grinder.canGrindFor(unrelevant)).thenReturn(true);
        when(grinder.grind(unrelevant)).thenReturn(5d);
        CoffeeReceipe receipe = CoffeeReceipe.builder().withMilkAmount(0).withWaterAmounts(waterAmounts).build();
        when(coffeeReceipes.getReceipe(anyType)).thenReturn(receipe);
    }

}
