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
	Label cloneLabel;
	
	Image turn;
	Image clone;
	
	public int numOfTurn;

	public HUDSystem(AbstractScreen screen) {
		super("hudsystem");
		this.screen = screen;
		
		turn = new Image(screen.getTextureRegion("turns_l,l"));
		turnLabel = new Label(String.valueOf(numOfTurn), screen.getSkin());
		turnLabel.x = turn.x + turn.getPrefWidth();
		
		clone = new Image(screen.getTextureRegion("nextclone_l,l"));
		clone.y = Gdx.graphics.getHeight() - clone.getPrefHeight();
		cloneLabel = new Label("", screen.getSkin());
		cloneLabel.y = clone.y;
		cloneLabel.x = clone.x + clone.getPrefWidth();
		this.addActor(turnLabel);
		this.addActor(cloneLabel);
		this.addActor(turn);
		this.addActor(clone);
	}

	@Override
	public void act(float delta) {
		// TODO Auto-generated method stub
		super.act(delta);
		if (UnitSystem.getCloneQueue() != null)
			cloneLabel.setText(UnitSystem.getCloneQueue().toString());
		else
			cloneLabel.setText("");
	}

	MovingLabel turnNotif;
	
	public void showTurnNotif(){
		turnNotif = new MovingLabel(this, "TURN ".concat(String.valueOf(numOfTurn)), screen.getSkin());
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
