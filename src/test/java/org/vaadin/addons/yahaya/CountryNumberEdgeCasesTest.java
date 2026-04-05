package org.vaadin.addons.yahaya;

import org.junit.Assert;
import org.junit.Test;

public class CountryNumberEdgeCasesTest {

    @Test
    public void setValue_withPlusOnePrefix_prefersUnitedStatesAsFirstMatch() {
        CountryNumber field = new CountryNumber();

        field.setValue("+14165550123");

        Assert.assertEquals(CountryCode.UNITED_STATES, field.getCountryCode());
        Assert.assertEquals("+14165550123", field.getValue());
    }

    @Test
    public void setValue_withWhitespace_normalizesToCanonicalNumber() {
        CountryNumber field = new CountryNumber();

        field.setValue("  +358 40 123 4567  ");

        Assert.assertEquals(CountryCode.FINLAND, field.getCountryCode());
        Assert.assertEquals("+358401234567", field.getValue());
    }
}

