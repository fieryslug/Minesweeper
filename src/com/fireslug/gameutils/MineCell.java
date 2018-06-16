package com.fireslug.gameutils;

import com.fireslug.Reference;
import com.sun.org.apache.bcel.internal.generic.ARRAYLENGTH;
import com.sun.org.apache.regexp.internal.RE;

import java.sql.Ref;
import java.util.ArrayList;
import java.util.Map;

public class MineCell {

    private Map<Coord2, MineCell> parent;
    public Coord2 coord;
    public boolean isBomb;
    public boolean ex;


    public MineCell(Map<Coord2, MineCell> parent, Coord2 coord, boolean isBomb) {

        this.parent = parent;
        this.coord = coord;
        this.isBomb = isBomb;
        this.ex = false;


    }

    public Map<Coord2, MineCell> getParent() {

        return this.parent;

    }

    public ArrayList<MineCell> getAdjacents() {

        ArrayList<MineCell> temp = new ArrayList<>();

        if(this.coord.x != Reference.WIDTH - 1) {

            temp.add(this.getParent().get(new Coord2(this.coord.x + 1, this.coord.y)));

            if(this.coord.y != Reference.HEIGHT - 1)
                temp.add(this.getParent().get(new Coord2(this.coord.x + 1, this.coord.y + 1)));

            if(this.coord.y != 0)
                temp.add(this.getParent().get(new Coord2(this.coord.x + 1, this.coord.y - 1)));

        }

        if(this.coord.x != 0) {

            temp.add(this.getParent().get(new Coord2(this.coord.x - 1, this.coord.y)));

            if(this.coord.y != Reference.HEIGHT -1)
                temp.add(this.getParent().get(new Coord2(this.coord.x - 1, this.coord.y + 1)));

            if(this.coord.y != 0)
                temp.add(this.getParent().get(new Coord2(this.coord.x - 1, this.coord.y - 1)));

        }

        if(this.coord.y != Reference.HEIGHT - 1) {

            temp.add(this.getParent().get(new Coord2(this.coord.x, this.coord.y + 1)));

        }

        if(this.coord.y != 0) {

            temp.add(this.getParent().get(new Coord2(this.coord.x, this.coord.y - 1)));

        }

        return temp;

    }

    public ArrayList<MineCell> getShareEdge() {

        ArrayList<MineCell> temp = new ArrayList<>();

        if(this.coord.x != Reference.WIDTH - 1) {

            temp.add(this.getParent().get(new Coord2(this.coord.x + 1, this.coord.y)));

        }

        if(this.coord.x != 0) {

            temp.add(this.getParent().get(new Coord2(this.coord.x - 1, this.coord.y)));

        }

        if(this.coord.y != Reference.HEIGHT - 1) {

            temp.add(this.getParent().get(new Coord2(this.coord.x, this.coord.y + 1)));

        }

        if(this.coord.y != 0) {

            temp.add(this.getParent().get(new Coord2(this.coord.x, this.coord.y - 1)));

        }

        return temp;

    }

    public int calcAdjBombs() {

        ArrayList<MineCell> adjs = this.getAdjacents();
        int tot = 0;

        for(MineCell cell : adjs) {

            if(cell.isBomb) {
                tot += 1;
            }

        }

        return tot;

    }

    public void update() {

        if (!this.ex) {
            this.ex = true;
            if (this.calcAdjBombs() == 0) {

                for (MineCell cell : this.getAdjacents()) {

                    if (cell.calcAdjBombs() == 0 && !cell.ex)
                        cell.update();

                }
                for (MineCell cell : this.getAdjacents()) {

                    if (cell.calcAdjBombs() > 0 && !cell.ex)
                        cell.update();

                }

            }


        }
    }


}
