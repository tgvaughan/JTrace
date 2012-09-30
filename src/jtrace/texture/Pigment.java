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
 * Abstract pigment class.
 *
 * @author Tim Vaughan <tgvaughan@gmail.com>
 */
public abstract class Pigment {
    
    /**
     * Obtain pigment colour on scene object at point of most recent collision.
     * 
     * @param object Scene object
     * @return pigment colour
     */
    public abstract Colour getPigment(SceneObject object);
    
}
