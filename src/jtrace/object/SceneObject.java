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

import jtrace.Colour;
import jtrace.Ray;
import jtrace.Scene;
import jtrace.texture.Texture;
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
    
    Vector3D location;
    Texture texture;
    
    Ray collidingRay;
    Ray normalRay;
    
    Scene scene;
    
    public void setScene(Scene scene) {
        this.scene = scene;
    }
    
    public Scene getScene() {
        return scene;
    }
    
    public Vector3D getLocation() {
        return location;
    }
    
    public Ray getCollidingRay() {
        return collidingRay;
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
        return texture.getCollisionColour();
    }
    
}
