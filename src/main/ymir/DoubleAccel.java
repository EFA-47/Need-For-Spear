package main.ymir;

import main.EnchantedSphere;
import main.Game;

public class DoubleAccel implements YmirStrategy{
    @Override
    public void activateAbility(Ymir ymir, Game parent) {
        EnchantedSphere sphere = EnchantedSphere.getInstance();
        sphere.setAcceleration(0.5);
    }
}
