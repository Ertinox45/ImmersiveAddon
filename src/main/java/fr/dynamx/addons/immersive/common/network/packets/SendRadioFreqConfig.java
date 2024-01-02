package fr.dynamx.addons.immersive.common.network.packets;

import fr.dynamx.addons.immersive.common.helpers.ConfigReader;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.io.IOException;

public class SendRadioFreqConfig implements IMessage {

    private String json;

    public SendRadioFreqConfig() {
    }

    public SendRadioFreqConfig(String json) {
        this.json = json;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        json = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, json);
    }

    public static class Handler implements IMessageHandler<SendRadioFreqConfig, IMessage> {

        @Override
        public IMessage onMessage(SendRadioFreqConfig message, MessageContext ctx) {
            try
            {
                ConfigReader.writeToFileString(message.json);
                ConfigReader.readFromFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}