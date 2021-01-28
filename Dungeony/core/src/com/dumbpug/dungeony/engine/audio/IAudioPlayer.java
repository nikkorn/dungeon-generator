package com.dumbpug.dungeony.engine.audio;

/**
 * The environment audio player.
 */
public interface IAudioPlayer {
    /**
     * Play the sound where the volume is based on the distance between the source and the environment camera.
     * @param sound The name of the sound.
     * @param volume The sound volume, based on the distance between the source and the environment camera.
     */
    void playSound(String sound, float volume);
}
