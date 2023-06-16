import raytracer.Image;
import raytracer.Scene;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServiceClient extends Remote {
    Image compute(Scene scene, int x0, int y0, int w, int h) throws RemoteException;

}
