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

import java.util.List;
import jtrace.Colour;
import jtrace.Ray;
import jtrace.Scene;
import jtrace.texture.Finish;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

/**
 * Abstract class for an object in a scene.  The primary method which needs
 * to be implemented for any real object is the getFirstCollision() method
 * responsible for determining where rays hit the object and the surface
 * normal of the object at those points.
 *
 * @author Tim Vaughan <tgvaughan@gmail.com>
 */
public abstract class SceneObject {
    
    List<Finish> textures;
    
    Ray incidentRay;
    Ray normalRay;
    
    // The small value collision locations are moved out from their surfaces
    // by to prevent artifacts:
    static double epsilon = 1e-5;
    
    // UV coordinates of collision point.
    double u, v;
    
    Scene scene;
    
    public void setScene(Scene scene) {
        this.scene = scene;
    }
    
    public Scene getScene() {
        return scene;
    }
    
    public Ray incidentRay() {
        return incidentRay;
    }
    
    public Ray getNormalRay() {
        return normalRay;
    }
    
    /**
     * Responsible for calculating first collision of ray with object,
     * returning the distance to this point from the origin of the ray.
     * Records the colliding and normal rays for future interrogation.
     * 
     * @param ray incomming ray
     * @return distance from origin of ray to first intersection
     */
    public abstract double getFirstCollision(Ray ray);
    
    public Colour getCollisionColour() {
        Colour colour = new Colour(0,0,0);
        for (Finish texture : textures) {
            colour = texture.getCollisionColour(this, colour);
        }
        return colour;
    }
    
    /**
     * Get u component for texture mapping.
     * 
     * @return u coordinate
     */
    public abstract double getU();
    
    /**
     * Get v component for texture mapping.
     * 
     * @return v coordinate
     */
    public abstract double getV();
    
}
