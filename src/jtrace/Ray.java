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
 * Class representing light rays in the ray tracer.
 * 
 * @author Tim Vaughan <tgvaughan@gmail.com>
 */
public class Ray {
    
    Vector3D origin;
    Vector3D direction;
    
    public Ray (Vector3D origin, Vector3D direction) {
        
        this.origin = origin;
        this.direction = direction;
    }
    
    public Vector3D getOrigin() {
        return origin;
    }
    
    public Vector3D getDirection() {
        return direction;
    }
    
}
