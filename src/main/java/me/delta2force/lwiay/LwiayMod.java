package me.delta2force.lwiay;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

import me.delta2force.lwiay.colors.ColorCalculator;
import me.delta2force.lwiay.items.LwiayItems;
import me.delta2force.lwiay.reddit.LwiayReddit;
import me.delta2force.lwiay.reddit.structure.Post;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FilledMapItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.map.MapState;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class LwiayMod implements ModInitializer{
	public static final ItemGroup LWIAY_GROUP = FabricItemGroupBuilder.build(new Identifier("lwiay", "creative_tab"), () -> new ItemStack(LwiayItems.LETTER_L));
	public static LwiayReddit LWIAY_REDDIT;
	public static boolean usingReddit;
	public static int postIndex;
	public static ItemFrameEntity[][] frames;
	public static World theWorld;
	public static PlayerEntity thePlayer;
	
	@Override
	public void onInitialize() {
		LWIAY_REDDIT = new LwiayReddit();
		
		LwiayItems.initialize();
		LwiayItems.registerItemsInMinecraft();
	}
	
	public static void update() {
		if(postIndex >= 27) {
			thePlayer.inventory.removeOne(new ItemStack(LwiayItems.LWIAY_REMOTE));
			thePlayer.sendMessage(new LiteralText(colorCode("c") + "That's everything I can pull from the reddit servers. Sorry, but this is where I have to end."));
			for(int x = 2;x>-1;x--) {
				for(int y = 2;y>-1;y--) {
					theWorld.setBlockState(frames[x][y].getBlockPos().add(0, 0, -1), Blocks.AIR.getDefaultState());
					frames[x][y].kill();
				}
			}
			thePlayer=null;
			theWorld=null;
			frames=null;
			postIndex=-1;
			usingReddit=false;
			return;
		}
		if(LWIAY_REDDIT.getPost(postIndex).post_hint.equals("image")) {
			Post post = LWIAY_REDDIT.getPost(postIndex);
			BufferedImage timage = null;
			try {
				timage = ImageIO.read(new URL(post.url));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			BufferedImage image = new BufferedImage(384, 384, BufferedImage.TYPE_INT_ARGB);
			image.createGraphics().drawImage(timage, 0, 0, 384, 384, null);
			BufferedImage[][] split = splitImage(image, 3, 3);
			for(int x = 2;x>-1;x--) {
				for(int y = 2;y>-1;y--) {
					int splity = y;
					if(splity == 0) {
						splity = 2;
					}else if(splity == 2) {
						splity = 0;
					}
					ItemStack is = createTexturedMap(split[splity][x], theWorld);
					frames[x][y].setHeldItemStack(is);
				}
			}
			thePlayer.sendMessage(new LiteralText(colorCode("6") + post.title));
			thePlayer.sendMessage(new LiteralText(" "));
			thePlayer.sendMessage(new LiteralText(colorCode("9") + "u/" + post.author + " | " + post.ups + " upvotes"));
			thePlayer.sendMessage(new LiteralText(" "));
		}else {
			postIndex++;
			update();
		}
	}
	
	/**
	 * @author ExcuseMi
	 */
	public static BufferedImage[][] splitImage(BufferedImage image, int rows, int cols) {
        BufferedImage[][] imgs = new BufferedImage[rows][cols]; //Image array to hold image chunks
        for (int col = 0; col < cols; col++) {
            for (int row = 0; row < rows; row++) {
                imgs[row][col] = new BufferedImage(128, 128, image.getType());

                Graphics2D gr = imgs[row][col].createGraphics();
                gr.drawImage(image, 0, 0,
                		128,
                		128,
                		128 * col,
                		128 * row,
                		128 * (col + 1),
                		128 * (row + 1),
                        null);
                gr.dispose();
            }
        }
        return imgs;
    }
	
	
	public static ItemStack createTexturedMap(BufferedImage tex, World w) {
		BufferedImage img = new BufferedImage(128, 128, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = img.createGraphics();
		g2d.setColor(Color.black);
		g2d.fillRect(0, 0, 128, 128);
		g2d.drawImage(tex, 0, 0, 128, 128, null);
		ItemStack newMap = FilledMapItem.createMap(w, -9999999, -9999999, (byte) 0, true, false);
		MapState ms = FilledMapItem.getMapState(newMap, w);
		ColorCalculator cc = new ColorCalculator();
		for(int x = 0;x<128;x++) {
			for(int y = 0;y<128;y++) {
				ms.colors[x+y*128] = cc.getNearestColor(img.getRGB(x, y));
			}
		}
		return newMap;
	}
	
	public static String colorCode(String color) {
        return (char) (0xfeff00a7) + color;
    }
}
