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
package jtrace.texture;

import java.util.List;
import jtrace.Colour;
import jtrace.LightSource;
import jtrace.Ray;
import jtrace.Scene;
import jtrace.object.SceneObject;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

/**
 * Abstract texture class.
 *
 * @author Tim Vaughan <tgvaughan@gmail.com>
 */
public abstract class Texture {
    
    SceneObject object;
    
    public void setObject(SceneObject object) {
        this.object = object;
    }
    
    /**
     * Obtain pigment at last ray collision location on object.
     * 
     * @return pigment colour
     */
    public abstract Colour getPigment();
    

    /**
     * Obtain relative intensity of diffuse reflection.
     * @return 
     */
    public abstract double getDiffuse();
    
    /**
     * Obtain relative intensity of specular reflection.
     * 
     * @return specular reflection intensity
     */
    public abstract double getSpecular();
    
    /**
     * Retrieve tightness of specular highlights. Higher numbers result
     * in smaller highlights.
     * 
     * @return tightness
     */
    public abstract double getSpecularTightness();
    
    
    /**
     * Obtain relative intensity of ambient light.
     * 
     * @return ambient light strength
     */
    public double getAmbient() {
        return 0.0;
    }
    
    /**
     * Get combined colour of diffusely scattered light.
     * 
     * @param scene
     * @param normalRay
     * @return 
     */
    public Colour getDiffuseColour(Scene scene,
            List<LightSource> visibleLights,
            Ray normalRay) {
        Colour colour = new Colour(0,0,0);
        

        
        for (LightSource light : visibleLights) {

            // Determine distance to and direction of light source
            Vector3D lightDir = light.getLocation()
                    .subtract(normalRay.origin);
            double lightDistanceSq = lightDir.getNormSq();
            lightDir = lightDir.normalize();
            
            // Projection of light source direction onto surface normal:
            double projection = lightDir.dotProduct(normalRay.direction);
            
            if (projection>0.0) {
                // Determine intensity of illumination:
                double intensity = projection*light.getScaleSq()/lightDistanceSq;
            
                // Scale light colour by intensity and add to diffuse colour:
                colour = colour.add(light.getColour().scale(intensity));      
            }
        }
        
        return getPigment().filter(colour);
    }

    /**
     * Get colour resulting from specular reflection.
     * 
     * @param scene
     * @param normalRay
     * @param incidentRay
     * @return 
     */
    public Colour getSpecularColour(Scene scene,
            List<LightSource> visibleLights,
            Ray normalRay, Ray incidentRay) {
        Colour colour = new Colour(0,0,0);
        
        // Calculate direction of reflected ray:
        Vector3D reflected = incidentRay.direction
                .add(-2.0*incidentRay.direction
                .dotProduct(normalRay.direction), normalRay.direction);
        
        for (LightSource light : visibleLights) {
            
            // Determine distance and direction to light:
            Vector3D lightDir = light.getLocation().subtract(normalRay.origin);
            double lightDistanceSq = lightDir.getNormSq();
            lightDir = lightDir.normalize();
            
            // Projection of light direction onto reflected ray:
            double projection = lightDir.dotProduct(reflected);
            
            if (projection>0) {
                // Intensity of specular highlighting:
                double intensity = Math.pow(projection,getSpecularTightness())
                        *light.getScaleSq()/lightDistanceSq;
                
                // Scale light colour by intensity and add to specular colour:
                colour = colour.add(light.getColour().scale(intensity));
            }
        }
        
        return colour;
    }
    
    /**
     * Get colour resulting from faux-ambient light.
     * 
     * @return colour
     */
    public Colour getAmbientColour() {
        return getPigment().scale(getAmbient());
    }
    
    /**
     * Obtain colour to propagate backward along ray.
     * 
     * @return collision colour.
     */
    public Colour getCollisionColour() {
        Scene scene = object.getScene();
        Ray normalRay = object.getNormalRay();
        Ray incidentRay = object.incidentRay();
        
        List<LightSource> visibleLights = scene.getVisibleLights(normalRay.origin);
        Colour colour = new Colour(0,0,0);
        
        if (getDiffuse()>0.0)
            colour = colour.add(getDiffuseColour(scene,
                    visibleLights, normalRay).scale(getDiffuse()));
        
        if (getSpecular()>0.0)
            colour = colour.add(getSpecularColour(scene,
                    visibleLights, normalRay, incidentRay).scale(getSpecular()));
        
        if (getAmbient()>0.0)
            colour = colour.add(getAmbientColour().scale(getAmbient()));
        
        return colour;
    }
}
