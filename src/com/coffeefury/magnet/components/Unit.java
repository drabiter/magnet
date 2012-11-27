package com.coffeefury.magnet.components;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.OnActionCompleted;
import com.badlogic.gdx.scenes.scene2d.actions.Delay;
import com.badlogic.gdx.scenes.scene2d.actions.FadeIn;
import com.badlogic.gdx.scenes.scene2d.actions.FadeOut;
import com.badlogic.gdx.scenes.scene2d.actions.Forever;
import com.badlogic.gdx.scenes.scene2d.actions.MoveBy;
import com.badlogic.gdx.scenes.scene2d.actions.MoveTo;
import com.badlogic.gdx.scenes.scene2d.actions.Sequence;
import com.badlogic.gdx.scenes.scene2d.ui.Align;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Scaling;
import com.coffeefury.magnet.systems.MenuSystem;
import com.coffeefury.magnet.systems.UnitSystem;
import com.coffeefury.magnet.systems.UnitSystem.AttackType;
import com.coffeefury.magnet.systems.UnitSystem.Type;
import com.coffeefury.magnet.utils.Constants;
import com.coffeefury.magnet.utils.UtilsBase.Facing;

public class Unit extends Image {

	public Type type;

	public float HP;
	public float HIT;

	public boolean playable;
	public boolean played = false;
	public boolean moved = false;

	public Vector2 lastPos = new Vector2(-1, -1);

	public Unit(TextureRegion region, Type type, int count, float x, float y) {
		super(region, Scaling.stretch, Align.CENTER, type.toString().concat(
				String.valueOf(count)));
		this.x = x;
		this.y = y;
		this.HP = Constants.HP[type.ordinal()];
		this.HIT = Constants.HIT[type.ordinal()];
		this.type = type;
		this.width = this.height = Constants.SIZE;
		this.playable = type.isPlayable();
	}

	public AttackType getAtkType() {
		// TODO Auto-generated method stub
		return type.getAtkType();
	}

	public void flash() {
		action(Forever.$(Sequence.$(FadeOut.$(.25f), Delay.$(.25f),
				FadeIn.$(.25f), Delay.$(.25f))));
	}

	public void shake() {
		float d = Constants.SIZE / 4;
		action(Sequence.$(MoveBy.$(-d, d, .05f), MoveBy.$(2 * d, 0, .05f),
				MoveBy.$(0, -2 * d, .05f), MoveBy.$(-2 * d, 0, .05f),
				MoveTo.$(x, y, .05f)));
	}

	public void darken() {
		played = true;
		moved = true;
		color.r = .35f;
		color.g = .35f;
		color.b = .35f;
	}

	public void played() {
		darken();
		release();
	}

	public void reset() {
		played = false;
		moved = false;
		color.r = color.g = color.b = 1f;
		saveCurrentPos();
		release();
	}

	public void release() {
		// TODO Auto-generated method stub
		clearActions();
		color.a = 1f;
	}

	public void undoMove() {
		if (lastPos.x < 0 || lastPos.y < 0)
			return;
		// TODO Auto-generated method stub
		this.x = lastPos.x;
		this.y = lastPos.y;
	}

	public void saveCurrentPos() {
		// TODO Auto-generated method stub
		lastPos.set(x, y);
	}

	public void damagedBy(Unit lastUnit, Facing facing) {
		// TODO Auto-generated method stub
		if (lastUnit.getAtkType() == AttackType.FIELDER) {
			// this.x = lastUnit.x + (facing.xd * lastUnit.HIT *
			// Constants.SIZE);
			// this.y = lastUnit.y + (facing.yd * lastUnit.HIT *
			// Constants.SIZE);
			// normalizePos();
//			shake();
		} else if (lastUnit.getAtkType() == AttackType.CLONE) {
			if (type != Type.FINISH && type != Type.SHEEP)
				UnitSystem.setCloneQueue(type);
		}
	}

	public void attack(Facing facing) {
		// TODO Auto-generated method stub
		played();
		float d = Constants.SIZE / 4;
		action(Sequence.$(MoveBy.$(facing.xd * d, facing.yd * d, .25f),
				MoveTo.$(x, y, .25f)));
	}

	public void move(final float x1, final float y1) {
		// TODO Auto-generated method stub
		saveCurrentPos();
		action(Sequence.$(
				MoveBy.$(x1 - x, 0, .25f).setCompletionListener(
						new OnActionCompleted() {

							@Override
							public void completed(Action action) {
								// TODO Auto-generated method stub
								x = x1;
							}
						}),
				MoveBy.$(0, y1 - y, .25f).setCompletionListener(
						new OnActionCompleted() {

							@Override
							public void completed(Action action) {
								// TODO Auto-generated method stub
								y = y1;
							}
						})).setCompletionListener(new OnActionCompleted() {
							
							@Override
							public void completed(Action action) {
								// TODO Auto-generated method stub
								((MenuSystem) getStage().findActor("menusystem")).openUnitMenu(Unit.this);
							}
						}));
	}

}
