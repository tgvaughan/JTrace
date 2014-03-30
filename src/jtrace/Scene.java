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

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import jtrace.object.SceneObject;

/**
 * Describes a scene to trace rays through.
 *
 * @author Tim Vaughan <tgvaughan@gmail.com>
 */
public class Scene {

    Camera camera;
    List<LightSource> lightSources;
    List<SceneObject> sceneObjects;
    Colour backgroundColour;
    
    int recursionDepth, maxRecursionDepth;
    
    /**
     * Constructor.
     */
    public Scene() {
        lightSources = new ArrayList<LightSource>();
        sceneObjects = new ArrayList<SceneObject>();
        backgroundColour = new Colour(0.0, 0.0, 0.0);
    }

    /**
     * Add object to scene.
     *
     * @param object
     */
    public void addObject(SceneObject object) {
        sceneObjects.add(object);
        object.setScene(this);
    }

    /**
     * Add light source to scene.
     *
     * @param lightSource
     */
    public void addLightSource(LightSource lightSource) {
        lightSources.add(lightSource);
    }

    /**
     * Define camera for scene.
     *
     * @param camera
     */
    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    /**
     * Set scene's background colour.
     *
     * @param colour
     */
    public void setBackground(Colour colour) {
        this.backgroundColour = colour;
    }
    
    /**
     * Retrieve light sources in scene.
     * 
     * @return list of light sources.
     */
    public List<LightSource> getLightSources() {
        return lightSources;
    }
    
    /**
     * Retrieve objects in scene.
     * 
     * @return list of scene objects.
     */
    public List<SceneObject> getSceneObjects() {
        return sceneObjects;
    }

    /**
     * Trace a ray through the scene, returning the resulting colour.
     *
     * @param ray
     * @return
     */
    public Colour traceRay(Ray ray) {

        // Check for recursion depth violation:
        if (recursionDepth++ > maxRecursionDepth){
            System.err.println("Warning: max recursion depth exceeded.");
            return backgroundColour;
        }
                    
        // Determine closest intersecting object in scene:
        double nearestObjectDist = Double.POSITIVE_INFINITY;
        SceneObject nearestObject = null;
        for (SceneObject object : sceneObjects) {
            double dist = object.getFirstCollision(ray);
            if (dist < nearestObjectDist) {
                nearestObject = object;
                nearestObjectDist = dist;
            }
        }

        if (nearestObject == null)
            return backgroundColour;
        else 
            return nearestObject.getCollisionColour();
    }

    /**
     * Render scene.
     *
     * @param width Width of resulting image.
     * @param height Height of resulting image.
     * @param maxRecursionDepth
     *
     * @return BufferedImage containing rendering.
     */
    public BufferedImage render(int width, int height, int maxRecursionDepth) {
        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_BGR);

        this.maxRecursionDepth = maxRecursionDepth;
        
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                
                //System.out.format("x:%d y%d\n", x,y);
                
                Ray ray = camera.getRay(width, height, x, y);
                
                // Reset recursion depth:
                recursionDepth = 0;
                
                // Trace ray through scene:
                Colour pixelColour = traceRay(ray);
              
                image.setRGB(x, y, pixelColour.getInt());
            }
        }
        
        return image;
    }

}
