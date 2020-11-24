
package com.mycompany.practica8;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;


public class Lienzo extends JPanel{
    private static BufferedImage originalImagen;
    private BufferedImage imagenModify;
    private static Mat orgMat;
    private Mat moMat;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imagenModify, 0, 0, this); 
    }
    public void setImg(String imgPath) throws IOException{
        orgMat = Imgcodecs.imread(imgPath);
        moMat = Imgcodecs.imread(imgPath);
        originalImagen = (BufferedImage) HighGui.toBufferedImage(moMat);
        imagenModify = originalImagen;
        
    }
    public BufferedImage getImageModify(){
        return imagenModify;
    }
    
    public void umbralizar(Integer umbral) {
        // crear dos imágenes en niveles de gris con el mismo
        // tamaño que la original
        Mat imagenGris = new Mat(orgMat.rows(),
                                orgMat.cols(),
                                CvType.CV_8U);        
        Mat imagenUmbralizada = new Mat(orgMat.rows(),
                                        orgMat.cols(),
                                        CvType.CV_8U);
        // convierte a niveles de grises la imagen original
        Imgproc.cvtColor(orgMat,
        imagenGris,
        Imgproc.COLOR_BGR2GRAY);
        // umbraliza la imagen:
        // - píxeles con nivel de gris > umbral se ponen a 1
        // - píxeles con nivel de gris <= umbra se ponen a 0
        Imgproc.threshold(imagenGris,
        imagenUmbralizada,
        umbral,
        255,
        Imgproc.THRESH_BINARY);
        try {
            // se devuelve la imagen umbralizada
            moMat = imagenUmbralizada;
            imagenModify = (BufferedImage) HighGui.toBufferedImage(moMat);
            
        } catch (Exception ex) {
            Logger.getLogger(Lienzo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void imagenOriginal(){
        imagenModify = originalImagen;
        moMat = orgMat;
    }
}
