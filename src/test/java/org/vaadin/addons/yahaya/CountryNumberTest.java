package org.vaadin.addons.yahaya;

import org.junit.Assert;
import org.junit.Test;

public class CountryNumberTest {

    @Test
    public void setValue_withKnownDialCode_parsesCountryAndKeepsRoundTripValue() {
        CountryNumber field = new CountryNumber();

        field.setValue("+358401234567");

        Assert.assertEquals(CountryCode.FINLAND, field.getCountryCode());
        Assert.assertEquals("+358401234567", field.getValue());
    }

    @Test
    public void setValue_withUnknownDialCode_keepsInputAndDefaultCountry() {
        CountryNumber field = new CountryNumber();

        field.setValue("+999123");

        Assert.assertEquals(CountryCode.FINLAND, field.getCountryCode());
        Assert.assertEquals("+999123", field.getValue());
    }

    @Test
    public void setValue_withEmptyString_clearsValue() {
        CountryNumber field = new CountryNumber();

        field.setValue("");

        Assert.assertEquals("", field.getValue());
    }

    @Test
    public void changingCountry_reformatsUsingParsedLocalNumber() {
        CountryNumber field = new CountryNumber();

        field.setValue("+358401234567");
        field.setCountryCode(CountryCode.UNITED_STATES);

        Assert.assertEquals(CountryCode.UNITED_STATES, field.getCountryCode());
        Assert.assertEquals("+1401234567", field.getValue());
    }
}

