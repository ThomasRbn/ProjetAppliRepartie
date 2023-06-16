import raytracer.Disp;
import raytracer.Image;
import raytracer.Scene;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;

public class LancerRaytracer {

    static ServiceDistributeur distributeur;
    static Disp disp;

    public static String aide = "Raytracer : synthèse d'image par lancé de rayons (https://en.wikipedia.org/wiki/Ray_tracing_(graphics))\n\nUsage : java LancerRaytracer [fichier-scène] [largeur] [hauteur]\n\tfichier-scène : la description de la scène (par défaut simple.txt)\n\tlargeur : largeur de l'image calculée (par défaut 512)\n\thauteur : hauteur de l'image calculée (par défaut 512)\n";

    public static void main(String[] args) throws RemoteException {

        try {
            String serveur = "100.64.80.242";
            int port = 1099;

            if (args.length > 0) {
                serveur = args[0];
            }
            if (args.length > 1) {
                port = Integer.parseInt(args[1]);
            }

            Registry reg2 = LocateRegistry.getRegistry(serveur, port);
            distributeur = (ServiceDistributeur) reg2.lookup("distributeur");




        // Le fichier de description de la scène si pas fournie
        String fichier_description = "src/simple.txt";

        // largeur et hauteur par défaut de l'image à reconstruire
        int largeur = 2048, hauteur = 2048;

        if (args.length > 0) {
            fichier_description = args[0];
            if (args.length > 1) {
                largeur = Integer.parseInt(args[1]);
                if (args.length > 2)
                    hauteur = Integer.parseInt(args[2]);
            }
        } else {
            System.out.println(aide);
        }


        // création d'une fenêtre
        disp = new Disp("Raytracer", largeur, hauteur);

        // Initialisation d'une scène depuis le modèle
        Scene scene = new Scene(fichier_description, largeur, hauteur);

        // Calcul de l'image de la scène les paramètres :
        // - x0 et y0 : correspondant au coin haut à gauche
        // - l et h : hauteur et largeur de l'image calculée
        // Ici on calcule toute l'image (0,0) -> (largeur, hauteur)

        int x0 = 0, y0 = 0;
        int l = largeur, h = hauteur;

        // Chronométrage du temps de calcul
//        Instant debut = Instant.now();
//        System.out.println("Calcul de l'image :\n - Coordonnées : " + x0 + "," + y0
//                + "\n - Taille " + largeur + "x" + hauteur);

//        Image image = scene.compute(x0, y0, l, h);
        int subdivisions = 14;
        int subdivisionWidth = largeur / subdivisions;
        int subdivisionHeight = hauteur / subdivisions;

        for (int i = 0; i < subdivisions; i++) {
            for (int j = 0; j < subdivisions; j++) {
                x0 = i * subdivisionWidth;
                y0 = j * subdivisionHeight;
                int w = subdivisionWidth;
                h = subdivisionHeight;

                new TransmetteurImage(scene, x0, y0, w, h).start();

            }
        }
//        Image image = scene.compute(x0, y0, l/2, h/2);
//        Image image2 = scene.compute(l/2, h/2, l/2, h/2);

//        Instant fin = Instant.now();
//
//        long duree = Duration.between(debut, fin).toMillis();
//
//        System.out.println("Image calculée en :" + duree + " ms");

        // Affichage de l'image calculée
//        disp.setImage(image, x0, y0);
//        disp.setImage(image2, l/2, h/2);
        } catch (NotBoundException | RemoteException e) {
            throw new RuntimeException(e);
        }
    }


    static class TransmetteurImage extends Thread {

        private Scene scene;

        private int x0,y0,w,h;
        public TransmetteurImage(Scene scene, int x0,int y0,int w, int h) {
            this.scene = scene;
            this.x0 = x0;
            this.y0 = y0;
            this.w = w;
            this.h = h;
        }

        @Override
        public void run() {
            try {
                Image img = distributeur.distribuerMessage(scene, x0, y0, w, h);
                if(img != null) {
                    disp.setImage(img, x0, y0);
                }
            } catch (RemoteException e) {
                System.out.println("Impossible de compute la scène");
                e.printStackTrace();
            }

        }
    }
}
