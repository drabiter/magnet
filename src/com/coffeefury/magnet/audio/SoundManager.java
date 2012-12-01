package com.coffeefury.magnet.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Disposable;
import com.coffeefury.magnet.audio.SoundManager.GameSound;
import com.coffeefury.magnet.utils.LRUCache;
import com.coffeefury.magnet.utils.LRUCache.CacheEntryRemovedListener;

/**
 * A service that manages the sound effects.
 */
public class SoundManager implements
		CacheEntryRemovedListener<GameSound, Sound>, Disposable {
	/**
	 * The available sound files.
	 */
	public enum GameSound {
		CANCEL("sounds/cancel.wav"),
		CLONE_START("sounds/clone_start.wav"),
		CLONE_FINISH("sounds/clone_finish.wav"),
		CONFIRM("sounds/confirm.wav"),
		SELECT("sounds/select.wav"),
		THROWPULL("sounds/throwpull.wav"),
		;

		private final String fileName;

		private GameSound(String fileName) {
			this.fileName = fileName;
		}

		public String getFileName() {
			return fileName;
		}
	}

	/**
	 * The volume to be set on the sound.
	 */
	private float volume = 1f;

	/**
	 * Whether the sound is enabled.
	 */
	private boolean enabled = true;

	/**
	 * The sound cache.
	 */
	private final LRUCache<GameSound, Sound> soundCache;

	/**
	 * Creates the sound manager.
	 */
	public SoundManager() {
		soundCache = new LRUCache<GameSound, Sound>(10);
		soundCache.setEntryRemovedListener(this);
	}

	/**
	 * Plays the specified sound.
	 */
	public void play(GameSound sound) {
		// check if the sound is enabled
		if (!enabled) return;

		// try and get the sound from the cache
		Sound soundToPlay = soundCache.get(sound);
		if (soundToPlay == null) {
			FileHandle soundFile = Gdx.files.internal(sound.getFileName());
			soundToPlay = Gdx.audio.newSound(soundFile);
			soundCache.add(sound, soundToPlay);
		}

		// play the sound
		soundToPlay.play(volume);
	}

	/**
	 * Sets the sound volume which must be inside the range [0,1].
	 */
	public void setVolume(float volume) {
		// check and set the new volume
		if (volume < 0) this.volume = 0f;
		else if (volume > 1f) this.volume = 1f;
		else this.volume = volume;
	}

	/**
	 * Enables or disabled the sound.
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public boolean isEnabled() {
		return enabled;
	}

	// EntryRemovedListener implementation

	@Override
	public void notifyEntryRemoved(GameSound key, Sound value) {
		value.dispose();
	}

	/**
	 * Disposes the sound manager.
	 */
	public void dispose() {
		for (Sound sound : soundCache.retrieveAll()) {
			sound.stop();
			sound.dispose();
		}
	}
}
