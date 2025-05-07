import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AudioManager {
    private Clip bgmClip;
    private boolean isPlaying = false;

    public void playBGM(String filePath) {
        try {
            // 获取音频文件
            File audioFile = new File(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            
            // 获取音频格式
            AudioFormat format = audioStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            
            // 创建音频剪辑
            bgmClip = (Clip) AudioSystem.getLine(info);
            bgmClip.open(audioStream);
            
            // 循环播放
            bgmClip.loop(Clip.LOOP_CONTINUOUSLY);
            isPlaying = true;
            
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println("Error playing BGM: " + e.getMessage());
        }
    }

    public void stopBGM() {
        if (bgmClip != null && isPlaying) {
            bgmClip.stop();
            bgmClip.close();
            isPlaying = false;
        }
    }

    public void pauseBGM() {
        if (bgmClip != null && isPlaying) {
            bgmClip.stop();
            isPlaying = false;
        }
    }

    public void resumeBGM() {
        if (bgmClip != null && !isPlaying) {
            bgmClip.start();
            isPlaying = true;
        }
    }
} 