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

/**
 *
 * @author Tim Vaughan <tgvaughan@gmail.com>
 */
public class Checker extends Finish {
    
    Colour colourA;
    Colour colourB;
    double period;
    
    double ambient;
    
    public Checker (Colour colourA, Colour colourB, double period,
            double ambient) {
        this.colourA = colourA;
        this.colourB = colourB;
        this.period = period;
        this.ambient = ambient;        
    }

    @Override
    public Colour getPigment() {
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

    @Override
    public double getAmbient() {
        return ambient;
    }

    @Override
    public double getDiffuse() {
        return 1.0;
    }

    @Override
    public double getSpecular() {
        return 0.0;
    }

    @Override
    public double getSpecularTightness() {
        return 10.0;
    }
    
}
