package com.example.kpstar.lyricsapp.Lyrics;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import android.util.Log;

/**
 * LyricDataBean
 *
 * @author lisc
 */
public class LyricDataBean {

    private static final String TAG = "LyricDataBean";

    private String singer;

    private String title;

    private String content;

    private int lyricStateFlay;

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getLyricStateFlay() {
        return lyricStateFlay;
    }

    public void setLyricStateFlay(int lyricStateFlay) {
        this.lyricStateFlay = lyricStateFlay;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    /**
     * @param singer
     * @param title
     * @param
     */
    public LyricDataBean(String singer, String title, String content) {
        this.singer = singer;
        this.title = title;
        this.content = content;
        this.lyricStateFlay = LyricConstants.RIC_STATE_FLAG_INIT;
    }

    /**
     * saveLyric
     *
     * @param savepath
     * @param
     * @return int
     */
    public int saveLyric(String savepath, String lyricFileName) {
//        if (MusicUtils.hasSdcard()) {
//            String lyricName = this.getLyricFileName(lyricFileName);
//            File dir = null;
//            if (savepath != null) {
//                dir = new File(savepath, StringConstant.LRC_DIRECTORY + File.separator);
//            } else {
//                dir = new File(StringConstant.CURRENT_PATH, StringConstant.LRC_DIRECTORY
//                        + File.separator);
//            }
//            if (!dir.exists()) {
//                dir.mkdirs();
//            }
//            File file = new File(dir, lyricName + StringConstant.LYRIC_SUFFIX);
//            if (file.exists()) {
//                file.delete();
//            }
//            if (MusicUtils.hasDataSpace(this.getContent().length())) {
//                Writer outStreamForLyric = null;
//                try {
//                    outStreamForLyric = new OutputStreamWriter(new FileOutputStream(file),
//                            LyricConstants.STRING_UTF_ENCOIDNG);
//                    outStreamForLyric.write(new String(String.valueOf(this.getContent()).getBytes(
//                            LyricConstants.STRING_UTF_ENCOIDNG)));
//                } catch (IOException e) {
//                    this.setLyricStateFlay(LyricConstants.RIC_STATE_FLAG_SDCARD_NOT_WRITE);
//                } finally {
//                    try {
//                        outStreamForLyric.close();
//                    } catch (IOException e) {
//                        Log.e(TAG, "SAVE LYRIC ERROR:" + e.getMessage());
//                    }
//                }
//            } else {
//                setLyricStateFlay(LyricConstants.RIC_STATE_FLAG_NO_SPACE);
//            }
//        } else {
//            setLyricStateFlay(LyricConstants.RIC_STATE_FLAG_NO_SDCARD);
//        }
//        return this.getLyricStateFlay();
        return 0;
    }

    private String getLyricFileName(String lyricFileName) {
        StringBuffer fileName = new StringBuffer();
        if (lyricFileName != null) {
            fileName.append(lyricFileName);
        } else {
            if (singer != null && singer.trim().length() > 0) {
                fileName.append(singer + StringConstant.LINK_FACTOR);
            }
            if (title != null && title.trim().length() > 0) {
                fileName.append(title);
            }
        }
        return fileName.toString();
    }

}