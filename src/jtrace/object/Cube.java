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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import jtrace.Ray;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

/**
 * Basic box object
 *
 * @author Tim Vaughan <tgvaughan@gmail.com>
 */
public class Cube extends SceneObject {
    
    /**
     * Normals for each of the 6 faces
     */
    Ray [] normals;
    
    /**
     * Create cube with unit side.
     */
    public Cube() {
        super();
        
        normals = new Ray[6];
        for (int i=0; i<6; i++)
            normals[i] = new Ray();
        
        normals[0].origin = new Vector3D(0.5, Vector3D.PLUS_I);
        normals[1].origin = new Vector3D(0.5, Vector3D.MINUS_I);
        normals[2].origin = new Vector3D(0.5, Vector3D.PLUS_J);
        normals[3].origin = new Vector3D(0.5, Vector3D.MINUS_J);
        normals[4].origin = new Vector3D(0.5, Vector3D.PLUS_K);
        normals[5].origin = new Vector3D(0.5, Vector3D.MINUS_K);
        
        normals[0].direction = Vector3D.PLUS_I;
        normals[1].direction = Vector3D.MINUS_I;
        normals[2].direction = Vector3D.PLUS_J;
        normals[3].direction = Vector3D.MINUS_J;
        normals[4].direction = Vector3D.PLUS_K;
        normals[5].direction = Vector3D.MINUS_K;
    }
    
    @Override
    public double getFirstCollisionObjectFrame(Ray ray) {
        
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
            if (Math.abs(delta.getX())>0.5
                    || Math.abs(delta.getY())>0.5
                    || Math.abs(delta.getZ())>0.5) {
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

    @Override
    public List<Vector3D[]> getWireFrameObjectFrame() {
        
        Vector3D[] vertices = new Vector3D[8];
        vertices[0] = new Vector3D(0.5,Vector3D.PLUS_K)
                .add(0.5,Vector3D.PLUS_I).add(0.5,Vector3D.PLUS_J);
        vertices[1] = new Vector3D(0.5,Vector3D.PLUS_K)
                .subtract(0.5,Vector3D.PLUS_I).add(0.5,Vector3D.PLUS_J);
        vertices[2] = new Vector3D(0.5,Vector3D.PLUS_K)
                .subtract(0.5,Vector3D.PLUS_I).subtract(0.5,Vector3D.PLUS_J);
        vertices[3] = new Vector3D(0.5,Vector3D.PLUS_K)
                .add(0.5,Vector3D.PLUS_I).subtract(0.5,Vector3D.PLUS_J);
        
        vertices[4] = new Vector3D(-0.5,Vector3D.PLUS_K)
                .add(0.5,Vector3D.PLUS_I).add(0.5,Vector3D.PLUS_J);
        vertices[5] = new Vector3D(-0.5,Vector3D.PLUS_K)
                .subtract(0.5,Vector3D.PLUS_I).add(0.5,Vector3D.PLUS_J);
        vertices[6] = new Vector3D(-0.5,Vector3D.PLUS_K)
                .subtract(0.5,Vector3D.PLUS_I).subtract(0.5,Vector3D.PLUS_J);
        vertices[7] = new Vector3D(-0.5,Vector3D.PLUS_K)
                .add(0.5,Vector3D.PLUS_I).subtract(0.5,Vector3D.PLUS_J);
        
        Vector3D[][] edges = {
            {vertices[0],vertices[1]},
            {vertices[1],vertices[2]},
            {vertices[2],vertices[3]},
            {vertices[3],vertices[0]},
            {vertices[4],vertices[5]},
            {vertices[5],vertices[6]},
            {vertices[6],vertices[7]},
            {vertices[7],vertices[4]},
            {vertices[0],vertices[4]},
            {vertices[1],vertices[5]},
            {vertices[2],vertices[6]},
            {vertices[3],vertices[7]}
        };
        
        return Arrays.asList(edges);
    }
    
}
