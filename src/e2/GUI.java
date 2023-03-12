package e2;

import e2.utils.Pair;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;

import java.util.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

public class GUI extends JFrame {
    
    private static final long serialVersionUID = -6218820567019985015L;
    private final Map<JButton, Pair<Integer,Integer>> buttons = new HashMap<>();
    private final Logics logics;
    
    public GUI(int size, int bombs) {
        this.logics = new LogicsImpl(size, bombs);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100*size, 100*size);
        
        JPanel panel = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(BorderLayout.CENTER,panel);
        
        ActionListener onClick = (e)->{
            final JButton bt = (JButton)e.getSource();
            final Pair<Integer,Integer> pos = this.buttons.get(bt);
            if (this.logics.isFlagged(pos)) return; // do nothing if the cell is flagged
            boolean aMineWasFound = this.logics.isBomb(pos); // call the logic here to tell it that cell at 'pos' has been seleced
            if (aMineWasFound) {
                quitGame();
                JOptionPane.showMessageDialog(this, "You lost!!");
            } else {
                this.logics.explore(pos);
                drawBoard();            	
            }
            boolean isThereVictory = this.logics.allDisplayed(); // call the logic here to ask if there is victory
            if (isThereVictory){
                quitGame();
                JOptionPane.showMessageDialog(this, "You won!!");
                System.exit(0);
            }
        };

        MouseInputListener onRightClick = new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                final JButton bt = (JButton)e.getSource();
                if (bt.isEnabled()){
                    final Pair<Integer,Integer> pos = buttons.get(bt);
                    logics.switchFlag(pos);
                }
                drawBoard(); 
            }
        };
                
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                final JButton jb = new JButton(" ");
                jb.addActionListener(onClick);
                jb.addMouseListener(onRightClick);
                this.buttons.put(jb,new Pair<>(i,j));
                panel.add(jb);
            }
        }
        this.drawBoard();
        this.setVisible(true);
    }
    
    private void quitGame() {
        this.drawBoard();
    	for (var entry: this.buttons.entrySet()) {
    	    Pair<Integer,Integer> pos = entry.getValue();
    	    if (this.logics.isBomb(pos)) {
                entry.getKey().setText("*");
            }
    	    entry.getKey().setEnabled(false);
            // call the logic here
            // if this button is a mine, draw it "*"
            // disable the button
    	}
    }

    private void drawBoard() {
        for (var entry: this.buttons.entrySet()) {
            Pair<Integer,Integer> pos = entry.getValue();
            if (this.logics.isExplored(pos)) {
                entry.getKey().setEnabled(false);
                int nearBombs = this.logics.getNearBombCount(pos);
                if (nearBombs == 0) {
                    entry.getKey().setText(" ");
                } else {
                    entry.getKey().setText(String.valueOf(nearBombs));
                }
            } else if (this.logics.isFlagged(pos)) {
                entry.getKey().setText("F");
            } else {
                entry.getKey().setText(" ");
            }
    	}
    }
    
}
