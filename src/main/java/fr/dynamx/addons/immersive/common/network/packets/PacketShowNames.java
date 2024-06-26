package fr.dynamx.addons.immersive.common.network.packets;

import fr.dynamx.addons.immersive.client.ClientEventHandler;
import fr.dynamx.addons.immersive.common.helpers.ConfigReader;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.IOException;

public class PacketShowNames implements IMessage {


    public PacketShowNames() {
    }

    @Override
    public void fromBytes(ByteBuf buf) {
    }

    @Override
    public void toBytes(ByteBuf buf) {
    }

    public static class Handler implements IMessageHandler<PacketShowNames, IMessage> {

        @SideOnly(Side.CLIENT)
        @Override
        public IMessage onMessage(PacketShowNames message, MessageContext ctx) {
            ClientEventHandler.showNames = !ClientEventHandler.showNames;
            return null;
        }
    }
}