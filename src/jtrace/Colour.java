/*
 * Copyright (C) 2012 Tim Vaughan <tgvaughan@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package jtrace;

import java.awt.Color;

/**
 * Class of objects describing colours.
 *
 * @author Tim Vaughan <tgvaughan@gmail.com>
 */
public class Colour {
    
    double r, g, b;
    
    /**
     * Construct colour from RGB triplet.
     * 
     * @param r
     * @param g
     * @param b 
     */
    public Colour(double r, double g, double b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }
    
    /**
     * Construct colour from 32 bit integer representation.
     * 
     * @param c integer representing colour
     */
    public Colour(int c) {
        this.r = ((0xff0000 & c) >> 16)/255.0;
        this.g = ((0x00ff00 & c) >> 8)/255.0;
        this.b = (0x0000ff & c)/255.0;
    }
    
    /**
     * Get 32bit integer representation of colour.
     * 
     * @return integer representing colour
     */
    public int getInt() {
        int res = (int)(Math.min(r, 1.0)*255) << 16;
        res += (int)(Math.min(g,1.0)*255) << 8;
        res += (int)(Math.min(b,1.0)*255);
        
        return res;
    }
    
    /**
     * Return a new colour with RGB components scaled by the given factor.
     * 
     * @param factor
     * @return a new colour object
     */
    public Colour scale(double factor) {
        return new Colour(r*factor, g*factor, b*factor);
    }
    
    /**
     * Return a new colour made by adding RGB components of this colour
     * to those of the colour c.
     * @param c colour to add
     * @return a new colour object
     */
    public Colour add(Colour c) {
        return new Colour(r+c.r, g+c.g, b+c.b);
    }
    
    /**
     * Return a new colour made by subtracting the RGB components of colour
     * c from those of this colour.
     * @param c colour to add
     * @return a new colour object
     */
    public Colour subtract(Colour c) {
        return new Colour(r-c.r, g-c.g, b-c.b);
    }
    
    
    /**
     * Return a new colour made through element by element multiplication of
     * this colour with colour c.
     * 
     * @param c
     * @return a new colour object.
     */
    public Colour filter(Colour c) {
        return new Colour(r*c.r, g*c.g, b*c.b);
    }
}
