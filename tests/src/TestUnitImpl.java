import com.erikpoerksen.erikciv.GameLogic.Helpers.PlayerTypes;
import com.erikpoerksen.erikciv.GameLogic.Helpers.UnitTypes;
import com.erikpoerksen.erikciv.GameLogic.Implementations.PlayerImpl;
import com.erikpoerksen.erikciv.GameLogic.Implementations.UnitImpl;
import com.erikpoerksen.erikciv.GameLogic.Structure.Player;
import com.erikpoerksen.erikciv.GameLogic.Structure.Unit;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

public class TestUnitImpl {

    Player player;
    Unit unit;

    @Before
    public void setUp() {
        player = new PlayerImpl(PlayerTypes.BLUE);
        unit = new UnitImpl(player, UnitTypes.ARCHER);
    }

    @Test
    public void performSpecialActionShouldDoNothing() {
        // does nothing as of alpha
    }

    @Test
    public void getTypeShouldReturnType() {
        assertThat(unit.getType(), is(UnitTypes.ARCHER));
    }

    @Test
    public void getOwnerShouldReturnOwner() {
        assertThat(unit.getOwner(), is(player));
    }

    @Test
    public void getRemainingMoveCountShouldReturn4ForAllButWorkerByDefault(){
        assertThat(unit.getRemainingMoveCount(), is(4));
    }

    @Test
    public void getRemainingMoveCountShouldReturn2ForWorkerByDefault(){
        unit = new UnitImpl(player, UnitTypes.WOKER);
        assertThat(unit.getRemainingMoveCount(), is(2));
    }

    @Test
    public void move1ShouldDecreaseStartingMoveCountBy1() {
        int startingMoveCount = unit.getRemainingMoveCount();
        unit.move(1);
        assertThat(unit.getRemainingMoveCount(), is(startingMoveCount-1));
    }

    @Test
    public void move2ShouldDecreaseStartingMoveCountBy2() {
        int startingMoveCount = unit.getRemainingMoveCount();
        unit.move(2);
        assertThat(unit.getRemainingMoveCount(), is(startingMoveCount-2));
    }

}
