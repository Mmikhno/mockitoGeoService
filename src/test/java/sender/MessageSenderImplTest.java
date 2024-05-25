package sender;

import entity.Country;
import entity.Location;
import geo.GeoService;
import i18n.LocalizationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MessageSenderImplTest {
    private Map<String, String> headers = new HashMap<String, String>();

    @Test
    void send() {
        String message = "Добро пожаловать";
        GeoService geo = Mockito.mock(GeoService.class);
        Mockito.when(geo.byIp("172.0.32.11"))
                .thenReturn(new Location("Moscow", Country.RUSSIA, "Lenina", 15));
        LocalizationService ls = Mockito.mock(LocalizationService.class);
        Mockito.when(ls.locale(Country.RUSSIA))
                .thenReturn(message);
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, "172.0.32.11");
        assertEquals(message, new MessageSenderImpl(geo, ls).send(headers));
    }

    @ParameterizedTest
    @CsvSource(value = {
            "Добро пожаловать, 172.0.32.11, RUSSIA, Moscow, Lenina, 15",
            "Welcome,96.44.183.149, USA, New York, 10th Avenue, 32",
            "Добро пожаловать, 172.,RUSSIA,Moscow,,0",
            "Welcome, 96.,USA,New York,,0",
            "Welcome,127.0.0.1,,,,0"
    })
    void sendWithParameters(String message, String ip, Country country, String city, String street, int building) {
        String lineParam = message;
        String ipParam = ip;
        Country countryParam = country;
        GeoService geo = Mockito.mock(GeoService.class);
        Mockito.when(geo.byIp(ipParam))
                .thenReturn(new Location(city, countryParam, street, building));
        LocalizationService ls = Mockito.mock(LocalizationService.class);
        Mockito.when(ls.locale(countryParam))
                .thenReturn(lineParam);
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, ipParam);
        assertEquals(lineParam, new MessageSenderImpl(geo, ls).send(headers));
    }
}