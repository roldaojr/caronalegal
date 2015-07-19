package br.edu.ifrn.tads.caronas.data;

import java.util.List;

public class UserDAO extends EntityDAO<User> {
    public UserDAO() {
        super(User.class);
    }
    public User findByEmail(String email) {
        List<User> vr = database.view("user/by_email")
                .key(email)
                .query(User.class);
        if (vr.size() > 0) {
            return vr.get(0);
        } else {
            return null;
        }
    }
}
