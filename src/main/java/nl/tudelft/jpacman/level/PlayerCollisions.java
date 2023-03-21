package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.board.Unit;
import nl.tudelft.jpacman.npc.Ghost;
import nl.tudelft.jpacman.points.PointCalculator;

/**
 * A simple implementation of a collision map for the JPacman player.
 * <p>
 * It uses a number of instanceof checks to implement the multiple dispatch for the 
 * collisionmap. For more realistic collision maps, this approach will not scale,
 * and the recommended approach is to use a {@link CollisionInteractionMap}.
 *
 * @author Arie van Deursen, 2014
 *
 */

public class PlayerCollisions implements CollisionMap {

    private PointCalculator pointCalculator;

    /**
     * Create a simple player-based collision map, informing the
     * point calculator about points to be added.
     *
     * @param pointCalculator
     *             Strategy for calculating points.
     */
    public PlayerCollisions(PointCalculator pointCalculator) {
        this.pointCalculator = pointCalculator;
    }

    @Override
    public void collide(Unit mover, Unit collidedOn) {
        if (mover instanceof Player) {
            if (collidedOn instanceof Ghost) {
                playerVersusGhost((Player) mover, (Ghost) collidedOn);
            }
            if (collidedOn instanceof Pellet) {
                playerVersusPellet((Player) mover, (Pellet) collidedOn);
            }
        }
    }

    /**
     * Actual case of player bumping into ghost or vice versa.
     *
     * @param player
     *          The player involved in the collision.
     * @param ghost
     *          The ghost involved in the collision.
     */
    public void playerVersusGhost(Player player, Ghost ghost) {
        pointCalculator.collidedWithAGhost(player, ghost);
        // Appeler la méthode loseVies() de la classe Player qui va décrémenter le nombre de vie du joueur
        // Enregistrer grid dans un fichier grid.txt afin de s'en servir dans une prochaine itération de la méthode makeGrid
        player.setAlive(false);
        player.setKiller(ghost);
    }

    /**
     * Actual case of player consuming a pellet.
     *
     * @param player
     *           The player involved in the collision.
     * @param pellet
     *           The pellet involved in the collision.
     */
    public void playerVersusPellet(Player player, Pellet pellet) {
        pointCalculator.consumedAPellet(player, pellet);
        pellet.leaveSquare();
    }

}
