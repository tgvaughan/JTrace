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

/**
 *
 * @author Tim Vaughan <tgvaughan@gmail.com>
 */
public abstract class Texture {
    
    SceneObject object;
    
    abstract Colour getPigment();
    abstract double getReflect();
    abstract double getRefract();
    abstract double getAmbient();
    
    public void setObject(SceneObject object) {
        this.object = object;
    }
    
    public Colour getDiffuseColour(Scene scene, Ray normalRay) {
        Colour colour = new Colour(0,0,0);
        
        List<LightSource> visibleLights = scene.getVisibleLights(normalRay.origin);
        
        for (LightSource light : visibleLights) {
            
        }
        
        return colour;
    }
    
    /**
     * Obtain colour to propagate backward along ray.
     * 
     * @return collision colour.
     */
    public Colour getCollisionColour() {
        Colour colour = new Colour(0,0,0);
        
        Scene scene = object.getScene();
        Ray collidingRay = object.getCollidingRay();
        Ray normalRay = object.getNormalRay();
        
        return colour;
    };
    
}
