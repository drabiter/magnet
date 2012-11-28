package com.coffeefury.magnet.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Disposable;

/**
 * A service that manages the background music.
 * <p>
 * Only one music may be playing at a given time.
 */
public class MusicManager implements Disposable {
	/**
	 * The available music files.
	 */
	public enum GameMusic {
		BG01("sounds/");

		private final String fileName;

		private GameMusic(String fileName) {
			this.fileName = fileName;
		}

		public String getFileName() {
			return fileName;
		}
	}

	/**
	 * Holds the music currently being played, if any.
	 */
	private Music musicBeingPlayed;

	/**
	 * The volume to be set on the music.
	 */
	private float volume = 1f;

	/**
	 * Whether the music is enabled.
	 */
	private boolean enabled = true;

	/**
	 * Plays the given music (starts the streaming).
	 * <p>
	 * If there is already a music being played it is stopped automatically.
	 */
	public void play(GameMusic music) {
		// check if the music is enabled
		if (!enabled) return;

		// stop any music being played
		stop();

		// start streaming the new music
		FileHandle musicFile = Gdx.files.internal(music.getFileName());
		musicBeingPlayed = Gdx.audio.newMusic(musicFile);
		musicBeingPlayed.setVolume(volume);
		musicBeingPlayed.setLooping(true);
		musicBeingPlayed.play();
	}

	/**
	 * Stops and disposes the current music being played, if any.
	 */
	public void stop() {
		if (musicBeingPlayed != null) {
			musicBeingPlayed.stop();
			musicBeingPlayed.dispose();
		}
	}
	
	/**
	 * Pauses the current music being played, if any.
	 */
	public void pause() {
		if (musicBeingPlayed != null) {
			musicBeingPlayed.pause();
		}
	}

	/**
	 * Sets the music volume which must be inside the range [0,1].
	 */
	public void setVolume(float volume) {
		// check and set the new volume
		if (volume < 0) this.volume = 0f;
		else if (volume > 1f) this.volume = 1f;
		else this.volume = volume;

		// if there is a music being played, change its volume
		if (musicBeingPlayed != null) musicBeingPlayed.setVolume(volume);
	}

	/**
	 * Enables or disabled the music.
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;

		// if the music is being deactivated, stop any music being played
		if (!enabled) stop();
	}

	/**
	 * Disposes the music manager.
	 */
	public void dispose() {
		stop();
	}
}
