/*
 * Code excerpt showcasing the GUI interface for navigating a game
 * board and placing a domino. Makes use of various action listeners
 * and events.
 * @author mjhancock, malmutoory
 */

//Fill with GUI settings for kingdom spaces as needed
private void setKingdomButton(){

    //Checks if the mouse is hovering over the button
    addMouseListener(new java.awt.event.MouseAdapter(){

        //If hovering, fill with player's tile/domino to place
        public void mouseEntered(java.awt.event.MouseEvent evt) {
                            
            //Check which part of their turn the player is on
            if(game.getGameState() == 0){
            
                replacedTile = getIcon();

                //Check for starting round
                if(game.getGameStage() == 0){

                    startingTile = (players.get(game.getCurPlayerNum())).getStartingTile();
                    Image temp = startingTile.getSprite().getImage();
                    Image resizedImage = temp.getScaledInstance(110, 110,  java.awt.Image.SCALE_SMOOTH);
                    setIcon(new ImageIcon(resizedImage));
                    displayGameBoard.updateDisplayBoard();
                }

                //Regular round
                else if(game.getGameStage() > 0){

                    domino = players.get(game.getCurPlayerNum()).getHeldDomino();
                    tiles = domino.getTiles();

                    setHoverImage(tiles[0]);
                    if(gameBoard.checkAdjacentSpace(x, y)){
                        gameBoard.hoverAdjacentSpace(tiles[1], x, y);
                    }
                    displayGameBoard.updateDisplayBoard();
                }
            }
        }
        
        //Remove hover image
        public void mouseExited(java.awt.event.MouseEvent evt) {
                            
            //Check which part of their turn the player is on
            if(game.getGameState() == 0){

                    clearHoverImage();

                    if(game.getGameStage() > 0){
                        if(gameBoard.checkAdjacentSpace(x, y)){
                            gameBoard.clearAdjacentSpace(x, y);
                        }
                    }
                displayGameBoard.updateDisplayBoard();
            }
        }

        public void mouseClicked(MouseEvent evt){
                            
            if(game.getGameState() <= 0){

                //Left click = place tile/domino
                if(SwingUtilities.isLeftMouseButton(evt)){

                    if(game.getGameStage() > 0){

                        //Checks if the placement properly connects to a matching tile
                        if(!gameLogic.validatePlacement(gameBoard, domino, x, y)){
                            return;
                        }
                    }

                    //Generate dialog box to ask user if they are sure of their selection
                    int result = JOptionPane.showConfirmDialog(null,"Are you sure you want to place your piece here?", "Confirm Placement", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                                    
                    //If yes is clicked
                    if (result == JOptionPane.YES_OPTION){

                        //Check for starting round
                        if(game.getGameStage() == 0){
                            fillKingdomButton(players.get(game.getCurPlayerNum()).getStartingTile());
                        }
                        else{
                            domino = players.get(game.getCurPlayerNum()).getHeldDomino();
                            fillKingdomButton(domino);
                        }
                        
                        //Updates miniboard
                        curPlayer = game.getCurPlayer()
                        curPlayer.updateMiniBoard().getBoard();

                        //Checks for last turn
                        if(game.getGameStage() < 2){

                            //Allow player to select a domino from the column
                            game.setGameState(1);
                            game.updateActionPrompt();
                        }
                        else{

                            //Pass control to next player
                            game.nextPlayer();
                            game.nextTurn();
                        }
                    }
                }

                //Right click == rotate domino
                else if(SwingUtilities.isRightMouseButton(evt)){
                    
                    if(!filled){

                        Image temp = new ImageIcon(this.getClass().getResource("/Images/startTile.png")).getImage(); 
                        Image resizedImage = temp.getScaledInstance(110, 110,  java.awt.Image.SCALE_SMOOTH); 
                        setIcon(new ImageIcon(resizedImage));

                        if(game.getGameStage() > 0){
                            if(gameBoard.checkAdjacentSpace(x, y)){
                                gameBoard.clearAdjacentSpace(x, y);
                            }
                        }
                    }
                    gameBoard.setRotateMod();
                    displayGameBoard.updateDisplayBoard();
                }
            }
            
        }
    });
}