package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;

import main.GamePanel;

public class TileManager {
	
	
	GamePanel gp;
	Tile[] tile;
	public static final String filePath = "/maps/world01.txt";
	int mapTileNum[][];
	
	public TileManager(GamePanel gp) {
		
		this.gp = gp;
		
		tile = new Tile[10];
		mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
		
		getTileImage();
		
		loadMap(filePath);
	}
	
	public void getTileImage() {
		
		try {
			
			tile[0] = new Tile();
			tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass00.png"));
			
			tile[1] = new Tile();
			tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall.png"));
			
			tile[2] = new Tile();
			tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water00.png"));
			
			tile[3] = new Tile();
			tile[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/earth.png"));
			
			tile[4] = new Tile();
			tile[4].image = ImageIO.read(getClass().getResourceAsStream("/tiles/tree.png"));
			
			tile[5] = new Tile();
			tile[5].image = ImageIO.read(getClass().getResourceAsStream("/tiles/sand.png"));
			
			
		}catch(IOException e){
			
			e.printStackTrace();
		}
		
	}
	
	public void loadMap(String filePath) {

        int row = 0;
        int col = 0;

        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            String line;
           
            while ((line = br.readLine()) != null && row < gp.maxWorldRow) {

                String numbers[] = line.split(" ");
                col = 0;

                while(col < gp.maxWorldCol && col < numbers.length) {
                    int num = Integer.parseInt(numbers[col]);
                    System.out.println(num);

                    mapTileNum[col][row] = num;
                    col++;
                }

                if(col == gp.maxWorldCol) {
                    row++;
                }

            }
            
            br.close();
            
        } catch (Exception e) {
            System.out.println("BIG BUG OVER HERE: " + e);
        }

    }

	
	public void draw(Graphics2D g2) {
		
		int worldCol = 0;
		int worldRow = 0;

		
		while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
			
			int tileNum = mapTileNum[worldCol][worldRow];
			
			int worldX = worldCol * gp.tileSize; 
			int worldY = worldRow * gp.tileSize;
			
			//Definisce le coordinate delle world tile in base alla posizione del player, che è sempre in mezzo allo screen
			int screenX = worldX - gp.player.worldX + gp.player.screenX;
			int screenY = worldY - gp.player.worldY + gp.player.screenY;
			
			//per evitare di renderizzare le tiles troppo lontane dal player - NON FUNZIONA somehow
			if(worldX  > gp.player.worldX - gp.player.screenX && 
			   worldX  < gp.player.worldX + gp.player.screenX && 
			   worldY  > gp.player.worldY - gp.player.screenY && 
			   worldY  > gp.player.worldY + gp.player.screenY) {
			
				
			};
			
			g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
		
			
			worldCol++;	
			
			if(worldCol == gp.maxWorldCol) {
				worldCol = 0;
				worldRow++;
			}
		}

	}
}
