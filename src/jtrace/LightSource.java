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

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

/**
 * Basic point light source.
 *
 * @author Tim Vaughan <tgvaughan@gmail.com>
 */
public class LightSource {   
    
    Vector3D location;
    Colour colour;
    
    public LightSource(Vector3D location) {
        this.location = location;
        this.colour = new Colour(1,1,1);
    }
    
    public LightSource(Vector3D location, Colour colour) {
        this.location= location;
        this.colour = colour;
    }
    
    /**
     * Obtain location of light source.
     * 
     * @return location of light.
     */
    public Vector3D getLocation() {
        return location;
    }
    
    /**
     * Obtain colour of light source.
     * 
     * @return colour
     */
    public Colour getColour() {
        return colour;
    }
}
