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
package jtrace.scenes;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import jtrace.Camera;
import jtrace.Colour;
import jtrace.LightSource;
import jtrace.Scene;
import jtrace.object.Cube;
import jtrace.object.Plane;
import jtrace.object.Sphere;
import jtrace.object.transformation.Scale;
import jtrace.object.transformation.Translation;
import jtrace.texture.AmbientFinish;
import jtrace.texture.DiffuseFinish;
import jtrace.texture.FlatTexture;
import jtrace.texture.ImagePigment;
import jtrace.texture.SolidPigment;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

/**
 * Cube test scene.
 *
 * @author Tim Vaughan <tgvaughan@gmail.com>
 */
public class OneCube {
    
    public static void main(String[] args) throws IOException {
        
        Camera camera = new Camera(
                new Vector3D(0, -2, 1),
                new Vector3D(0, 0, 0),
                Vector3D.PLUS_K,
                1.0, 1440.0/900.0);

        Scene scene = new Scene();
        scene.setCamera(camera);
        
        scene.addLightSource(new LightSource(new Vector3D(-3,3,3), 4));
        scene.addLightSource(new LightSource(new Vector3D(3,-3,1), 4));
        
        FlatTexture cubeTexture = (new FlatTexture(new SolidPigment(new Colour(0,0,1))));
        cubeTexture.addFinish(new DiffuseFinish(1.0));
        cubeTexture.addFinish(new AmbientFinish(0.1));
        //cubeTexture.addFinish(new MirrorFinish(0.2));
        
        Cube cube = new Cube();
        //cube.addTransformation(new Scale(0.5));
        cube.addTexture(cubeTexture);
        scene.addObject(cube);
        
        FlatTexture floorTexture = new FlatTexture(
                new ImagePigment(new File("wood.jpg"), 1.0));
        floorTexture.addFinish(new DiffuseFinish(1.0));
        
        Plane plane = new Plane();
        plane.addTransformation(new Translation(0, 0, -0.5));
        plane.addTexture(floorTexture);
        scene.addObject(plane);
        
        BufferedImage image = scene.render(1440, 900, 10);
        
        ImageIO.write(image, "PNG", new File("out.png"));
    }
}
