# CountryNumber — Vaadin Phone Input Add-on

A server-side Vaadin add-on that provides a phone number input field with an integrated country-code selector (flag + dial code). Built on `CustomField<String>` — no client-side JavaScript required.

## Features

- 🏳️ Country flag + dial-code dropdown (25 countries)
- 📞 Combined model value (`+358401234567`)
- 🔍 Filterable country list (by name, ISO code, or dial code)
- ✅ Automatic whitespace normalization
- 🔁 Round-trip value parsing — set `+447700900123` and it selects 🇬🇧 UK automatically
- ♿ Inherits Vaadin's built-in validation, read-only, and error states

## Requirements

| Dependency | Version |
|------------|---------|
| Java       | 17+     |
| Vaadin     | 23.3+   |
| Maven      | 3.8+    |

## Quick Start

### Run the Demo

```bash
mvn jetty:run -Pdevelopment
```

Open [http://localhost:8080](http://localhost:8080) — the `DemoView` showcase page loads automatically.

### Run Unit Tests

```bash
mvn test
```

Runs `CountryNumberTest` (value parsing, round-trip, reformat) and `CountryNumberEdgeCasesTest` (+1 ambiguity, whitespace normalization).

## Usage

### Basic

```java
CountryNumber phone = new CountryNumber();
phone.setPlaceholder("Phone number");
phone.addValueChangeListener(e -> 
    System.out.println("Full number: " + e.getValue()));
```

### Pre-select a Country

```java
phone.setCountryCode(CountryCode.UNITED_KINGDOM);
// or by ISO code
phone.setCountryCodeByIso("GB");
```

### Set a Full Value Programmatically

```java
phone.setValue("+447700900123"); // auto-selects 🇬🇧 UK
```

### Validation State

```java
phone.setInvalid(true);
phone.setErrorMessage("Number is too short");
```

## Project Structure

```
src/
├── main/java/org/vaadin/addons/yahaya/
│   ├── CountryCode.java       # Enum of supported countries (ISO, flag, dial code)
│   └── CountryNumber.java     # The add-on component (CustomField<String>)
├── main/resources/META-INF/resources/frontend/
│   └── country-number.css     # Component styles
└── test/java/org/vaadin/addons/yahaya/
    ├── DemoView.java                   # Showcase page (@Route(""))
    ├── CountryNumberTest.java          # Unit tests — parsing & formatting
    └── CountryNumberEdgeCasesTest.java # Edge-case tests — +1 ambiguity, whitespace
```

> Test classes live under `src/test` and are **not** packaged into the add-on JAR.

## Contributing

Contributions are welcome! Here's how to get started:

1. **Open an issue first** — describe the bug, fix, or feature you want to work on. This avoids duplicate effort and gives maintainers a chance to provide early feedback.
2. **Fork & clone** the repository.
3. **Create a branch** for your change:
   ```bash
   git checkout -b feature/your-feature-name
   ```
4. **Run the demo** to make sure the project builds and works:
   ```bash
   mvn jetty:run -Pdevelopment
   ```
5. **Make your changes** — keep them focused on a single concern.
6. **Add or update tests** for any new or changed behavior.
7. **Run all tests** before committing:
   ```bash
   mvn test
   ```
8. **Commit** with a clear, descriptive message.
9. **Push** your branch and open a **pull request** against `main`.

### Guidelines

- Follow existing code style and naming conventions.
- Keep PRs small and reviewable — one feature or fix per PR.
- New public API methods should include usage examples in the PR description.
- All existing tests must continue to pass.

## License

[Apache License 2.0](LICENSE)
