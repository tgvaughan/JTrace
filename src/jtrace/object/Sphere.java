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
package jtrace.object;

import jtrace.Ray;
import jtrace.SceneObject;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

/**
 * Basic sphere object.
 * 
 * @author Tim Vaughan <tgvaughan@gmail.com>
 */
public class Sphere extends SceneObject {

    @Override
    public double getFirstCollision(Ray ray) {
        
        Vector3D displacement = ray.getOrigin().subtract(getLocation());
        
        double a = ray.getDirection().getNormSq();
        double b = 0.5*ray.getDirection().dotProduct(displacement);
        double c = displacement.getNormSq();
        
        // Check for miss:
        if (b*b < 4.0*a*c)
            return Double.POSITIVE_INFINITY;
        
        // Determine actual intersections
        
        return 0.0;
    }
    
}
