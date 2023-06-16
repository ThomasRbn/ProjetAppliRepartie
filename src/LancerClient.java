import java.rmi.NotBoundException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;

public class LancerClient {
    public static void main(String args[]) throws RemoteException {
        try {
            String serveur = "100.64.80.242";
            int port = 1099;

            if (args.length > 0) {
                serveur = args[0];
            }
            if (args.length > 1) {
                port = Integer.parseInt(args[0]);
            }

            Registry reg2 = LocateRegistry.getRegistry(serveur, port);

//            String[] list = reg2.list();
//            System.out.println("Liste des Service :");
//            for (int i = 0; i < list.length; i++) {
//                System.out.println("* " + list[i]);
//            }

            ServiceDistributeur distributeur = (ServiceDistributeur) reg2.lookup("distributeur");


            Client client = new Client(); /* Créer une instance de Compteur */
            ServiceClient rd = (ServiceClient) UnicastRemoteObject.exportObject(client, 0);
            /* Un_port = un entier particulier ou 0 pour auto-assigné */

            try {
                distributeur.enregistrerClient(rd);
                System.out.println("Connexion reussie, attente des calculs!");
            } catch (RemoteException e) {
                System.out.println("Impossible d'enregistrer la reference: \n" + e.getMessage());
            }
        } catch (NotBoundException e) {
            throw new RuntimeException(e);
        }
    }
}