package com.coffeefury.magnet.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.coffeefury.magnet.components.MovingLabel;
import com.coffeefury.magnet.screens.AbstractScreen;
import com.coffeefury.magnet.utils.Constants;

public class HUDSystem extends Group implements System {
	
	private static final float ALPHA = .6f;

	AbstractScreen screen;
	
	Label turnCount;
	Image cloneIcon;
	
	Image turn;
	Image clone;
	
	public int numOfTurn;

	public HUDSystem(AbstractScreen screen) {
		super("hudsystem");
		this.screen = screen;
		
		turn = new Image(screen.getTextureRegion("turns_l,l"));
		turnCount = new Label(String.valueOf(numOfTurn), screen.getSkin());
		turnCount.x = turn.x + turn.getPrefWidth() + 10;
		turnCount.y = turn.y = Gdx.graphics.getHeight() - turn.getPrefHeight();
		turnCount.getStyle().font.setScale(turn.getPrefHeight() / turnCount.getPrefHeight());
		
		clone = new Image(screen.getTextureRegion("nextclone_l,l"));
		clone.x = Gdx.graphics.getWidth() - (clone.getPrefWidth() + Constants.SIZE + 10);
		clone.y = Gdx.graphics.getHeight() - clone.getPrefHeight();
		cloneIcon = new Image(screen.getTextureRegion("cloner_l,l"));
		cloneIcon.x = clone.x + clone.getPrefWidth() + 10;
		cloneIcon.y = Gdx.graphics.getHeight() - Constants.SIZE;
		
		color.a = ALPHA;

		this.addActor(turnCount);
		this.addActor(cloneIcon);
		this.addActor(turn);
		this.addActor(clone);
	}

	@Override
	public void act(float delta) {
		// TODO Auto-generated method stub
		super.act(delta);
		if (UnitSystem.getCloneQueue() != null){
			cloneIcon.setRegion(screen.getTextureRegion(UnitSystem.getCloneQueue().getTexture()));
		}else{
			cloneIcon.setRegion(null);
		}
	}

	MovingLabel turnNotif;
	
	public void showTurnNotif(){
		turnNotif = new MovingLabel(this, "TURN ".concat(String.valueOf(numOfTurn)), screen.getSkin());
		turnNotif.getStyle().font.scale(.75f);
		this.addActor(turnNotif);
		
		updateTurn();
	}
	
	public void updateTurn() {
		// TODO Auto-generated method stub
		numOfTurn++;
		turnCount.setText(String.valueOf(numOfTurn));
		turnCount.getStyle().font.setScale(turn.getPrefHeight() / turnCount.getPrefHeight());
	}

	public void resetTurn(){
		((UnitSystem) screen.getStage().findActor("unitsystem")).resetPlayableUnits();
	}
}
