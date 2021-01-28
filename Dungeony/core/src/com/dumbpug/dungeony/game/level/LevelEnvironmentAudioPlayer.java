package com.dumbpug.dungeony.game.level;

import com.badlogic.gdx.audio.Sound;
import com.dumbpug.dungeony.Constants;
import com.dumbpug.dungeony.audio.AudioProvider;
import com.dumbpug.dungeony.audio.SoundEffect;
import com.dumbpug.dungeony.engine.audio.IAudioPlayer;

/**
 * The audio player used in a level environment.
 */
public class LevelEnvironmentAudioPlayer implements IAudioPlayer {
    /**
     * Play the sound where the volume is based on the distance between the source and the environment camera.
     * @param sound The name of the sound.
     * @param volume The sound volume, based on the distance between the source and the environment camera.
     */
    @Override
    public void playSound(String sound, float volume) {
        // Get the relevant sound from the audio provider.
        Sound soundEffect = AudioProvider.getSoundEffect(SoundEffect.valueOf(sound));

        // Play the sound effect!
        soundEffect.play(Constants.AUDIO_SOUND_EFFECT_VOLUME * volume);
    }
}
