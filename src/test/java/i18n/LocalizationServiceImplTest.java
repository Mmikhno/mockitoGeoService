package i18n;

import entity.Country;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class LocalizationServiceImplTest {
    LocalizationService ls = new LocalizationServiceImpl();

    @ParameterizedTest
    @CsvSource(value = {
            "RUSSIA, Добро пожаловать",
            "USA, Welcome",
            "BRAZIL,Welcome",
            "GERMANY,Welcome"
    })
    void locale(Country country, String message) {
        String result = ls.locale(country);
        assertEquals(result, message);
    }
}