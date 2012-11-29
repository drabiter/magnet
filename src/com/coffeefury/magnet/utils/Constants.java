package com.coffeefury.magnet.utils;

public class Constants {
	
	public static final int TILE_W = 20, TILE_H = 12, SIZE = 40, TOTAL_LEVEL = 1;
	
	public static int level = 2;
	
	public static final int[] ATTACK_RANGE = {
		4, //puller
		1, //pusher
		1, //cloner
		0, //sheep
		0, //finish
	};
	
	public static final int[] HIT = {
		1, //puller
		4, //pusher
		0, //cloner
		0, //sheep
		0, //finish
	};
	
	public static final int[] HP = {
		0, //puller
		0, //pusher
		0, //cloner
		0, //sheep
		0, //finish
	};
	
	public static final int[] MOVE_RANGE = {
		3, //puller
		2, //pusher
		1, //cloner
		0, //sheep
		0, //finish
	};

}
