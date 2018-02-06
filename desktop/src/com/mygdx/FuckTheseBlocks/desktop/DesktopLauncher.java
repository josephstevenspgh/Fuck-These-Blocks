package com.mygdx.FuckTheseBlocks.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.FuckTheseBlocks.mainClass;

public class DesktopLauncher {
	public static void main (String[] arg) {
                //base res 320 x 180
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
                config.title = "Fuck These Blocks";
                config.x = 600;
                config.y = 30;
                config.height = 180*4;
                config.width = 320*4;
		new LwjglApplication(new mainClass(), config);
	}
}


/*
    oldshitdone v0.0.0.0.0.0.01
        check if you can rotate before you rotatessssssaaa
        CanRotate can still crash
        Clearing more than 2 rows at once fucks shit up
        Add bitmap font for score/stat/debug info ingame

    oldshitdone 2016 5 26
        Still possible to go OoB?d 
            Not just edges, they can get misaligned in the middle of the field
        can somehow land diaganol to a block? Might be related to above

    Needs Fixed Still    
        Rotate by middle block, not top left
        counterclockwise rotation

    Improvements to base play
        give a friendly amount of frames to release Left or Right after tapping, for more precise control
        Change the way speed works
        Visual cueues on collision
            - White? outlines around collision marks
            - drop shadow under collision for a couple pixels
            
    Game Concept Thoughts
        I need it to drop faster
        Insta Drop - only allowed in score attack, or item battle too?
        Small frame window to still maneuver blocks after they touch the ground
        Small Time Freeze upon clearing rows - fixed amount, 15 frames? 20, 30?
            - This will making clearing multiple lines at once better for time
            - Gives your opponent time to counter attack?
            - Line Clear animations will play here

        Eye Candy - Instead of grey blocks, the tiles that land will turn into artwork
            - Keep the artwork the same reguardless of blocks cleared?
            - allow lines cleared to take lines out of the art and drop it, with different art on top?
            - Change art with game progress?
            - This idea is dumb in practice

*/