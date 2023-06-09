import raytracer.Image;
import raytracer.Scene;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class Distributeur implements ServiceDistributeur {
    private ArrayList<ServiceClient> clients;
    private int i = 0;

    public Distributeur() {
        clients = new ArrayList<>();
    }

    @Override
    public ArrayList<ServiceClient> getClients() throws RemoteException {
        return clients;
    }

    @Override
    public void enregistrerClient(ServiceClient var1) throws RemoteException {
        System.out.println(var1);
        clients.add(var1);
    }

    @Override
    public Image distribuerMessage(Scene scene, int x0, int y0, int w, int h) throws RemoteException {
        int clientIndex = (i) % clients.size();
        ServiceClient client = clients.get(clientIndex);
        try {
            return client.compute(scene, x0, y0, w, h);
        }catch (RemoteException e){
            System.out.println("Suppression client");
            synchronized (clients) {
                clients.remove(client);
            }
        }
        return null;
    }


}