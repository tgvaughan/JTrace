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
import jtrace.object.SceneObject;

/**
 * Finish to simulate ambient illumination of object.
 *
 * @author Tim Vaughan <tgvaughan@gmail.com>
 */
public class AmbientFinish extends Finish {
    
    double ambient;
    
    /**
     * Create ambient finish.
     * 
     * @param ambientStrength Strength of ambient finish. 
     */
    public AmbientFinish(double ambientStrength) {
        this.ambient = ambientStrength;
    }

    @Override
    public Colour layerFinish(SceneObject object, Colour pigmentColour, Colour colour) {
        return colour.add(pigmentColour.scale(ambient));
    }
    
}
