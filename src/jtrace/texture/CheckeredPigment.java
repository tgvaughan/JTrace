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
package jtrace.texture;

import jtrace.Colour;
import jtrace.object.SceneObject;

/**
 * A checkered two-coloured pigment.
 *
 * @author Tim Vaughan <tgvaughan@gmail.com>
 */
public class CheckeredPigment extends Pigment {
    
    Colour colourA;
    Colour colourB;
    double period;
    
    /**
     * Create a pigment using squares having alternate colours
     * of colourA and colourB. The size of the squares are fixed
     * by the period parameter, which specifies the u or v distance
     * over which the pattern repeats.
     * 
     * @param colourA
     * @param colourB
     * @param period 
     */
    public CheckeredPigment (Colour colourA, Colour colourB, double period) {
        this.colourA = colourA;
        this.colourB = colourB;
        this.period = period;
    }

    @Override
    public Colour getPigment(SceneObject object) {
        
        double u = object.getU();
        double v = object.getV();
        
        double scaledU = Math.abs(object.getU()/period)%1.0;
        if (u<0)
            scaledU = 1.0 - scaledU;
        
        double scaledV = Math.abs(object.getV()/period)%1.0;
        if (v<0)
            scaledV = 1.0 - scaledV;
        
        if ((scaledU<0.5 && scaledV<0.5) || (scaledU>0.5 && scaledV>0.5))
            return colourA;
        else
            return colourB;
    }
    
}
