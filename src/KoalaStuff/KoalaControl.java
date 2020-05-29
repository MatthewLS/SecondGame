package KoalaStuff;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package tankrotationexample;


import GameObjects.*;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;


/**
 *
 * @author anthony-pc
 */
public class KoalaControl implements KeyListener {

    private ArrayList<Koala> koalas;
    private final int up;
    private final int down;
    private final int right;
    private final int left;
    private int koalaCount;
    
    public KoalaControl(ArrayList<Koala> koalas, int up, int down, int left, int right) {
        this.koalas = koalas;
        this.up = up;
        this.down = down;
        this.right = right;
        this.left = left;
        koalaCount = koalas.size();
    }

    @Override
    public void keyTyped(KeyEvent ke) {
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        int keyPressed = ke.getKeyCode();
        koalas.forEach(koala -> {
            if (keyPressed == up) {
                koala.toggleUpPressed();
            }
            if (keyPressed == down) {
                koala.toggleDownPressed();
            }
            if (keyPressed == left) {
                koala.toggleLeftPressed();
            }
            if (keyPressed == right) {
                koala.toggleRightPressed();
            }
        });
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        int keyReleased = ke.getKeyCode();
        koalas.forEach(koala -> {
            if (keyReleased  == up) {
                koala.unToggleUpPressed();
            }
            if (keyReleased == down) {
                koala.unToggleDownPressed();
            }
            if (keyReleased  == left) {
                koala.unToggleLeftPressed();
            }
            if (keyReleased  == right) {
                koala.unToggleRightPressed();
            }
        });
    }
}
