package org.vaadin.addons.yahaya;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("")
public class DemoView extends VerticalLayout {

    public DemoView() {
        setSizeFull();
        setPadding(true);
        setSpacing(false);
        setAlignItems(Alignment.START);

        VerticalLayout content = new VerticalLayout();
        content.setWidth("920px");
        content.setPadding(false);
        content.setSpacing(true);

        H1 title = new H1("CountryNumber Showcase");
        Paragraph subtitle = new Paragraph("A cleaner demo page for testing value parsing, country selection, and common UI states.");
        subtitle.getStyle().set("margin-top", "0").set("color", "var(--lumo-secondary-text-color)");

        content.add(title, subtitle, buildPlaygroundSection(), buildStatesSection(), buildReferenceImagesSection());
        add(content);
    }

    private VerticalLayout buildPlaygroundSection() {
        CountryNumber playground = new CountryNumber();
        playground.setPlaceholder("Enter your phone number");
        playground.setWidth("420px");

        Span valueLabel = new Span("Value: " + playground.getValue());
        valueLabel.getStyle()
                .set("font-family", "monospace")
                .set("font-size", "0.95rem")
                .set("color", "var(--lumo-secondary-text-color)");

        playground.addValueChangeListener(event ->
                valueLabel.setText("Value: " + event.getValue()));

        Button setFinland = new Button("Set Finland", event -> playground.setValue("+358401234567"));
        Button setUsa = new Button("Set USA", event -> playground.setValue("+14155550123"));
        Button clear = new Button("Clear", event -> playground.setValue(""));
        Button toggleInvalid = new Button("Toggle Invalid", event -> {
            boolean invalid = !playground.isInvalid();
            playground.setInvalid(invalid);
            playground.setErrorMessage(invalid ? "Invalid phone number format" : "");
        });

        HorizontalLayout actions = new HorizontalLayout(setFinland, setUsa, clear, toggleInvalid);
        actions.setSpacing(true);
        actions.getStyle().set("flex-wrap", "wrap");

        return buildSection("Live Playground", "Use the controls below to quickly test behavior.", playground, actions, valueLabel);
    }

    private VerticalLayout buildStatesSection() {
        CountryNumber prefilled = new CountryNumber();
        prefilled.setValue("+447700900123");
        prefilled.setWidth("300px");

        CountryNumber readOnly = new CountryNumber();
        readOnly.setValue("+14155550123");
        readOnly.setReadOnly(true);
        readOnly.setWidth("300px");

        CountryNumber invalid = new CountryNumber();
        invalid.setValue("+35812");
        invalid.setInvalid(true);
        invalid.setErrorMessage("Number is too short");
        invalid.setWidth("300px");

        VerticalLayout col1 = buildStateColumn("Prefilled", prefilled);
        VerticalLayout col2 = buildStateColumn("Read-only", readOnly);
        VerticalLayout col3 = buildStateColumn("Invalid", invalid);

        HorizontalLayout row = new HorizontalLayout(col1, col2, col3);
        row.setWidthFull();
        row.setSpacing(true);
        row.getStyle().set("flex-wrap", "wrap");

        return buildSection("Common States", "Quick references for typical addon usage states.", row);
    }

    private VerticalLayout buildReferenceImagesSection() {
        HorizontalLayout gallery = new HorizontalLayout(
                buildImageCard("Image 1", "/Images/image1.png"),
                buildImageCard("Image 2", "/Images/image2.png"),
                buildImageCard("Image 3", "/Images/image3.png")
        );
        gallery.setSpacing(true);
        gallery.getStyle().set("flex-wrap", "wrap");

        return buildSection("Reference UI Targets", "These design references help align the component visuals with your intended UX.", gallery);
    }

    private VerticalLayout buildSection(String title, String description, com.vaadin.flow.component.Component... components) {
        H2 sectionTitle = new H2(title);
        sectionTitle.getStyle().set("margin-bottom", "0.25rem");

        Paragraph sectionDescription = new Paragraph(description);
        sectionDescription.getStyle().set("margin-top", "0").set("color", "var(--lumo-secondary-text-color)");

        VerticalLayout section = new VerticalLayout();
        section.setPadding(true);
        section.setSpacing(true);
        section.setWidthFull();
        section.getStyle()
                .set("border", "1px solid var(--lumo-contrast-10pct)")
                .set("border-radius", "10px")
                .set("background", "var(--lumo-base-color)");

        section.add(sectionTitle, sectionDescription);
        section.add(components);
        return section;
    }

    private VerticalLayout buildStateColumn(String label, CountryNumber field) {
        Span caption = new Span(label);
        caption.getStyle().set("font-weight", "600");

        VerticalLayout column = new VerticalLayout(caption, field);
        column.setPadding(false);
        column.setSpacing(true);
        column.setWidth("auto");
        return column;
    }

    private VerticalLayout buildImageCard(String title, String src) {
        Span caption = new Span(title);
        caption.getStyle().set("font-weight", "600");

        Image image = new Image(src, title);
        image.setWidth("280px");
        image.getStyle()
                .set("border", "1px solid var(--lumo-contrast-20pct)")
                .set("border-radius", "8px")
                .set("background", "white");

        VerticalLayout card = new VerticalLayout(caption, image);
        card.setPadding(false);
        card.setSpacing(true);
        card.setWidth("auto");
        return card;
    }
}
