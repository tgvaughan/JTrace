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
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

/**
 * Adds specular highlights to a texture.
 *
 * @author Tim Vaughan <tgvaughan@gmail.com>
 */
public class SpecularFinish extends Finish {
    
    Colour pigment;
    double diffuse, specular, tightness, ambient;
    
    public SpecularFinish(Colour pigment,
            double diffuse, double specular, double tightness,
            double ambient) {
        this.pigment = pigment;
        this.diffuse = diffuse;
        this.specular = specular;
        this.tightness = tightness;
        this.ambient = ambient;
    }
    
    @Override
    public double getSpecular() {
        return specular;
    }
    
    @Override
    public double getSpecularTightness() {
        return tightness;
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
}
