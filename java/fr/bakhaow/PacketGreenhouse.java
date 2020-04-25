package fr.bakhaow;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import fr.bakhaow.block.tile.TileEntityAntenne;
import fr.bakhaow.block.tile.TileEntityGreenhouse;
import fr.bakhaow.init.ModBlocks;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class PacketGreenhouse implements IMessage {

	private boolean isMessageValid = false;
	private static int x, y, z, xs, zs, p, s, sc, sp, sn;

	public PacketGreenhouse() {
	}

	public PacketGreenhouse(int x, int y, int z, int xs, int zs, int p, int s, int sc, int sp, int sn) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.xs = xs;
		this.zs = zs;
		this.p = p;
		this.s = s;
		this.sc = sc;
		this.sp = sp;
		this.sn = sn;
		this.isMessageValid = true;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.x = buf.readInt();
		this.y = buf.readInt();
		this.z = buf.readInt();
		this.xs = buf.readInt();
		this.zs = buf.readInt();
		this.p = buf.readInt();
		this.s = buf.readInt();
		this.sc = buf.readInt();
		this.sp = buf.readInt();
		this.sn = buf.readInt();
		this.isMessageValid = true;
	}

	@Override
	public void toBytes(ByteBuf buf) {
		if (!this.isMessageValid)
			return;
		buf.writeInt(this.x);
		buf.writeInt(this.y);
		buf.writeInt(this.z);
		buf.writeInt(this.xs);
		buf.writeInt(this.zs);
		buf.writeInt(this.p);
		buf.writeInt(this.s);
		buf.writeInt(this.sc);
		buf.writeInt(this.sp);
		buf.writeInt(this.sn);
	}

	public static class PacketGreenhouseHandler implements IMessageHandler<PacketGreenhouse, IMessage> {

		@Override
		public IMessage onMessage(PacketGreenhouse message, MessageContext ctx)  {
			World w = ctx.getServerHandler().playerEntity.worldObj;
			EntityPlayerMP player = ctx.getServerHandler().playerEntity;
			TileEntityGreenhouse t = (TileEntityGreenhouse) w.getTileEntity(x, y, z);
			int xBis = x + 1;
			if(((s + sc + sp + sn) * 3) + y <= 255) {
				if(canAfford(w, x, y, z, p)) {
					if(canCreate(w, xBis, y, z, xs, zs, s, sc, sp, sn)) {
						player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_GREEN + "Un esclave arrive sur place!"));
						usePower(w, x, y, z, p);
						create(w, xBis, y, z, xs, zs, s, sc, sp, sn);
					} else {
						player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_RED + "QUOI!"));
						player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_RED + "... T'es aveugle?"));
						player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_RED + "Tu ne voit pas les blocs là ?"));
					}
				} else {
					player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_RED + "Manque d'énergie prenez un actimel! (ou une antenne)"));
				}
			} else {
				player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_RED + "Vous dépasseriez la hauteur maximale du jeu."));
				player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_RED + "On annule l'opération chef!"));
			}
			return null;
		}
		
		public boolean canCreate(World w, int x, int y, int z, int xs, int zs, int s, int sc, int sp, int sn) {
			int ys = (s + sc + sp + sn) * 3;
			for(int i = 0; i < xs; i++) {
				for(int j = 0; j < zs; j++) {
					for(int k = 0; k < ys; k++) {
						Block b = w.getBlock(x + i, y + k, z + j);
						if(b.equals(Blocks.air)) {
							continue;
						} else {
							return false;
						}
					}
				}
			}
			return true;
		}
		
		/*public void create(World w, int x, int y, int z, int xs, int zs, int s, int sc, int sp, int sn) {
			int yBis = y;
			for(int i = 0; i < s; i++) {
				for(int j = 0; j < xs; j++) {
					for(int k = 0; k < zs; k++) {
						w.setBlock(x + j, yBis, z + k, Blocks.farmland);
					}
				}
				yBis++;
				for(int j = 0; j < xs; j++) {
					for(int k = 0; k < zs; k++) {
						w.setBlock(x + j, yBis, z + k, Blocks.wheat);
					}
				}
				yBis += 2;
			}
			for(int i = 0; i < sc; i++) {
				for(int j = 0; j < xs; j++) {
					for(int k = 0; k < zs; k++) {
						w.setBlock(x + j, yBis, z + k, Blocks.farmland);
					}
				}
				yBis++;
				for(int j = 0; j < xs; j++) {
					for(int k = 0; k < zs; k++) {
						w.setBlock(x + j, yBis, z + k, Blocks.carrots);
					}
				}
				yBis += 2;
			}
			for(int i = 0; i < sp; i++) {
				for(int j = 0; j < xs; j++) {
					for(int k = 0; k < zs; k++) {
						w.setBlock(x + j, yBis, z + k, Blocks.farmland);
					}
				}
				yBis++;
				for(int j = 0; j < xs; j++) {
					for(int k = 0; k < zs; k++) {
						w.setBlock(x + j, yBis, z + k, Blocks.potatoes);
					}
				}
				yBis += 2;
			}
			for(int i = 0; i < sn; i++) {
				for(int j = 0; j < xs; j++) {
					for(int k = 0; k < zs; k++) {
						w.setBlock(x + j, yBis, z + k, Blocks.soul_sand);
					}
				}
				yBis++;
				for(int j = 0; j < xs; j++) {
					for(int k = 0; k < zs; k++) {
						w.setBlock(x + j, yBis, z + k, Blocks.nether_wart);
					}
				}
				yBis += 2;
			}
		}*/
		
		public void create(World w, int x, int y, int z, int xs, int zs, int s, int sc, int sp, int sn) {
			int yBis = y;
			yBis = createStages(w, x, yBis, z, xs, zs, s, Blocks.farmland, Blocks.wheat);
			createStages(w, x, yBis, z, xs, zs, s, Blocks.farmland, Blocks.wheat);
			yBis = createStages(w, x, yBis, z, xs, zs, s, Blocks.farmland, Blocks.carrots);
			createStages(w, x, yBis, z, xs, zs, s, Blocks.farmland, Blocks.carrots);
			yBis = createStages(w, x, yBis, z, xs, zs, s, Blocks.farmland, Blocks.potatoes);
			createStages(w, x, yBis, z, xs, zs, s, Blocks.farmland, Blocks.potatoes);
			yBis = createStages(w, x, yBis, z, xs, zs, s, Blocks.soul_sand, Blocks.nether_wart);
			createStages(w, x, yBis, z, xs, zs, s, Blocks.soul_sand, Blocks.nether_wart);
		}
		
		public int createStages(World w, int x, int y, int z, int xs, int zs, int stages, Block a, Block b) {
			for(int i = 0; i < s; i++) {
				for(int j = 0; j < xs; j++) {
					for(int k = 0; k < zs; k++) {
						w.setBlock(x + j, y, z + k, a);
					}
				}
				y++;
				for(int j = 0; j < xs; j++) {
					for(int k = 0; k < zs; k++) {
						w.setBlock(x + j, y, z + k, b);
					}
				}
				y += 2;
			}
			return y;
		}
		
		public boolean canAfford(World w, int x, int y, int z, int p) {
			for(int i = -16; i < 16; i++) {
				for(int j = -16; j < 16; j++) {
					for(int k = 0; k < 255; k++) {
						Block b = w.getBlock(x + i, y + k, z + j);
						if(b.equals(ModBlocks.antenne)) {
							TileEntityAntenne t = (TileEntityAntenne) w.getTileEntity(x + i, y + k, z + j);
							if(!(i > t.radius|| j > t.radius)) {
								if(t.energy >= p) {
									return true;
								}
							}
						}
					}
				}
			}
			System.out.println("cant afford");
			return false;
		}
		
		public void usePower(World w, int x, int y, int z, int p) {
			for(int i = -16; i < 16; i++) {
				for(int j = -16; j < 16; j++) {
					for(int k = 0; k < 255; k++) {
						Block b = w.getBlock(x + i, y + k, z + j);
						if(b.equals(ModBlocks.antenne)) {
							TileEntityAntenne t = (TileEntityAntenne) w.getTileEntity(x + i, y + k, z + j);
							if(!(i > t.radius|| j > t.radius)) {
								t.energy -= p;
							}
						}
					}
				}
			}
		}
	}
}