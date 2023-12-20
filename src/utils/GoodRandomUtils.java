package utils;
import java.util.Random;

public class GoodRandomUtils {
    long seed;
    Random rand;

    public GoodRandomUtils(long seed) {
        this.seed = seed;
        rand = new Random();
        rand.setSeed(seed);
    }
    public double randBoundedGaussian() {
        return rand.nextInt()&0xff;
    }

    public double zeroToOneDoubleGauss() {
        return randBoundedGaussian() / 256 ;
    }

    public int zeroToMaxIntGauss(int max) {
        return (int) Math.floor(randBoundedGaussian() / 256 * max) ;
    }

    public int minToMaxIntGauss(int min, int max) {
        int tempNum = (int) Math.floor(randBoundedGaussian() / 256 * max);

        while (tempNum < min) {
            tempNum = (int) Math.floor(randBoundedGaussian() / 256 * max);
        }
        return tempNum;
    }

    public int minToMaxIntUniform(int min, int max) {
        if (max <= 0) {
            throw new IllegalArgumentException("argument must be positive: " + max);
        }
        int tempNum = rand.nextInt(max);
        while (tempNum < min) {
            tempNum = rand.nextInt(max);
        }
        return tempNum;
    }

}
