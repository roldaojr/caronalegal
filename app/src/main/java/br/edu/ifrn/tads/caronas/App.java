package br.edu.ifrn.tads.caronas;

import br.edu.ifrn.tads.caronas.data.User;

public class App {
    public static String TAG = "Caronas";
    public static User currentUser;

    public static User getCurrentUser() {
        if (currentUser == null) {
            currentUser = new User();
            currentUser.setId("roldaojr");
            currentUser.setName("Roldão Rego Jr");
            currentUser.setEmail("roldaogjr@gmail.com");
            currentUser.setPhone("8494730001");
        }
        return currentUser;
    }
}
