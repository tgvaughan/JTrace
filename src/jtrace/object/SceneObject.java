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
import jtrace.Colour;
import jtrace.LightSource;
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
    
    /**
     * Scene that object resides in
     */
    private Scene scene;
    
    /**
     * Textures to apply to object.
     */
    private List<Texture> textures;
    
    /**
     * Details of last collision.
     */
    protected Ray incidentRay;
    protected Ray normalRay, normalRayRef, normalRayTrans;
    protected Ray reflectedRay;
    protected boolean internal;
    
    /**
     * The small value collision locations are moved out from their surfaces
     * by to prevent artifacts.
     */
    static double EPSILON = 1e-5;
    
    /**
     * UV coordinates of collision point.
     */
    protected double u, v;
    
    /**
     * Light sources visible from last collision point.
     */
    private List<LightSource> visibleLights;
    
    public void setScene(Scene scene) {
        this.scene = scene;
    }
    
    public Scene getScene() {
        return scene;
    }
    
    public void addTexture(Texture texture) {
        if (textures == null)
            textures = new ArrayList<>();
        
        textures.add(texture);
    }
    
    public Ray getIncidentRay() {
        return incidentRay;
    }
    
    public Ray getNormalRay() {
        return normalRay;
    }
    
    public boolean isInternal() {
        return internal;
    }
    
    /**
     * Retrieve ray normal to surface with the origin shifted by epsilon
     * so that the surface itself cannot be hit by a REFLECTED ray starting
     * at this location.
     * 
     * @return normal ray
     */
    public Ray getNormalRayRef() {
        if (normalRayRef == null) {
            normalRayRef = new Ray();
            normalRayRef.direction = normalRay.direction;
            if (internal)
                normalRayRef.origin = normalRay.origin.subtract(EPSILON, normalRay.direction);
            else
                normalRayRef.origin = normalRay.origin.add(EPSILON, normalRay.direction);
        }
        
        return normalRayRef;
    }
    
    /**
     * Retrieve ray normal to surface with the origin shifted by epsilon
     * so that the surface itself cannot be hit by a TRANSMITTED ray starting
     * at this location.
     * 
     * @return normal ray
     */
    public Ray getNormalRayTrans() {
        if (normalRayTrans == null) {
            normalRayTrans = new Ray();
            normalRayTrans.direction = normalRay.direction;
            if (internal)
                normalRayTrans.origin = normalRay.origin.add(EPSILON, normalRay.direction);
            else
                normalRayTrans.origin = normalRay.origin.subtract(EPSILON, normalRay.direction);
        }
        
        return normalRayTrans;
    }
    
    /**
     * Retrieve ray reflected from point of last collision.
     * 
     * @return reflected ray
     */
    public Ray getReflectedRay() {
        
        // Return existing value if it's already been calculated:
        if (reflectedRay != null)
            return reflectedRay;
        
        Vector3D reflectedOrigin = getNormalRayRef().origin;
        Vector3D reflectedDir = incidentRay.direction
                .add(-2.0*incidentRay.direction.dotProduct(normalRay.direction),
                        normalRay.direction);
        
        reflectedRay = new Ray(reflectedOrigin, reflectedDir);
        
        return reflectedRay;
    }
    
    /**
     * Responsible for calculating first collision of ray with object,
     * returning the distance to this point from the origin of the ray.
     * Records the colliding and normal rays for future interrogation.
     * 
     * @param ray incoming ray
     * @return distance from origin of ray to first intersection
     */
    public abstract double getFirstCollision(Ray ray);
    
    /**
     * Mix values obtained from the layered textures to obtain the colour at
     * the point of collision.
     * 
     * @return colour at collision point
     */
    public Colour getCollisionColour() {
        
        // Clear calculated collision information (allows for caching):
        visibleLights = null;
        reflectedRay = null;
        normalRayRef = null;
        normalRayTrans = null;
        
        internal = incidentRay.direction.dotProduct(normalRay.direction)>0;
        
        Colour colour = new Colour(0,0,0);
        for (Texture texture : textures) {
            colour = texture.layerTextureColour(this, colour);
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
    
    /**
     * Retrieve list of light source visible from location of last
     * collision.
     * 
     * @return List of visible light sources.
     */
    public List<LightSource> getVisibleLights() {
        
        // Return existing list if it's already been calculated:
        if (visibleLights != null)
            return visibleLights;
        
        visibleLights = new ArrayList<LightSource>();
        Vector3D location = getNormalRayRef().origin;
        
        for (LightSource light : scene.getLightSources()) {
            
            Vector3D dirToLight = light.getLocation().subtract(location).normalize();
            Ray rayToLight = new Ray(location, dirToLight);
            
            boolean occluded = false;
            
            for (SceneObject object : scene.getSceneObjects()) {
                if (object.getFirstCollision(rayToLight)<Double.POSITIVE_INFINITY) {
                    occluded = true;
                    break;
                }
            }
            
            if (!occluded)
                visibleLights.add(light);
        }
        
        return visibleLights;
    }
    
    /**
     * Obtain a list of length 2 arrays of vectors representing edges in a
     * wireframe representation of this object.
     * 
     * @return wireframe edges
     */
    public abstract List<Vector3D[]> getWireFrame();
}
