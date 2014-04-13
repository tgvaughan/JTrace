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

import java.util.ArrayList;
import java.util.List;
import jtrace.Ray;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

/**
 * Basic plane object.
 *
 * @author Tim Vaughan <tgvaughan@gmail.com>
 */
public class Plane extends SceneObject {
    
    Vector3D planeNormal;
    Vector3D planeNorth;
    Vector3D planeEast;
    
    /**
     * Create plane that passes through the origin and with a normal pointing
     * in the positive y direction. The positive u and v directions are the
     * x and y unit vectors.
     */
    public Plane() {
        super();

        this.planeNormal = Vector3D.PLUS_K;
        this.planeNorth = Vector3D.PLUS_J;
        this.planeEast = Vector3D.PLUS_I;
    }

    @Override
    public double getFirstCollisionObjectFrame(Ray ray) {
        
        double alpha = planeNormal.dotProduct(Vector3D.ZERO.subtract(ray.origin))
                / planeNormal.dotProduct(ray.direction);
        
        if (alpha<0)
            return Double.POSITIVE_INFINITY;
        
        incidentRay = ray;
        
        Vector3D collisionLocation = ray.origin.add(alpha, ray.direction);
        normalRay = new Ray(collisionLocation, planeNormal);
        
        Vector3D q = collisionLocation;
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

    @Override
    public List<Vector3D[]> getWireFrameObjectFrame() {
        List<Vector3D[]> edges = new ArrayList<>();

        Vector3D A = new Vector3D(0.5,planeNorth).add(0.5,planeEast);
        Vector3D B = new Vector3D(0.5,planeNorth).subtract(0.5,planeEast);
        Vector3D C = new Vector3D(-0.5,planeNorth).subtract(0.5,planeEast);
        Vector3D D = new Vector3D(-0.5,planeNorth).add(0.5,planeEast);
        
        Vector3D[] edge1 = {A,B};
        Vector3D[] edge2 = {B,C};
        Vector3D[] edge3 = {C,D};
        Vector3D[] edge4 = {D,A};
        
        edges.add(edge1);
        edges.add(edge2);
        edges.add(edge3);
        edges.add(edge4);
        
        return edges;
    }
}
