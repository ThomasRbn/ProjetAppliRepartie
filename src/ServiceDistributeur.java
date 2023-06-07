import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ServiceDistributeur extends Remote {
    ArrayList<ServiceClient> getClients() throws RemoteException;

    void enregistrerClient(ServiceClient var1) throws RemoteException;
}
