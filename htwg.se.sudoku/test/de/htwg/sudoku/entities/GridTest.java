package de.htwg.sudoku.entities;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class GridTest {

	@Before
	public void setUp()  {
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGridArgumentTooSmall() {
		new Grid(0);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testGridArgumentTooLarge() {
		new Grid(4);
	}
	
	@Test
	public void testCellsPerEdge()  {
		assertEquals(1, new Grid(1).getCellsPerEdge());
		assertEquals(4, new Grid(2).getCellsPerEdge());
		assertEquals(9, new Grid(3).getCellsPerEdge());	
	}
	
	@Test
	public void testGridSize()  {
		assertEquals(1, new Grid(1).getGridSize());
		assertEquals(16, new Grid(2).getGridSize());
		assertEquals(81, new Grid(3).getGridSize());
	}
	
	@Test
	public void testGetCell() {
		for (int size = 1; size < 4; size++) {
			Grid grid = new Grid(size);
			assertReachAllCells(grid);
		}
	}
	
	@Test
	public void testRows() {
		Grid grid1 = new Grid(1);
		assertEquals(grid1.getCell(0, 0), grid1.rows[0].getCell(0));
		
		Grid grid2 = new Grid(2);
		assertEquals(grid2.getCell(0, 0), grid2.rows[0].getCell(0));
		
	}
	
	@Test
	public void testBlockAt() {
		Grid grid1 = new Grid(1);
		assertEquals(0, grid1.blockAt(0, 0));
		
		Grid grid2 = new Grid(2);
		assertEquals(0, grid2.blockAt(0, 0));
		assertEquals(0, grid2.blockAt(0, 1));
		assertEquals(1, grid2.blockAt(0, 2));
		assertEquals(1, grid2.blockAt(0, 3));
		assertEquals(0, grid2.blockAt(1, 0));
		assertEquals(0, grid2.blockAt(1, 1));
		assertEquals(1, grid2.blockAt(1, 2));
		assertEquals(1, grid2.blockAt(1, 3));
		assertEquals(2, grid2.blockAt(2, 0));
		assertEquals(2, grid2.blockAt(2, 1));
		assertEquals(3, grid2.blockAt(2, 2));
		assertEquals(3, grid2.blockAt(2, 3));
		assertEquals(2, grid2.blockAt(3, 0));
		assertEquals(2, grid2.blockAt(3, 1));
		assertEquals(3, grid2.blockAt(3, 2));
		assertEquals(3, grid2.blockAt(3, 3));
		
		
	}

	private boolean assertReachAllCells(Grid grid) {
		int cellsPerEdge=grid.getCellsPerEdge();
		for (int row = 0; row < cellsPerEdge; row++) {
			for (int column = 0; column < cellsPerEdge; column++) {
				assertCellIsAt(row,column,grid);
			}
		}
		return true;
	}


	public boolean assertCellIsAt( int row, int column, Grid grid){
		assertEquals(row,grid.getCell(row, column).getRow());
		assertEquals(column,grid.getCell(row, column).getColumn());
		return true;	
	}
	
}
