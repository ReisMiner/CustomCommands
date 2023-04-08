package xyz.reisminer.chtop.commands;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.apache.commons.lang3.math.NumberUtils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import xyz.reisminer.chtop.lib.FastNoiseLite;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.net.URL;

public class Magik {
    public static void fakeMagik(Message msg, MessageChannel channel, MessageReceivedEvent event) {
        String[] splitMessage = msg.getContentRaw().split(" ");
        msg.reply("generating...").queue();

        if (splitMessage.length < 2) {
            channel.sendMessage("Too few arguments (or too many lul)").queue();
            return;
        }
        int intensity = 100;
        if (splitMessage.length >= 3) {
            if (NumberUtils.isParsable(splitMessage[2])) {
                intensity = Integer.parseInt(splitMessage[2]);
            }
        }
        try {
            URL url = new URL(splitMessage[1]);
            BufferedImage img = ImageIO.read(url);
            img = toBufferedImageOfType(img, BufferedImage.TYPE_3BYTE_BGR);

            Mat image = bufferedImageToMat(img);

            // Create a Perlin noise function
            FastNoiseLite noise = new FastNoiseLite();
            noise.SetSeed((int) (Math.random() * 1 * 1000));
            noise.SetNoiseType(FastNoiseLite.NoiseType.Perlin);

            // Iterate over each pixel and calculate distortion factor
            Mat mapX = new Mat(image.size(), CvType.CV_32FC1);
            Mat mapY = new Mat(image.size(), CvType.CV_32FC1);
            for (int y = 0; y < image.rows(); y++) {
                for (int x = 0; x < image.cols(); x++) {
                    double brightness = image.get(y, x)[0] / 255.0; // normalize to 0-1
                    double distortionFactor = brightness * intensity; // adjust as desired
                    double distortionX = x + noise.GetNoise(x, y) * distortionFactor;
                    double distortionY = y + noise.GetNoise(x + 1000, y + 1000) * distortionFactor;
                    mapX.put(y, x, distortionX);
                    mapY.put(y, x, distortionY);
                }
            }

            // Apply the distortion to the image
            Mat distortedImage = new Mat();
            Imgproc.remap(image, distortedImage, mapX, mapY, Imgproc.INTER_LINEAR);

            String filename = msg.getAuthor().getId() + ".jpg";
            Imgcodecs.imwrite(filename, distortedImage);

            File finalImg = new File(msg.getAuthor().getId() + ".jpg");
            channel.sendFile(finalImg).queue((x) -> {
                finalImg.delete();
            });
        } catch (Exception e) {
            channel.sendMessage("no url ig").queue();
        }

    }

    private static Mat bufferedImageToMat(BufferedImage bi) {
        Mat mat = new Mat(bi.getHeight(), bi.getWidth(), CvType.CV_8UC3);
        byte[] data = ((DataBufferByte) bi.getRaster().getDataBuffer()).getData();
        mat.put(0, 0, data);
        return mat;
    }

    //https://stackoverflow.com/questions/21740729/converting-bufferedimage-to-mat-opencv-in-java
    private static BufferedImage toBufferedImageOfType(BufferedImage original, int type) {
        if (original == null) {
            throw new IllegalArgumentException("original == null");
        }

        // Don't convert if it already has correct type
        if (original.getType() == type) {
            return original;
        }

        // Create a buffered image
        BufferedImage image = new BufferedImage(original.getWidth(), original.getHeight(), type);

        // Draw the image onto the new buffer
        Graphics2D g = image.createGraphics();
        try {
            g.setComposite(AlphaComposite.Src);
            g.drawImage(original, 0, 0, null);
        } finally {
            g.dispose();
        }

        return image;
    }
}
