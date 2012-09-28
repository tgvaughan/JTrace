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

/**
 *
 * @author Tim Vaughan <tgvaughan@gmail.com>
 */
public class Colour {
    
    double[] rgb;
    
    public Colour(double r, double g, double b) {
        rgb = new double[3];
        rgb[0] = r;
        rgb[1] = g;
        rgb[2] = b;
    }
    
    public int getInt() {
        int res = (int)(rgb[0]*255) << 16;
        res += (int)(rgb[1]*255) << 8;
        res += (int)(rgb[2]*255);
        
        return res;
    }
}
