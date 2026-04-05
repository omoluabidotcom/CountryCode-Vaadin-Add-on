package org.vaadin.addons.yahaya;

public enum CountryCode {
    FINLAND("FI", "🇫🇮", "+358", "Finland"),
    UNITED_STATES("US", "🇺🇸", "+1", "United States"),
    UNITED_KINGDOM("GB", "🇬🇧", "+44", "United Kingdom"),
    GERMANY("DE", "🇩🇪", "+49", "Germany"),
    FRANCE("FR", "🇫🇷", "+33", "France"),
    SWEDEN("SE", "🇸🇪", "+46", "Sweden"),
    NORWAY("NO", "🇳🇴", "+47", "Norway"),
    DENMARK("DK", "🇩🇰", "+45", "Denmark"),
    SPAIN("ES", "🇪🇸", "+34", "Spain"),
    ITALY("IT", "🇮🇹", "+39", "Italy"),
    NETHERLANDS("NL", "🇳🇱", "+31", "Netherlands"),
    BELGIUM("BE", "🇧🇪", "+32", "Belgium"),
    SWITZERLAND("CH", "🇨🇭", "+41", "Switzerland"),
    AUSTRIA("AT", "🇦🇹", "+43", "Austria"),
    POLAND("PL", "🇵🇱", "+48", "Poland"),
    INDIA("IN", "🇮🇳", "+91", "India"),
    CHINA("CN", "🇨🇳", "+86", "China"),
    JAPAN("JP", "🇯🇵", "+81", "Japan"),
    AUSTRALIA("AU", "🇦🇺", "+61", "Australia"),
    CANADA("CA", "🇨🇦", "+1", "Canada"),
    BRAZIL("BR", "🇧🇷", "+55", "Brazil"),
    MEXICO("MX", "🇲🇽", "+52", "Mexico"),
    SOUTH_KOREA("KR", "🇰🇷", "+82", "South Korea"),
    RUSSIA("RU", "🇷🇺", "+7", "Russia"),
    NIGERIA("NG", "🇳🇬", "+234", "Nigeria");

    private final String iso;
    private final String flag;
    private final String dialCode;
    private final String countryName;

    CountryCode(String iso, String flag, String dialCode, String countryName) {
        this.iso = iso;
        this.flag = flag;
        this.dialCode = dialCode;
        this.countryName = countryName;
    }

    public String getIso() {
        return iso;
    }

    public String getFlag() {
        return flag;
    }

    public String getDialCode() {
        return dialCode;
    }

    public String getCountryName() {
        return countryName;
    }
}
