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
import jtrace.object.Plane;
import jtrace.object.Sphere;
import jtrace.texture.AmbientFinish;
import jtrace.texture.CheckeredPigment;
import jtrace.texture.DiffuseFinish;
import jtrace.texture.FlatTexture;
import jtrace.texture.MirrorFinish;
import jtrace.texture.SolidPigment;
import jtrace.texture.SpecularFinish;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

/**
 * Test for the mirrored finish.
 *
 * @author Tim Vaughan <tgvaughan@gmail.com>
 */
public class MirroredSphere {

    public static void main(String[] args) throws IOException {

        Camera camera = new Camera(
                new Vector3D(-1, 1, 3),
                new Vector3D(0, 0, 0),
                Vector3D.PLUS_J,
                1.0, 1.6);

        Scene scene = new Scene();
        scene.setCamera(camera);

        scene.addLightSource(new LightSource(new Vector3D(-3, 3, 3), 4));

        FlatTexture sphereTexture =
                (new FlatTexture(new SolidPigment(new Colour(1,0,1))))
                .addFinish(new AmbientFinish(0.1))
                .addFinish(new MirrorFinish(0.2))
                .addFinish(new DiffuseFinish(0.8))
                .addFinish(new SpecularFinish(1.0, 100));
        
        Sphere sphere = new Sphere(new Vector3D(0,0,0), 0.4);
        sphere.addTexture(sphereTexture);
        scene.addObject(sphere);

        FlatTexture floorTexture =
                (new FlatTexture(new CheckeredPigment(new Colour(.5,.5,.5), new Colour(1,1,1), 1)))
                .addFinish(new DiffuseFinish(1.0))
                .addFinish(new AmbientFinish(0.05));
        
        Plane plane = new Plane(new Vector3D(0,-0.4,0),
                Vector3D.PLUS_J, Vector3D.PLUS_K);
        plane.addTexture(floorTexture);
        scene.addObject(plane);
        
        BufferedImage image = scene.render(1440, 900, 10);
        ImageIO.write(image, "PNG", new File("out.png"));
    }
}
