import core.AutograderBuddy;
import core.Save;
import edu.princeton.cs.algs4.StdDraw;
import org.junit.jupiter.api.Test;
import tileengine.TERenderer;
import tileengine.TETile;

import java.util.ArrayList;

import static com.google.common.truth.Truth.assertThat;

public class WorldGenTests {
    @Test
    public void basicTest() {
        // put different seeds here to test different worlds
        TETile[][] tiles = AutograderBuddy.getWorldFromInput("s19913813n");

        TERenderer ter = new TERenderer();
        ter.initialize(tiles.length, tiles[0].length);
        ter.renderFrame(tiles);
        StdDraw.pause(5000); // pause for 5 seconds so you can see the output
    }

    @Test
    public void basicInteractivityTest() {
        // TODO: write a test that uses an input like "n123swasdwasd"
        TETile[][] tiles = AutograderBuddy.getWorldFromInput("N999SDDD:Q");
        tiles = AutograderBuddy.getWorldFromInput("LWWWDDD");
        System.out.println("done");
    }

    @Test
    public void basicInteractivityTest2() {
        // TODO: write a test that uses an input like "n123swasdwasd"
        TETile[][] tiles = AutograderBuddy.getWorldFromInput("N999SDDD:Q");
        tiles = AutograderBuddy.getWorldFromInput("LWWW:Q");
        tiles = AutograderBuddy.getWorldFromInput("LDDD:Q");
        System.out.println("done");
    }

    @Test
    public void basicInteractivityTest3() {
        // TODO: write a test that uses an input like "n123swasdwasd"
        TETile[][] tiles = AutograderBuddy.getWorldFromInput("n5643591630821615871swwaawd");
        tiles = AutograderBuddy.getWorldFromInput("n5643591630821615871swwaawd");
        tiles = AutograderBuddy.getWorldFromInput("n142533315309887380sdddawawsa");
        tiles = AutograderBuddy.getWorldFromInput("n142533315309887380sdddawawsa");
        tiles = AutograderBuddy.getWorldFromInput("n2913037645193632194ssadwwwwsaad");
        tiles = AutograderBuddy.getWorldFromInput("n2913037645193632194ssadwwwwsaad");
        tiles = AutograderBuddy.getWorldFromInput("n1403711858794727810ssdwdd");
        tiles = AutograderBuddy.getWorldFromInput("n1403711858794727810ssdwdd");
        tiles = AutograderBuddy.getWorldFromInput("n1392967723524655428sddsaawwsaddw");
        tiles = AutograderBuddy.getWorldFromInput("n1392967723524655428sddsaawws");
        tiles = AutograderBuddy.getWorldFromInput("laddw");
        System.out.println("done");
    }

}
