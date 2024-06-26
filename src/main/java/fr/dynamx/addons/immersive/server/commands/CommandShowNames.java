package fr.dynamx.addons.immersive.server.commands;

import fr.dynamx.addons.immersive.common.network.ImmersiveAddonPacketHandler;
import fr.dynamx.addons.immersive.common.network.packets.PacketShowNames;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.server.permission.PermissionAPI;

public class CommandShowNames extends CommandBase {

    public String getName() {
        return "shownames";
    }

    @Override
    public String getUsage(ICommandSender sender) {

        return "/shownames";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (sender instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) sender;
            player.sendMessage(new TextComponentString(TextFormatting.GREEN + "L'affichage des noms est modifié"));
            ImmersiveAddonPacketHandler.getInstance().getNetwork().sendTo(new PacketShowNames(), (EntityPlayerMP) player);
        }
    }
}