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

/**
 * Flat diffuse texture.
 *
 * @author Tim Vaughan <tgvaughan@gmail.com>
 */
public class DiffuseTexture extends Texture {
    
    Colour pigment;
    double ambient;
    
    public DiffuseTexture(Colour pigment, double ambient) {
        this.pigment = pigment;
        this.ambient = ambient;
    }
    
    @Override
    public double getDiffuse() {
        return 1.0;
    }

    @Override
    public Colour getPigment() {
        return pigment;
    }
    
    @Override
    public double getAmbient() {
        return ambient;
    }

    @Override
    public double getSpecular() {
        return 0.0;
    }

    @Override
    public double getSpecularTightness() {
        return 10.0;
    }
    
}
