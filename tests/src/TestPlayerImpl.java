import com.erikpoerksen.blackjack.GameLogic.Helpers.PlayerTypes;
import com.erikpoerksen.blackjack.GameLogic.Implementations.PlayerImpl;
import com.erikpoerksen.blackjack.GameLogic.Structure.Player;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TestPlayerImpl {

    Player player;

    @Before
    public void setUp() {
        player = new PlayerImpl(PlayerTypes.BLUE);
    }

    @Test
    public void getColor() {
        assertThat(player.getColor(), is(PlayerTypes.BLUE));
    }

    @Test
    public void getWoodCount() {
        assertThat(player.getWoodCount(), is(50));
    }

    @Test
    public void getFoodCount() {
        assertThat(player.getFoodCount(), is(100));
    }

    @Test
    public void getOreCount() {
        assertThat(player.getOreCount(), is(20));
    }

}
