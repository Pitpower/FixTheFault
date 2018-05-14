package Logic;

import com.google.firebase.auth.FirebaseAuth;

public class Session {
    private static final Session session = new Session();
    private Usuario user;
    FirebaseAuth mAuth;
    public static Session getInstance() {
        return session;
    }

    private Session() {
        mAuth=FirebaseAuth.getInstance();
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public void cerrarSesion()
        {mAuth.signOut();}
}
