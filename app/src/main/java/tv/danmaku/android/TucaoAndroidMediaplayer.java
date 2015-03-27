package tv.danmaku.android;

import com.studyjun.tucao.bean.Segment;

import java.util.List;

import tv.danmaku.ijk.media.player.AndroidMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;
import tv.danmaku.ijk.media.widget.VideoView;

/**
 * Created by hn on 2015/2/9.
 *
 * @TODO
 */
public class TucaoAndroidMediaplayer extends AndroidMediaPlayer {
    public List<Segment> mSegments;


    public void setDataSource(List<Segment> segments) {
        mSegments = segments;
        VideoView ss;
        IjkMediaPlayer ijkMediaPlayer;
    }
}

