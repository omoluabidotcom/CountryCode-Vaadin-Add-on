package org.vaadin.addons.yahaya;

import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;

import java.util.Arrays;

@StyleSheet("frontend://country-number.css")
public class CountryNumber extends CustomField<String> {

    private final ComboBox<CountryCode> countryCodeSelect;
    private final TextField phoneNumberField;
    private final HorizontalLayout layout;
    private boolean updatingPresentation;

    public CountryNumber() {
        setWidthFull();

        countryCodeSelect = new ComboBox<>();
        countryCodeSelect.setItemLabelGenerator(cc -> cc.getFlag() + " " + cc.getDialCode());
        countryCodeSelect.setItems(query -> {
            String filterText = query.getFilter().orElse("").toLowerCase();
            int offset = query.getOffset();
            int limit = query.getLimit();

            // Vaadin lazy data providers must honor query paging to satisfy the contract.
            return Arrays.stream(CountryCode.values())
                    .filter(country -> filterText.isEmpty()
                            || country.getCountryName().toLowerCase().contains(filterText)
                            || country.getDialCode().toLowerCase().contains(filterText)
                            || country.getIso().toLowerCase().contains(filterText))
                    .skip(offset)
                    .limit(limit);
        });
        countryCodeSelect.setAllowCustomValue(false);
        countryCodeSelect.setClearButtonVisible(false);
        countryCodeSelect.setRenderer(new ComponentRenderer<>(country -> {
            Div wrapper = new Div();
            wrapper.getStyle()
                    .set("display", "flex")
                    .set("align-items", "center")
                    .set("gap", "8px")
                    .set("padding", "4px");

            Span flag = new Span(country.getFlag());
            flag.getStyle().set("font-size", "20px");

            Span code = new Span(country.getDialCode());
            code.getStyle().set("font-weight", "500");

            Span name = new Span(country.getCountryName());
            name.getStyle().set("color", "var(--lumo-secondary-text-color)");

            wrapper.add(flag, code, name);
            return wrapper;
        }));
        countryCodeSelect.getStyle()
                .set("--vaadin-combo-box-overlay-width", "300px")
                .set("--vaadin-input-field-border-width", "0")
                .set("--vaadin-input-field-background", "transparent")
                .set("flex", "1")
                .set("min-width", "0")
                .set("background", "transparent");

        Div countryCodeSection = new Div(countryCodeSelect);
        countryCodeSection.addClassName("country-code-section");
        countryCodeSection.setWidth("auto");

        phoneNumberField = new TextField();
        phoneNumberField.setPlaceholder("Phone number");
        phoneNumberField.setValueChangeMode(ValueChangeMode.EAGER);
        phoneNumberField.getStyle()
                .set("flex", "1")
                .set("min-width", "0")
                .set("border", "none")
                .set("background", "transparent")
                .set("--vaadin-input-field-border-width", "0");

        Div separator = new Div();
        separator.getStyle()
                .set("width", "1px")
                .set("background", "var(--lumo-contrast-20pct, #e0e0e0)")
                .set("margin", "8px 0")
                .set("flex-shrink", "0");

        layout = new HorizontalLayout(countryCodeSection, separator, phoneNumberField);
        layout.setSpacing(false);
        layout.setPadding(false);
        layout.setWidthFull();
        layout.setAlignItems(FlexComponent.Alignment.STRETCH);
        layout.getStyle()
                .set("border", "1px solid var(--lumo-contrast-20pct, #e0e0e0)")
                .set("border-radius", "var(--lumo-border-radius-m, 4px)")
                .set("background", "var(--lumo-base-color, #ffffff)")
                .set("overflow", "hidden")
                .set("display", "flex")
                .set("align-items", "stretch");

        add(layout);

        countryCodeSelect.addValueChangeListener(event -> {
            if (!updatingPresentation) {
                updateValue();
            }
        });
        phoneNumberField.addValueChangeListener(event -> {
            if (!updatingPresentation) {
                updateValue();
            }
        });

        countryCodeSelect.setValue(CountryCode.FINLAND);
    }

    @Override
    public void setValue(String value) {
        super.setValue(normalizeValue(value));
    }

    @Override
    protected String generateModelValue() {
        CountryCode country = countryCodeSelect.getValue();
        String phone = normalizeValue(phoneNumberField.getValue());
        if (country == null || phone.isBlank()) {
            return "";
        }
        return country.getDialCode() + phone;
    }

    @Override
    protected void setPresentationValue(String fullNumber) {
        updatingPresentation = true;
        try {
            String normalizedValue = normalizeValue(fullNumber);
            if (normalizedValue.isBlank()) {
                phoneNumberField.clear();
                if (countryCodeSelect.getValue() == null) {
                    countryCodeSelect.setValue(CountryCode.FINLAND);
                }
                return;
            }

            for (CountryCode country : CountryCode.values()) {
                if (normalizedValue.startsWith(country.getDialCode())) {
                    countryCodeSelect.setValue(country);
                    phoneNumberField.setValue(normalizedValue.substring(country.getDialCode().length()));
                    return;
                }
            }

            phoneNumberField.setValue(normalizedValue);
            if (countryCodeSelect.getValue() == null) {
                countryCodeSelect.setValue(CountryCode.FINLAND);
            }
        } finally {
            updatingPresentation = false;
        }
    }

    public void setPlaceholder(String placeholder) {
        phoneNumberField.setPlaceholder(placeholder);
    }

    public void setCountryCode(CountryCode country) {
        countryCodeSelect.setValue(country);
    }

    public void setCountryCodeByIso(String countryIso) {
        Arrays.stream(CountryCode.values())
                .filter(cc -> cc.getIso().equalsIgnoreCase(countryIso))
                .findFirst()
                .ifPresent(countryCodeSelect::setValue);
    }

    public CountryCode getCountryCode() {
        return countryCodeSelect.getValue();
    }

    @Override
    public void setInvalid(boolean invalid) {
        super.setInvalid(invalid);
        if (invalid) {
            layout.getStyle().set("border-color", "var(--lumo-error-color)");
            layout.addClassName("invalid");
        } else {
            layout.getStyle().set("border-color", "var(--lumo-contrast-20pct)");
            layout.removeClassName("invalid");
        }
    }

    @Override
    public void setErrorMessage(String errorMessage) {
        super.setErrorMessage(errorMessage);
        phoneNumberField.setErrorMessage(errorMessage);
    }

    private String normalizeValue(String value) {
        if (value == null) {
            return "";
        }
        return value.trim().replaceAll("\\s+", "");
    }
}