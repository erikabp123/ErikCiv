import com.erikpoerksen.blackjack.GameLogic.Helpers.PlayerTypes;
import com.erikpoerksen.blackjack.GameLogic.Helpers.Position;
import com.erikpoerksen.blackjack.GameLogic.Helpers.TerrainTypes;
import com.erikpoerksen.blackjack.GameLogic.Helpers.UnitTypes;
import com.erikpoerksen.blackjack.GameLogic.Implementations.CityImpl;
import com.erikpoerksen.blackjack.GameLogic.Implementations.GameImpl;
import com.erikpoerksen.blackjack.GameLogic.Implementations.PlayerImpl;
import com.erikpoerksen.blackjack.GameLogic.Implementations.WorldImpl;
import com.erikpoerksen.blackjack.GameLogic.Structure.City;
import com.erikpoerksen.blackjack.GameLogic.Structure.Game;
import com.erikpoerksen.blackjack.GameLogic.Structure.Player;
import com.erikpoerksen.blackjack.GameLogic.Structure.World;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

public class TestGameImpl {

    Game game;

    @Before
    public void setUp() {

        String[][] worldString = new String[3][10];
        worldString[0] = new String[]{
                "F.P.P.P.P.F.O.O.M.P",
                "F.F.P.P.P.F.F.O.M.P",
                "F.F.F.O.O.O.F.M.M.P",
                "O.O.O.O.O.F.F.F.F.F",
                "O.O.P.P.P.P.F.F.P.F",
                "O.M.M.M.P.P.P.F.P.M",
                "M.M.M.M.M.P.P.P.P.P",
                "M.M.M.M.P.P.P.H.H.H",
                "P.M.M.P.P.P.P.P.H.H",
                "H.H.H.P.P.F.O.P.F.H"
        };
        worldString[1] = new String[]{
                "-.RC.-.-.-.-.-.-.-.-",
                "-.-.-.-.-.-.-.-.-.-",
                "-.-.-.-.-.-.-.-.-.-",
                "-.-.-.-.-.-.-.-.-.-",
                "-.-.-.-.-.-.-.-.-.-",
                "-.-.-.-.-.-.-.-.-.-",
                "-.-.-.-.-.-.-.-.-.-",
                "-.-.-.-.-.-.-.-.-.-",
                "-.-.-.-.-.-.-.-.-.-",
                "-.-.-.-.-.-.-.BC.-.-"
        };
        worldString[2] = new String[]{
                "-.RA.-.-.-.-.-.-.-.-",
                "-.-.-.RW.RL.-.-.-.-.-",
                "RA.-.-.-.-.-.-.-.-.-",
                "-.-.-.-.-.-.-.-.-.-",
                "-.-.-.-.-.-.-.-.-.-",
                "-.-.-.-.-.-.-.-.-.-",
                "-.-.-.-.-.-.-.-.-.-",
                "-.-.-.-.-.-.BL.BW.-.-",
                "BA.-.-.-.-.-.-.-.-.-",
                "-.-.-.-.-.-.-.BL.-.-"
        };

        game = new GameImpl(worldString);
    }

    @Test
    public void getPlayerInTurnReturnsBlueAsFirst() {
        assertThat(game.getPlayerInTurn().getColor(), is(PlayerTypes.BLUE));
    }

    @Test
    public void endTurnShouldMakeItRedsTurn() {
        game.endTurn();
        assertThat(game.getPlayerInTurn().getColor(), is(PlayerTypes.RED));
    }

    @Test
    public void endTurnTwiceShouldMakeItBluesTurn() {
        game.endTurn();
        game.endTurn();
        assertThat(game.getPlayerInTurn().getColor(), is(PlayerTypes.BLUE));
    }

    @Test
    public void getTerrainAtX00Y00ShouldReturnForest(){
        assertThat(game.getTerrainAtPosition(new Position(0, 0)), is(TerrainTypes.FOREST));
    }

    @Test
    public void getCityAtX00Y01ShouldReturnRedCity(){
        assertNotNull(game.getCityAtPosition(new Position(0, 1)));
        assertThat(game.getCityAtPosition(new Position(0, 1)).getOwner().getColor(), is(PlayerTypes.RED));
    }

    @Test
    public void getUnitnAtX00Y01ShouldReturnRedArcher(){
        assertNotNull(game.getUnitAtPosition(new Position(0, 1)));
        assertThat(game.getUnitAtPosition(new Position(0, 1)).getOwner().getColor(), is(PlayerTypes.RED));
        assertThat(game.getUnitAtPosition(new Position(0, 1)).getType(), is(UnitTypes.ARCHER));
    }

}
