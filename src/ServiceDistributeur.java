import raytracer.Image;
import raytracer.Scene;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ServiceDistributeur extends Remote {
    ArrayList<ServiceClient> getClients() throws RemoteException;

    void enregistrerClient(ServiceClient var1) throws RemoteException;

    Image distribuerMessage(Scene scene, int x0, int y0, int w, int h) throws RemoteException;
}
