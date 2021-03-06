/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bucketlist.model;

/**
 * Klasa reprezentująca cel (pojedynczy element listy) użytkownika. Obiekt celu
 * jest powiązany z obiektem użytkownika relacją kompozycji.
 * @author Daniel
 */
public class BucketlistListItem {

    /**
     * Kontruktor klasy pobierający tworzący nowy obiekt z zainicjalizowanym
     * polem opisującym cel użytkownika.
     * @param content opis celu użytkownika
     */
    public BucketlistListItem(String content) {
        this.content = content;
    }
    
    /**
     * Domyślny konstruktor klasy wymagany przez Hibernate. Tworzony pusty obiekt
     * celu, który wymaga zainicjalizowania opisu celu poprzez setter.
     */
    public BucketlistListItem() {
        
    }
    
    private int itemId;
    private String content;

    /**
     * Zwraca identyfikator obiektu celu w bazie danych. Każdy cel można
     * jednoznacznie zidentyfikować za pomocą tego numeru
     * @return identyfikator obiektu w bazie danych
     */
    public int getItemId() {
        return itemId;
    }

    /**
     * Ustawia wartość identyfikatora obiektu. Metoda jest zachowana ze względu
     * na wymagania Hibernate. Nie powinna być używana.
     * @param itemId nowy identyfikator obiektu w bazie danych
     */
    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    /**
     * Zwraca opis celu zdefiniowany przy dodawaniu do bazy danych
     * @return opis obiektu
     */
    public String getContent() {
        return content;
    }

    /**
     * Ustawia opis projektu. Metoda powinna być wywoływana w przypadku użycia
     * domyślnego kontruktora lub w celu edycji opisu.
     * @param content nowy opis celu, nie może być dłuższy niż 200 znaków
     */
    public void setContent(String content) {
        this.content = content;
    }
}
