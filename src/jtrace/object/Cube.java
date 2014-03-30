/*
 * Copyright (C) 2014 Tim Vaughan <tgvaughan@gmail.com>
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
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

/**
 * Basic box object
 *
 * @author Tim Vaughan <tgvaughan@gmail.com>
 */
public class Cube extends SceneObject {

    /**
     * Centre of cube.
     */
    Vector3D centre;

    /**
     * Length of cube side.
     */
    double side;
    
    /**
     * Normals for each of the 6 faces
     */
    Ray [] normals;
    
    /**
     * Create square.
     * 
     * @param centre
     * @param side 
     */
    public Cube(Vector3D centre, double side) {
        this.centre = centre;
        this.side = side;
        
        normals = new Ray[6];
        for (int i=0; i<6; i++)
            normals[i] = new Ray();
        
        normals[0].origin = centre.add(0.5*side, Vector3D.PLUS_I);
        normals[1].origin = centre.add(0.5*side, Vector3D.MINUS_I);
        normals[2].origin = centre.add(0.5*side, Vector3D.PLUS_J);
        normals[3].origin = centre.add(0.5*side, Vector3D.MINUS_J);
        normals[4].origin = centre.add(0.5*side, Vector3D.PLUS_K);
        normals[5].origin = centre.add(0.5*side, Vector3D.MINUS_K);
        
        normals[0].direction = Vector3D.PLUS_I;
        normals[1].direction = Vector3D.MINUS_I;
        normals[2].direction = Vector3D.PLUS_J;
        normals[3].direction = Vector3D.MINUS_J;
        normals[4].direction = Vector3D.PLUS_K;
        normals[5].direction = Vector3D.MINUS_K;
    }
    
    @Override
    public double getFirstCollision(Ray ray) {
        
        // Exit early if no chance of collision
        //Vector3D CminusL = this.centre.subtract(ray.origin);
        //double closest = (CminusL).getNorm()/Math.abs(CminusL.dotProduct(ray.direction));
        //if (closest > side/Math.sqrt(2))
        //    return Double.POSITIVE_INFINITY; // miss
        
        // Cube edges and vertices are the intersections of 6 planes.
        // Need to find points of intersection with each of these planes.
        
        double alpha = Double.POSITIVE_INFINITY;
        Ray collisionNormal = new Ray();
        for (int i=0; i<6; i++) {
            double thisAlpha = normals[i].direction.dotProduct(normals[i].origin.subtract(ray.origin))
                    /normals[i].direction.dotProduct(ray.direction);
            
            if (thisAlpha<=0)
                continue;
            
            Vector3D collisionLocation = ray.origin.add(thisAlpha, ray.direction);
            Vector3D delta = collisionLocation.subtract(normals[i].origin);
            if (Math.abs(delta.getX())>0.5*side
                    || Math.abs(delta.getY())>0.5*side
                    || Math.abs(delta.getZ())>0.5*side) {
                continue;
            }
            
            if (thisAlpha<alpha) {
                alpha = thisAlpha;
                collisionNormal.origin = collisionLocation;
                collisionNormal.direction = normals[i].direction;
            }
        }
        
        if (alpha<Double.POSITIVE_INFINITY) {
            incidentRay = ray;
            normalRay = collisionNormal;
        }
        
        return alpha;
    }

    @Override
    public double getU() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public double getV() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
