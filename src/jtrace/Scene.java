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

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import jtrace.object.SceneObject;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

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
    
    boolean debugThisRay;
    double debugFrac;
    
    PathNode pathTreeRoot;
    
    public class PathNode {
            Ray ray;
            PathNode parent;
            List<PathNode> children;

            public PathNode(PathNode parent, Ray ray) {
                this.parent = parent;
                this.ray = ray;
                this.children = new ArrayList<>();
            }
    
            public void addChild(PathNode child) {
                children.add(child);
            }
            
            PathNode getNode(Ray otherRay) {
                if (ray.equals(otherRay))
                    return this;
                else {
                    for (PathNode child : children) {
                        PathNode res = child.getNode(otherRay);
                        if (res != null)
                            return res;
                    }
                }
                
                return null;
            }
    }
    
    /**
     * Constructor.
     */
    public Scene() {
        lightSources = new ArrayList<>();
        sceneObjects = new ArrayList<>();
        backgroundColour = new Colour(0.0, 0.0, 0.0);
        
        debugThisRay = false;
        debugFrac = -1;
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
     * Enable/disable debug rays.
     * 
     * @param frac fraction of rays to debug
     */
    public void enableDebug(double frac) {
        this.debugFrac = frac;
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
        else {
            return nearestObject.getCollisionColour();
        }
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
        
        Random random = new Random();
        
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                
                //System.out.format("x:%d y%d\n", x,y);
                
                Ray ray = camera.getRay(width, height, x, y);
                
                // Reset recursion depth:
                recursionDepth = 0;
                
                debugThisRay = random.nextDouble()<debugFrac;
                
                // Trace ray through scene:
                Colour pixelColour = traceRay(ray);
              
                image.setRGB(x, y, pixelColour.getInt());
                
            }
        }
        
        return image;
    }
    
    /**
     * Render scene overlayed with wire frame representation of objects.
     * 
     * @param width
     * @param height
     * @param maxRecursionDepth
     * @return 
     */
    public BufferedImage renderWireFrame(int width, int height, int maxRecursionDepth) {
        BufferedImage image = render(width, height, maxRecursionDepth);
        Graphics gr = image.getGraphics(); 
        gr.setColor(java.awt.Color.cyan);
        
        for (SceneObject object : sceneObjects) {
            for (Vector3D[] edge : object.getWireFrame()) {
                int[] coord1 = camera.getPixel(width, height, edge[0]);
                int[] coord2 = camera.getPixel(width, height, edge[1]);
                
                gr.drawLine(coord1[0], coord1[1], coord2[0], coord2[1]);
            }
        }
        
        return image;
    }

}
