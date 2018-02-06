/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.FuckTheseBlocks;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Color;


/**
 *
 * @author setz
 */
public class Block {
    //different block types
    final int BlockA1 = 0;  //1 block
    final int BlockB1 = 1;  //2 blocks
    final int BlockC1 = 2;  //3 blocks
    final int BlockC2 = 3;
    final int BlockC3 = 4;
    final int BlockD1 = 5;  //4 blocks
    final int BlockD2 = 6;
    final int BlockD3 = 7;
    final int BlockD4 = 8;
    final int BlockD5 = 9;
    final int BlockD6 = 10;
    final int BlockD7 = 11;
    final int BlockE1 = 12;  //5blocks
    final int BlockE2 = 13;
    final int BlockE3 = 14;
    final int BlockE4 = 15;
    final int BlockE5 = 16;
    final int BlockE6 = 17;
    final int BlockE7 = 18;
    final int BlockE8 = 19;
    final int BlockE9 = 20;
    final int BlockEA = 21;
    final int BlockEB = 22;
    final int BlockEC = 23;
    final int BlockED = 24;
    final int BlockEE = 25;
    final int BlockEF = 26;
    
    //Special Blocks
    final int BLockS1 = 100;
    
    //TODO: garbage cleanup
    
    //TODO - Internally, immediately snap the block left/right/down, visually, scroll it smoothly
    //This will get rid of a lot of collision bullshit, still keep the smooth look
    //rotate + move bad idea
    
    //block logic
    final boolean BlockLEFT = true;
    final boolean BlockRIGHT = false;
    int InternalLeftBoundary;
    int InternalRightBoundary;
    int InternalBottomBoundary;
    int InternalX;
    int InternalY;
    int XOffset;
    int YOffset;
    int Speed;
    int SpeedIterator;
    int Type;
    boolean BlockMoving = false;
    boolean BlockDirection = true;
    boolean MoveMade = true;
    
    //block construction and collision
    boolean[][] BlockLayout;
    Pixmap SourceArt;
    Pixmap BlockArt;
    
    
    public Block(int BlockType, int Difficulty, Pixmap Art){
        //define the block structure
        Type = BlockType;
        SourceArt = Art;
        BlockArt = new Pixmap(320, 180, Pixmap.Format.RGBA8888);
        GenerateBlockBody(Type);
        
        //Different difficulties wille ventually have different block and arena properties
        switch(Difficulty){
            case 0:
                //Set start position and bounds
                InternalX = 5;
                InternalY = 0;
                XOffset = 0;
                YOffset = 0;
                //boundaries
                InternalLeftBoundary = 0;
                InternalRightBoundary = 11;
                InternalBottomBoundary = 18;
                //Speed
                Speed = 5;
                SpeedIterator = 0;
                break;
        }        
    }
    
    public void Reset(int BlockType, int Difficulty){
        //define the block structure
        Type = BlockType;
        GenerateBlockBody(Type);
        
        //Different difficulties wille ventually have different block and arena properties
        switch(Difficulty){
            case 0:
                //Set start position and bounds
                InternalX = 5;
                InternalY = 0;
                XOffset = 0;
                YOffset = 0;
                //boundaries
                InternalLeftBoundary = 0;
                InternalRightBoundary = 11;
                InternalBottomBoundary = 18;
                //Speed
                Speed = 5;
                SpeedIterator = 0;
                break;
        }        
    }
    
    private void GenerateBlankLayout(){
        //create empty slate
        BlockLayout = new boolean[6][6];
        for(int i=0; i<5; i++){
            for(int j=0; j<5; j++){
                BlockLayout[i][j] = false;
            }
        }
        Color c = Color.CLEAR;
        BlockArt.setColor(c);
        BlockArt.fill();
    }
    
    private void GenerateGraphics(){
        int Color = 0;
        if(Type < 1){
            Color = 40;
        }else if (Type < 2){
            Color = 48;
        }else if (Type < 5){
            Color = 56;
        }else if (Type < 12){
            Color = 64;
        }else{
            Color = 72;
        }
        
        for(int i=0; i < BlockLayout.length; i++){
            for(int j=0; j < BlockLayout[i].length; j++){
                if(BlockLayout[j][i]){
                    int X = i*8;
                    int Y = j*8;
                    BlockArt.drawPixmap(SourceArt, X, Y, Color, 0, 8, 8);                
                }
            }
        }
    }
    
    private void GenerateBlockBody(int Type){
        //Different blocks different colors
        GenerateBlankLayout();
        switch(Type){
            default:
            case BlockA1:
                BlockLayout[0][0]=true;
                break;
            case BlockB1:
                BlockLayout[0][0] = true;
                BlockLayout[0][1] = true;
                break;
            case BlockC1:
                BlockLayout[0][0] = true;
                BlockLayout[0][1] = true;
                BlockLayout[1][0] = true;
                break;
            case BlockC2:
                BlockLayout[0][0] = true;
                BlockLayout[0][1] = true;
                BlockLayout[0][2] = true;
                break;
            case BlockC3:
                BlockLayout[0][0] = true;
                BlockLayout[1][0] = true;
                BlockLayout[0][1] = true;      
                break;
            case BlockD1:
                BlockLayout[0][0] = true;
                BlockLayout[0][1] = true;
                BlockLayout[0][2] = true;
                BlockLayout[0][3] = true;
                break;
            case BlockD2:
                BlockLayout[0][0] = true;
                BlockLayout[0][1] = true;
                BlockLayout[1][0] = true;
                BlockLayout[1][1] = true;
                break;
            case BlockD3:
                BlockLayout[1][1] = true;
                BlockLayout[0][0] = true;
                BlockLayout[0][1] = true;
                BlockLayout[0][2] = true;
                break;
            case BlockD4:
                BlockLayout[0][1] = true;
                BlockLayout[0][2] = true;
                BlockLayout[1][0] = true;
                BlockLayout[1][1] = true;
                break;
            case BlockD5:
                BlockLayout[0][0] = true;
                BlockLayout[0][1] = true;
                BlockLayout[0][2] = true;
                BlockLayout[1][0] = true;        
                break;
            case BlockD6:
                BlockLayout[0][0] = true;
                BlockLayout[0][1] = true;
                BlockLayout[0][2] = true;
                BlockLayout[1][2] = true;    
                break;
            case BlockD7:
                BlockLayout[0][0] = true;
                BlockLayout[1][1] = true;
                BlockLayout[1][2] = true;
                BlockLayout[0][1] = true;   
                break;
            case BlockE1:
                BlockLayout[0][0] = true;
                BlockLayout[0][1] = true;
                BlockLayout[0][2] = true;
                BlockLayout[0][3] = true;
                BlockLayout[0][4] = true;
                break;
            case BlockE2:
                BlockLayout[0][1] = true;
                BlockLayout[1][0] = true;
                BlockLayout[1][1] = true;
                BlockLayout[1][2] = true;
                BlockLayout[2][1] = true;
                break;
            case BlockE3:
                BlockLayout[0][0] = true;
                BlockLayout[0][1] = true;
                BlockLayout[0][2] = true;
                BlockLayout[1][1] = true;
                BlockLayout[2][1] = true;
                break;
            case BlockE4:
                BlockLayout[0][0] = true;
                BlockLayout[0][1] = true;
                BlockLayout[0][2] = true;
                BlockLayout[1][0] = true;
                BlockLayout[2][0] = true;
                break;
            case BlockE5:
                BlockLayout[0][0] = true;                
                BlockLayout[0][1] = true;
                BlockLayout[0][2] = true;
                BlockLayout[1][1] = true;
                BlockLayout[1][2] = true;                
                break;
            case BlockE6:
                BlockLayout[0][0] = true;
                BlockLayout[0][1] = true;
                BlockLayout[1][1] = true;
                BlockLayout[1][2] = true;
                BlockLayout[2][2] = true;
                break;
            case BlockE7:
                BlockLayout[0][0] = true;
                BlockLayout[0][1] = true;
                BlockLayout[0][2] = true;
                BlockLayout[0][3] = true;
                BlockLayout[1][0] = true;
                break;
            case BlockE8:
                BlockLayout[0][0] = true;
                BlockLayout[0][1] = true;
                BlockLayout[0][2] = true;
                BlockLayout[1][2] = true;
                BlockLayout[1][3] = true;                
                break;
            case BlockE9:
                BlockLayout[0][0] = true;
                BlockLayout[0][1] = true;
                BlockLayout[0][2] = true;
                BlockLayout[0][3] = true;
                BlockLayout[1][1] = true;
                break;
            case BlockEA:
                BlockLayout[0][0] = true;
                BlockLayout[0][1] = true;
                BlockLayout[0][2] = true;
                BlockLayout[1][0] = true;
                BlockLayout[1][2] = true;
                break;
            case BlockEB:
                BlockLayout[0][0] = true;                
                BlockLayout[0][1] = true;
                BlockLayout[0][2] = true;
                BlockLayout[1][1] = true;
                BlockLayout[1][2] = true;                
                break;
            case BlockEC:
                BlockLayout[1][0] = true;                
                BlockLayout[0][1] = true;
                BlockLayout[0][2] = true;
                BlockLayout[1][1] = true;
                BlockLayout[1][2] = true;    
                break;
            case BlockED:
                BlockLayout[0][0] = true;
                BlockLayout[0][1] = true;
                BlockLayout[0][2] = true;
                BlockLayout[0][3] = true;
                BlockLayout[1][0] = true;
                break;
            case BlockEE:
                BlockLayout[1][0] = true;
                BlockLayout[1][1] = true;
                BlockLayout[1][2] = true;
                BlockLayout[0][2] = true;
                BlockLayout[0][3] = true;
                break;
            case BlockEF:
                BlockLayout[0][0] = true;
                BlockLayout[0][1] = true;
                BlockLayout[0][2] = true;
                BlockLayout[0][3] = true;
                BlockLayout[1][1] = true;
                break;
        }             
        GenerateGraphics();
        
    }
    
    public void Tick(int Gamespeed, boolean[][]Collision){
        if(BlockMoving){
            if(BlockDirection == BlockLEFT){
                XOffset += 2;
                if(XOffset == 8){
                    BlockMoving = false;
                    XOffset = 0;
                }
            }else{
                XOffset += 2;
                if(XOffset == 8){
                    BlockMoving = false;
                    XOffset = 0;
                }
            }
        }
        
        SpeedIterator++;
        if(SpeedIterator >= Speed){
            YOffset += 1;
            SpeedIterator = 0;
            if(YOffset == 8){
                YOffset = 0;
                InternalY += 1;
            }
        }
    }
    
    public void RotateL(boolean[][] Collision){
        //spin block L
        int Width = GetWidth();
        int Height = GetHeight();
        boolean[][] OldLayout = BlockLayout;
        
        GenerateBlankLayout();
        
        for(int a = 0; a < 5; a++){
            for(int b = 0; b < 5; b++){
                if(OldLayout[a][b]){
                    BlockLayout[b][Height-a-1] = true;
                }
            }
        }
        GenerateGraphics();
    }
    
    public void RotateR(boolean[][] Collision){
        //spin block R
        int Width = GetWidth();
        int Height = GetHeight();
        boolean[][] OldLayout = BlockLayout;
        
        GenerateBlankLayout();
        
        for(int a = 0; a < 5; a++){
            for(int b = 0; b < 5; b++){
                if(OldLayout[a][b]){
                    BlockLayout[Width - 1 - b][a] = true;
                }
            }
        }
        if(!CanRotate(BlockLayout, Collision)){
            BlockLayout = OldLayout;
        }
        GenerateGraphics();
        
        /*
            Source - http://stackoverflow.com/a/2800033
        
            int[][] ret = new int[N][M];
            for (int r = 0; r < M; r++) {
                for (int c = 0; c < N; c++) {
                    ret[c][M-1-r] = mat[r][c];
                }
            }
        */
    }
    
    public void GoFast(){
        SpeedIterator = 5000;
    }
    
    public void InstaDrop(){
        //land instantly
    }
    
    public void MoveLeft(boolean[][] Collision){
        //check collision
        boolean CanMove = true;
        
        if(InternalX <= InternalLeftBoundary){
            CanMove = false;
        }
        
        if(CanMove){
            for (int r = 0; r < BlockLayout.length; r++){
                for(int c = 0; c < BlockLayout[r].length; c++){
                    if(BlockLayout[r][c]){
                        int CheckX = InternalX + c - 1;
                        int CheckY = InternalY + r + 2;
                        
                        if(CheckY < 19){
                            if(Collision[CheckX][CheckY]){
                                CanMove = false;
                            }
                        }else{
                            CanMove = false;
                            System.out.println("The fuck happened?");
                        }
                    }
                }
            }
        }
        
        if(CanMove){
            BlockMoving = true;
            BlockDirection = BlockLEFT;
            InternalX -= 1;
        }
    }
    
    public void MoveRight(boolean[][] Collision){
        //check collision
        boolean CanMove = true;
        
        if(InternalX + GetWidth() >= InternalRightBoundary){
            CanMove = false;
        }
        
        if(CanMove){
            for (int r = 0; r < BlockLayout.length; r++){
                for(int c = 0; c < BlockLayout[r].length; c++){
                    if(BlockLayout[r][c]){
                        int CheckX = InternalX + c + 1;
                        int CheckY = InternalY + r + 2;
                        
                        if(CheckY < 19){
                            if(Collision[CheckX][CheckY]){
                                CanMove = false;
                            }
                        }else{
                            CanMove = false;
                            System.out.println("The fuck happened?");
                        }
                    }
                }
            }
        }
        
        if(CanMove){
            BlockMoving = true;
            BlockDirection = BlockRIGHT;
            InternalX += 1;
        }
    }
    
    public boolean CheckLanding(boolean[][] Collision){
        //Check Floor
        if(InternalY + GetHeight() >= InternalBottomBoundary){
            return true;
        }
        
        for(int r = 0; r < BlockLayout.length; r++){
            for(int c = 0; c < BlockLayout[r].length; c++){
                if(BlockLayout[r][c]){
                    int CheckX = InternalX + c;
                    int CheckY = InternalY + r;
                    
                    if(Collision[CheckX][CheckY+2]){
                        //block below you
                        return true;
                    }
                }
            }
        }
        
        return false;
    }
    
    public boolean CanRotate(boolean[][] NewLayout, boolean[][] Collision){
        for(int r = 0; r < NewLayout.length; r++){
            for(int c = 0; c < NewLayout[r].length; c++){
                if(NewLayout[r][c]){
                    
                    int CheckX = InternalX+c;
                    int CheckY = InternalY+r+1;
                    
                    //check left wall
                    boolean LeftCheck = CheckX < InternalLeftBoundary;
                    //check right wall
                    boolean RightCheck = CheckX >= InternalRightBoundary;
                    //check bottom
                    boolean BottomCheck = CheckY >= InternalBottomBoundary;
                    //check collision
                    if(CheckX >= 11 || CheckY >= 19){
                        return false;
                    }
                    boolean CollisionCheck = Collision[CheckX][CheckY];
                    
                    
                    if(LeftCheck || RightCheck || BottomCheck || CollisionCheck){
                        return false;
                    }
                }                
            }
        }
        return true;
    }
    
    public boolean[][] GetBlockLayout(){
        return BlockLayout;
    }
    
    public int GetWidth(){
        int Width = 0;
        
        for(int r = 0; r < BlockLayout.length; r++){
            for(int c = 0; c < BlockLayout[r].length; c++){
                if(BlockLayout[r][c]){
                    if(c > Width){
                        Width = c;
                    }
                }
            }
        }
        
        Width += 1;
        
        return Width;
    }
    
    public int GetHeight(){
        int Height = 0;
        
        for(int r = 0; r < BlockLayout.length; r++){
            for(int c = 0; c < BlockLayout[r].length; c++){
                if(BlockLayout[r][c]){
                    if(r > Height){
                        Height = r;
                    }
                }
            }
        }
        
        Height += 1;
        
        return Height;        
    }
    
    public boolean IsMoving(){
        return BlockMoving;
    }
    
    public boolean GetDirection(){
        return BlockDirection;
    }
    
    public int GetXOffset(){
        return XOffset;
    }
    
    public int GetYOffset(){
        return YOffset;
    }
    
    public int GetGridX(){
        return InternalX;
    }
    
    public int GetGridY(){
        return InternalY;
    }
    
    public int GetID(){
        return Type;
    }
    
    public Pixmap GetGraphics(){
        return BlockArt;
    }
}
