package com.mygdx.FuckTheseBlocks;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Locale;
import java.util.Random;

public class mainClass extends ApplicationAdapter {
        
    //grid array - 16 high 11 wide
    boolean[][] GameGrid = new boolean[11][50];
    final int GridStartX = 24;
    final int GridStartY = 8;

    //block logic
    final int PlayerOneXOffset = 24;
    final int PlayerTwoYOffset = 16;
    final int LeftBoundary = GridStartX;
    final int RightBoundary = GridStartX + (11 * 8);
    final int BottomBoundary = GridStartY + (19 * 8);
    final boolean BlockLEFT = true;
    final boolean BlockRIGHT = false;
    int CurrentBlockX = 16*10;
    int CurrentBlockY = 24;
    int BlockSpeed = 0;
    int BlockIterator = 0;
    int BlockWidth = 0;
    int BlockHeight = 0;
    boolean BlockMoving = false;
    boolean BlockDirection = true;

    //Art to load
    Pixmap BGImage;
    Pixmap GameBoard;
    Pixmap PlayerOne;
    Pixmap PlayerTwo;
    Pixmap BlockTiles;
    Pixmap FaceMap;
    
    //things in the game
    SpriteBatch batch;
    Block CurrentBlock;
    boolean Paused = false;
    boolean GameOver = false;
    Random RNG = new Random();
    
    //statistics
    PixelFont StatusFont;
    int LinesCleared;
    int GameLevel;
    int BlocksDropped;
    int CombosOf1;
    int CombosOf2;
    int CombosOf3;
    int CombosOf4;
    int CombosOf5;

    //screen
    Pixmap MainScreen;
    Texture DrawMe;

    @Override
    public void create () {
        InitGame();
    }

    @Override
    public void render () {
        /* Pause Game Functions */
        
        boolean PausePressed = Gdx.input.isKeyJustPressed(Input.Keys.P);
        
        if(PausePressed){
            if(Paused){
                Paused = false;
            }else{
                Paused = true;
            }
        }
        
        //debug
        boolean NewGame = Gdx.input.isKeyJustPressed(Input.Keys.O);

        if(NewGame){
            InitGame();
        }
        
        
        Gdx.gl.glClearColor(0.5f, 0.4f, 0.4f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        StaticBG();
        if(Paused){
            DrawPause();
        }else{
            GameLogic();
        }
        DrawStatusBar();
        DrawMe.dispose();
        DrawMe = new Texture(MainScreen);
        batch.draw(DrawMe, 0, 0, 320*4, 180*4);
        batch.end();
    }
    
    private void InitGame(){       
        /* Create objects */
        batch       = new SpriteBatch();
        FaceMap     = new Pixmap(88, 160, Pixmap.Format.RGBA8888);
        MainScreen  = new Pixmap(320, 180, Pixmap.Format.RGB888);
        DrawMe      = new Texture(320*4, 180*4, Pixmap.Format.RGB888);
        
        /* Load and create assets */
        BGImage     = new Pixmap(Gdx.files.getFileHandle("Stages/Mountain Beta 2.png", Files.FileType.Local));
        GameBoard   = new Pixmap(Gdx.files.getFileHandle("Stages/Mountain Beta Game Board.png", Files.FileType.Local));
        BlockTiles  = new Pixmap(Gdx.files.getFileHandle("art.png", Files.FileType.Local));
        FaceMap     = new Pixmap(Gdx.files.getFileHandle("Stages/MOuntain Board Fill.png", Files.FileType.Local));
        PlayerOne   = new Pixmap(40, 40, Pixmap.Format.RGBA8888);
        PlayerTwo   = new Pixmap(40, 40, Pixmap.Format.RGBA8888);
        StatusFont  = new PixelFont("EnhancedFont Tall.png", 8, 16);

        PlayerOne.drawPixmap(BlockTiles, 0, 0, 404, 24, 40, 40);
        PlayerTwo.drawPixmap(BlockTiles, 0, 0, 455, 17, 40, 40);
        
        /* Initialize variables */
        Paused          = false;
        GameOver        = false;
        LinesCleared    = 0;
        GameLevel       = 0;
        BlocksDropped   = 0;
        CombosOf1       = 0;
        CombosOf2       = 0;
        CombosOf3       = 0;
        CombosOf4       = 0;
        CombosOf5       = 0;

        /* Prep Game */
        ClearGrid();
        NewBlock(); 
    }
    
    private void StaticBG(){
            //construct game field

            //BG Layer + Board Layer
            MainScreen.drawPixmap(BGImage, 0, 0, 0, 0, 320, 180);
            MainScreen.drawPixmap(GameBoard, 0, 0, 0, 0, 320, 180);
            
            //Players
            MainScreen.drawPixmap(PlayerOne, 124, 108, 0, 0, 320, 180);
            MainScreen.drawPixmap(PlayerTwo, 165, 101, 0, 0, 320, 180);

            //filled blocks
            for(int i = 0; i < 11; i++){
                for(int j = 0; j < 19; j++){
                    if(GameGrid[i][j] == true){
                        int ThisX = (i * 8) + GridStartX;
                        int ThisY = (j * 8) + GridStartY;
                        //MainScreen.drawPixmap(FilledBlock, ThisX, ThisY, 0, 0, 8, 8);
                        MainScreen.drawPixmap(FaceMap,ThisX, ThisY,  i*8, j*8, 8, 8);
                    }
                }
            }

    }
    
    private void DrawPause(){
        StatusFont.SetText("Game Paused");
        MainScreen.drawPixmap(StatusFont.GetPixmap(), 116, 90);
    }
    
    private void DrawStatusBar(){
        String DrawString = "";
        /* Game Info */
        /*
        DrawString = "Level:  "+Integer.toString(GameLevel);
        MainScreen.drawPixmap(StatusFont.GetPixmap(DrawString), 8, 40);
        DrawString = "Lines:  "+Integer.toString(LinesCleared);
        MainScreen.drawPixmap(StatusFont.GetPixmap(DrawString), 8, 56);
        DrawString = "Blocks: "+Integer.toString(BlocksDropped);
        MainScreen.drawPixmap(StatusFont.GetPixmap(DrawString), 8, 72);
        DrawString = "x1:     "+Integer.toString(CombosOf1);
        MainScreen.drawPixmap(StatusFont.GetPixmap(DrawString), 8, 88);
        DrawString = "x2:     "+Integer.toString(CombosOf2);
        MainScreen.drawPixmap(StatusFont.GetPixmap(DrawString), 8, 104);
        DrawString = "x3:     "+Integer.toString(CombosOf3);
        MainScreen.drawPixmap(StatusFont.GetPixmap(DrawString), 8, 130);
        DrawString = "x4:     "+Integer.toString(CombosOf4);
        MainScreen.drawPixmap(StatusFont.GetPixmap(DrawString), 8, 146);
        DrawString = "x5:     "+Integer.toString(CombosOf5);
        MainScreen.drawPixmap(StatusFont.GetPixmap(DrawString), 8, 162);
        */
        
        /* Debug */
        /*
        DrawString = "Block ID: "+Integer.toString(CurrentBlock.GetID());
        MainScreen.drawPixmap(StatusFont.GetPixmap(DrawString), 0, 0);
        
        MainScreen.drawPixmap(StatusFont.GetPixmap("XOffset: "+CurrentBlock.GetXOffset()), 0, 10);
        MainScreen.drawPixmap(StatusFont.GetPixmap("YOffset: "+CurrentBlock.GetYOffset()), 0, 26);
        
        MainScreen.drawPixmap(StatusFont.GetPixmap("GX: "+CurrentBlock.GetGridX()), 8*20, 10);
        MainScreen.drawPixmap(StatusFont.GetPixmap("GY: "+CurrentBlock.GetGridY()), 8*20, 26);
        */
        
        /* Memory */
        long total = Runtime.getRuntime().totalMemory();
        long free = Runtime.getRuntime().freeMemory();
        long used = total - free;
        /*
        DrawString = "Total Memory: "+NumberFormat.getNumberInstance(Locale.US).format(total);
        MainScreen.drawPixmap(StatusFont.GetPixmap(DrawString), 8, 156);
        
        DrawString = "Free Memory:  "+NumberFormat.getNumberInstance(Locale.US).format(free);
        MainScreen.drawPixmap(StatusFont.GetPixmap(DrawString), 8, 164);
        
        DrawString = "Used Memory:  "+NumberFormat.getNumberInstance(Locale.US).format(used);
        MainScreen.drawPixmap(StatusFont.GetPixmap(DrawString), 8, 172);
        */
    }

    private void ClearGrid(){
        LinesCleared = 0;
        for(int i = 0; i < 11; i++){
            for(int j = 0; j < 19; j++){
                GameGrid[i][j] = false;
            }
        }
        CurrentBlock = new Block(RNG.nextInt(27), 0, BlockTiles);    
    }
    
    private void CheckInputs(){
        //catch inputs
        boolean LeftJustPressed = Gdx.input.isKeyJustPressed(Input.Keys.A);
        boolean RightJustPressed = Gdx.input.isKeyJustPressed(Input.Keys.D);
        boolean LeftHeld = Gdx.input.isKeyPressed(Input.Keys.A);
        boolean RightHeld = Gdx.input.isKeyPressed(Input.Keys.D);
        boolean DownHeld = Gdx.input.isKeyPressed(Input.Keys.S);
        boolean ActionLJustPressed = Gdx.input.isKeyJustPressed(Input.Keys.J);
        boolean ActionRJustPressed = Gdx.input.isKeyJustPressed(Input.Keys.K);
        boolean CHEATCHEATCHEAT = Gdx.input.isKeyJustPressed(Input.Keys.I);
        boolean QuitButton = Gdx.input.isKeyJustPressed(Input.Keys.Q);
        
        if(QuitButton){
            dispose();
        }
        
        if(CHEATCHEATCHEAT){
            NewBlock();
        }
        
        if(DownHeld){
            CurrentBlock.GoFast();
        }
        
        if(!CurrentBlock.IsMoving()){
            if(LeftJustPressed || LeftHeld){
                CurrentBlock.MoveLeft(GameGrid);
            }else if(RightJustPressed || RightHeld){
                CurrentBlock.MoveRight(GameGrid);
            }
        }

        if(ActionLJustPressed){
            //CurrentBlock.RotateL(GameGrid);
        }

        if(ActionRJustPressed){
            CurrentBlock.RotateR(GameGrid);
        }
        
    }
    
    private void CheckLanding(){
        if(CurrentBlock.CheckLanding(GameGrid)){
            int BlockX = CurrentBlock.GetGridX();
            int BlockY = CurrentBlock.GetGridY();
            boolean[][] BlockLayout = CurrentBlock.GetBlockLayout();
            
            //draw collision map
            
            for(int i=0; i < (BlockLayout.length) - 1; i++){
                for(int j=0; j < (BlockLayout[i].length) -1; j++){
                    if(BlockLayout[i][j] == true){
                        GameGrid[BlockX+j][(BlockY+i+1)] = true;
                    }
                }
            }
            int Combos = CheckCombos();
            switch(Combos){
                case 0:
                    break;
                case 1:
                    CombosOf1++;
                    break;
                case 2:
                    CombosOf2++;
                    break;
                case 3:
                    CombosOf3++;
                    break;
                case 4:
                    CombosOf4++;
                    break;
                case 5:
                    CombosOf5++;
                    break;
            }
            CheckClear();
            NewBlock();
        }
        
    }
    
    private void DrawGameBlock(){//draw block
        int DrawX = GridStartX + CurrentBlock.GetGridX()*8;
        int DrawY = GridStartY + CurrentBlock.GetGridY()*8 + CurrentBlock.GetYOffset() + 8;
        if(CurrentBlock.IsMoving()){
            if(CurrentBlock.GetDirection()){
                //DrawX += CurrentBlock.GetXOffset();
                DrawX = DrawX - CurrentBlock.GetXOffset() + 8;
            }else{
                DrawX += CurrentBlock.GetXOffset() - 8;
            }
        }
        MainScreen.drawPixmap(CurrentBlock.GetGraphics(), DrawX, DrawY);
        
    }

    private void GameLogic(){
        
        CheckInputs();
        
        if(GameOver){
            //104
            MainScreen.drawPixmap(StatusFont.GetPixmap("Git Gud"), 132, 90);
            return;
        }
        
        CurrentBlock.Tick(BlockSpeed, GameGrid);

        CheckLanding();
        DrawGameBlock();
        
        
        if(CheckLose()){
            GameOver = true;
        }
    }

    private void CheckClear(){
        boolean ClearRows = false;
        for(int r = 0; r < 19; r++){
            boolean CanClear = true;
            for(int c = 0; c < 11; c++){
                if(GameGrid[c][r] == false){
                    CanClear = false;
                }
            }
                
            if(CanClear){
                LinesCleared++;
                DropRows(r);
                CheckClear();
            }
        }
    }
    
    private boolean CheckLose(){
        /*
        System.out.println("");
        for(int i = 0; i < GameGrid.length; i++){
            String Out = i+". ";
            for(int j = 0; j < GameGrid[i].length; j++){
                Out += GameGrid[i][j]+" ";
            }
            System.out.println(Out);
        }*/
        if(GameGrid[5][1]){
            return true;
        }
        return false;
    }
    
    private int CheckCombos(){
        int AmountOfLines = 0;
        for(int r = 0; r < 19; r++){
            boolean CanClear = true;
            for(int c = 0; c < 11; c++){
                if(GameGrid[c][r] == false){
                    CanClear = false;
                }
            }
            if(CanClear){
                AmountOfLines++;
            }
        }
        return AmountOfLines;
    }
    
    private void DropRows(int StartRow){
        for(int r = StartRow; r >= 1; r--){
            for(int c = 0; c < 11; c++){
                GameGrid[c][r] = GameGrid[c][r-1];
            }
        }
    }

    private void NewBlock(){
            //Generate New Block
            /*
                Weight the blockdrops
                10% - 1blocks
                10% - 2blocks
                10% - 3blocks
                50% - 4blocks
                20% - 5blocks
            
                higher difficulties should scale more to 5b
            
                every 20/30/40 or so drops should be special block
            */
            
            // Odds in x/100
            int OneBlock    = 2;
            int TwoBlock    = 3     + OneBlock;
            int ThreeBlock  = 5     + TwoBlock;
            int FourBlock   = 75    + ThreeBlock;
            int FiveBlock   = 15    + FourBlock;
            
            
            int BlockCount = RNG.nextInt(100);
            
            System.out.println("RNG: "+BlockCount);
            
            if(BlockCount < OneBlock){            // 1 Block
                CurrentBlock.Reset(0, 0);
            }else if(BlockCount < OneBlock){      // 2 Block
                CurrentBlock.Reset(1, 0);
            }else if(BlockCount < TwoBlock){      // 3 block
                CurrentBlock.Reset(2 + RNG.nextInt(3), 0);
            }else if(BlockCount < FourBlock){      // 4 block
                CurrentBlock.Reset(5 + RNG.nextInt(7), 0);
            }else{                          // 5 block
                CurrentBlock.Reset(12 + RNG.nextInt(14), 0);                
            }    
            //CurrentBlock = new Block(20, 0, BlockTiles);      
            BlocksDropped++;
        }
}
