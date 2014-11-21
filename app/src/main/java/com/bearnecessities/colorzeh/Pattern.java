package com.bearnecessities.colorzeh;

import java.util.Random;
import android.util.Log;

/**
 * Created by tjf3191 on 10/17/14.
 *
 * Bugs:
 * - position unlock is not working, but did not go out of bounds
 *
 * ToDo:
 *  - Refine the randomness generator. See method for more notes.
 *  - Add more implementation for different orders of patterns.
 *  - Test all types of input patterns
 *      = ORDER test (fully tested)
 *      = POSITION test (fully tested)
 *      = QUANTITY test (fully tested)
 *      = Two mode tests ?????
 *      = ORDER_POSITION_QUANTITY test ()
 *      = ORDER_QUANTITY_POSITION test ()
 *      = POSITION_ORDER_QUANTITY test ()
 *      = POSITION_QUANTITY_ORDER test ()
 *      = QUANTITY_ORDER_POSITION test ()
 *      = QUANTITY_POSITION_ORDER test ()
 *  - Review documentation and spelling.
 */
public class Pattern {

    /* =========================================================================
       STATIC VARIABLES
    ========================================================================= */

    // For logging?
    private static final String TAG = Pattern.class.getSimpleName();

    // Colors
    public static final String[] COLORS = {"RED", "BLUE", "YELLOW", "GREEN"};

    // Lock pattern types
    public static final String ORDER = "ORDER";
    public static final String POSITION = "POSITION";
    public static final String QUANTITY = "QUANTITY";
    // TWO MODE PATTERNS ??????
    public static final String ORDER_POSITION_QUANTITY = "ORDER_POSITION_QUANTITY";
    public static final String ORDER_QUANTITY_POSITION = "ORDER_QUANTITY_POSITION";
    public static final String POSITION_ORDER_QUANTITY = "POSITION_ORDER_QUANTITY";
    public static final String POSITION_QUANTITY_ORDER = "POSITION_QUANTITY_ORDER";
    public static final String QUANTITY_ORDER_POSITION = "QUANTITY_ORDER_POSITION";
    public static final String QUANTITY_POSITION_ORDER = "QUANTITY_POSITION_ORDER";

    /* =========================================================================
       INSTANCE VARIABLES
    ========================================================================= */

    private final String orderPattern;      // pattern of colors required ex["RGBYGR"]
    private final String positionPattern;   // pattern of clicking required ex["00010102"]
    private final int[] quantityPattern;    // quantities of colors required ex[5,2,4,2]

    private String layout;                  // order of color blocks from left to right, top to bottom

    private final String whichPattern;      // pattern to operate
    private int orderPosition;              // position in the order pattern
    private int positionPosition;           // position in the position pattern
    private int[] quantityPosition;         // position of the quantities

    private Random rand;                    // randomly changing the layout

    /* =========================================================================
       THE CONSTRUCTOR
    ========================================================================= */

    /**
     * This is the only constructor for the class Pattern
     * @param patterns the array of patterns to run
     *                 Array: (
     *                 "Pattern.[patterntype]"
     *                 "an order pattern [RGYRGG]",
     *                 "a position pattern [01221100]",
     *                 "a quantity pattern [4235]")
     * @param seed the seed for the random generator
     *
     * Example:
     *             String[] array = {Pattern.POSITION, "", "010203", "0000"};
     *             Pattern p = new Pattern (array, 1234L);
     */
    public Pattern (String mode, String[] patterns, long seed) {

        // some error checking
        if (patterns.length != 3) { System.exit(3);}
        if (patterns[1].length() % 2 != 0) { System.exit(3);}
        if (patterns[2].length() != 4) {System.exit(3);}

        // assign pattern items to the object
        this.whichPattern = mode;
        this.orderPattern = patterns[0];
        this.positionPattern = patterns[1];
        this.quantityPattern = new int[4];

        this.quantityPattern[0] = Integer.parseInt(patterns[2].substring(0,1));
        this.quantityPattern[1] = Integer.parseInt(patterns[2].substring(1,2));
        this.quantityPattern[2] = Integer.parseInt(patterns[2].substring(2,3));
        this.quantityPattern[3] = Integer.parseInt(patterns[2].substring(3,4));

        // set up all the internal object values
        this.orderPosition = 0;
        this.positionPosition = 0;
        this.quantityPosition = new int[COLORS.length];
        this.quantityPosition[0] = 0;
        this.quantityPosition[1] = 0;
        this.quantityPosition[2] = 0;
        this.quantityPosition[3] = 0;

        // Sets up the random generator object
        this.rand = new Random();
        this.rand.setSeed(seed);

        this.layout = generateLayout();
    }

    /* =========================================================================
       PUBLIC INSTANCE METHODS
    ========================================================================= */

    /**
     * Updates the pattern layout to a random new pattern when called.
     */
    public void updatePattern () {
        this.layout = generateLayout();
    }

    /**
     * Returns the Pattern.COLORS at the given position.
     * @param x the x position [0-2]
     * @param y the y position [0-2]
     * @return a Pattern.COLORS
     */
    public String getColorAtPosition (int x, int y) {
        return getColor(this.layout.substring(x + (y * 3), x + ((y * 3) + 1)));
    }

    /**
     * Updates the necessary pattern tracking based on the input give.
     * @param x the x position pressed [0-2]
     * @param y the y position pressed [0-2]
     * @return true if the pattern has been successfully executed.
     */
    public boolean input (int x, int y) {

        boolean unlocked = false;

        //ToDo:
        // - Add an option for every possible unlock method
        // - Figure out if android studio can use SDK 7

        /* will only work with SDK 7 or greater aka I hope it does
           because it's much cleaner.

        switch (this.whichPattern) {
            case Pattern.ORDER :
                updateOrderPattern(getColorAtPosition(x,y).substring(0,1));
                unlocked = this.orderPosition >= this.orderPattern.length();
                break;
            case Pattern.POSITION :
                updatePositionPattern(x,y);
                unlocked = this.positionPosition >= this.positionPattern.length();
                break;
            case Pattern.QUANTITY :
                updateQuantityPattern(getColorAtPosition(x,y).substring(0,1));
                unlocked = checkQuantities();
                break;
            case Pattern.ORDER_POSITION_QUANTITY :
            case Pattern.ORDER_QUANTITY_POSITION :
            case Pattern.POSITION_ORDER_QUANTITY :
            case Pattern.POSITION_QUANTITY_ORDER :
            case Pattern.QUANTITY_ORDER_POSITION :
            case Pattern.QUANTITY_POSITION_ORDER :
                break;
        }
        */

        if (this.whichPattern.equals(Pattern.ORDER)) {

            updateOrderPattern(getColorAtPosition(x,y).substring(0,1));
            unlocked = this.orderPosition >= this.orderPattern.length();

        } else if (this.whichPattern.equals(Pattern.POSITION)) {

            updatePositionPattern(x,y);
            unlocked = this.positionPosition >= this.positionPattern.length();

        } else if (this.whichPattern.equals(Pattern.QUANTITY)) {

            updateQuantityPattern(getColorAtPosition(x,y).substring(0,1));
            unlocked = checkQuantities();

        } else if (this.whichPattern.equals(Pattern.ORDER_POSITION_QUANTITY)) {

            unlocked = true;

        } else if (this.whichPattern.equals(Pattern.ORDER_QUANTITY_POSITION)) {

            unlocked = true;

        } else if (this.whichPattern.equals(Pattern.POSITION_ORDER_QUANTITY)) {

            unlocked = true;

        } else if (this.whichPattern.equals(Pattern.POSITION_QUANTITY_ORDER)) {

            unlocked = true;

        } else if (this.whichPattern.equals(Pattern.QUANTITY_ORDER_POSITION)) {

            unlocked = true;

        } else if (this.whichPattern.equals(Pattern.QUANTITY_POSITION_ORDER)) {

            unlocked = true;

        }
        
        return unlocked;
    }

    /* =========================================================================
       PRIVATE UTILITY METHODS
    ========================================================================= */

    /**
     * Checks if each quantity is at the correct value.
     * @return true if evey quantity is at the correct value, and false otherwise
     */
    private boolean checkQuantities() {
        boolean overage;
        boolean correct = false;

        overage = this.quantityPosition[0] > this.quantityPattern[0];
        overage = overage || this.quantityPosition[1] > this.quantityPattern[1];
        overage = overage || this.quantityPosition[2] > this.quantityPattern[2];
        overage = overage || this.quantityPosition[3] > this.quantityPattern[3];

        if (overage) {
            this.quantityPosition[0] = 0;
            this.quantityPosition[1] = 0;
            this.quantityPosition[2] = 0;
            this.quantityPosition[3] = 0;
        } else {
            correct = this.quantityPosition[0] == this.quantityPattern[0];
            correct = correct && this.quantityPosition[1] == this.quantityPattern[1];
            correct = correct && this.quantityPosition[2] == this.quantityPattern[2];
            correct = correct && this.quantityPosition[3] == this.quantityPattern[3];
        }
        return correct;
    }

    /**
     * This updates the quantity based on the input
     * @param c the color inputted [R|B|Y|G]
     */
    private void updateQuantityPattern (String c) {
        if (c.equals("R")) {
            this.quantityPosition[0]++;
        } else if (c.equals("B")) {
            this.quantityPosition[1]++;
        } else if (c.equals("Y")) {
            this.quantityPosition[2]++;
        } else {
            this.quantityPosition[3]++;
        }
    }

    /**
     * This checks if the inputted value is correct based on its position. if the correct
     * value is inputted then the position is advanced, otherwise it is reset.
     * @param x the x position pressed [0-2]
     * @param y the y position pressed [0-2]
     */
    private void updatePositionPattern (int x, int y) {
        String expected = this.positionPattern.substring(this.positionPosition, this.positionPosition + 2);
        String result = "" + x + "" + y;
        if (result.equals(expected)) {
            this.positionPosition += 2;
        } else {
            this.positionPosition = 0;
        }
    }
    
    /**
     * This checks if the inputted value is correct according to the orderPattern.
     * If the correct value is inputted then the position is advanced, otherwise it is
     * reset.
     * @param c the color inputted [R|B|Y|G]
     */
    private void updateOrderPattern (String c) {
        String expected = this.orderPattern.substring(this.orderPosition, this.orderPosition + 1);
        if (c.equals(expected)) {   // right input was entered
            this.orderPosition++;
        } else {                    // wrong input was entered reset
            this.orderPosition = 0;
        }
    }

    /**
     * Gets the Pattern.COLORS based on the input
     * @param c the color inputted [R|B|Y|G]
     * @return the Pattern.COLOR
     */
    private String getColor (String c) {
        if (c.equals("R")) {
            return Pattern.COLORS[0];
        } else if (c.equals("B")) {
            return Pattern.COLORS[1];
        } else if (c.equals("Y")) {
            return Pattern.COLORS[2];
        } else {
            return Pattern.COLORS[3];
        }
    }

    private String newLayout(int[] colorCount, String layoutBuilder, String mostRepresentedColor) {
        for (int i = 0; i < colorCount.length; i++) {
            if (colorCount[i] == 0) {
                int replaceIndex = layoutBuilder.indexOf(mostRepresentedColor);
                if (replaceIndex == 0) {
                   layoutBuilder = Pattern.COLORS[i] + layoutBuilder.substring(replaceIndex + 1);
                    } else {
                    layoutBuilder = layoutBuilder.substring(0, replaceIndex) + Pattern.COLORS[i] + layoutBuilder.substring(replaceIndex + 1);
                    }
                }
            }
        return layoutBuilder;
     }

    /**
     * This will randomly generate a new layout
     * @return the layout generated
     */
    private String generateLayout() {

        //Todo:
        // - Make sure that one of every color is generated.
        // - Make the randomness better.

        String layoutBuilder = "";
        int[] colorCount = {0,0,0,0};       //RBYG
        for (int c = 0; c < 9; c++) {
            String randomColor = Pattern.COLORS[this.rand.nextInt(4)].substring(0,1);
            if (randomColor.equals("R")) {
                    colorCount[0]++;
            } else if (randomColor.equals("B")) {
                colorCount[1]++;
            } else if (randomColor.equals("Y")) {
                colorCount[2]++;
            } else {
                colorCount[3]++;
            }
            layoutBuilder += randomColor;
        }

        String mostRepresentedColor = Pattern.COLORS[0];
        for (int c = 1; c < colorCount.length; c++) {
            if (colorCount[c] < colorCount[c-1]) {
                mostRepresentedColor = Pattern.COLORS[c];
            }
        }
        layoutBuilder = newLayout(colorCount, layoutBuilder, mostRepresentedColor);
        Log.v(TAG, layoutBuilder);
        return layoutBuilder;
    }
}
