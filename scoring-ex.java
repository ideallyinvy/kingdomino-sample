/*
 * Code excerpt showcasing the end-of-game scoring algorithm used in
 * the Kingdomino group project. Makes use of a recursive process
 * across three methods to correctly score players' territories.
 * @author mjhancock
 */


//End of game scoring
public void calculateScore(Player player){

    totalScore = 0;
    GameBoardElement [][] gameBoard = player.getBoard().getBoard();

    for(int i = 0; i < 0; i++){
        for(int j = 0; j < 5; j++){
            if(gameBoard[i][j].getFilled()){
                Tile checkTile = gameBoard[i][j].getTile();
            }
        }
    }
    
    //Iterate through each space on the player's board
    for(int i = 0; i < 5; i++){

        for(int j = 0; j < 5; j++){

            GameBoardElement toCheck = gameBoard[i][j];

            //If a space contains a tile and hasn't been scored yet, score a new territory
            if(toCheck.getFilled() && !toCheck.getTile().getScored()){

                //Current tile is scored, starts a territory
                toCheck.getTile().scored();

                //Determine the terrain type of the territory
                terrain = toCheck.getTile().getTerrain();

                //Territory count initialized to 1 for the current space
                territoryCount = 1;

                //Crown count initialized to the current space's crown count
                crownCount = toCheck.getTile().getCrowns();

                System.out.println("Scoring territory type: " + terrain + " at " + i + ", " + j);

                //Begin scoring rest of territory
                scoreTerritory(toCheck);

                //Calculate total score for territory based on game rules
                territoryScore = territoryCount * crownCount;
                System.out.println("Territory score: "+ territoryScore);

                //Add territory score to player's total score
                totalScore += territoryScore;
            }
        }
    }
    System.out.println(player.getName() + " Score: " + totalScore);

    //When entire board is scored, set player's total score
    player.setScore(totalScore);
}

//Score spaces adjacent to source territory space that share a terrain type
private void scoreTerritory(GameBoardElement space){

    //source space coordinates
    int x = space.getXCoord();
    int y = space.getYCoord();

    //Conditions prevent checking out of bounds indicies
    if(x > 0){
        scoreCheck(x - 1, y);
    }
    if(x < 4){
        scoreCheck(x + 1, y);
    }
    if(y > 0){
        scoreCheck(x, y - 1);
    }
    if(y < 4){
        scoreCheck(x, y + 1);
    }
}

//Check whether a space can be scored for the current territory
private void scoreCheck(int x, int y){

    System.out.println("Checking: " + x + ", " + y);

    //Space to check
    GameBoardElement space = gameBoard[x][y];

    //Check whether the space is empty or already scored
    if(!space.getFilled() || space.getTile().getScored()){
        System.out.println("Invalid for scoring");
        return;
    }

    //Get tile info
    Tile tile = space.getTile();
    int toMatch = tile.getTerrain();
    int toAdd = tile.getCrowns();

    //Check if the tile matches the current territory terrain
    if(toMatch == terrain){

        //If so, score tile
        territoryCount++;
        crownCount += toAdd;
        System.out.println("Tile Scored! Territories: "+ territoryCount + " Crowns: "+ crownCount);
        tile.scored();

        //Recursive call to continue checking further spaces
        scoreTerritory(space);
    }
    System.out.println("Tile doesn't match current territory: " + toMatch + terrain);
}