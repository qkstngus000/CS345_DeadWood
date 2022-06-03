/*

   Deadwood GUI helper file
   Author: Moushumi Sharmin
   This file shows how to create a simple GUI using Java Swing and Awt Library
   Classes Used: JFrame, JLabel, JButton, JLayeredPane

*/

import java.awt.*;
import javax.swing.*;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;

public class View extends JFrame {

   // JLabels
   JLabel boardlabel;
   JLabel cardlabel;
   JLabel playerlabel;
   JLabel mLabel;
   JLabel shotlabel;
   HashMap<Drawable,JLabel> gameLabels;
   
   //JButtons
   JButton[] buttons;
   private int buttonPressedIndex = -1;
   
   // JLayered Pane
   JLayeredPane bPane;
   
   // Info
   private String playerName;
   private int playerRank;
   private int playerFunds;
   private int playerCredits;
   private String playerRoom;
   private int playerTokens;

   // Constructor
   public View() {
      
      // Set the title of the JFrame
      super("Deadwood");
      // Set the exit option for the JFrame
      setDefaultCloseOperation(EXIT_ON_CLOSE);
   
      // Create the JLayeredPane to hold the display, cards, dice and buttons
      bPane = getLayeredPane();
   
      // Create the deadwood board
      boardlabel = new JLabel();
      ImageIcon icon =  new ImageIcon("../images/board.jpg");
      boardlabel.setIcon(icon); 
      boardlabel.setBounds(0,0,icon.getIconWidth(),icon.getIconHeight());
      
      // Add the board to the lowest layer
      bPane.add(boardlabel, Integer.valueOf(0));
      
      // Initialize the game element hashmap
      gameLabels = new HashMap<Drawable,JLabel>();

      // Set the size of the GUI
      setSize(icon.getIconWidth()+200,icon.getIconHeight());

      // Create the Menu for action buttons
      mLabel = new JLabel();
      mLabel.setVerticalAlignment(SwingConstants.TOP);
      mLabel.setBounds(icon.getIconWidth()+10,0,160,200);
      bPane.add(mLabel,Integer.valueOf(2));

      // Create Action buttons
      buttons = new JButton[7];
      for(int i = 0; i < buttons.length; i++)
      {
         buttons[i] = new JButton();
         buttons[i].setBackground(Color.white);
         buttons[i].setBounds(icon.getIconWidth()+10,210+40*i,160,30);
         buttons[i].addMouseListener(new boardMouseListener());
         buttons[i].setVisible(false);
         bPane.add(buttons[i],Integer.valueOf(2));
      }

      this.setVisible(true);
   }

   /**
    * updates the text above the button menu. Must be called by whenever info is changed in order to see the change.
    */
   public void updateInfo()
   {
      String tokenInfo = playerTokens < 0 ? "" : String.format("<p>rehearsal bonus: +%d</p>",playerTokens);
      mLabel.setText(String.format("<html><body><h3>%s'S TURN!</h3><p>rank: %d</p><p>funds: %d</p><p>credits: %d</p><h4>Room: %s</h4>%s</body></html>",
                  playerName.toUpperCase(),playerRank,playerFunds,playerCredits,playerRoom,tokenInfo));
      // mLabel.setText(String.format("<html><body><h3>%s'S TURN!</h3><p>rank: %d</p><p>funds: %d</p><p>credits: %d</p><h4>Room: %s</h4></body></html>",
      //             playerName.toUpperCase(),playerRank,playerFunds,playerCredits,playerRoom));
   }

   public void setPlayerName(String playerName)
   {
      this.playerName = playerName;
   }

   public void setPlayerRank(int playerRank)
   {
      this.playerRank = playerRank;
   }

   public void setPlayerFunds(int playerFunds)
   {
      this.playerFunds = playerFunds;
   }

   public void setPlayerCredits(int playerCredits)
   {
      this.playerCredits = playerCredits;
   }

   public void setPlayerRoom(String playerRoom)
   {
      this.playerRoom = playerRoom;
   }

   public void setPlayerTokens(int playerTokens)
   {
      this.playerTokens = playerTokens;
   }

   public void drawScene() {
      // Add a scene card to this room
      cardlabel = new JLabel();
      ImageIcon cIcon =  new ImageIcon("../images/card_back.png");
      cardlabel.setIcon(cIcon); 
      cardlabel.setBounds(20,65,cIcon.getIconWidth()+2,cIcon.getIconHeight());
      cardlabel.setOpaque(true);
   
      // Add the card to the lower layer
      bPane.add(cardlabel, Integer.valueOf(2));
   }
      
   /**
    * Draws an element to the board, creating a new JLabel for it if necessary.
    * @param <T> Type of object to draw
    * @param element The element to be drawn
    */
   public <T extends Drawable> void drawElement(T element)
   {
      System.out.println("Drawing Element: "+element);
      ImageIcon img = new ImageIcon(element.getImgPath());
      ObjCoord coord = element.getCoord();
      JLabel l = gameLabels.get((Drawable) element);
      if(l == null)
      {
         System.out.println("Creating Element: "+element);
         l = new JLabel();
         gameLabels.put((Drawable) element, l);
         bPane.add(l,Integer.valueOf(element.getDepth()));
      }
      l.setIcon(img);
      l.setBounds(coord.getX(),coord.getY(),coord.getH(),coord.getW());
      l.setVisible(true);
   }

   /**
    * Removes an element from the board.
    * @param <T> Type of element to remove
    * @param element Element to be removed
    */
   public <T extends Drawable> void removeElement(T element)
   {
      JLabel l = gameLabels.get((Drawable) element);
      if(l == null) return;
      bPane.remove(l);
      gameLabels.remove(element);
   }

   /**
    * Draws Player icons to the board.
    * @Deprecated
    * @param players list of players
    */
   public void drawPlayers(Player[] players) {
      // String path = "../images/dice/";
      // System.out.println("fullpath " + String.format("%s%s%d.png", path, players[i].getName(), players[i].getRank()));
      // Add a dice to represent a player. 
      // Role for Crusty the prospector. The x and y co-ordiantes are taken from Board.xml file
      // ImageIcon[] pIcon = new ImageIcon[players.length];
      for (int i = 0; i < players.length; i++) {
         System.out.println(i);
         playerlabel = new JLabel();
          
         ImageIcon pIcon = new ImageIcon(players[i].getImgPath());
         System.out.println("icon: "+pIcon);
         // ImageIcon pIcon = new ImageIcon(String.format("%s%s%d.png", path, players[i].getName(), players[i].getRank()));
         playerlabel.setIcon(pIcon);
         Room curRoom = players[i].getRoom();
         // curRoom.get
         int x_adj = i * pIcon.getIconWidth();
         playerlabel.setBounds(114 + x_adj,227,pIcon.getIconWidth(),pIcon.getIconHeight());  
         //  playerlabel.setBounds(114,227,46,46);

         playerlabel.setVisible(true);
         bPane.add(playerlabel,Integer.valueOf(3));
      }
      
   }

   /**
    * Updates the shot counters for a room in order to accurately reflect the number of shots left in that room.
    * @param r Room to update
    */
   public void drawShotCounters(SceneRoom r)
   {
      ShotCounter[] counters = r.getShotCounters();
      int curShot = r.getCurShot();
      for(int i = 0; i < counters.length; i++)
      {
         // Create the element if not already created
         if(gameLabels.get(counters[i]) == null)
         {
            drawElement(counters[i]);
         }

         // set visibility based on curShot
         JLabel l = gameLabels.get(counters[i]);
         if(i<curShot)
         {
            l.setVisible(false);
         }
         else l.setVisible(true);
      }
   }

   //Draw and update the token 
   /*public void drawShots(SceneRoom room) {
      String path = "../images/shot.png";
      ObjCoord[] shotCoord = room.getShotCoord();
      for (int i = 0; i < shotCoord.length; i++) {
         shotlabel = new JLabel();
         ImageIcon shotIcon = new ImageIcon(path);
         shotlabel.setBounds(shotCoord[i].getX(), shotCoord[i].getY(), shotIcon.getIconWidth(), shotIcon.getIconHeight());
         shotlabel.setVisible(true);
         System.out.println("X_cord: " + shotCoord[i].getX());
         bPane.add(shotlabel,Integer.valueOf(4));
      }
      
   }*/
       
   /*public void drawMenu() {
      // Create the Menu for action buttons
      mLabel = new JLabel("MENU");
      mLabel.setBounds(icon.getIconWidth()+40,0,100,20);
      bPane.add(mLabel,new Integer(2));

      // Create Action buttons
      bAct = new JButton("ACT");
      bAct.setBackground(Color.white);
      bAct.setBounds(icon.getIconWidth()+10, 30,100, 20);
      bAct.addMouseListener(new boardMouseListener());
      
      bRehearse = new JButton("REHEARSE");
      bRehearse.setBackground(Color.white);
      bRehearse.setBounds(icon.getIconWidth()+10,60,100, 20);
      bRehearse.addMouseListener(new boardMouseListener());
      
      bMove = new JButton("MOVE");
      bMove.setBackground(Color.white);
      bMove.setBounds(icon.getIconWidth()+10,90,100, 20);
      bMove.addMouseListener(new boardMouseListener());

      // Place the action buttons in the top layer
      bPane.add(bAct, new Integer(2));
      bPane.add(bRehearse, new Integer(2));
      bPane.add(bMove, new Integer(2));
   }*/

   public int askNumPlayer() {
      JFrame errorFrame = new JFrame("Error");
      errorFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      // Take input from the user about number of players
      int numPlayer = 0;
      boolean flag = false;
      while (!flag) {
         try {
            numPlayer = Integer.parseInt(JOptionPane.showInputDialog(this, "How many players?"));

            if (numPlayer > 8 || numPlayer < 2) {
               JOptionPane.showMessageDialog(errorFrame, "Please enter a valid number of players!", "Number of Player Error", JOptionPane.ERROR_MESSAGE);
            }
            else {
               flag = true;
            }
         }
         catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(errorFrame, "Input given is not a number. Please enter a valid number of players!", "Input Type Error", JOptionPane.ERROR_MESSAGE);
         }
      }
      return numPlayer;
   }


   /**
    * Displays a list of option buttons on the side menu. Can only display up to 7 buttons (the max that should be necessary)
    * @param buttonNames The text on each button. length also determines number of buttons.
    */
   public void showButtonMenu(ArrayList<String> buttonNames)
   {
      buttonPressedIndex = -1;
      int len = buttonNames.size();
      for(int i = 0; i < buttons.length; i++)
      {
         if(i < len)
         {
            buttons[i].setVisible(true);
            buttons[i].setText(buttonNames.get(i));
         }
         else buttons[i].setVisible(false);
      }
   }

   /**
    * Used to get the index of the button pressed after {@link #showButtonMenu} was called to display them.
    * @return index of button pressed, or -1 if a button has not yet been pressed.
    */
   public int getButtonPressed()
   {
      return buttonPressedIndex;
   }
  
  // This class implements Mouse Events
  
   class boardMouseListener implements MouseListener{
  
      // Code for the different button clicks
      public void mouseClicked(MouseEvent e) {
         
         for(int i = 0; i < buttons.length; i++)
         {
            if(e.getSource() == buttons[i])
            {
               // Set buttonPressedIndex and hide buttons
               buttonPressedIndex = i;
               for(JButton button : buttons)
               {
                  button.setVisible(false);
               }
               System.out.printf("Button #%d was pressed!\n",i);
               return;
            }
         }        
      }
      public void mousePressed(MouseEvent e) {
      }
      public void mouseReleased(MouseEvent e) {
      }
      public void mouseEntered(MouseEvent e) {
      }
      public void mouseExited(MouseEvent e) {
      }
   }

   
   /*public static void main(String[] args) {
  
      Vuew board = new View();
      board.setVisible(true);


      JFrame errorFrame = new JFrame("Error");
      errorFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      // Take input from the user about number of players
      int numPlayer = 0;
      boolean flag = false;
      while (!flag) {
         try {
            numPlayer = Integer.parseInt(JOptionPane.showInputDialog(board, "How many players?"));

            if (numPlayer > 8 || numPlayer < 2) {
               JOptionPane.showMessageDialog(errorFrame, "Please enter a valid number of players!", "Number of Player Error", JOptionPane.ERROR_MESSAGE);
            }
            else {
               flag = true;
            }
         }
         catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(errorFrame, "Input given is not a number. Please enter a valid number of players!", "Input Type Error", JOptionPane.ERROR_MESSAGE);
         }
         
      }
    
   }*/
}