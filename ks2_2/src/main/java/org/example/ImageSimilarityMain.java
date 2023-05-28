package org.example;

import java.util.List;


public class ImageSimilarityMain {

    public static void main(String[] args) {
        String folderPath = "C:\\Users\\bahar\\OneDrive\\Masaüstü\\ALGORTIMA_ANALIZI_ODEV_SOURCES";
        List<String> imageFiles = ImageUtils.getAllPngFiles(folderPath);
        ImageUtils.compareImages(imageFiles);
    }
}