package com.erikpoerksen.erikciv;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.erikpoerksen.erikciv.Adapter.CityTile;
import com.erikpoerksen.erikciv.Adapter.TerrainTile;
import com.erikpoerksen.erikciv.Adapter.TileMap;
import com.erikpoerksen.erikciv.Adapter.UnitTile;
import com.erikpoerksen.erikciv.GameLogic.Helpers.Position;
import com.erikpoerksen.erikciv.GameLogic.Implementations.GameImpl;

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

    public MainGameScreen(Game g){
        this.gameGraphics = g;
        this.stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        Skin skin = new Skin(Gdx.files.internal("Skins/uiskin.json"));

        this.textField = new TextField("", skin);
        this.textField.setPosition(0, 500);
        this.textField.setSize(300, 40);
        this.stage.addActor(textField);

        this.textButton = new TextButton("Perform", skin);
        this.textButton.setPosition(300, 500);
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
        }
        textField.setText("");
    }



    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        ArrayList<TerrainTile> terrain = tileMap.getTerrainLayout();
        for(TerrainTile tile : terrain){
            batch.draw(tile.getTexture(), tile.getxCord(), tile.getyCord());
        }

        ArrayList<CityTile> cities = tileMap.getCityLayout();
        for(CityTile tile : cities){
            batch.draw(tile.getTexture(), tile.getxCord(), tile.getyCord());
        }

        ArrayList<UnitTile> units = tileMap.getUnitLayout();
        for(UnitTile tile : units){
            batch.draw(tile.getTexture(), tile.getxCord(), tile.getyCord());
        }
        batch.end();
        stage.act(delta);
        stage.draw();
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
    }
}
