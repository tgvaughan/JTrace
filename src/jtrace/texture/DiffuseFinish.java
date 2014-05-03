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

import jtrace.Colour;
import jtrace.LightSource;
import jtrace.Ray;
import jtrace.object.SceneObject;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

/**
 * Finish to simulate diffuse illumination of object.
 *
 * @author Tim Vaughan <tgvaughan@gmail.com>
 */
public class DiffuseFinish extends Finish {
    
    double diffuse;
    
    /**
     * Create diffuse finish.
     * 
     * @param diffuseStrength Strength of diffuse illumination. 
     */
    public DiffuseFinish(double diffuseStrength) {
        this.diffuse = diffuseStrength;
    }

    @Override
    public Colour layerFinish(SceneObject object, Colour pigmentColour, Colour colour) {
        
        Colour diffuseColour = new Colour(0,0,0);
        
        Ray normalRay = object.getNormalRay();
        
        for (LightSource light : object.getVisibleLights()) {

            // Determine distance to and direction of light source
            Vector3D lightDir = light.getLocation()
                    .subtract(normalRay.origin);
            double lightDistanceSq = lightDir.getNormSq();
            lightDir = lightDir.normalize();
            
            // Projection of light source direction onto surface normal:
            double projection = lightDir.dotProduct(normalRay.direction);
            
            if (projection>0.0) {
                // Determine degree of illumination:
                double illum = projection*light.getIntensity(lightDistanceSq);
            
                // Scale light colour by illumination and add to diffuse colour:
                diffuseColour = diffuseColour
                        .add(light.getColour().scale(illum));      
            }
        }
        
        return colour.add(pigmentColour.filter(diffuseColour).scale(diffuse));
    }
}
