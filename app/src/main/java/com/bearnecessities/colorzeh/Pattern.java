package com.bearnecessities.colorzeh;

import android.util.Log;

import java.util.Random;

/**
 * Created by tjf3191 on 10/17/14.
 *
 * ToDo:
 *  - Test all types of input patterns
 *      = ORDER                     (fully tested)
 *      = POSITION                  (fully tested)
 *      = QUANTITY                  (fully tested)
 *      = ORDER_POSITION            (need testing)
 *      = ORDER_QUANTITY            (fully tested)
 *      = POSITION_ORDER            (need testing)
 *      = POSITION_QUANTITY         (need testing)
 *      = QUANTITY_ORDER            (need testing)
 *      = QUANTITY_POSITION         (need testing)
 *      = ORDER_POSITION_QUANTITY   (fully tested)
 *      = ORDER_QUANTITY_POSITION   (need testing)
 *      = POSITION_ORDER_QUANTITY   (need testing)
 *      = POSITION_QUANTITY_ORDER   (need testing)
 *      = QUANTITY_ORDER_POSITION   (need testing)
 *      = QUANTITY_POSITION_ORDER   (need testing)
 */
public class Pattern {

    /* =========================================================================
       STATIC VARIABLES
    ========================================================================= */

    // Colors
    public static final String[] COLORS = {"RED", "BLUE", "YELLOW", "GREEN"};

    // Lock pattern types
    public static final String ORDER = "ORDER";
    public static final String POSITION = "POSITION";
    public static final String QUANTITY = "QUANTITY";
    public static final String ORDER_POSITION = "ORDER_POSITION";
    public static final String ORDER_QUANTITY = "ORDER_QUANTITY";
    public static final String POSITION_ORDER = "POSITION_ORDER";
    public static final String POSITION_QUANTITY = "POSITION_QUANTITY";
    public static final String QUANTITY_ORDER = "QUANTITY_ORDER";
    public static final String QUANTITY_POSITION = "QUANTITY_POSITION";
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

    private final int n;                    // the size of the grid
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
     * @param size must be greater or equal to 2
     *
     * Example:
     *             String[] array = {Pattern.POSITION, "", "010203", "0000"};
     *             Pattern p = new Pattern (array, 1234L);
     */
    public Pattern (String mode, int size, String[] patterns, long seed) {

        // some error checking
        if (patterns.length != 3) { System.exit(3);}
        if (patterns[1].length() % 2 != 0) { System.exit(3);}
        if (patterns[2].length() != 4) {System.exit(3);}
        if (size < 2) {System.exit(3);}

        // assign pattern items to the object
        this.whichPattern = mode;
        this.n = size;
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
        Log.v("pattern layout", this.layout);
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
     * @param x the x position [0-(n - 1)]
     * @param y the y position [0-(n - 1)]
     * @return a Pattern.COLORS
     */
    public String getColorAtPosition (int x, int y) {
        return getColor(this.layout.substring(x + (y * this.n), x + ((y * this.n) + 1)));
    }

    /**
     * Returns the Color letter at the given position.
     * @param x the x position [0-(n - 1)]
     * @param y the y position [0-(n - 1)]
     * @return a [R|G|B|Y]
     */
    public String getCAtPosition (int x, int y) {
        return getColorAtPosition(x, y).substring(0, 1);
    }

    /**
     * Returns the size of one edge of the grid.
     * @return the size of one edge
     */
    public int getGridSize () {return this.n;}

    /**
     * Updates the necessary pattern tracking based on the input give.
     * @param x the x position pressed [0-(n - 1)]
     * @param y the y position pressed [0-(n - 1)]
     * @return true if the pattern has been successfully executed.
     */
    public boolean input (int x, int y) {

        boolean unlocked = false;


        if (this.whichPattern.equals(Pattern.ORDER)) {

            updateOrderPattern(getCAtPosition(x,y));
            unlocked = this.orderPosition >= this.orderPattern.length();

        } else if (this.whichPattern.equals(Pattern.POSITION)) {

            updatePositionPattern(x,y);
            unlocked = this.positionPosition >= this.positionPattern.length();

        } else if (this.whichPattern.equals(Pattern.QUANTITY)) {

            updateQuantityPattern(getCAtPosition(x,y));
            unlocked = checkQuantities();

        }  else if (this.whichPattern.equals(Pattern.ORDER_POSITION)) {

            if (this.orderPosition >= this.orderPattern.length()) {
                updatePositionPattern(x,y);
                unlocked = this.positionPosition >= this.positionPattern.length();
            } else {
                updateOrderPattern(getCAtPosition(x,y));
            }

        } else if (this.whichPattern.equals(Pattern.ORDER_QUANTITY)) {

            if (this.orderPosition >= this.orderPattern.length()) {
                updateQuantityPattern(getCAtPosition(x,y));
                unlocked = checkQuantities();
            } else {
                updateOrderPattern(getCAtPosition(x,y));
            }

        } else if (this.whichPattern.equals(Pattern.POSITION_ORDER)) {

            if (this.positionPosition >= this.positionPattern.length()) {
                updateOrderPattern(getCAtPosition(x,y));
                unlocked = this.orderPosition >= this.orderPattern.length();
            } else {
                updatePositionPattern(x,y);
            }

        } else if (this.whichPattern.equals(Pattern.POSITION_QUANTITY)) {

            if (this.positionPosition >= this.positionPattern.length()) {
                updateQuantityPattern(getCAtPosition(x,y));
                unlocked = checkQuantities();
            } else {
                updatePositionPattern(x,y);
            }

        } else if (this.whichPattern.equals(Pattern.QUANTITY_ORDER)) {

            if (checkQuantities()) {
                updateOrderPattern(getCAtPosition(x,y));
                unlocked = this.orderPosition >= this.orderPattern.length();
            } else {
                updateQuantityPattern(getCAtPosition(x,y));
            }

        } else if (this.whichPattern.equals(Pattern.QUANTITY_POSITION)) {

            if (checkQuantities()) {
                updatePositionPattern(x,y);
                unlocked = this.positionPosition >= this.positionPattern.length();
            } else {
                updateQuantityPattern(getCAtPosition(x,y));
            }

        } else if (this.whichPattern.equals(Pattern.ORDER_POSITION_QUANTITY)) {

            if (this.orderPosition >= this.orderPattern.length()) {
                if (this.positionPosition >= this.positionPattern.length()) {
                    updateQuantityPattern(getCAtPosition(x,y));
                    unlocked = checkQuantities();
                } else {
                    updatePositionPattern(x, y);
                }
            } else {
                updateOrderPattern(getCAtPosition(x,y));
            }

        } else if (this.whichPattern.equals(Pattern.ORDER_QUANTITY_POSITION)) {

            if (this.orderPosition >= this.orderPattern.length()) {
                if (checkQuantities()) {
                    updatePositionPattern(x, y);
                    unlocked = this.positionPosition >= this.positionPattern.length();
                } else {
                    updateQuantityPattern(getCAtPosition(x,y));
                }
            } else {
                updateOrderPattern(getCAtPosition(x,y));
            }

        } else if (this.whichPattern.equals(Pattern.POSITION_ORDER_QUANTITY)) {

            if (this.positionPosition >= this.positionPattern.length()) {
                if (this.orderPosition >= this.orderPattern.length()) {
                    updateQuantityPattern(getCAtPosition(x,y));
                    unlocked = checkQuantities();
                } else {
                    updateOrderPattern(getCAtPosition(x,y));
                }
            } else {
                updatePositionPattern(x, y);
            }

        } else if (this.whichPattern.equals(Pattern.POSITION_QUANTITY_ORDER)) {

            if (this.positionPosition >= this.positionPattern.length()) {
                if (checkQuantities()) {
                    updateOrderPattern(getCAtPosition(x,y));
                    unlocked = this.orderPosition >= this.orderPattern.length();
                } else {
                    updateQuantityPattern(getCAtPosition(x,y));
                }
            } else {
                updatePositionPattern(x, y);
            }

        } else if (this.whichPattern.equals(Pattern.QUANTITY_ORDER_POSITION)) {

            if (checkQuantities()) {
                if (this.orderPosition >= this.orderPattern.length()) {
                    updateOrderPattern(getCAtPosition(x,y));
                    unlocked = this.positionPosition >= this.positionPattern.length();
                } else {
                    updatePositionPattern(x, y);
                }
            } else {
                updateQuantityPattern(getCAtPosition(x,y));
            }

        } else if (this.whichPattern.equals(Pattern.QUANTITY_POSITION_ORDER)) {

            if (checkQuantities()) {
                if (this.positionPosition >= this.positionPattern.length()) {
                    updateOrderPattern(getCAtPosition(x,y));
                    unlocked = this.orderPosition >= this.orderPattern.length();
                } else {
                    updatePositionPattern(x, y);
                }
            } else {
                updateQuantityPattern(getCAtPosition(x,y));
            }

        }
        
        return unlocked;
    }

    /* =========================================================================
       PRIVATE LOCK CHECKING METHODS
    ========================================================================= */

    private void resetPositions () {
        this.quantityPosition[0] = 0;
        this.quantityPosition[1] = 0;
        this.quantityPosition[2] = 0;
        this.quantityPosition[3] = 0;
        this.positionPosition = 0;
        this.orderPosition = 0;
    }

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
            this.resetPositions();
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
     * @param x the x position pressed [0-(n-1)]
     * @param y the y position pressed [0-(n-1)]
     */
    private void updatePositionPattern (int x, int y) {
        String expected = this.positionPattern.substring(this.positionPosition, this.positionPosition + 2);
        String result = "" + x + "" + y;
        if (result.equals(expected)) {
            this.positionPosition += 2;
        } else {
            this.resetPositions();
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
            this.resetPositions();
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

    /* =========================================================================
       PRIVATE GRID GENERATION METHODS
    ========================================================================= */

    private String newLayout(int[] colorCount, String layoutBuilder, String mostRepresentedColor) {
        for (int i = 0; i < colorCount.length; i++) {
            if (colorCount[i] == 0) {
                int replaceIndex = layoutBuilder.indexOf(mostRepresentedColor);
                if (replaceIndex == 0) {
                   layoutBuilder = Pattern.COLORS[i].substring(0,1) + layoutBuilder.substring(replaceIndex + 1);
                } else {
                    layoutBuilder = layoutBuilder.substring(0, replaceIndex) + Pattern.COLORS[i].substring(0,1) + layoutBuilder.substring(replaceIndex + 1);
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

        String layoutBuilder = "";
        int[] colorCount = {0,0,0,0};       //RBYG
        for (int c = 0; c < this.n * this.n; c++) {
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

        int largestValue = colorCount[0];
        String mostRepresentedColor = Pattern.COLORS[0].substring(0,1);
        for (int c = 0; c < colorCount.length; c++) {
            if (colorCount[c] >= largestValue) {
                largestValue = colorCount[c];
                mostRepresentedColor = Pattern.COLORS[c].substring(0,1);
            }
        }
        
        return newLayout(colorCount, layoutBuilder, mostRepresentedColor);
    }
}
