/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bucketlist.controller;

import bucketlist.model.BucketlistListItem;
import bucketlist.model.BucketlistUserInfo;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

/**
 * Klasa zajmująca się komunikacją z bazą danych,
 * zawiera metody używane przez resztę kodu w aplikacji do odczytywania aktualnego stanu bazy
 * i uaktualniania jej. Interakcja aplikacji z bazą danych jest ograniczona przez publiczne metody tej klasy.
 * Klasa używa do komunikacji z bazą danych obiektu reprezentującego sesję.
 * @author Daniel
 */
public class BucketlistController {

    private final Session session;

    private static final SessionFactory factory = init();

    private static SessionFactory init() {
        Configuration configuration = new Configuration().configure();
        //new SchemaExport(configuration).create(true, false);
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().
                applySettings(configuration.getProperties());

        SessionFactory fact = configuration.buildSessionFactory(builder.build());
        return fact;
    }

    /**
     * Domyślny kontruktor obiektów klasy. Przy wywołaniu otwiera nową sesję
     * umożliwiającą komunikację z bazą danych.
     */
    public BucketlistController() {
        session = factory.openSession();
    }

    /**
     * Metoda powinna być wywoływana po zakończeniu pracy z kontrolerem
     * w celu zamknięcia sesji i umożliwienia zwolnienia nieużywanych zasobów.
     */
    public void CloseSession() {
        session.close();
    }

    /**
     * Metoda służy do dodawania do bazy danych nowego użytkownika ze zdefiniowanym
     * adresem email i hasłem. Jest to minimalny zestaw informacji umożliwiający
     * stworzenie nowego użytkownika.
     * @param email adres email, który ma być przypisany do nowego użytkwnika
     * @param passwordHash hasło nowego użytkownika
     */
    public void addNewUser(String email, String passwordHash) {
        this.addNewUser(null, null, email, passwordHash);
    }
    
    /**
     * Metoda służy do dodawania do bazy danych nowego użytkownika ze zdefiniowanym
     * imieniem, nazwiskiem, adresem email i hasłem. Jest to kompletny zestaw informacji przechowywanych
     * na temat użytkownika.
     * @param firstName imię nowego użytkownika
     * @param lastName nazwisko nowego użytkonwika
     * @param email adres email, który ma być przypisany do nowego użytkwnika
     * @param passwordHash hasło nowego użytkownika
     */
    public void addNewUser(String firstName, String lastName, String email, String passwordHash) {
        Transaction t = session.beginTransaction();

        BucketlistUserInfo newUser = new BucketlistUserInfo(firstName, lastName, email, passwordHash);

        session.persist(newUser);
        t.commit();
    }
    
    /**
     * Metoda zwracająca kompletny obiekt BucketlistUserInfo przechowujący wszystkie
     * informacje o użytkonwiku zdefiniowane podczas wprowadzania do bazy danych.
     * @param id numer id użytkownika serwisu, którego obiekt ma być zwrócony
     * @return obiekt przechowujący dostępne dane o użytkowniku
     */
    public BucketlistUserInfo getUser(int id) {
        BucketlistUserInfo retrievedUser;
        //Query q = session.createQuery("from BucketlistUserInfo as userInfo where userInfo.id = '" + id + "'");
        
        retrievedUser = (BucketlistUserInfo)session.get(BucketlistUserInfo.class, id); //q.list().get(0);
        
        return retrievedUser;
    }
    
    /**
     * Metoda dodająca użytkownikowi o podanym id nowy cel o zawartości podanej
     * w parametrze content.
     * @param userId numer id użytkownika, który będzie miał dodany nowy cel
     * @param content zawartość celu, która ma być dodana użytkownikowi
     */
    public void addListItemToUser(int userId, String content)
    {
        Transaction t = session.beginTransaction();

        BucketlistListItem newItem = new BucketlistListItem(content);
        
        BucketlistUserInfo user = getUser(userId);
        user.getListItems().add(newItem);

        session.persist(user);
        t.commit();
    }

    /**
     *
     * @param id
     * @return
     */
    public List<BucketlistListItem> getUserItems(int id) {
        List<BucketlistListItem> retrievedItems;
        Query q = session.createQuery("from BucketlistListItem where itemOwner = '" + id + "'");

        retrievedItems = (List<BucketlistListItem>) q.list();

        return retrievedItems;
    }
    /**
     *
     *
     * @return
     */
    public List<BucketlistListItem> getAllItems(){
        List<BucketlistListItem> retrievedItems;
        Query q = session.createQuery("from BucketlistListItem");
        retrievedItems = (List<BucketlistListItem>) q.list();
        
        return retrievedItems;
    }
    

    /**
     *
     * @param email
     * @return
     */
    public List<BucketlistUserInfo> getUserByEmail(String email) {

        if (email == null) {
            email = new String();
        }

        List<BucketlistUserInfo> retrievedUser;

        Query q = session.createQuery("from BucketlistUserInfo as userInfo where userInfo.email = '" + email + "'");

        retrievedUser = (List<BucketlistUserInfo>) q.list();

        return retrievedUser;
    }
}
