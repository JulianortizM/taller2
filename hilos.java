import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class hilos {
    public static void main(String[] args) {
        Thread songThread = new Thread(() -> {
            playAudio("Travis Scott - SICKO MODE (Audio) (online-audio-converter.com).wav");
        });

        Thread consoleThread = new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Digite lo que quiera:");
            String input = scanner.nextLine();
            System.out.println("Lo que digito fue: " + input);
        });

        Thread numbersThread = new Thread(() -> {
            for (int n = 1; n <= 30; n++) {
                System.out.println(n);
            }
        });

        Thread caractersThread = new Thread(() -> {
            for (char l= 'a'; l < 'z'; l++){
                System.out.println(l);
            }
        });

        songThread.start();
        consoleThread.start();
        numbersThread.start();
        caractersThread.start();
    }

    public static void playAudio(String filePath) {
        File audioFile = new File(filePath);
        try (AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile)) {
            AudioFormat format = audioStream.getFormat();
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
            try (SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info)) {
                line.open(format);
                line.start();

                int bufferSize = 4096;
                byte[] buffer = new byte[bufferSize];
                int bytesRead;

                while ((bytesRead = audioStream.read(buffer, 0, buffer.length)) != -1) {
                    line.write(buffer, 0, bytesRead);
                }

                line.drain();
                line.stop();
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}