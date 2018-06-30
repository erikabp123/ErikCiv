import com.erikpoerksen.erikciv.GameLogic.Helpers.HelperMethods;
import com.erikpoerksen.erikciv.GameLogic.Helpers.PlayerTypes;
import com.erikpoerksen.erikciv.GameLogic.Helpers.Position;
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

public class TestHelperMethods {

    @Before
    public void setUp() {

    }

    @Test
    public void distanceShouldBe1WhenGoingDown() {
        int distance = HelperMethods.calculateShortestDirectDistance(new Position(0, 0), new Position(1, 0));
        assertThat(distance, is(distance));
    }

    @Test
    public void distanceShouldBe1WhenGoingRight() {
        int distance = HelperMethods.calculateShortestDirectDistance(new Position(0, 0), new Position(0, 1));
        assertThat(distance, is(distance));
    }

    @Test
    public void distanceShouldBe1WhenGoingUp() {
        int distance = HelperMethods.calculateShortestDirectDistance(new Position(1, 0), new Position(0, 0));
        assertThat(distance, is(distance));
    }

    @Test
    public void distanceShouldBe2() {
        int distance = HelperMethods.calculateShortestDirectDistance(new Position(0, 0), new Position(0, 2));
        assertThat(distance, is(distance));
    }

}
