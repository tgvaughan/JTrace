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

import jtrace.object.SceneObject;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import jtrace.object.Sphere;
import jtrace.texture.DiffuseTexture;
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

        // Determine closest object in scene:
        double nearestObjectDist = Double.POSITIVE_INFINITY;
        SceneObject nearestObject = null;
        for (SceneObject object : sceneObjects) {
            double dist = object.getFirstCollision(ray);
            if (dist < nearestObjectDist)
                nearestObjectDist = dist;
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
     *
     * @return BufferedImage containing rendering.
     */
    public BufferedImage render(int width, int height) {
        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_BGR);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Ray ray = camera.getRay(width, height, x, y);
                Colour pixelColour = traceRay(ray);

                image.setRGB(x, y, pixelColour.getInt());
            }
        }            

        return image;
    }
    
    /**
     * Retrieve list of light source visible from location of last
     * collision.
     * 
     * @return List of visible light sources.
     */
    public List<LightSource> getVisibleLights(Vector3D location) {
        
        List<LightSource> visibleLights = new ArrayList<LightSource>();
        
        for (LightSource light : getLightSources()) {
            
            Vector3D dirToLight = light.getLocation().subtract(location).normalize();
            Ray rayToLight = new Ray(location, dirToLight);
            
            boolean occluded = false;
            
            for (SceneObject object : getSceneObjects()) {
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
     * Main method for debugging.
     *
     * @param args
     */
    public static void main(String[] args) throws IOException {
        Camera camera = new Camera(
                new Vector3D(0, 0, 1),
                new Vector3D(0, 0, 0),
                Vector3D.PLUS_J,
                1.0, 1.0);

        Scene scene = new Scene();
        scene.setCamera(camera);
        
        LightSource light = new LightSource(new Vector3D(1,1,1));
        scene.addLightSource(light);
        
        Colour green = new Colour(0,1,0);
        DiffuseTexture greenPigment = new DiffuseTexture(green);
        Sphere sphere = new Sphere(new Vector3D(0,0,0), 0.5, greenPigment);
        scene.addObject(sphere);

        BufferedImage image = scene.render(640, 480);

        ImageIO.write(image, "PNG", new File("out.png"));
    }
}