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
 * Container for texture elements.
 * 
 * @author Tim Vaughan <tgvaughan@gmail.com>
 */
public abstract class Texture {
    
    /**
     * Incorporate colour due to this texture at collision point into colour
     * derived from underlying textures.
     * 
     * @param object Object with which ray has collided
     * @param colour Colour due to underlying texture layers
     * @return New colour at collision point.
     */
    public abstract Colour layerTextureColour(SceneObject object, Colour colour);
    
    
}
