package com.coffeefury.magnet.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.coffeefury.magnet.components.MovingLabel;
import com.coffeefury.magnet.screens.AbstractScreen;

public class HUDSystem extends Group implements System {
	
	private static final String TURN_TEXT = "Number of Turns : ", CLONE_TEXT = "Next Clone : ";

	AbstractScreen screen;
	
	Label turnLabel;
	Label cloneLabel;
	
	public int numOfTurn;

	public HUDSystem(AbstractScreen screen) {
		super("hudsystem");
		this.screen = screen;
		
		turnLabel = new Label(TURN_TEXT.concat(String.valueOf(numOfTurn)), screen.getSkin());
		cloneLabel = new Label(CLONE_TEXT, screen.getSkin());
		cloneLabel.y = Gdx.graphics.getHeight() - cloneLabel.getPrefHeight();
		this.addActor(turnLabel);
		this.addActor(cloneLabel);
	}

	@Override
	public void act(float delta) {
		// TODO Auto-generated method stub
		super.act(delta);
		if (UnitSystem.getCloneQueue() != null)
			cloneLabel.setText(CLONE_TEXT.concat(UnitSystem.getCloneQueue().toString()));
		else
			cloneLabel.setText(CLONE_TEXT);
	}

	MovingLabel turnNotif;
	
	public void showTurnNotif(){
		turnNotif = new MovingLabel(this, "TURN -UNKNOWN-", screen.getSkin());
		this.addActor(turnNotif);
		
		updateTurn();
	}
	
	public void updateTurn() {
		// TODO Auto-generated method stub
		numOfTurn++;
		turnLabel.setText(TURN_TEXT.concat(String.valueOf(numOfTurn)));
	}

	public void resetTurn(){
		((UnitSystem) screen.getStage().findActor("unitsystem")).resetPlayableUnits();
	}
}
