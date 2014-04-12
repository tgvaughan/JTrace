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

package jtrace.object.transformation;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

/**
 * Abstract class representing general coordinate transformations.
 *
 * @author Tim Vaughan <tgvaughan@gmail.com>
 */
public abstract class Transformation {
    
    /**
     * Apply reversible coordinate transformation to vector.
     * 
     * @param vec vector to transform
     * @return transformed vector
     */
    public abstract Vector3D apply(Vector3D vec);
    
    /**
     * Apply inverse transformation to vector.
     * 
     * @param vec vector to transform
     * @return transformed vector
     */
    public abstract Vector3D applyInverse(Vector3D vec);
    
}
