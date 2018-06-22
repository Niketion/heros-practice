package net.herospvp.plugins.practiceheros.match.kits;

import net.herospvp.plugins.practiceheros.match.kits.Kits;

public enum KitsUnranked implements Kits
{
    NODEBUFF(0),
    BUILDUHC(0),
    COMBO(0),
    OP(0),
    MCSG(0),
    ARCHER(0);
    
    private int number;

    private KitsUnranked(int number) {
        this.number = number;
    }

    @Override
    public int getNumber() {
        return this.number;
    }

    @Override
    public void addNumber() {
        this.number += 2;
    }

    @Override
    public void removeNumber() {
        this.number -= 2;
    }
}

