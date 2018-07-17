import com.erikpoerksen.erikciv.GameLogic.Helpers.PlayerTypes;
import com.erikpoerksen.erikciv.GameLogic.Helpers.Position;
import com.erikpoerksen.erikciv.GameLogic.Helpers.TerrainTypes;
import com.erikpoerksen.erikciv.GameLogic.Helpers.UnitTypes;
import com.erikpoerksen.erikciv.GameLogic.Implementations.PlayerImpl;
import com.erikpoerksen.erikciv.GameLogic.Implementations.WorldImpl;
import com.erikpoerksen.erikciv.GameLogic.Structure.City;
import com.erikpoerksen.erikciv.GameLogic.Structure.Player;
import com.erikpoerksen.erikciv.GameLogic.Structure.Unit;
import com.erikpoerksen.erikciv.GameLogic.Structure.World;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class TestWorldImpl {

    World world;

    @Before
    public void setUp() {
        Player bluePlayer = new PlayerImpl(PlayerTypes.BLUE);
        Player redPlayer = new PlayerImpl(PlayerTypes.RED);
        ArrayList<Player> players = new ArrayList<>();
        players.add(bluePlayer);
        players.add(redPlayer);

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

        world = new WorldImpl(worldString, players);
    }

    @Test
    public void shouldHaveForestOnX00Y00(){
        assertThat(world.getTerrainAtPosition(new Position(0, 0)), is(TerrainTypes.FOREST));
    }

    @Test
    public void shouldHavePlainsOnX01Y03(){
        assertThat(world.getTerrainAtPosition(new Position(1, 3)), is(TerrainTypes.PLAINS));
    }

    @Test
    public void shouldHaveOceanOnX03Y03(){
        assertThat(world.getTerrainAtPosition(new Position(3, 3)), is(TerrainTypes.OCEAN));
    }

    @Test
    public void shouldHaveMountainsOnX07Y01(){
        assertThat(world.getTerrainAtPosition(new Position(7, 1)), is(TerrainTypes.MOUNTAIN));
    }

    @Test
    public void shouldHaveHillsOnX09Y09(){
        assertThat(world.getTerrainAtPosition(new Position(9, 9)), is(TerrainTypes.HILLS));
    }

    @Test
    public void shouldHaveRedCityOnX00Y01(){
        City city = world.getCityAtPosition(new Position(0, 1));
        assertNotNull(city);
        assertThat(city.getOwner().getColor(), is(PlayerTypes.RED));
    }

    @Test
    public void shouldHaveBlueCityOnX09Y07(){
        City city = world.getCityAtPosition(new Position(9, 7));
        assertNotNull(city);
        assertThat(city.getOwner().getColor(), is(PlayerTypes.BLUE));
    }

    @Test
    public void shouldNotHaveCityOnX04Y07(){
        City city = world.getCityAtPosition(new Position(4, 7));
        assertNull(city);
    }

    @Test
    public void shouldHaveRedArcherOnX00Y01(){
        Unit unit = world.getUnitAtPosition(new Position(0, 1));
        assertNotNull(unit);
        assertThat(unit.getOwner().getColor(), is(PlayerTypes.RED));
        assertThat(unit.getType(), is(UnitTypes.ARCHER));
    }

    @Test
    public void shouldHaveRedWorkerOnX01Y03(){
        Unit unit = world.getUnitAtPosition(new Position(1, 3));
        assertNotNull(unit);
        assertThat(unit.getOwner().getColor(), is(PlayerTypes.RED));
        assertThat(unit.getType(), is(UnitTypes.WORKER));
    }

    @Test
    public void shouldHaveRedLegionOnX01Y04(){
        Unit unit = world.getUnitAtPosition(new Position(1, 4));
        assertNotNull(unit);
        assertThat(unit.getOwner().getColor(), is(PlayerTypes.RED));
        assertThat(unit.getType(), is(UnitTypes.LEGION));
    }

    @Test
    public void shouldHaveBlueLegionOnX09Y07(){
        Unit unit = world.getUnitAtPosition(new Position(9, 7));
        assertNotNull(unit);
        assertThat(unit.getOwner().getColor(), is(PlayerTypes.BLUE));
        assertThat(unit.getType(), is(UnitTypes.LEGION));
    }

    @Test
    public void shouldMoveBlueLegionOnX09Y07ToX08Y07(){
        Position start = new Position(9, 7);
        Position end = new Position(8, 7);
        Unit unit = world.getUnitAtPosition(new Position(9, 7));
        assertNotNull(unit);
        world.moveUnit(start, end);
        assertThat(world.getUnitAtPosition(end), is(unit));
        assertNull(world.getUnitAtPosition(start));
    }

    @Test
    public void getAllUnitsShouldReturn8Units(){
        assertThat(world.getAllUnits().size(), is(8));
    }

}
