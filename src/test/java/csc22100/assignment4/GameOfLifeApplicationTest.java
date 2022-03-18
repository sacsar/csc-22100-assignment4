package csc22100.assignment4;


import csc22100.assignment4.gameoflife.Position;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.Condition;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

@Tag("ui")
@ExtendWith(ApplicationExtension.class)
@ExtendWith(MockitoExtension.class)
class GameOfLifeApplicationTest implements WithAssertions {


    @Start
    private void start(Stage stage) throws IOException {
        GameOfLifeApplication application = new GameOfLifeApplication();
        application.start(stage);
    }

    @Test
    public void testInitialButtonStates(FxRobot robot) {
        // when we first start, we should have the start, reset and sep buttons enabled
        // and the stop button disabled

        assertThat(robot.lookup("#startButton").queryButton())
                .hasFieldOrPropertyWithValue("disabled", false);

        assertThat(robot.lookup("#stopButton").queryButton())
                .hasFieldOrPropertyWithValue("disabled", true);

        assertThat(robot.lookup("#stepButton").queryButton())
                .hasFieldOrPropertyWithValue("disabled", false);

        assertThat(robot.lookup("#resetButton").queryButton())
                .hasFieldOrPropertyWithValue("disabled", false);
    }

    @Test
    public void testStartButtonDisablesButtonsAppropriately(FxRobot robot) {
        robot.clickOn("#startButton");

        assertThat(robot.lookup("#startButton").queryButton().disableProperty()
                .get()).isTrue();
        assertThat(robot.lookup("#stopButton").queryButton().disableProperty().getValue())
                .isFalse();

        assertThat(robot.lookup("#resetButton").queryButton().disableProperty().getValue())
                .isTrue();

        assertThat(robot.lookup("#stepButton").queryButton().disableProperty().getValue()).isTrue();
    }

    @Test
    public void testStartThenStopButtons(FxRobot robot) {
        robot.clickOn("#startButton");

        assertThat(robot.lookup("#startButton").queryButton().disableProperty()
                .get()).isTrue();
        assertThat(robot.lookup("#stopButton").queryButton().disableProperty().getValue())
                .isFalse();

        assertThat(robot.lookup("#resetButton").queryButton().disableProperty().getValue())
                .isTrue();

        assertThat(robot.lookup("#stepButton").queryButton().disableProperty().getValue()).isTrue();

        robot.clickOn("#stopButton");
        assertThat(robot.lookup("#startButton").queryButton())
                .hasFieldOrPropertyWithValue("disabled", false);

        assertThat(robot.lookup("#stopButton").queryButton())
                .hasFieldOrPropertyWithValue("disabled", true);

        assertThat(robot.lookup("#stepButton").queryButton())
                .hasFieldOrPropertyWithValue("disabled", false);

        assertThat(robot.lookup("#resetButton").queryButton())
                .hasFieldOrPropertyWithValue("disabled", false);
    }

    @Test
    public void testStepUpdatesCells(FxRobot robot) {
        GridPane gridPane = robot.lookup("#gridPane").queryAs(GridPane.class);
        Map<Position, Paint> colors = gridPane.getChildren().stream()
                .filter(x -> (x instanceof Rectangle))
                .collect(Collectors.toMap(n -> new Position(GridPane.getRowIndex(n), GridPane.getColumnIndex(n)),
                        n -> ((Rectangle) n).getFill()));

        robot.clickOn("#stepButton");

        GridPane gridPaneAfter = robot.lookup("#gridPane").queryAs(GridPane.class);

        Map<Position, Paint> afterColors = gridPaneAfter.getChildren().stream()
                .filter(x -> (x instanceof Rectangle))
                .collect(Collectors.toMap(n -> new Position(GridPane.getRowIndex(n), GridPane.getColumnIndex(n)),
                        n -> ((Rectangle) n).getFill()));

        Condition<Map.Entry<Position, Paint>> inOriginalCondition = new Condition<>(
                positionPaintEntry -> colors.get(positionPaintEntry.getKey()).equals(positionPaintEntry.getValue()),
                "color unchanged"
        );

        Assertions.assertThat(afterColors).hasEntrySatisfying(not(inOriginalCondition));
    }
}