package com.dumbpug.dungeony.game.character.enemy;

import com.dumbpug.dungeony.engine.Position;
import com.dumbpug.dungeony.game.character.enemy.enemies.Grunt;
import com.dumbpug.dungeony.game.weapon.WeaponQuality;
import com.dumbpug.dungeony.game.weapon.handgun.Pistol;
import com.dumbpug.levelgeneration.IEntityProperties;

/**
 * Factory for creating enemy NPC instances.
 */
public class EnemyFactory {
    /**
     * Create an Enemy instance of the given type.
     * @param type The enemy type.
     * @param position The initial position of the enemy.
     * @param properties The entity properties.
     * @return An Enemy instance of the given type and position.
     */
    public static Enemy create(EnemyType type, Position position, IEntityProperties properties) {
        switch (type) {
            case GRUNT:
                Grunt grunt = new Grunt(position, properties);
                grunt.setWeapon(new Pistol(WeaponQuality.AVERAGE));
                return grunt;
            default:
                throw new RuntimeException("cannot create Enemy instance for unknown type: " + type);
        }
    }
}
