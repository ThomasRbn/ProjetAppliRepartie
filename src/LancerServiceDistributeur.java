import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class LancerServiceDistributeur {
    public static void main(String args[]){
        try {

            Distributeur distributeur = new Distributeur(); /* Créer une instance de Compteur */
            ServiceDistributeur rd = (ServiceDistributeur) UnicastRemoteObject.exportObject(distributeur, 0);
            /* Un_port = un entier particulier ou 0 pour auto-assigné */
            try {

                int port = 1099;                      // le port de la rmiregistry par défaut
                if (args.length > 0) {
                    port = Integer.parseInt(args[0]);
                }
                Registry reg = LocateRegistry.createRegistry(port); /* Création de l'annuaire */
                System.out.println(reg.toString());
                try {

                    reg.rebind("distributeur", rd);                  /* Enregistrement de la référence sous le nom "Contact" */
                } catch (RemoteException e) {
                    System.out.println("Impossible d'enregistrer la reference: \n" + e.getMessage());
                }
            } catch (RemoteException e) {
                System.out.println("Impossible de creer le registry: \n" + e.getMessage());
            }
        } catch (RemoteException e) {
            System.out.println("Erreur d'exportation du service: \n" + e.getMessage());
        }
    }
}