/*
 * Copyright (C) 2012 Vitaljok
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
package phd.mrs.heuristic.ga;

import org.jgap.FitnessEvaluator;
import org.jgap.IChromosome;
import org.jgap.util.ICloneable;

/**
 *
 * @author Vitaljok
 */
public class InverseFitnessEvaluator implements FitnessEvaluator, ICloneable, Comparable {

    @Override
    public boolean isFitter(final double a_fitness_value1,
            final double a_fitness_value2) {
        return a_fitness_value1 < a_fitness_value2;
    }

    @Override
    public boolean isFitter(IChromosome a_chrom1, IChromosome a_chrom2) {
        return isFitter(a_chrom1.getFitnessValue(), a_chrom2.getFitnessValue());
    }

    @Override
    public Object clone() {
        return new InverseFitnessEvaluator();
    }

    @Override
    public int compareTo(Object a_other) {
        if (a_other.getClass().equals(getClass())) {
            return 0;
        } else {
            return getClass().getName().compareTo(a_other.getClass().getName());
        }
    }
}
