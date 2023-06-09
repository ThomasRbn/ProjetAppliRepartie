import java.rmi.RemoteException;
import java.util.ArrayList;

public class Distributeur implements ServiceDistributeur {
    private ArrayList<ServiceClient> clients;

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
}