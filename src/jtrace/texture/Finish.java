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
 * Abstract class encompassing finishes to apply to objects.
 *
 * @author Tim Vaughan <tgvaughan@gmail.com>
 */
public abstract class Finish {
    
    /**
     * Incorporate colour due to finish into the texture colour, given
     * the provided pigment colour at the most recent collision point.
     * 
     * @param object Object on which texture is to be applied.
     * @param pigmentColour Colour of pigment
     * @param colour Colour resulting from previous finish applications
     * @return New colour.
     */
    public abstract Colour layerFinish(SceneObject object,
            Colour pigmentColour, Colour colour);

}
