package Logic;

public class Session {
    private static final Session session = new Session();
    private Usuario user;

    public static Session getInstance() {
        return session;
    }

    private Session() {
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }
}
