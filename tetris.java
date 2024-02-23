import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;


public class tetris {
    private GameArena arena;
    private TetrisShape currentShape;
    private Rectangle[][] grid;
    private final int ROWS = 20;
    private final int COLUMNS = 10;
    private final int BLOCK_SIZE = 30;
    private boolean[][] fixedBlocks;

    public tetris() {
        arena = new GameArena(COLUMNS * BLOCK_SIZE, ROWS * BLOCK_SIZE);
        fixedBlocks = new boolean[ROWS][COLUMNS];
        grid = new Rectangle[ROWS][COLUMNS];
        createGrid();
        addNewShape();
        arena.addKeyListener((KeyListener) this);
        arena.requestFocus();
        runGame();
    }

    private void createGrid() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                Rectangle block = new Rectangle(j * BLOCK_SIZE, i * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE, "WHITE");
                arena.addRectangle(block);
                grid[i][j] = block;
            }
        }
    }

    private void addNewShape() {
        currentShape = new TetrisShape(getRandomShape(), 4, 0, "BLUE");
        ArrayList<Rectangle> blocks = currentShape.getBlocks();
        for (Rectangle block : blocks) {
            arena.addRectangle(block);
        }
    }


    private void runGame() {
        while (true) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (!moveDown()) {
                fixShape();
                checkRows();
                addNewShape();
            }
        }
    }

    private boolean moveDown() {
        if (currentShape.getY() + BLOCK_SIZE * 4 >= arena.getArenaHeight()) {
            return false;
        }
        if (collision(currentShape.getX(), currentShape.getY() + BLOCK_SIZE)) {
            return false;
        }
        currentShape.moveDown();
        return true;
    }

    private void fixShape() {
        for (int i = 0; i < 4; i++) {
            int row = (currentShape.getY() + i * BLOCK_SIZE) / BLOCK_SIZE;
            int col = currentShape.getX() / BLOCK_SIZE;
            fixedBlocks[row][col] = true;
            grid[row][col].setColour("BLUE");
        }
    }

    private boolean collision(int x, int y) {
        for (int i = 0; i < 4; i++) {
            int row = (y + i * BLOCK_SIZE) / BLOCK_SIZE;
            int col = x / BLOCK_SIZE;
            if (row >= ROWS || col >= COLUMNS || fixedBlocks[row][col]) {
                return true;
            }
        }
        return false;
    }

    private void checkRows() {
        for (int i = ROWS - 1; i >= 0; i--) {
            boolean fullRow = true;
            for (int j = 0; j < COLUMNS; j++) {
                if (!fixedBlocks[i][j]) {
                    fullRow = false;
                    break;
                }
            }
            if (fullRow) {
                clearRow(i);
                moveRowsDown(i);
                i++;
            }
        }
    }

    private void clearRow(int row) {
        for (int i = 0; i < COLUMNS; i++) {
            fixedBlocks[row][i] = false;
            grid[row][i].setColour("WHITE");
        }
    }

    private void moveRowsDown(int clearedRow) {
        for (int i = clearedRow - 1; i >= 0; i--) {
            for (int j = 0; j < COLUMNS; j++) {
                if (fixedBlocks[i][j]) {
                    fixedBlocks[i][j] = false;
                    fixedBlocks[i + 1][j] = true;
                    grid[i][j].setColour("WHITE");
                    grid[i + 1][j].setColour("BLUE");
                }
            }
        }
    }

    private int[][] getRandomShape() {
        int[][][] shapes = {
                {
                        {1, 1, 0},
                        {0, 1, 1}
                },
                {
                        {0, 1, 0},
                        {1, 1, 1}
                },
                {
                        {1, 0, 0},
                        {1, 1, 1}
                },
                {
                        {1, 1},
                        {1, 1}
                }
        };
        return shapes[(int) (Math.random() * shapes.length)];
    }

    public static void main(String[] args) {
        new tetris();
    }
}
