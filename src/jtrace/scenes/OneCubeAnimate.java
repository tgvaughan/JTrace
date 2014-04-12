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
import jtrace.texture.AmbientFinish;
import jtrace.texture.DiffuseFinish;
import jtrace.texture.FlatTexture;
import jtrace.texture.ImagePigment;
import jtrace.texture.MirrorFinish;
import jtrace.texture.SolidPigment;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

/**
 * Cube test scene.
 *
 * @author Tim Vaughan <tgvaughan@gmail.com>
 */
public class OneCubeAnimate {
    
    public static void main(String[] args) throws IOException {
        


        Scene scene = new Scene();

        
        scene.addLightSource(new LightSource(new Vector3D(-3,3,-3), 4));
        scene.addLightSource(new LightSource(new Vector3D(6,6,-3), 4));
        
        FlatTexture cubeTexture = (new FlatTexture(new SolidPigment(new Colour(0,0,1))));
        cubeTexture.addFinish(new DiffuseFinish(1.0));
        cubeTexture.addFinish(new AmbientFinish(0.1));
        cubeTexture.addFinish(new MirrorFinish(0.2));
        
        Cube cube = new Cube(new Vector3D(0,0,0), 0.5);
        cube.addTexture(cubeTexture);
        scene.addObject(cube);
        
//        FlatTexture floorTexture = new FlatTexture(new CheckeredPigment(
//                new Colour(.5,.5,.5), new Colour(1,1,1), 1));
        FlatTexture floorTexture = new FlatTexture(
                new ImagePigment(new File("wood.jpg"), 1.0));
        floorTexture.addFinish(new DiffuseFinish(1.0));
        
        Plane plane = new Plane(new Vector3D(0,-0.25,0),
                Vector3D.PLUS_J, Vector3D.PLUS_K);
        plane.addTexture(floorTexture);
        scene.addObject(plane);

        Vector3D pointAt = new Vector3D(0,0,0);

        int steps = 100;
        for (int i=0; i<steps; i++) {
            double R = 2.0;
            double x = R*Math.sin(i*2*Math.PI/steps);
            double z = -R*Math.cos(i*2*Math.PI/steps);
            Camera camera = new Camera(
                    new Vector3D(x, 1, z),
                    pointAt,
                    Vector3D.PLUS_J,
                    1.0, 800.0/600.0);
            scene.setCamera(camera);
            BufferedImage image = scene.render(800, 600, 10);
            ImageIO.write(image, "PNG", new File(String.format("out_%02d.png", i)));
        }
    }
}
