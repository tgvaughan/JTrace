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
package jtrace;

import java.util.List;
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
     * @return pigment colour.
     */
    public abstract Colour getPigment();
    
    /**
     * Get combined colour of diffusely scattered light.
     * 
     * @param scene
     * @param normalRay
     * @return 
     */
    public Colour getDiffuseColour(Scene scene, Ray normalRay) {
        Colour colour = new Colour(0,0,0);
        
        List<LightSource> visibleLights = scene.getVisibleLights(normalRay.origin);
        
        for (LightSource light : visibleLights) {

            // Determine direction of light source
            Vector3D lightDir = light.getLocation()
                    .subtract(normalRay.origin).normalize();
            
            // Projection of light source direction onto surface normal:
            double projection = lightDir.dotProduct(normalRay.direction);
            
            // Scale light colour by projection and add to diffuse colour:
            colour = colour.add(light.getColour().scale(projection));            
        }
        
        return getPigment().filter(colour);
    }
    
    /**
     * Obtain colour to propagate backward along ray.
     * 
     * @return collision colour.
     */
    public Colour getCollisionColour() {
        Scene scene = object.getScene();
        Ray normal = object.getNormalRay();
        Ray collider = object.getCollidingRay();
        
        return getDiffuseColour(scene, normal);
    }
}
