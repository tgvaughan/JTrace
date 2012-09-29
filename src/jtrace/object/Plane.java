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
 * Basic plane object.
 *
 * @author Tim Vaughan <tgvaughan@gmail.com>
 */
public class Plane extends SceneObject {
    
    Vector3D planeLocation;
    Vector3D planeNormal;
    Vector3D planeNorth;
    Vector3D planeEast;
    
    public Plane(Vector3D location, Vector3D normal, Vector3D north, Texture texture) {
        this.planeLocation = location;
        this.planeNormal = normal;
        this.texture = texture;
        texture.setObject(this);
        
        this.planeNorth = normal.crossProduct(north.crossProduct(normal)).normalize();
        this.planeEast = north.crossProduct(normal);
    }

    @Override
    public double getFirstCollision(Ray ray) {
        
        double alpha = planeNormal.dotProduct(planeLocation.subtract(ray.origin))
                / planeNormal.dotProduct(ray.direction);
        
        if (alpha<0)
            return Double.POSITIVE_INFINITY;
        
        incidentRay = ray;
        
        Vector3D collisionLocation = ray.origin.add(alpha, ray.direction);
        collisionLocation = collisionLocation.add(epsilon, planeNormal);
        normalRay = new Ray(collisionLocation, planeNormal);
        
        Vector3D q = collisionLocation.subtract(planeLocation);
        u = q.dotProduct(planeNorth);
        
        v = q.dotProduct(planeEast);
        
        return alpha;
    }

    @Override
    public double getU() {
        return u;
    }

    @Override
    public double getV() {
        return v;
    }
}
