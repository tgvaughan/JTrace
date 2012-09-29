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
import jtrace.texture.Texture;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

/**
 * Basic sphere object.
 * 
 * @author Tim Vaughan <tgvaughan@gmail.com>
 */
public class Sphere extends SceneObject {
    
    // Centre of sphere
    Vector3D centre;
    
    // Radius of sphere
    double radius;
    
    /**
     * Constructor for sphere objects.
     * 
     * @param centre
     * @param radius
     * @param texture 
     */
    public Sphere(Vector3D centre, double radius, Texture texture) {
        this.centre = centre;
        this.radius = radius;
        this.texture = texture;
        texture.setObject(this);
    }

    @Override
    public double getFirstCollision(Ray ray) {
        
        Vector3D displacement = ray.getOrigin().subtract(centre);
        
        double a = ray.getDirection().getNormSq();
        double b = 2.0*ray.getDirection().dotProduct(displacement);
        double c = displacement.getNormSq() - radius*radius;
        
        // Check for miss:
        if (b*b < 4.0*a*c)
            return Double.POSITIVE_INFINITY;
        
        // Determine actual intersections
        double alpha0 = -0.5*b/a;
        double dalpha = 0.5*Math.sqrt(b*b-4.0*a*c)/a;
        
        double alphaPlus = alpha0 + dalpha;
        double alphaMinus = alpha0 - dalpha;
       
        if (alphaMinus < alphaPlus && alphaMinus>0) {
            collidingRay = ray;
            Vector3D collisionLocation = ray.direction.scalarMultiply(alphaMinus).add(ray.origin);
            Vector3D normal = collisionLocation.subtract(centre).normalize();
            
            // Hack to avoid child rays colliding with the same surface
            // (Need a better way of doing this.)
            collisionLocation = collisionLocation.add(0.001, normal);
            
            normalRay = new Ray(collisionLocation, normal);
            
            return alphaMinus;
        }
        
        if (alphaPlus > 0) {
            collidingRay = ray;
            Vector3D collisionLocation = ray.direction.scalarMultiply(alphaPlus).add(ray.origin);
            Vector3D normal = collisionLocation.subtract(centre).normalize();
            normalRay = new Ray(collisionLocation, normal);
            
            return alphaPlus;
        }
        
        return Double.POSITIVE_INFINITY;
    }
    
}
