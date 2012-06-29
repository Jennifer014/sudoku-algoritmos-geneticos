// ============================================================================
//   Copyright 2006, 2007 Daniel W. Dyer
//
//   Licensed under the Apache License, Version 2.0 (the "License");
//   you may not use this file except in compliance with the License.
//   You may obtain a copy of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
//   Unless required by applicable law or agreed to in writing, software
//   distributed under the License is distributed on an "AS IS" BASIS,
//   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//   See the License for the specific language governing permissions and
//   limitations under the License.
// ============================================================================
package ejemploDescargadoWatchmakerSudoku;

import SudokuGenetico.watchmaker.Sudoku;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.uncommons.maths.NumberGenerator;
import org.uncommons.watchmaker.framework.operators.AbstractCrossover;

/**
 * Performs cross-over between Sudoku grids by re-combining rows from parents
 * to form new offspring.  Rows are copied intact, only columns are disrupted
 * by this cross-over.
 * @author Daniel Dyer
 */
public class SudokuVerticalCrossover extends AbstractCrossover<Sudoku>
{
    public SudokuVerticalCrossover()
    {
        this(1);
    }


    public SudokuVerticalCrossover(int crossoverPoints)
    {
        super(crossoverPoints);
    }


    public SudokuVerticalCrossover(NumberGenerator<Integer> crossoverPointsVariable)
    {
        super(crossoverPointsVariable);
    }


    protected List<? extends Sudoku> mate(Sudoku parent1,
                                          Sudoku parent2,
                                          int numberOfCrossoverPoints,
                                          Random rng)
    {
        Sudoku.Gen[][] offspring1 = new Sudoku.Gen[Sudoku.SIZE][];
        Sudoku.Gen[][] offspring2 = new Sudoku.Gen[Sudoku.SIZE][];
        for (int i = 0; i < Sudoku.SIZE; i++)
        {
            offspring1[i] = parent1.getRow(i);
            offspring2[i] = parent2.getRow(i);
        }
        
        // Apply as many cross-overs as required.
        Sudoku.Gen[][] temp = new Sudoku.Gen[Sudoku.SIZE][];
        for (int i = 0; i < numberOfCrossoverPoints; i++)
        {
            // Cross-over index is always greater than zero and less than
            // the length of the parent so that we always pick a point that
            // will result in a meaningful cross-over.
            int crossoverIndex = (1 + rng.nextInt(Sudoku.SIZE - 1));
            System.arraycopy(offspring1, 0, temp, 0, crossoverIndex);
            System.arraycopy(offspring2, 0, offspring1, 0, crossoverIndex);
            System.arraycopy(temp, 0, offspring2, 0, crossoverIndex);
        }

        List<Sudoku> result = new ArrayList<Sudoku>(2);
        result.add(new Sudoku(offspring1));
        result.add(new Sudoku(offspring2));
        return result;
    }
}
