package org.example;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ImageUtils {

    static {
       System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

    }

    public static List<String> getAllPngFiles(String folderPath) {
        List<String> imageFiles = new ArrayList<>();
        File folder = new File(folderPath);
        File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".png"));

        if (files != null) {
            for (File file : files) {
                imageFiles.add(file.getAbsolutePath());
            }
        }

        return imageFiles;
    }

    public static double calculateImageSimilarity(String imagePath1, String imagePath2) {
        Mat image1 = Imgcodecs.imread(imagePath1, Imgcodecs.IMREAD_GRAYSCALE);
        Mat image2 = Imgcodecs.imread(imagePath2, Imgcodecs.IMREAD_GRAYSCALE);

        Mat hist1 = new Mat();
        Mat hist2 = new Mat();

        Imgproc.calcHist(
                new ArrayList<>(List.of(image1)), new MatOfInt(0), new Mat(),
                hist1, new MatOfInt(256), new MatOfFloat(0, 256)
        );

        Imgproc.calcHist(
                new ArrayList<>(List.of(image2)), new MatOfInt(0), new Mat(),
                hist2, new MatOfInt(256), new MatOfFloat(0, 256)
        );

        Core.normalize(hist1, hist1);
        Core.normalize(hist2, hist2);

        double similarity = Imgproc.compareHist(hist1, hist2, Imgproc.CV_COMP_CORREL);

        return similarity;
    }

    public static void compareImages(List<String> imageFiles) {
        int numImages = imageFiles.size();

        for (int i = 0; i < numImages - 1; i++) {
            String imagePath1 = imageFiles.get(i);
            Mat image1 = Imgcodecs.imread(imagePath1);

            double maxSimilarity = 0;
            String mostSimilarImage = "";

            for (int j = i + 1; j < numImages; j++) {
                String imagePath2 = imageFiles.get(j);
                Mat image2 = Imgcodecs.imread(imagePath2);

                double similarity = calculateImageSimilarity(imagePath1, imagePath2);

                if (similarity > maxSimilarity) {
                    maxSimilarity = similarity;
                    mostSimilarImage = imagePath2;
                }
            }

            System.out.println("En çok benzeyen görsel: " + mostSimilarImage);
            System.out.println("Benzerlik yüzdesi: " + maxSimilarity);
            System.out.println("-------------------------------------");
        }
    }
}