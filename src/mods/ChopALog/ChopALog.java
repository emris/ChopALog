package mods.ChopALog;

import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(modid="ChopALog", name="ChopALog", version="0.2.b76", dependencies = "after:TerraFirmaCraft")
@NetworkMod(clientSideRequired = true, serverSideRequired = true, versionBounds = "[0.2.b76]")
public class ChopALog {
	@Instance("ChopALog")
	public static ChopALog instance;
	
	@PreInit
	public void preInit(FMLPreInitializationEvent event) {	}
	
	@Init
	public void load(FMLInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(new AxeHandler());
	}
	
}
