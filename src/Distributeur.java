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
        clients.add(var1);
    }

}


//    private Map<Integer, Map<Integer, Dessin>> ensembleDessins;
//        ensembleDessins = new HashMap<>();
//        int x = var1.x;
//        int y = var1.y;
//
//        // Vérifier si la clé x existe
//        if (ensembleDessins.containsKey(x)) {
//            Map<Integer, Dessin> dessinsY = ensembleDessins.get(x);
//            // Remplacer le dessin existant ou ajouter un nouveau dessin
//            dessinsY.put(y, var1);
//        } else {
//            // Créer une nouvelle entrée pour la clé x et ajouter le dessin
//            Map<Integer, Dessin> dessinsY = new HashMap<>();
//            dessinsY.put(y, var1);
//            ensembleDessins.put(x, dessinsY);
//        }
