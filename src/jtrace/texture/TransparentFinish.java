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
package jtrace.texture;

import jtrace.Colour;
import jtrace.Ray;
import jtrace.object.SceneObject;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

/**
 * Finish to allow refracted light to contribute to texture colour.
 *
 * @author Tim Vaughan <tgvaughan@gmail.com>
 */
public class TransparentFinish extends Finish {
    
    double ior;
    
    /**
     * Create finish to simulate a transparent object with refractive
     * index ior relative to the surrounding space.
     * 
     * @param ior refractive index
     */
    public TransparentFinish(double ior) {
        this.ior = ior;
    }
    
    /**
     * Obtain ray refracted according to Snell's law.
     * 
     * @param incidentRay
     * @param normalRay
     * @return refracted ray
     */
    Ray getRefractedRay(Ray incidentRay, Ray normalRay) {
 
        // Check direction of incident ray (inside to out or outside to in)
        // and adjust ior accordingly:
        double snellFactor, sign;
        if (incidentRay.direction.dotProduct(normalRay.direction)<0.0) {
            snellFactor = ior;
            sign = 1.0;
        } else {
            snellFactor = 1.0/ior;
            sign = -1.0;
        }
        
        Vector3D horiz = normalRay.direction.crossProduct(
                incidentRay.direction.crossProduct(normalRay.direction))
                .scalarMultiply(snellFactor);
        
        double vertMagnitude = sign*Math.sqrt(1.0 - horiz.getNormSq());
        Vector3D vert = normalRay.direction.scalarMultiply(vertMagnitude);
        
        Vector3D refractedDir = vert.add(horiz);
        
        return new Ray(normalRay.getOrigin(), refractedDir);
    }

    @Override
    public Colour layerFinish(SceneObject object, Colour pigmentColour, Colour colour) {

        // Trace refracted ray through scene:
        Ray refractedRay = getRefractedRay(object.getIncidentRay(),
                object.getNormalRayTrans());
        Colour transmittedColour = object.getScene().traceRay(refractedRay);
        
        // Combine refracted colour with existing finish colours:
        return transmittedColour.filter(pigmentColour).add(colour);
    }
    
}
