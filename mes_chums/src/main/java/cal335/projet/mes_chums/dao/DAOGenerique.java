package cal335.projet.mes_chums.dao;

import java.util.List;

public interface DAOGenerique<T> {
    void ajouter(T objet);
    void supprimer(T objet);
    void mettreAJour(T objet);
    T trouverParId(Integer id);
     List<T> trouverTous();
}
