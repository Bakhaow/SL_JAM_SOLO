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
	private static int x, y, z, xs, zs, p, s;

	public PacketGreenhouse() {
	}

	public PacketGreenhouse(int x, int y, int z, int xs, int zs, int p, int s) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.xs = xs;
		this.zs = zs;
		this.p = p;
		this.s = s;
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
	}

	public static class PacketGreenhouseHandler implements IMessageHandler<PacketGreenhouse, IMessage> {

		@Override
		public IMessage onMessage(PacketGreenhouse message, MessageContext ctx)  {
			World w = ctx.getServerHandler().playerEntity.worldObj;
			EntityPlayerMP player = ctx.getServerHandler().playerEntity;
			TileEntityGreenhouse t = (TileEntityGreenhouse) w.getTileEntity(x, y, z);
			int xBis = x + 1;
			if(canAfford(w, x, y, z, p)) {
				if(canCreate(w, xBis, y, z, xs, zs, s)) {
					player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_GREEN + "Construction de la serre"));
					usePower(w, x, y, z, p);
					create(w, xBis, y, z, xs, zs, s);
				} else {
					player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_RED + "Serre impossible à créer, des blocs sont déja présents."));
				}
			} else {
				player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_RED + "Vous n'avez pas assez d'énergie disponible, créez une antenne !"));
			}
			return null;
		}
		
		public boolean canCreate(World w, int x, int y, int z, int xs, int zs, int s) {
			int ys = s * 3;
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
		
		public void create(World w, int x, int y, int z, int xs, int zs, int s) {
			int yBis = y;
			for(int i = 0; i < s; i++) {
				for(int j = 0; j < xs; j++) {
					for(int k = 0; k < zs; k++) {
						w.setBlock(x + j, yBis, z + k, Blocks.farmland);
					}
				} System.out.println(i + " stage | 1: added GRASS");
				yBis++;
				for(int j = 0; j < xs; j++) {
					for(int k = 0; k < zs; k++) {
						w.setBlock(x + j, yBis, z + k, Blocks.wheat);
					}
				} System.out.println(i + " stage | 2: adding WHEAT");
				yBis += 2;
			}
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