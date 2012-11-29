package com.coffeefury.magnet.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.coffeefury.magnet.components.MovingLabel;
import com.coffeefury.magnet.screens.AbstractScreen;

public class HUDSystem extends Group implements System {

	AbstractScreen screen;
	
	Label turnLabel;
	Image cloneIcon;
	
	Image turn;
	Image clone;
	
	public int numOfTurn;

	public HUDSystem(AbstractScreen screen) {
		super("hudsystem");
		this.screen = screen;
		
		turn = new Image(screen.getTextureRegion("turns_l,l"));
		turnLabel = new Label(String.valueOf(numOfTurn), screen.getSkin());
		turnLabel.x = turn.x + turn.getPrefWidth() + 10;
		turnLabel.getStyle().font.scale(.5f);
		
		clone = new Image(screen.getTextureRegion("nextclone_l,l"));
		clone.y = Gdx.graphics.getHeight() - clone.getPrefHeight();
		cloneIcon = new Image();
		cloneIcon.y = clone.y;
		cloneIcon.x = clone.x + clone.getPrefWidth() + 10;

		this.addActor(turnLabel);
		this.addActor(cloneIcon);
		this.addActor(turn);
		this.addActor(clone);
	}

	@Override
	public void act(float delta) {
		// TODO Auto-generated method stub
		super.act(delta);
		if (UnitSystem.getCloneQueue() != null)
			cloneIcon.setRegion(screen.getTextureRegion(UnitSystem.getCloneQueue().getTexture()));
		else
			cloneIcon.setRegion(null);
	}

	MovingLabel turnNotif;
	
	public void showTurnNotif(){
		turnNotif = new MovingLabel(this, "TURN ".concat(String.valueOf(numOfTurn)), screen.getSkin());
		turnNotif.getStyle().font.setScale(.75f);
		this.addActor(turnNotif);
		
		updateTurn();
	}
	
	public void updateTurn() {
		// TODO Auto-generated method stub
		numOfTurn++;
		turnLabel.setText(String.valueOf(numOfTurn));
	}

	public void resetTurn(){
		((UnitSystem) screen.getStage().findActor("unitsystem")).resetPlayableUnits();
	}
}
