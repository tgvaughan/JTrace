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

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

/**
 *
 * @author Tim Vaughan <tgvaughan@gmail.com>
 */
public class Camera {
    
    Vector3D location;
    Vector3D direction;
    Vector3D up;
    Vector3D right;
    
    double fovUp, fovRight;
    
    /**
     * Create camera at location directed at pointAt with the top of the
     * frame defined by the up vector and FOV defined by fovUp and fovRight.
     * 
     * @param location
     * @param pointAt
     * @param up
     * @param fovUp
     * @param fovRight 
     */
    public Camera(Vector3D location, Vector3D pointAt, Vector3D up,
            double fovUp, double fovRight) {
        
        this.location = location;
        
        // Ensure up vector is normalized:
        up = up.normalize();
        
        // Obtain direction vector from pointAt location:
        this.direction = pointAt.subtract(location).normalize();
        
        // Use approximate up vector to determine true up and right vectors:
        this.right = direction.crossProduct(up).normalize();
        this.up = this.right.crossProduct(direction);
        
        this.fovUp = fovUp;
        this.fovRight = fovRight;
    }
    
    /**
     * Obtain camera ray to trace through scene.
     * 
     * @param width Width of image in pixels.
     * @param height Height of image in pixels.
     * @param x X-coordinate of pixel to trace.
     * @param y Y-coordinate of pixel to trace.
     * @return 
     */
    public Ray getRay(int width, int height, int x, int y) {

        // Determine angles in each direction.  Note the negative
        // in the expression for the vertical angle - this flips
        // the image in that direction to account for the matrix
        // coordinate scheme used in images.
        double tanThetaUp = -fovUp*(y/(double)height - 0.5);
        double tanThetaRight = fovRight*(x/(double)width - 0.5);
        
        Vector3D raydir = new Vector3D(1.0, direction);
        raydir = raydir.add(tanThetaUp, up);
        raydir = raydir.add(tanThetaRight, right);
        
        return new Ray(location, raydir.normalize());
    }
    
    /**
     * Retrieve coordinates of pixel corresponding to given point in an image
     * with the given width and height.
     * 
     * @param width
     * @param height
     * @param point 3d point to project
     * @return double[] containing x and y coordinates of pixel.
     */
    public int[] getPixel(int width, int height, Vector3D point) {
        int [] coord = new int[2];
        
        Vector3D l = point.subtract(location);
        
        double lu = l.dotProduct(up);
        double lr = l.dotProduct(right);
        double lp = l.dotProduct(direction);
        
        coord[0] = (int)Math.round(((lr/lp)*(1.0/fovRight) + 0.5)*width);
        coord[1] = (int)Math.round(((lu/lp)*(-1.0/fovUp) + 0.5)*height);
        
        return coord;
    }
}
