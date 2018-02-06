/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.FuckTheseBlocks;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.utils.CharArray;

/**
 *
 * @author setz
 */
public class PixelFont {
    String FontText;
    Pixmap Art;
    Pixmap RenderArea;
    int FontWidth;
    int FontHeight;
    
    public PixelFont(String Filename, int width, int height){
        Art = new Pixmap(Gdx.files.getFileHandle("Fonts/"+Filename, Files.FileType.Local));
        RenderArea = new Pixmap(320, 180, Pixmap.Format.RGBA8888);
        FontWidth = width;
        FontHeight = height;
    }
    
    private Pixmap GeneratePixmap(String InputText){
        RenderArea.setColor(Color.CLEAR);
        RenderArea.fill();
        char[] c = InputText.toCharArray();
        for(int i = 0; i < c.length; i++){
            int[] CharData = GetCharData(c[i]);
            RenderArea.drawPixmap(Art, 0+i*FontWidth, 0, CharData[0], CharData[1], FontWidth, FontHeight);
            //do linebreaks later
        }
        return RenderArea;
    }
    private Pixmap GeneratePixmap(String InputText, boolean CenteredX){
        RenderArea.setColor(Color.CLEAR);
        RenderArea.fill();
        char[] c = InputText.toCharArray();
        
        int StartX = 320 + (c.length * FontWidth) / 2;
        for(int i = 0; i < c.length; i++){
            int[] CharData = GetCharData(c[i]);
            RenderArea.drawPixmap(Art, StartX+i*FontWidth, 0, CharData[0], CharData[1], FontWidth, FontHeight);
            //do linebreaks later
        }
        return RenderArea;
    }
    
    public Pixmap GetPixmap(String InputText){
        return GeneratePixmap(InputText);
    }
    public Pixmap GetPixmap(String InputText, boolean CenteredX){
        return GeneratePixmap(InputText, CenteredX);
    }
    
    public Pixmap GetPixmap(){
        return GeneratePixmap(FontText);
    }
    
    public void SetText(String InputText){
        FontText = InputText;
    }
    
    public void ClearText(){
        FontText = "";
    }
    
    private int[] GetCharData(char c){
        int[] r = {0, 0};
        switch(c){
            case 'a':
            case 'A':
                r[0] = 0;
                r[1] = 0;
                break;
            case 'b':
            case 'B':
                r[0] = 1 * FontWidth;
                r[1] = 0;
                break;
            case 'c':
            case 'C':
                r[0] = 2 * FontWidth;
                r[1] = 0;
                break;
            case 'd':
            case 'D':
                r[0] = 3 * FontWidth;
                r[1] = 0;
                break;
            case 'e':
            case 'E':
                r[0] = 4 * FontWidth;
                r[1] = 0;
                break;
            case 'f':
            case 'F':
                r[0] = 5 * FontWidth;
                r[1] = 0;
                break;
            case 'g':
            case 'G':
                r[0] = 6 * FontWidth;
                r[1] = 0;
                break;
            case 'h':
            case 'H':
                r[0] = 7 * FontWidth;
                r[1] = 0;
                break;
            case 'i':
            case 'I':
                r[0] = 8 * FontWidth;
                r[1] = 0;
                break;
            case 'j':
            case 'J':
                r[0] = 9 * FontWidth;
                r[1] = 0;
                break;
            case 'k':
            case 'K':
                r[0] = 10 * FontWidth;
                r[1] = 0;
                break;
            case 'l':
            case 'L':
                r[0] = 11 * FontWidth;
                r[1] = 0;
                break;
            case 'm':
            case 'M':
                r[0] = 12 * FontWidth;
                r[1] = 0;
                break;
            case 'n':
            case 'N':
                r[0] = 13 * FontWidth;
                r[1] = 0;
                break;
            case 'o':
            case 'O':
                r[0] = 0;
                r[1] = 1 * FontHeight;
                break;
            case 'p':
            case 'P':
                r[0] = FontWidth;
                r[1] = 1 * FontHeight;
                break;
            case 'q':
            case 'Q':
                r[0] = 2 * FontWidth;
                r[1] = 1 * FontHeight;
                break;
            case 'r':
            case 'R':
                r[0] = 3 * FontWidth;
                r[1] = 1 * FontHeight;
                break;
            case 's':
            case 'S':
                r[0] = 4 * FontWidth;
                r[1] = 1 * FontHeight;
                break;
            case 't':
            case 'T':
                r[0] = 5 * FontWidth;
                r[1] = 1 * FontHeight;
                break;
            case 'u':
            case 'U':
                r[0] = 6 * FontWidth;
                r[1] = 1 * FontHeight;
                break;
            case 'v':
            case 'V':
                r[0] = 7 * FontWidth;
                r[1] = 1 * FontHeight;
                break;
            case 'w':
            case 'W':
                r[0] = 8 * FontWidth;
                r[1] = 1 * FontHeight;
                break;
            case 'x':
            case 'X':
                r[0] = 9 * FontWidth;
                r[1] = 1 * FontHeight;
                break;
            case 'y':
            case 'Y':
                r[0] = 10 * FontWidth;
                r[1] = 1 * FontHeight;
                break;
            case 'z':
            case 'Z':
                r[0] = 11 * FontWidth;
                r[1] = 1 * FontHeight;
                break;
            case ' ':
                r[0] = 0;
                r[1] = 4 * FontHeight;
                break;
            case ':':
                r[0] = 4 * FontWidth;
                r[1] = 3 * FontHeight;
                break;
            case '0':
                r[0] = 0;
                r[1] = 2 * FontHeight;
                break;
            case '1':
                r[0] = 0 * FontWidth;
                r[1] = 2 * FontHeight;
                break;
            case '2':
                r[0] = 2 * FontWidth;
                r[1] = 2 * FontHeight;
                break;
            case '3':
                r[0] = 3 * FontWidth;
                r[1] = 2 * FontHeight;
                break;
            case '4':
                r[0] = 4 * FontWidth;
                r[1] = 2 * FontHeight;
                break;
            case '5':
                r[0] = 5 * FontWidth;
                r[1] = 2 * FontHeight;
                break;
            case '6':
                r[0] = 6 * FontWidth;
                r[1] = 2 * FontHeight;
                break;
            case '7':
                r[0] = 7 * FontWidth;
                r[1] = 2 * FontHeight;
                break;
            case '8':
                r[0] = 8 * FontWidth;
                r[1] = 2 * FontHeight;
                break;
            case '9':
                r[0] = 9 * FontWidth;
                r[1] = 2 * FontHeight;
                break;
            case ',':
                r[0] = 3 * FontWidth;
                r[1] = 3 * FontHeight;
                break;
            case '.':
                r[0] = 2 * FontWidth;
                r[1] = 3 * FontHeight;
                break;
            case '?':
                r[0] = FontWidth;
                r[1] = 3 * FontHeight;
                break;
            case '-':
                r[0] = 5 * FontWidth;
                r[1] = 3 * FontHeight;
                break;
            case '(':
                r[0] = 9 * FontWidth;
                r[1] = 3 * FontHeight;
                break;
            case ')':
                r[0] = 10 * FontWidth;
                r[1] = 3 * FontHeight;
                break;
            case '+':
                r[0] = 13 * FontWidth;
                r[1] = 3 * FontHeight;
                break;
            case '"':
                r[0] = 4 * FontWidth;
                r[1] = 4 * FontHeight;
                break;
            case '|':
                r[0] = 5 * FontWidth;
                r[1] = 4 * FontHeight;
                break;
            case '<':
                r[0] = 9 * FontWidth;
                r[1] = 4 * FontHeight;
                break;
            case '>':
                r[0] = 10 * FontWidth;
                r[1] = 4 * FontHeight;
                break;
        }
        return r;
    }
}
