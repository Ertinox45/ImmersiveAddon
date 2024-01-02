package fr.dynamx.addons.immersive.common.helpers;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.goxr3plus.streamplayer.enums.Status;
import com.goxr3plus.streamplayer.stream.StreamPlayer;
import com.goxr3plus.streamplayer.stream.StreamPlayerEvent;
import com.goxr3plus.streamplayer.stream.StreamPlayerException;
import com.goxr3plus.streamplayer.stream.StreamPlayerListener;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RadioPlayer extends StreamPlayer implements StreamPlayerListener {

    public RadioPlayer() {
        try {
            addStreamPlayerListener(this);
        } catch (final Exception ex) {
            ex.printStackTrace();
        }
    }

    //service

    private static final ExecutorService SERVICE = Executors.newSingleThreadExecutor(new ThreadFactoryBuilder().setNameFormat("image-cache-downloader").build());

    public void playRadio(URL radioUrl)
    {
        if(!getStatus().equals(Status.PLAYING))
        {
            SERVICE.execute(() -> {
                try {

                    InputStream stream = radioUrl.openConnection().getInputStream();
                    InputStream bufferedIn = new BufferedInputStream(stream);
                    open(bufferedIn);
                    play();
                }
                catch (IOException | StreamPlayerException e)
                {
                    e.printStackTrace();
                }
            });
        }
        else {
            stop();
            playRadio(radioUrl);
        }
    }

    public void stopRadio()
    {
        try {
            stop();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void opened(final Object dataSource, final Map<String, Object> properties) {}

    @Override
    public void progress(final int nEncodedBytes, final long microsecondPosition, final byte[] pcmData,final Map<String, Object> properties) {
    }

    @Override
    public void statusUpdated(final StreamPlayerEvent streamPlayerEvent) {
        final Status status = streamPlayerEvent.getPlayerStatus();

        if (status == Status.OPENED) {

        } else if (status == Status.OPENING) {

        } else if (status == Status.RESUMED) {

        } else if (status == Status.PLAYING) {

        } else if (status == Status.STOPPED) {

        } else if (status == Status.SEEKING) {

        } else if (status == Status.SEEKED) {

        }
    }

}