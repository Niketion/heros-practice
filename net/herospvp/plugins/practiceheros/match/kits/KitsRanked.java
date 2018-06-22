package net.herospvp.plugins.practiceheros.match.kits;

import net.herospvp.plugins.practiceheros.match.kits.Kits;

public enum KitsRanked implements Kits
{
    NODEBUFF(0),
    BUILDUHC(0),
    COMBO(0),
    OP(0);
    
    private int number;

    private KitsRanked(int number) {
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

