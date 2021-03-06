package com.massivecraft.vampire.altar;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.massivecraft.mcore3.util.MUtil;
import com.massivecraft.vampire.Conf;
import com.massivecraft.vampire.Lang;
import com.massivecraft.vampire.Permission;
import com.massivecraft.vampire.VPlayer;
import com.massivecraft.vampire.util.ResourceUtil;

public class AltarLight extends Altar
{
	public AltarLight()
	{
		this.name = Lang.altarLightName;
		this.desc = Lang.altarLightDesc;
		
		this.coreMaterial = Material.LAPIS_BLOCK;
		
		this.materialCounts = new HashMap<Material, Integer>();
		this.materialCounts.put(Material.GLOWSTONE, 30);
		this.materialCounts.put(Material.YELLOW_FLOWER, 5);
		this.materialCounts.put(Material.RED_ROSE, 5);
		this.materialCounts.put(Material.DIAMOND_BLOCK, 2);
		
		this.resources = MUtil.list(
			new ItemStack(Material.WATER_BUCKET, 1),
			new ItemStack(Material.DIAMOND, 1),
			new ItemStack(Material.SUGAR, 20),
			new ItemStack(Material.WHEAT, 20)
		);
	}
	
	@Override
	public void use(VPlayer vplayer, Player player)
	{
		vplayer.msg("");
		vplayer.msg(this.desc);
		
		if ( ! Permission.ALTAR_LIGHT.has(player, true)) return;
		
		if ( ! vplayer.vampire() && playerHoldsWaterBottle(player))
		{
			if ( ! ResourceUtil.playerRemoveAttempt(player, Conf.holyWaterResources, Lang.altarLightWaterResourceSuccess, Lang.altarLightWaterResourceFail)) return;
			ResourceUtil.playerAdd(player, new ItemStack(Material.POTION, 1, Conf.holyWaterPotionValue));
			vplayer.msg(Lang.altarLightWaterResult);
			vplayer.fxEnderBurstRun();
			return;
		}
		
		vplayer.msg(Lang.altarLightCommon);
		vplayer.fxEnderRun();
		
		if (vplayer.vampire())
		{
			if ( ! ResourceUtil.playerRemoveAttempt(player, this.resources, Lang.altarResourceSuccess, Lang.altarResourceFail)) return;
			vplayer.msg(Lang.altarLightVampire);
			player.getWorld().strikeLightningEffect(player.getLocation().add(0, 3, 0));
			vplayer.fxEnderBurstRun();
			vplayer.vampire(false);
			return;
		}
		else if (vplayer.healthy())
		{
			vplayer.msg(Lang.altarLightHealthy);
		}
		else if (vplayer.infected())
		{
			vplayer.msg(Lang.altarLightInfected);
			vplayer.infection(0);
			vplayer.fxEnderBurstRun();
		}
	}
	
	protected static boolean playerHoldsWaterBottle(Player player)
	{
		if (player.getItemInHand().getType() != Material.POTION) return false;
		return player.getItemInHand().getDurability() == 0;
	}
}
