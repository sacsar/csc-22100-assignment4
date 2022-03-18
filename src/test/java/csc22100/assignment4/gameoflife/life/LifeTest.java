package csc22100.assignment4.gameoflife.life;

import csc22100.assignment4.gameoflife.Position;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;

import java.util.Map;


class LifeTest implements WithAssertions {

    // make sure that we evolve a "boundary" cell
    @Test
    public void testBoundaryEvolution() {

        Boolean[][] startingStates = {{true}, {true}, {true}};

        Life life = Life.fromArray(startingStates);

        Life nextGeneration = life.evolve();
        assertThat(nextGeneration.getCellStates()).containsEntry(new Position(1, -1), true);
    }

    @Test
    public void testOneGeneration() {
        Boolean[][] initialStates = {{true, false, false},
                {false, true, true},
                {false, false, false}};

        Boolean[][] finalState = {{false, false, false, false, false},
                {false, false, true, false, false},
                {false, false, true, false, false},
                {false, false, false, false, false},
                {false, false, false, false, false}};
        Map<Position, Boolean> expectedStates = Life.fromArray(finalState, -1,-1).getCellStates();

        Life life = Life.fromArray(initialStates);
        Life result = life.evolve();

        assertThat(result.getCellStates()).containsExactlyEntriesOf(expectedStates);
    }
}