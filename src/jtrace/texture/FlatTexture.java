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

import java.util.ArrayList;
import java.util.List;
import jtrace.Colour;
import jtrace.object.SceneObject;

/**
 * A texture with a single pigment and set of finishes.
 *
 * @author Tim Vaughan <tgvaughan@gmail.com>
 */
public class FlatTexture extends Texture {
    
    Pigment pigment;
    List<Finish> finishes;
    
    /**
     * Create a flat texture with a single pigment.
     * 
     * @param pigment 
     */
    public FlatTexture(Pigment pigment) {
        this.pigment = pigment;
        finishes = new ArrayList<>();
    }
    
    /**
     * Add finish to texture.
     * 
     * @param finish
     * @return this - allows chaining of addFinish() calls.
     */
    public FlatTexture addFinish(Finish finish) {
        finishes.add(finish);
        return this;
    }

    @Override
    public Colour layerTextureColour(SceneObject object, Colour colour) {
        Colour pigmentColour = pigment.getPigment(object);
        for (Finish finish : finishes)
            colour = finish.layerFinish(object, pigmentColour, colour);
        
        return colour;
    }
    
}
