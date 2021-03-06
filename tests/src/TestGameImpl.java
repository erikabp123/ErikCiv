import com.erikpoerksen.erikciv.GameLogic.Helpers.PlayerTypes;
import com.erikpoerksen.erikciv.GameLogic.Helpers.Position;
import com.erikpoerksen.erikciv.GameLogic.Helpers.TerrainTypes;
import com.erikpoerksen.erikciv.GameLogic.Helpers.UnitTypes;
import com.erikpoerksen.erikciv.GameLogic.Implementations.GameImpl;
import com.erikpoerksen.erikciv.GameLogic.Structure.Game;
import com.erikpoerksen.erikciv.GameLogic.Structure.Unit;
import org.junit.Before;
import org.junit.Test;

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

    @Test
    public void moveUnit1ShouldReturnTrueOntoPlains(){
        Position from = new Position(0, 1);
        game.endTurn(); // make it RED's turn
        boolean result = game.moveUnit(from, new Position(0, 2));
        assertThat(result, is(true));
    }

    @Test
    public void moveUnit2ShouldReturnTrue(){
        Position from = new Position(0, 1);
        game.endTurn(); // make it RED's turn
        boolean result = game.moveUnit(from, new Position(0, 3));
        assertThat(result, is(true));
    }

    @Test
    public void moveUnitShouldReturnFalseOntoOcean(){
        Position from = new Position(1, 3);
        game.endTurn(); // make it RED's turn
        boolean result = game.moveUnit(from, new Position(2, 4));
        assertThat(result, is(false));
    }

    @Test
    public void moveUnitShouldReturnFalseOntoAlliedUnit(){
        Position from = new Position(1, 3);
        Position to = new Position(1, 4);
        game.endTurn(); // make it RED's turn
        boolean result = game.moveUnit(from, to);
        assertThat(result, is(false));
    }

    @Test
    public void moveUnitShouldReturnFalseIfMoreThanMoveCount(){
        Position from = new Position(1, 3);
        Position to = new Position(1, 6);
        game.endTurn(); // make it RED's turn
        boolean result = game.moveUnit(from, to);
        assertThat(result, is(false ));
    }

    @Test
    public void moveUnitShouldReturnFalseIfEnemyIsBlockingOnlyPath(){
        game.moveUnit(new Position(7, 6), new Position(3, 6));
        game.endTurn();
        game.moveUnit(new Position(1, 4), new Position(0, 3)); // move red unit to block path
        game.endTurn();
        game.moveUnit(new Position(3, 6), new Position(2, 6));
        game.endTurn();
        boolean result = game.moveUnit(new Position(3, 6), new Position(1, 2)); // attempt to move unit past blockade
        assertThat(result, is(false));
    }

    @Test
    public void moveUnitAttackFromArcherOntoLegionShouldNotKillLegion(){
        Unit attackingUnit = game.getUnitAtPosition(new Position(7, 6));
        int attackerStartingHealth = attackingUnit.getCurrentHealth();
        Unit defendingUnit = game.getUnitAtPosition(new Position(1, 4));
        int defenderStartingHealth = defendingUnit.getCurrentHealth();
        game.moveUnit(new Position(7, 6), new Position(3, 6));
        game.endTurn(); // make it red's turn
        game.endTurn(); // make it blue's turn again
        boolean result = game.moveUnit(new Position(3, 6), new Position(1, 4)); // move legion onto other legion
        assertThat(result, is(true));
        assertThat(attackingUnit.getRemainingMoveCount(), is(0));
        assertThat(attackingUnit.getCurrentHealth(), is(attackerStartingHealth - 3));
        assertThat(defendingUnit.getOwner().getColor(), is(PlayerTypes.RED));
        assertThat(defendingUnit.getCurrentHealth(), is(defenderStartingHealth - 3));
    }




}
