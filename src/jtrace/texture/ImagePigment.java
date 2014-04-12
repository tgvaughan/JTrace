/*
 * Copyright (C) 2014 Tim Vaughan <tgvaughan@gmail.com>
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

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import jtrace.Colour;
import jtrace.object.SceneObject;

/**
 * Pigment which obtains colours from an image.
 *
 * @author Tim Vaughan <tgvaughan@gmail.com>
 */
public class ImagePigment extends Pigment {

    BufferedImage image;
    double scaleU, scaleV;
    
    /**
     * Create a pigment using given image file. The values of scaleU and
     * scaleV determine the extent of the image in the uv coordinate space.
     * The image will be centred at the origin and tiled.
     * 
     * @param file Image file
     * @param scaleU 
     * @param scaleV
     * 
     * @throws IOException Thrown if image cannot be read.
     */
    public ImagePigment(File file,
            double scaleU, double scaleV) throws IOException {
        image = ImageIO.read(file);
        
        this.scaleU = scaleU;
        this.scaleV = scaleV;
    }
    
    /**
     * Create a pigment using given image file.  The value of scale determines
     * the size of the image in the uv coodinate space.  The image will be
     * centred at the origin and tiled.
     * 
     * @param file
     * @param scale
     * @throws IOException 
     */
    public ImagePigment(File file, double scale) throws IOException {
        image = ImageIO.read(file);
        double maxDim = Math.max(image.getWidth(), image.getHeight());
        
        this.scaleU = image.getWidth()/maxDim*scale;
        this.scaleV = image.getWidth()/maxDim*scale;
    }

    @Override
    public Colour getPigment(SceneObject object) {
        int x = (int)(image.getWidth()*object.getU()/scaleU + 0.5*image.getWidth());
        int y = (int)(image.getHeight()*object.getV()/scaleV + 0.5*image.getHeight());
        
        if (x<0)
            x = image.getWidth()-1 - (-x)%image.getWidth();
        else
            x = x%image.getWidth();
        
        if (y<0)
            y = image.getHeight()-1 - (-y)%image.getHeight();
        else
            y = y%image.getHeight();
        
        return new Colour(image.getRGB(x, y));
    }
    
}
