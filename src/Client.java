import raytracer.Image;
import raytracer.Scene;

import java.rmi.RemoteException;

public class Client implements ServiceClient {

    @Override
    public Image compute(Scene scene, int x0, int y0, int w, int h) throws RemoteException {
        System.out.println("Calcul de: x0 "+x0+" y0 "+y0+" w "+w+" h "+h);
        Image image = scene.compute(x0,y0,w,h);
        return image;
    }
}
