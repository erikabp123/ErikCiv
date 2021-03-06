package com.erikpoerksen.erikciv;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.erikpoerksen.erikciv.Adapter.CityTile;
import com.erikpoerksen.erikciv.Adapter.TerrainTile;
import com.erikpoerksen.erikciv.Adapter.TileMap;
import com.erikpoerksen.erikciv.Adapter.UnitTile;
import com.erikpoerksen.erikciv.GameLogic.Helpers.GameConstants;
import com.erikpoerksen.erikciv.GameLogic.Helpers.Position;
import com.erikpoerksen.erikciv.GameLogic.Implementations.GameImpl;
import com.erikpoerksen.erikciv.GameLogic.Pathfinding.Node;
import com.erikpoerksen.erikciv.GameLogic.Pathfinding.Pathfinding;
import com.erikpoerksen.erikciv.GameLogic.Structure.Unit;

import java.awt.*;
import java.util.ArrayList;


public class MainGameScreen implements Screen {

    Game gameGraphics;
    Stage stage;
    TextField textField;
    SpriteBatch batch;
    com.erikpoerksen.erikciv.GameLogic.Structure.Game game;
    TileMap tileMap;
    TextButton textButton;
    Texture selectionFrameBackground;
    SelectionFrame selectionFrame;
    ArrayList<Texture> disposableTextures;
    BitmapFont font;
    Position cursorPosition;


    public MainGameScreen(Game g){
        this.gameGraphics = g;
        this.font = new BitmapFont();
        this.stage = new Stage(){
            @Override
            public boolean keyUp (int keycode) {
                if(keycode == Input.Keys.M){
                    System.out.println("Changed to move!");
                    GraphicsConstants.MOUSEMODE = MouseMode.MOVE;
                } else if(keycode == Input.Keys.S){
                    System.out.println("Changed to select!");
                    GraphicsConstants.MOUSEMODE = MouseMode.SELECT;
                } else if(keycode == Input.Keys.P){
                    System.out.println("Changed to special!");
                    GraphicsConstants.MOUSEMODE = MouseMode.SPECIAL;
                } else if(keycode == Input.Keys.A){
                    System.out.println("Changed to attack!");
                    GraphicsConstants.MOUSEMODE = MouseMode.ATTACK;
                } else if(keycode == Input.Keys.TAB){
                    game.endTurn();
                    System.out.println("Ending turn! Now " + game.getPlayerInTurn().getColor() + "'s turn!");
                }
                return false;
            }
        };
        setupClickRegistration();
        Gdx.input.setInputProcessor(stage);
        Skin skin = new Skin(Gdx.files.internal("Skins/uiskin.json"));
        this.disposableTextures = new ArrayList<>();
        this.selectionFrameBackground = new Texture("UI/SelectionFrame.png");


        this.cursorPosition = convertCursorCoordinatesToGamePosition(Gdx.input.getX(), Gdx.input.getY());


        int yOffset = GraphicsConstants.TILE_SIZE*GameConstants.Y_LENGTH + GraphicsConstants.UI_SELECTION_FRAME_HEIGHT;
        this.textField = new TextField("", skin);
        this.textField.setPosition(0, yOffset);
        this.textField.setSize(300, 40);
        this.stage.addActor(textField);

        this.textButton = new TextButton("Perform", skin);
        this.textButton.setPosition(300, yOffset);
        this.textButton.setSize(200, 40);
        this.textButton.addListener(new ClickListener(){
            @Override
            public void clicked (InputEvent event, float x, float y) {
                processTextCommand();
            }
        });
        this.stage.addActor(textButton);



        batch = new SpriteBatch();
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
        this.game = new GameImpl(worldString);
        this.tileMap = new TileMap(game);
        this.selectionFrame = new SelectionFrame(game);
    }

    public void processTextCommand(){
        String command = textField.getText();
        String[] breakdown = command.split(" ");
        if(breakdown[0].equals("m")){
            int xFrom = Integer.parseInt(breakdown[1]);
            int yFrom = Integer.parseInt(breakdown[2]);
            int xTo = Integer.parseInt(breakdown[3]);
            int yTo = Integer.parseInt(breakdown[4]);
            Position from = new Position(yFrom, xFrom);
            Position to = new Position(yTo, xTo);

            System.out.println("Attempting: MOVE (" + xFrom + "," + yFrom + ") to (" + xTo + "," + yTo + ")");
            Boolean result = game.moveUnit(from, to);
            System.out.println("    ==>  " + result);
            tileMap.updateUnitLayout();
        } else if(breakdown[0].equals("end")){
            game.endTurn();
            System.out.println("Ending turn. New player in turn: ");
            System.out.println("    ==>  " + game.getPlayerInTurn().getColor());
        } else if(breakdown[0].equals("s")){
            Position selectedPosition = new Position(Integer.parseInt(breakdown[2]), Integer.parseInt(breakdown[1]));
            selectionFrame.selectPosition(selectedPosition);
            if(selectionFrame.selectedPosition == null){
                System.out.println("Deselecting");
            } else {
                System.out.println("Selecting: ");
                System.out.println("    ==>  (" + selectedPosition.getY() + "," + selectedPosition.getX() + ")");
            }
        }
        textField.setText("");
    }

    private void selectPosition(Position selectedPosition){
        selectionFrame.selectPosition(selectedPosition);
        if(selectionFrame.selectedPosition == null){
            System.out.println("Deselecting");
        } else {
            System.out.println("Selecting: ");
            System.out.println("    ==>  (" + selectedPosition.getY() + "," + selectedPosition.getX() + ")");
        }
    }

    private void setupClickRegistration(){
        stage.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(!coordinatesAreInsideWorld(x, y)){
                    return;
                }
                Position tilePosition = convertClickCoordinatesToGamePosition(x, y);
                System.out.println(GraphicsConstants.MOUSEMODE);
                if(GraphicsConstants.MOUSEMODE == MouseMode.SELECT){
                    selectionClick(tilePosition);
                } else if(GraphicsConstants.MOUSEMODE == MouseMode.MOVE){
                    if(moveClick(tilePosition)){
                        selectionClick(tilePosition); // update selection
                    }
                } else if(GraphicsConstants.MOUSEMODE == MouseMode.ATTACK){
                    attackClick(tilePosition);
                } else if(GraphicsConstants.MOUSEMODE == MouseMode.SPECIAL){
                    specialClick(tilePosition);
                }

            }
        });
    }

    private void selectionClick(Position tilePosition){
        selectPosition(tilePosition);
    }

    private boolean moveClick(Position tilePosition){
        if(selectionFrame.getSelectedPosition() == null){
            return false;
        }
        System.out.println("Attempting: MOVE (" + selectionFrame.selectedPosition.getX() + "," +
                selectionFrame.selectedPosition.getY() + ") to (" + tilePosition.getX() + "," +
                tilePosition.getY() + ")");
        Boolean result = game.moveUnit(selectionFrame.selectedPosition, tilePosition);
        System.out.println("    ==>  " + result);
        if(result){
            tileMap.updateUnitLayout();
        }
        return result;
    }

    private boolean specialClick(Position tilePosition){
        if(selectionFrame.getSelectedPosition() == null){
            return false;
        }
        System.out.println("Attempting: SPECIAL (" + selectionFrame.selectedPosition.getX() + "," +
                selectionFrame.selectedPosition.getY() + ") to (" + tilePosition.getX() + "," +
                tilePosition.getY() + ")");
        Boolean result = game.performSpecialActionAt(selectionFrame.getSelectedPosition(), tilePosition);
        System.out.println("    ==>  " + result);
        if(result){
            tileMap.updateCityLayout();
            tileMap.updateUnitLayout();
        }
        return result;
    }

    private boolean attackClick(Position tilePosition){
        if(selectionFrame.getSelectedPosition() == null){
            return false;
        }
        System.out.println("Attempting: Attack (" + selectionFrame.selectedPosition.getX() + "," +
                selectionFrame.selectedPosition.getY() + ") to (" + tilePosition.getX() + "," +
                tilePosition.getY() + ")");
        Boolean result = game.attackEnemy(selectionFrame.selectedPosition, tilePosition);
        System.out.println("    ==>  " + result);
        if(result){
            tileMap.updateCityLayout();
            tileMap.updateUnitLayout();
        }
        return result;
    }

    private boolean coordinatesAreInsideWorld(float x, float y){
        if(x < 0 || y < 0){
            return false;
        }
        int yOffset = GraphicsConstants.UI_SELECTION_FRAME_HEIGHT;
        if(y < yOffset){
            return false;
        }
        if(y >= yOffset + GraphicsConstants.WORLD_HEIGHT){
            return false;
        }
        if(x >= GraphicsConstants.WORLD_WIDTH){
            return false;
        }
        return true;
    }

    public Position convertCursorCoordinatesToGamePosition(int cursorX, int cursorY){
        int yOffset = GraphicsConstants.UI_COMMAND_HEIGHT;
        int x = cursorX/GraphicsConstants.TILE_SIZE;
        int y = (cursorY  - yOffset)/GraphicsConstants.TILE_SIZE;
        return new Position(y, x);
    }

    public Position convertClickCoordinatesToGamePosition(float x, float y){
        int yCoord = ((int) x)/GraphicsConstants.TILE_SIZE;
        int xCoord = ((int) y-GraphicsConstants.UI_SELECTION_FRAME_HEIGHT)/GraphicsConstants.TILE_SIZE;
        int xFlipped = GameConstants.X_LENGTH - 1 - xCoord;
        return new Position(xFlipped, yCoord);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        drawTerrain();
        drawCities();
        drawUnits();
        drawUI();
        updateCursorPosition();
        if(GraphicsConstants.MOUSEMODE == MouseMode.MOVE){
            drawPathfinding();
        }

        batch.end();
        stage.act(delta);
        stage.draw();
    }

    private void updateCursorPosition(){
        if(!isCursorInsideWorld()){
            return;
        }
        cursorPosition = convertCursorCoordinatesToGamePosition(Gdx.input.getX(), Gdx.input.getY());
    }

    private boolean isCursorInsideWorld(){
        int yOffset = GraphicsConstants.UI_COMMAND_HEIGHT;
        int x = Gdx.input.getX();
        int y = Gdx.input.getY() - yOffset;
        if(x < 0 || x >= GraphicsConstants.WORLD_WIDTH*GraphicsConstants.TILE_SIZE){
            System.out.println(x);
            return false;
        }
        if(y < 0 || y >= GraphicsConstants.WORLD_HEIGHT*GraphicsConstants.TILE_SIZE){
            return false;
        }
        return true;
    }

    private void drawPathfinding(){
        ArrayList<Node> path = (new Pathfinding(cursorPosition, selectionFrame.selectedPosition, game)).getPath();
        if(path.isEmpty()){
            return;
        }
        path.remove(path.size() - 1);
        path.add(0, new Node(null, 1, cursorPosition));
        int moveCount = game.getUnitAtPosition(selectionFrame.selectedPosition).getRemainingMoveCount();
        for(int i=path.size(); i>moveCount; i--){
            path.remove(0);
        }
        for(Node node : path){
            Texture nodeTexture = new Texture("UI/Destination.png");
            disposableTextures.add(nodeTexture);
            Position convertedPos = TileMap.convertPosition(node.getLocation());
            batch.draw(nodeTexture, convertedPos.getX(), convertedPos.getY());
        }
    }



    private void drawTerrain(){
        ArrayList<TerrainTile> terrain = tileMap.getTerrainLayout();
        for(TerrainTile tile : terrain){
            Texture terrainTexture = tile.getTexture();
            disposableTextures.add(terrainTexture);
            batch.draw(terrainTexture, tile.getxCord(), tile.getyCord());
        }
    }

    private void drawCities(){
        ArrayList<CityTile> cities = tileMap.getCityLayout();
        for(CityTile tile : cities){
            Texture cityTexture = tile.getTexture();
            disposableTextures.add(cityTexture);
            batch.draw(cityTexture, tile.getxCord(), tile.getyCord());
        }
    }

    private void drawUnits(){
        ArrayList<UnitTile> units = tileMap.getUnitLayout();
        for(UnitTile tile : units){
            Texture unitTexture = tile.getTexture();
            disposableTextures.add(unitTexture);
            batch.draw(unitTexture, tile.getxCord(), tile.getyCord());
        }
    }

    private void drawUI(){
        drawUIBackground();
        drawSelectionFrameContents();
    }

    private void drawSelectionFrameContents(){
        Position selectedPosition = selectionFrame.getSelectedPosition();
        if(selectedPosition != null){
            drawSelectionBrackets(selectedPosition);
            drawSelectedTerrain();
            drawSelectedCity(selectedPosition);
            drawSelectedUnit(selectedPosition);
        }
    }

    private void drawSelectedUnit(Position selectedPosition){
        Unit unit = game.getUnitAtPosition(selectedPosition);
        if(unit != null){
            Texture unitTexture = selectionFrame.getUnitTexture();
            disposableTextures.add(unitTexture);
            batch.draw(unitTexture, 270, 107);
            font.draw(batch, unit.getRemainingMoveCount() + "/" + unit.getDefaultMoveCount(), 330, 77);
            font.draw(batch, unit.getCurrentHealth() + "/" + unit.getMaxHealth(), 300, 37);
        }
    }

    private void drawSelectionBrackets(Position selectedPosition){
        Texture selectedBrackets = new Texture("UI/Selected.png");
        disposableTextures.add(selectedBrackets);
        Position converted = TileMap.convertPosition(selectedPosition);
        batch.draw(selectedBrackets, converted.getX(), converted.getY());
    }

    private void drawSelectedTerrain(){
        Texture terrainTexture = selectionFrame.getTerrainTexture();
        disposableTextures.add(terrainTexture);
        batch.draw(terrainTexture, 30, 50);
    }

    private void drawSelectedCity(Position selectedPosition){
        if(game.getCityAtPosition(selectedPosition) != null){
            Texture cityTexture = selectionFrame.getCityTexture();
            disposableTextures.add(cityTexture);
            batch.draw(cityTexture, 115, 50);
        }
    }

    private void drawUIBackground(){
        batch.draw(selectionFrameBackground, 0, 0);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        stage.dispose();
        selectionFrameBackground.dispose();
        for(Texture texture : disposableTextures){
            texture.dispose();
        }
    }
}
