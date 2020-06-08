package edu.iis.mto.testreactor.coffee;

import static org.hamcrest.MatcherAssert.assertThat;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

class CoffeeMachineTest {

    @Test
    public void itCompiles() {
        assertThat(true, Matchers.equalTo(true));
    }

}
