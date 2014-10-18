package com.bearnecessities.colorzeh;

import java.util.Random;

/**
 * Created by tjf3191 on 10/17/14.
 */
public class Pattern {

    // Colors
    public static final String[] COLORS = {"RED", "BLUE", "YELLOW", "GREEN"};

    private final String orderPattern;      // the pattern of colors required
    private final String positionPattern;   // the pattern of clicking required
    private final String quantityPattern;   // the quantities required

    private String layout;                  // the order of color blocks from left to right and top to bottom

    private int orderPosition;              // the position in the order pattern
    private int positionPosition;           // the position in the position pattern
    private int[] quantityPosition;         // the position of the quantities

    private Random rand;                    // for randomly changing the entire grid layout

    /**
     * This is the only constructor for the class Pattern
     * @param patterns the array of patterns to run from first to last for now
     *                 Array -> ("an order pattern [RGYRGG]",
     *                 "a positon pattern [01221100]",
     *                 "a quantity pattern [4235]")
     * @param seed the seed for the random generator
     */
    public Pattern (String[] patterns, long seed) {
        this.orderPattern = patterns[0];
        this.positionPattern = patterns[1];
        this.quantityPattern = patterns[2];

        this.orderPosition = 0;
        this.positionPosition = 0;
        this.quantityPosition = new int[COLORS.length];
        this.quantityPosition[0] = 0;
        this.quantityPosition[1] = 0;
        this.quantityPosition[2] = 0;
        this.quantityPosition[3] = 0;

        this.rand = new Random(seed);

        this.layout = generateLayout();
    }

    /**
     * Updates the pattern when called.
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
        if (y == 0) {
            return getColor(this.layout.substring(x, x+1));
        } else if (y == 1) {
            return getColor(this.layout.substring(x+3, x+4));
        } else {
            return getColor(this.layout.substring(x+6, x+7));
        }
    }

    public boolean input (int x, int y) {
        updateOrderPattern(getColorAtPosition(x,y).substring(0,1));
        return this.orderPosition >= this.orderPattern.length();
    }





    /*
        =========================================================
        Tommy's methods don't look!!! O_o
     */

    private void updateOrderPattern (String c) {
        String expected = this.orderPattern.substring(this.orderPosition, this.orderPosition + 1);
        if (c.equals(expected)) {   // right input was entered
            this.orderPosition++;
        } else {                    // wrong input was entered reset
            this.orderPosition = 0;
        }
    }

    private void updatePositionPattern (int x, int y) {
        String expected = this.positionPattern.substring(this.positionPosition, this.positionPosition + 2);
        if (c.equals(expected)) {
            this.positionPosition += 2;
        } else {
            this.positionPosition = 0;
        }

    }

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

    private String generateLayout() {
        return "RBYYGRGBY"; // static layout for now

        //ToDo
        // add a randome update;
    }
}