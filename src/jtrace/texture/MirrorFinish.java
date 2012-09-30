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
import jtrace.Scene;
import jtrace.object.SceneObject;

/**
 * Finish to add a truly reflective finish to an object.
 * 
 * @author Tim Vaughan <tgvaughan@gmail.com>
 */
public class MirrorFinish extends Finish {
    
    double mirrorStrength;
    
    /**
     * Create a mirror finish.
     * 
     * @param mirrorStrength 
     */
    public MirrorFinish(double mirrorStrength) {
        this.mirrorStrength = mirrorStrength;
    }

    @Override
    public Colour layerFinish(SceneObject object, Colour pigmentColour, Colour colour) {

        // Trace reflected ray through scene:
        Scene scene = object.getScene();
        Ray reflectedRay = object.getReflectedRay();
        Colour mirroredColour = scene.traceRay(reflectedRay);
        
        // Return mixture of reflected colour and existing finish colours:
        return colour.add(mirroredColour.scale(mirrorStrength));
    }
    
}
