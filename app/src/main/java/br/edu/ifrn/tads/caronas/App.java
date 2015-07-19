package br.edu.ifrn.tads.caronas;

import br.edu.ifrn.tads.caronas.data.User;

public class App {
    public final static String TAG = "Caronas";
    private static User currentUser;

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        App.currentUser = currentUser;
    }
}
