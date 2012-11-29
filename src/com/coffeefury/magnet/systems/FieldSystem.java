package com.coffeefury.magnet.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.coffeefury.magnet.components.Tile;
import com.coffeefury.magnet.components.Unit;
import com.coffeefury.magnet.screens.AbstractScreen;
import com.coffeefury.magnet.utils.Constants;

public class FieldSystem extends Group implements System{
	
	private static final String MOVE_TILE = "blue_l,l", ATTACK_TILE = "red_l,l";
	
	public enum State{
		IDLE, MOVE, ATTACK;
	}

	AbstractScreen screen;

	public FieldSystem(AbstractScreen screen) {
		super("fieldsystem");
		this.screen = screen;
		
		area = new Group("area");
		this.addActor(area);
	}

	private boolean[][] areaMarker;
	private Group area;
	
	public State state = State.IDLE;

	public void selectArea(Unit unit, String tileName, int radius) {
		// TODO Auto-generated method stub
		float x = unit.x;
		float y = unit.y;
		areaMarker = new boolean[2 * radius + 1][2 * radius + 1];
		areaMarker[radius][radius] = true;

		addOpaqueArea(area, tileName, x, y, radius, x, y, radius);
	}
	
	public void selectMoveArea(Unit unit){
		clearArea();
		state = State.MOVE;
		selectArea(unit, MOVE_TILE, Constants.MOVE_RANGE[unit.type.ordinal()]);
	}
	
	public void selectAttackArea(Unit unit){
		clearArea();
		state = State.ATTACK;
		selectArea(unit, ATTACK_TILE, Constants.ATTACK_RANGE[unit.type.ordinal()]);
	}

	private void addOpaqueArea(Group area, String tileName, float oriX, float oriY, int oriRadius, float x, float y, int radius) {
		int ix = oriRadius + (int) ((x - oriX) / Constants.SIZE);
		int iy = oriRadius + (int) ((y - oriY) / Constants.SIZE);
		
		if (!areaMarker[ix][iy]) {
			// TODO Auto-generated method stub
			Tile tile = new Tile(screen.getTextureRegion(tileName), x, y);
			area.addActor(tile);
			areaMarker[ix][iy] = true;
//			Gdx.app.log(null, "added "+x+" "+y);
		}

		if (radius > 0) {
			if (x - Constants.SIZE >= 0)
				addOpaqueArea(area, tileName, oriX, oriY, oriRadius, x - Constants.SIZE, y, radius - 1);
			if (x + Constants.SIZE < Gdx.graphics.getWidth())
				addOpaqueArea(area, tileName, oriX, oriY, oriRadius, x + Constants.SIZE, y, radius - 1);
			if (y - Constants.SIZE >= 0)
				addOpaqueArea(area, tileName, oriX, oriY, oriRadius, x, y - Constants.SIZE, radius - 1);
			if (y + Constants.SIZE < Gdx.graphics.getHeight())
				addOpaqueArea(area, tileName, oriX, oriY, oriRadius, x, y + Constants.SIZE, radius - 1);
		}
	}
	
	public boolean inMoveRadius(Unit unit, float x, float y){
		int dx = (int) (unit.x / Constants.SIZE);
		dx -= (int) (x / Constants.SIZE);
		int dy = (int) (unit.y / Constants.SIZE);
		dy -= (int) (Gdx.graphics.getHeight() - y) / Constants.SIZE;
		Gdx.app.log("move", dx+" "+dy);
		return (Math.abs(dx) + Math.abs(dy)) <= Constants.MOVE_RANGE[unit.type.ordinal()];
	}
	
	public boolean inAttackRadius(Unit unit, Unit target) {
		if (target == null) return false;
		int dx = (int) ((unit.x - target.x) / Constants.SIZE);
		int dy = (int) ((unit.y - target.y) / Constants.SIZE);
		return (Math.abs(dx) + Math.abs(dy)) <= Constants.ATTACK_RANGE[unit.type.ordinal()];
	}
	
	public boolean inAttackRadius(Unit unit, int x, int y) {
		// TODO Auto-generated method stub
		int targetX = x - (x % Constants.SIZE);
		int targetY = Gdx.graphics.getHeight() - (y - (y % Constants.SIZE));
		int dx = (int) ((unit.x - targetX) / Constants.SIZE);
		int dy = (int) ((unit.y - targetY) / Constants.SIZE);
		return (Math.abs(dx) + Math.abs(dy)) <= Constants.ATTACK_RANGE[unit.type.ordinal()];
	}
	
//	public void moveUnitTo(Unit unit, float x, float y){
//		unit.move(x, y);
//	}

	public void clearArea() {
		area.clear();
		areaMarker = null;
		state = State.IDLE;
	}
	
	public boolean isMoveState(){
		return state == State.MOVE;
	}
	
	public boolean isAttackState(){
		return state == State.ATTACK;
	}
	
	public static float normalizeXPos(float x) {
		if (x < 0)
			return 0;
		else if (x > Gdx.graphics.getWidth() - Constants.SIZE)
			return Gdx.graphics.getWidth() - Constants.SIZE;
		return x;
	}
	
	public static float normalizeYPos(float y){
		if (y < 0)
			return 0;
		else if (y > Gdx.graphics.getHeight() - Constants.SIZE)
			return Gdx.graphics.getHeight() - Constants.SIZE;
		return y;
	}
}
