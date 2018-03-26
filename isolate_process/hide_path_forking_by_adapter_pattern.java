// bad

public class Query...
    private SDLogin sdLogin; // needed for SD version 5.1
    private SDSession sdSession; // needed for SD version 5.1
    private SDLoginSession sdLoginSession; // needed for SD version 5.2
    private boolean sd52; // tells if we're running under SD 5.2
    private SDQuery sdQuery; // this is needed for SD versions 5.1 & 5.2
    // this is a login for SD 5.1
    // NOTE: remove this when we convert all aplications to 5.2
    public void login(String server, String user, String password) throws QueryException {
        sd52 = false;
        try {
            sdSession = sdLogin.loginSession(server, user, password);
        } catch (SDLoginFailedException lfe) {
            throw new QueryException(QueryException.LOGIN_FAILED,
                "Login failure\n" + lfe, lfe);
        } catch (SDSocketInitFailedException ife) {
            throw new QueryException(QueryException.LOGIN_FAILED,
                "Socket fail\n" + ife, ife);
        }
    }
    // 5.2 login
    public void login(String server, String user, String password, String sdConfigFileName) throws QueryException {
        sd52 = true;
        sdLoginSession = new SDLoginSession(sdConfigFileName, false);
        try {
            sdLoginSession.loginSession(server, user, password);
        } catch (SDLoginFailedException lfe) {
            throw new QueryException(QueryException.LOGIN_FAILED,
                "Login failure\n" + lfe, lfe);
        } catch (SDSocketInitFailedException ife) {
            throw new QueryException(QueryException.LOGIN_FAILED,
                "Socket fail\n" + ife, ife);
        } catch (SDNotFoundException nfe) {
            throw new QueryException(QueryException.LOGIN_FAILED,
                "Not found exception\n" + nfe, nfe);
        }
    }
    public void doQuery() throws QueryException {
        if (sdQuery != null)
            sdQuery.clearResultSet();
        if (sd52)
            sdQuery = sdLoginSession.createQuery(SDQuery.OPEN_FOR_QUERY);
        else
            sdQuery = sdSession.createQuery(SDQuery.OPEN_FOR_QUERY);
        executeQuery();
    }

// good

public abstract class Query {
    protected abstract SDQuery createQuery(); // a Factory Method [GoF]

    public void doQuery() throws QueryException { // a Template Method [GoF]
        if (sdQuery != null)
            sdQuery.clearResultSet();
        sdQuery = createQuery(); // call to the Factory Method
        executeQuery();
    }
}
class QuerySD51 extends Query {}
protected SDQuery createQuery() {
    return sdSession.createQuery(SDQuery.OPEN_FOR_QUERY);
}
class QuerySD52 extends Query {
protected SDQuery createQuery() {
    return sdLoginSession.createQuery(SDQuery.OPEN_FOR_QUERY);
}
