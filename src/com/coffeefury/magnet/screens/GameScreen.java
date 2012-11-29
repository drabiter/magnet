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
		
		fadeIn(1.5f);
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

		String stage = "ready ";
		
		if (menuSystem.poolMenu(x, y)){
			menuSystem.clear();
			return;
		}
		
		Unit unit = unitSystem.findByLocation(x, y, true, true);
		
//		if (unit != null && unit.name.contains(Type.FINISH.toString()))
//			unit = null;
		
		if (unit != null){
			stage = stage.concat("1");
			if (lastUnit != null){
				if (unit.name.equals(lastUnit.name)){
					//open unit's menu
					stage = stage.concat(".01");
					menuSystem.openUnitMenu(lastUnit);
					fieldSystem.clearArea();
				}else if (!menuSystem.isOpenMenu() && !fieldSystem.isAttackState()){
					//change unit focus
					stage = stage.concat(".02");
					if (!unit.played && unit.playable) {
						releaseUnit();
						selectUnit(unit);
					}
				}else if (fieldSystem.isAttackState()){
					fieldSystem.clearArea();
					if (fieldSystem.inAttackRadius(lastUnit, unit)){
						stage = stage.concat(".06");
						Facing facing = UtilsBase.getFacing(lastUnit, unit);
						lastUnit.attack(facing);
						unit.damagedBy(lastUnit, facing);
						unitSystem.adjustUnitPosition(lastUnit, unit, facing);
						unitSystem.isPlayableUnitsDone();
						fieldSystem.clearArea();
						releaseUnit();
					}else{
						stage = stage.concat(".07");
						menuSystem.openUnitMenu(lastUnit);
					}
				}
				else{
					stage = stage.concat(".03");
				}
			}else{
				stage = stage.concat(".0a");
				if (!unit.played){
					selectUnit(unit);
				}
			}
		}else{
			stage = stage.concat("2");
			if (lastUnit != null){
				if (fieldSystem.isMoveState()){
					fieldSystem.clearArea();
					if (fieldSystem.inMoveRadius(lastUnit, x, y)) {
						stage = stage.concat(".04");
						float mapX = x - (x % Constants.SIZE);
						float mapY = (Gdx.graphics.getHeight() - y) - ((Gdx.graphics.getHeight() - y) % Constants.SIZE);
						lastUnit.move(mapX, mapY);
						unitSystem.updateUnitPosition(lastUnit.name, mapX, mapY);
//						menuSystem.openUnitMenu(lastUnit);
					}else{
						stage = stage.concat(".05");
						releaseUnit();
					}
				}else if (lastUnit.type == Type.CLONER && fieldSystem.isAttackState() && UnitSystem.getCloneQueue() != null){
					fieldSystem.clearArea();
					if (fieldSystem.inAttackRadius(lastUnit, x, y)){
						stage = stage.concat(".10");
						unitSystem.createUnit(UnitSystem.getCloneQueue(), x, Gdx.graphics.getHeight() - y);
						UnitSystem.setCloneQueue(null);
						lastUnit.played();
						releaseUnit();
					}else{
						stage = stage.concat(".11");
						menuSystem.openUnitMenu(lastUnit);
					}
				}else{
					if (!lastUnit.played) {
						stage = stage.concat(".09");
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
					stage = stage.concat(".0b");
					menuSystem.clear();
				}else{
					stage = stage.concat(".0c");
					menuSystem.openMenu(x, y);
				}
			}
		}
		
		Gdx.app.log(null, stage);
		
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
