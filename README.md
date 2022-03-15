# CSc 22100 Assignment 4: Conway's Game of Life

> :warning: **Warning:**  This assignment is still under development. Begin working on it at your own risk.

In this assignment, you'll implement [Conway's Game of Life](https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life) as
a JavaFX application.

## Rules

Conway's Game of Life is a cellular automaton that exists on an infinite two-dimensional grid. Like
the elementary cellular automata of Assignment 2, each cell is either alive or dead. Unlike in assignment 2,
each cell has eight neighbor, not just two. The "evolution" of a cell is governed by the following rules:

1) Any live cell with fewer than two live neighbours dies, as if by underpopulation.
2) Any live cell with two or three live neighbours lives on to the next generation.
3) Any live cell with more than three live neighbours dies, as if by overpopulation.
4) Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.

Remember that the grid is _infinite_, meaning that, even if we don't display a cell's neigbor's they still exist.

## What's in the box?

- The `csc22100.assignment4` package contains some classes to help you implement the Game of Life:
  - `Position` to represent a position in the grid with an (x, y) coordinate
  - `Direction` an enum for the different directions
- `csc22100.assignment4.GameOfLifeApplication` is the entry point for the application and wires all the pieces together
- `csc22100.assignment4.ui.GameOfLifeController` is the "view" of the MVVM architecture (it's called "controller" because JavaFX
was designed with MVC in mind and it would be the "controller" piece of MVC)
- `csc22100.assignment4.ui.GameOfLifeViewModel` is, unsurprisingly the view model.

[!screenshot](./screenshot.png)

## Your Mission

1) Implement `evolve` in the `Life` class. (Recommendation: Do a 5x5 or 10x10 example on paper and then write some unit tests.)
2) Complete `init` in `GameOfLifeController` to bind the various UI elements to functions in the `GameOfLifeViewModel`
3) Implement `receiveNotification` in the `GameOfLifeViewModel`. After an evolution, the view model will receive a notification from
  the `LifeService` containing the new state. You then need to update the view model (and thus the view).
4) Wire the start, stop, step and reset buttons in the view/`GameOfLifeController` to the `GameOfLifeViewModel`. I created a `start`
  method, you'll need to add the others.
5) Wire up the slider so that it controls how long to sleep between evolutions.