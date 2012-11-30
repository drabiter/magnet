package com.coffeefury.magnet.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.coffeefury.magnet.components.Unit;
import com.coffeefury.magnet.screens.AbstractScreen;
import com.coffeefury.magnet.screens.GameScreen;
import com.coffeefury.magnet.utils.Constants;
import com.coffeefury.magnet.utils.UtilsBase;

public class MenuSystem extends Group implements System{
	
	AbstractScreen screen;

	public MenuSystem(AbstractScreen screen) {
		// TODO Auto-generated constructor stub
		super("menusystem");
		this.screen = screen;
	}
	
	ButtonMenu attackMenu;
	ButtonMenu waitMenu;
	
	ButtonMenu restartMenu;
	ButtonMenu backToLevel;
	
	Unit lastUnit = null;
	
	private float offX;
	private float offY;

	public void openUnitMenu(Unit unit) {
		// TODO Auto-generated method stub
		lastUnit = unit;
		
		prepareUnitMenu(unit);
		
		this.addActor(attackMenu);
		this.addActor(waitMenu);
	}
	
	public void openMenu(int x, int y){
		prepareMenu(x, y);
		
		this.addActor(restartMenu);
		this.addActor(backToLevel);
	}

	public boolean poolMenu(float x, float y){
		float y1 =  Gdx.graphics.getHeight() - y;
		for (Actor actor : this.getActors()){
			if (x >= actor.x && y1 >= actor.y && x <= actor.x + Constants.SIZE && y1 <= actor.y + Constants.SIZE){
				((Button) actor).click(x, y1);
				return true;
			}
		}
		return false;
	}
	
	public boolean isOpenMenu(){
		return children.size() > 0;
	}
	
	@Override
	public void clear() {
		// TODO Auto-generated method stub
		super.clear();
	}

	private void prepareUnitMenu(Unit unit){
		attackMenu = new ButtonMenu(screen.getTextureRegion("atk_".concat(unit.type.getTexture())));
		attackMenu.setClickListener(new ClickListener(){

			@Override
			public void click(Actor actor, float x, float y) {
				// TODO Auto-generated method stub
				( (FieldSystem) screen.getStage().findActor("fieldsystem")).selectAttackArea(lastUnit);
			}
			
		});
		
		if (waitMenu == null){
			waitMenu = new ButtonMenu(screen.getTextureRegion("wait_l,l"));
			waitMenu.setClickListener(new ClickListener() {
				
				@Override
				public void click(Actor actor, float x, float y) {
					// TODO Auto-generated method stub
					if (lastUnit != null){
						lastUnit.played();
					}
					if (((UnitSystem) screen.getStage().findActor("unitsystem")).isPlayableUnitsDone()){
						screen.notified(GameScreen.PLAYED);
					}
				}
			});
		}
		
		offX = ((unit.x >= Constants.SIZE)? -Constants.SIZE : Constants.SIZE);
		offY = 0;
		waitMenu.setPos(unit.x, unit.y, offX, offY);
		offX = 0;
		offY = ((unit.y >= Constants.SIZE)? -Constants.SIZE : Constants.SIZE);
		attackMenu.setPos(unit.x, unit.y, offX, offY);
	}
	
	private void prepareMenu(int x, int y) {
		// TODO Auto-generated method stub
		if (restartMenu == null){
			restartMenu = new ButtonMenu(screen.getTextureRegion("restart_l,l"));
			restartMenu.setClickListener(new ClickListener() {
				
				@Override
				public void click(Actor actor, float x, float y) {
					// TODO Auto-generated method stub
					((UnitSystem) screen.getStage().findActor("unitsystem")).create(UtilsBase.loadLevel(Constants.level));
				}
			});
		}
		
		if (backToLevel == null){
			backToLevel = new ButtonMenu(screen.getTextureRegion("return_l,l"));
			backToLevel.setClickListener(new ClickListener() {
				
				@Override
				public void click(Actor actor, float x, float y) {
					// TODO Auto-generated method stub
					screen.notified(0);
				}
			});
		}
		
		float offset = Constants.SIZE * .5f; 
		restartMenu.setPos(x, Gdx.graphics.getHeight() - y, offset, offset);
		restartMenu.moveable = false;
		backToLevel.setPos(x - Constants.SIZE, Gdx.graphics.getHeight() - y, -offset, offset);
		backToLevel.moveable = false;
	}
	
	class ButtonMenu extends Button{
		
//		float offX, offY;
		
		public boolean moveable;
		
		public ButtonMenu(TextureRegion region) {
			super(region);
			// TODO Auto-generated constructor stub
		}
		
		public void setPos(float x, float y, float offX, float offY){
//			this.offX = offX;
//			this.offY = offY;
			this.x = x + offX;
			this.y = y + offY;
		}

//		@Override
//		public void act(float delta) {
//			// TODO Auto-generated method stub
//			super.act(delta);
//			if (moveable) {
//				this.x = lastUnit.x + offX;
//				this.y = lastUnit.y + offY;
//			}
//		}
		
	}
}
