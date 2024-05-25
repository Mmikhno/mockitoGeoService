package geo;

import entity.Country;
import entity.Location;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class GeoServiceImplTest {
    GeoServiceImpl gsi = new GeoServiceImpl();

    @ParameterizedTest
    @CsvSource(value = {
            "172.0.32.11,Moscow,RUSSIA,Lenina,15",
            "96.44.183.149,New York,USA,10th Avenue,32",
            "172.,Moscow,RUSSIA,,0",
            "96.,New York, USA,,0",
            "127.0.0.1,,,,0"
    })
    void byIpWithParameters(String ip, String city, Country country, String street, int building) {
        Location expected = gsi.byIp(ip);
        assertAll(
                () -> assertEquals(expected.getCity(), city),
                () -> assertEquals(expected.getCountry(), country),
                () -> assertEquals(expected.getStreet(), street),
                () -> assertEquals(expected.getBuiling(), building)
        );
    }

    @Test
    void byCoordinatesThrowsRuntimeException() {
        RuntimeException exception = Assertions.assertThrows(RuntimeException.class,
                () -> gsi.byCoordinates(59.976931, 30.192464));
        assertEquals("Not implemented", exception.getMessage());
    }
}