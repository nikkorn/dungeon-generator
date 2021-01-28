package com.dumbpug.dungeony.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.dumbpug.dungeony.Constants;

import java.util.HashMap;

/**
 * Provides all game audio.
 */
public class AudioProvider {
    /**
     * All cached sounds effects.
     */
    private static HashMap<SoundEffect, Sound> cachedSoundEffects = new HashMap<SoundEffect, Sound>();
    /**
     * All cached music.
     */
    private static HashMap<MusicTrack, Music> cachedMusic = new HashMap<MusicTrack, Music>();

    static {
        // Populate our sound effect map.
        for(SoundEffect soundEffect : SoundEffect.values()) {
            String soundEffectPath = "audio/sounds/" + soundEffect + ".wav";
            Sound sound = Gdx.audio.newSound(Gdx.files.internal(soundEffectPath));
            cachedSoundEffects.put(soundEffect, sound);
        }
        // Populate our music map.
        for(MusicTrack musicTrack : MusicTrack.values()) {
            String musicPath = "audio/music/" + musicTrack + ".wav";
            Music music = Gdx.audio.newMusic(Gdx.files.internal(musicPath));
            music.setLooping(true);
            music.setVolume(Constants.AUDIO_MUSIC_VOLUME);
            cachedMusic.put(musicTrack, music);
        }
    }

    /**
     * Get sound effect.
     * @return sound effect.
     */
    public static Sound getSoundEffect(SoundEffect soundEffect) {
        return cachedSoundEffects.get(soundEffect);
    }

    /**
     * Get music track.
     * @return music track.
     */
    public static Music getMusic(MusicTrack music) {
        return cachedMusic.get(music);
    }
}