package entity;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity{
    GamePanel gp;
    KeyHandler keyH;
    public final int screenX;
    public final int screenY;
    public int hasKey = 0;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);
        setDefaultValues();
        getPlayerImage();
        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;

    }
    public void setDefaultValues() {
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 4;
        direction = "down";
    }
    public void getPlayerImage() {
        up1 = setup("boy_up_1");
        up2 = setup("boy_up_2");
        down1 = setup("boy_down_1");
        down2 = setup("boy_down_2");
        left1 = setup("boy_left_1");
        left2 = setup("boy_left_2");
        right1 = setup("boy_right_1");
        right1 = setup("boy_right_2");
    }
    public BufferedImage setup(String imageName) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image;

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/player/"+ imageName + ".png"));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return image;
    }
    public void update() {
        if(keyH.upPressed || keyH.downPressed || keyH.leftPressed  || keyH.rightPressed  ) {
            spriteCounter++;
            if(spriteCounter > 13) {
                if(spriteNum == 1) {
                    spriteNum = 2;
                }
                else if(spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }
        if(keyH.upPressed) {
            direction = "up";

        }
        else if(keyH.downPressed) {
            direction = "down";

        }
        else if(keyH.leftPressed) {
            direction = "left";

        }
        else if(keyH.rightPressed) {
            direction = "right";

        }
        //Check tile collision
        collisionOn = false;
        gp.cChecker.checkTile(this);

        //Check object collision
        int objIndex = gp.cChecker.checkObject(this, true);
        pickUpObject(objIndex);

        // if collision false player can move
        if(!collisionOn) {
            switch(direction) {
                case "up":
                    if(keyH.upPressed) { worldY -= speed; }
                    break;
                case "down":
                    if(keyH.downPressed) {worldY += speed; }
                    break;
                case "left":
                    if(keyH.leftPressed) {worldX -= speed;}
                    break;
                case "right":
                    if(keyH.rightPressed) {worldX += speed;}
                    break;

            }
        }
    }
    public void pickUpObject(int i) {
        if(i != 999) {
            String objectName = gp.obj[i].name;
            switch(objectName) {
                case "Key":
                    hasKey++;
                    gp.playSE(1);
                    gp.obj[i] = null;
                    gp.ui.showMessage("Got key!");
                    break;
                case "Door":
                    if(hasKey > 0) {
                        gp.playSE(2);
                        gp.obj[i] = null;
                        hasKey--;
                    }
                    else {
                        gp.ui.showMessage("Get a key!");
                    }
                    break;
                case "Boots":
                    speed += 2;
                    gp.obj[i] = null;
                    gp.playSE(4);
                    gp.ui.showMessage("Speed up!");
                    break;
                case "Chest":
                    gp.ui.gameFinished = true;
                    gp.stopMusic();
                    gp.playSE(3);
                    break;
            }
        }
    }
    public void draw(Graphics2D g2){
        BufferedImage image = null;

        switch(direction) {
            case "up":
                if(spriteNum == 1) {
                    image = up1;
                }
                if(spriteNum == 2) {
                    image = up2;
                }
                break;
            case "down":
                if (spriteNum == 1) {
                    image = down1;
                }
                if(spriteNum == 2) {
                    image = down2;
                }
                break;
            case "left":
                if(spriteNum == 1) {
                    image = left1;
                }
                if(spriteNum == 2) {
                    image = left2;
                }
                break;
            case "right":
                if(spriteNum == 1) {
                    image = right1;
                }
                if(spriteNum == 2) {
                    image = right2;
                }
                break;
        }
        g2.drawImage(image, screenX, screenY, null);
    }

}
