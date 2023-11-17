package io.github.chinawaremc.nocoolmod.enums;

import net.minecraft.world.item.Item;

import java.math.BigDecimal;

import static java.math.RoundingMode.UNNECESSARY;
@Deprecated
public enum RightClientRandom {
    ;
    private final double bound;
    private final Item item;
    private final int mod = 100;
    RightClientRandom(int percentage, Item item) {
        BigDecimal bigDecimal = new BigDecimal(60).divide(new BigDecimal(percentage), UNNECESSARY);
        bound = bigDecimal.doubleValue();
        this.item = item;
    }
}
