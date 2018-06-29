import com.erikpoerksen.erikciv.GameLogic.Helpers.PlayerTypes;
import com.erikpoerksen.erikciv.GameLogic.Helpers.UnitTypes;
import com.erikpoerksen.erikciv.GameLogic.Implementations.CityImpl;
import com.erikpoerksen.erikciv.GameLogic.Implementations.PlayerImpl;
import com.erikpoerksen.erikciv.GameLogic.Structure.City;
import com.erikpoerksen.erikciv.GameLogic.Structure.Player;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

public class TestCityImpl {

    City city;
    Player player;

    @Before
    public void setUp() {
        player = new PlayerImpl(PlayerTypes.BLUE);
        city = new CityImpl(player);
    }

    @Test
    public void getOwnerShouldReturnOwner() {
        assertThat(city.getOwner(), is(player));
    }

    @Test
    public void getProductionShouldReturnNull() {
        assertNull(city.getProduction());
    }

    @Test
    public void shouldSetProductionToArcher() {
        city.setProduction(UnitTypes.ARCHER);
        assertThat(city.getProduction(), is(UnitTypes.ARCHER));
    }

    @Test
    public void annexCityShouldChangeOwner() {
        Player enemy = new PlayerImpl(PlayerTypes.RED);
        city.annexCity(enemy);
        assertThat(city.getOwner(), is(enemy));
    }

}
