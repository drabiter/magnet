package com.coffeefury.magnet.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.OnActionCompleted;
import com.badlogic.gdx.scenes.scene2d.actions.FadeOut;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.coffeefury.magnet.Magnet;
import com.coffeefury.magnet.components.Unit;
import com.coffeefury.magnet.systems.FieldSystem;
import com.coffeefury.magnet.systems.GameInputSystem;
import com.coffeefury.magnet.systems.HUDSystem;
import com.coffeefury.magnet.systems.MenuSystem;
import com.coffeefury.magnet.systems.UnitSystem;
import com.coffeefury.magnet.systems.UnitSystem.Type;
import com.coffeefury.magnet.utils.Constants;
import com.coffeefury.magnet.utils.UtilsBase;
import com.coffeefury.magnet.utils.UtilsBase.Facing;

public class GameScreen extends AbstractScreen {
	
	FieldSystem fieldSystem;
	HUDSystem hudSystem;
	MenuSystem menuSystem;
	UnitSystem unitSystem;

	public GameScreen(Magnet game) {
		super(game);
		// TODO Auto-generated constructor stub
		inputSystem = new GameInputSystem(this);
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		super.show();
		Gdx.input.setInputProcessor(inputSystem);
		
		fieldSystem = new FieldSystem(this);
		hudSystem = new HUDSystem(this);
		menuSystem = new MenuSystem(this);
		unitSystem = new UnitSystem(this);
		
		stage.addActor(new Image(getTextureRegion("background_l,l")));
		
		stage.addActor(fieldSystem);
		stage.addActor(unitSystem);
		stage.addActor(menuSystem);
		stage.addActor(hudSystem);
		
		unitSystem.create(UtilsBase.loadLevel(Constants.level));
		
		fadeIn(1f);
	}
	
	Unit lastUnit;
	
	private void releaseUnit(){
		if (lastUnit != null){
			lastUnit.release();
			lastUnit = null;
		}
		fieldSystem.clearArea();
	}
	
	private void selectUnit(Unit unit){
		lastUnit = unit;
		lastUnit.flash();
		fieldSystem.selectMoveArea(lastUnit);
		menuSystem.clear();
	}
	
	public void touchUp(int x, int y){
		
		if (menuSystem.poolMenu(x, y)){
			menuSystem.clear();
			return;
		}
		
		Unit unit = unitSystem.findByLocation(x, y, true, true);
		
		if (unit != null){
			if (lastUnit != null){
				if (unit.name.equals(lastUnit.name)){
					//open unit's menu
					menuSystem.openUnitMenu(lastUnit);
					fieldSystem.clearArea();
				}else if (!menuSystem.isOpenMenu() && !fieldSystem.isAttackState()){
					//change unit focus
					if (!unit.played && unit.playable) {
						releaseUnit();
						selectUnit(unit);
					}
				}else if (fieldSystem.isAttackState()){
					fieldSystem.clearArea();
					if (fieldSystem.inAttackRadius(lastUnit, unit)){
						Facing facing = UtilsBase.getFacing(lastUnit, unit);
						lastUnit.attack(facing);
						unit.damagedBy(lastUnit, facing);
						unitSystem.adjustUnitPosition(lastUnit, unit, facing);
						unitSystem.isPlayableUnitsDone();
						fieldSystem.clearArea();
						releaseUnit();
					}else{
						menuSystem.openUnitMenu(lastUnit);
					}
				}
			}else{
				if (!unit.played){
					selectUnit(unit);
				}
			}
		}else{
			if (lastUnit != null){
				if (fieldSystem.isMoveState()){
					fieldSystem.clearArea();
					if (fieldSystem.inMoveRadius(lastUnit, x, y)) {
						float mapX = x - (x % Constants.SIZE);
						float mapY = (Gdx.graphics.getHeight() - y) - ((Gdx.graphics.getHeight() - y) % Constants.SIZE);
						lastUnit.move(mapX, mapY);
						unitSystem.updateUnitPosition(lastUnit.name, mapX, mapY);
					}else{
						releaseUnit();
					}
				}else if (lastUnit.type == Type.CLONER && fieldSystem.isAttackState() && UnitSystem.getCloneQueue() != null){
					fieldSystem.clearArea();
					if (fieldSystem.inAttackRadius(lastUnit, x, y)){
						unitSystem.createUnit(UnitSystem.getCloneQueue(), x, Gdx.graphics.getHeight() - y);
						UnitSystem.setCloneQueue(null);
						lastUnit.played();
						releaseUnit();
					}else{
						menuSystem.openUnitMenu(lastUnit);
					}
				}else{
					if (!lastUnit.played) {
						lastUnit.undoMove();
						unitSystem.updateUnitPosition(lastUnit);
						menuSystem.clear();
						fieldSystem.selectMoveArea(lastUnit);
					}else{
						releaseUnit();
					}
				}
			}else{
				if (menuSystem.isOpenMenu()){
					menuSystem.clear();
				}else{
					menuSystem.openMenu(x, y);
				}
			}
		}
		
		if (unitSystem.checkWin()){
			inputSystem.enabled = false;
			fadeOut(2f, false);
		}
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		super.dispose();
		
		fieldSystem = null;
		hudSystem = null;
		menuSystem = null;
		unitSystem = null;
	}

	@Override
	protected void fadedIn() {
		// TODO Auto-generated method stub
		inputSystem.enabled = true;
	}

	@Override
	protected void fadedOut() {
		// TODO Auto-generated method stub
		Constants.level++;
		if (Constants.level > Constants.TOTAL_LEVEL)
			game.setScreen(game.getLevelScreen());
		else
			game.setScreen(game.getPassingScreen());
	}

	@Override
	public void notified(int id) {
		// TODO Auto-generated method stub
		if (id == 0){
			stage.getRoot().action(FadeOut.$(2f).setCompletionListener(new OnActionCompleted() {
				
				@Override
				public void completed(Action action) {
					// TODO Auto-generated method stub
					game.setScreen(game.getLevelScreen());
				}
			}));
		}
	}
}
