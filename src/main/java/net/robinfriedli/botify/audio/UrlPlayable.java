package net.robinfriedli.botify.audio;

import java.util.concurrent.TimeUnit;

import javax.annotation.Nullable;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.User;
import net.robinfriedli.botify.entities.Playlist;
import net.robinfriedli.botify.entities.PlaylistItem;
import net.robinfriedli.botify.entities.UrlTrack;
import org.hibernate.Session;

/**
 * Playable implementation for any URL that is not from Spotify or YouTube. Can either be instantiated for an
 * {@link AudioTrack} loaded by lavaplayer using the {@link UrlAudioLoader} or for a persisted {@link UrlTrack}
 */
public class UrlPlayable implements Playable {

    private final String url;
    private final String display;
    private final long duration;
    @Nullable
    private AudioTrack audioTrack;

    public UrlPlayable(AudioTrack audioTrack) {
        url = audioTrack.getInfo().uri;
        display = audioTrack.getInfo().title;
        duration = audioTrack.getDuration();
        this.audioTrack = audioTrack;
    }

    public UrlPlayable(UrlTrack urlTrack) {
        url = urlTrack.getUrl();
        display = urlTrack.getTitle();
        duration = urlTrack.getDuration();
        audioTrack = null;
    }

    @Override
    public String getPlaybackUrl() {
        return url;
    }

    @Override
    public String getId() {
        return getPlaybackUrl();
    }

    @Override
    public String getDisplay() {
        return display;
    }

    @Override
    public String getDisplay(long timeOut, TimeUnit unit) {
        return getDisplay();
    }

    @Override
    public String getDisplayNow(String alternativeValue) {
        return getDisplay();
    }

    @Override
    public long getDurationMs() {
        return duration;
    }

    @Override
    public long getDurationMs(long timeOut, TimeUnit unit) {
        return getDurationMs();
    }

    @Override
    public long getDurationNow(long alternativeValue) {
        return getDurationMs();
    }

    @Nullable
    @Override
    public String getAlbumCoverUrl() {
        return null;
    }

    @Override
    public PlaylistItem export(Playlist playlist, User user, Session session) {
        return new UrlTrack(this, user, playlist);
    }

    @Override
    public String getSource() {
        return "Url";
    }

    @Nullable
    @Override
    public AudioTrack getCached() {
        return audioTrack;
    }

    @Override
    public void setCached(AudioTrack audioTrack) {
        this.audioTrack = audioTrack;
    }
}
