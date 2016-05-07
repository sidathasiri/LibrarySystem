package librarysystem;

import DB.PeopleHandler;
import java.util.ArrayList;

public class LibrarySystem {

    public static Admin loggedAdmin;
    public static Member loggedMember;
    public static Librarian loggedLibrarian;
    public static CoLibrarian loggedCoLibrarian;
    public static ArrayList<String> categoryList = new ArrayList<>();
    
    public PeopleHandler peopleHandlerObj = new PeopleHandler("member");

    public String login(String un, String pw) {
        return peopleHandlerObj.login(un, pw);
    }

    public static void setLoggedAdmin(Admin loggedAdmin) {
        LibrarySystem.loggedAdmin = loggedAdmin;
    }

    public static void setLoggedMember(Member loggedMember) {
        LibrarySystem.loggedMember = loggedMember;
    }

    public static void setLoggedLibrarian(Librarian loggedLibrarian) {
        LibrarySystem.loggedLibrarian = loggedLibrarian;
    }

    public static void setLoggedCoLibrarian(CoLibrarian loggedCoLibrarian) {
        LibrarySystem.loggedCoLibrarian = loggedCoLibrarian;
    }
    
    
}
