package com.coffeefury.magnet.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.OnActionCompleted;
import com.badlogic.gdx.scenes.scene2d.actions.Delay;
import com.badlogic.gdx.scenes.scene2d.actions.MoveBy;
import com.badlogic.gdx.scenes.scene2d.actions.Sequence;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.coffeefury.magnet.systems.HUDSystem;

public class MovingLabel extends Label {
	
//	HUDSystem hudSystem;

	public MovingLabel(final HUDSystem hudSystem, CharSequence text, Skin skin) {
		super(text, skin);
		// TODO Auto-generated constructor stub
		x = (Gdx.graphics.getWidth() - this.getPrefWidth()) / 2;
		y = Gdx.graphics.getHeight();
		float dy = -(Gdx.graphics.getHeight() / 2);
		action(Sequence.$(MoveBy.$(0, dy, .5f), Delay.$(1f), MoveBy.$(0, dy, .5f)).setCompletionListener(new OnActionCompleted() {
			
			@Override
			public void completed(Action action) {
				// TODO Auto-generated method stub
				markToRemove(true);
//				hudSystem.resetTurn();
			}
			
		}));
	}

}
