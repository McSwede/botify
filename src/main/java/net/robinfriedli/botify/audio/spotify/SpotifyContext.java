package net.robinfriedli.botify.audio.spotify;

import com.neovisionaries.i18n.CountryCode;
import com.wrapper.spotify.model_objects.credentials.ClientCredentials;
import net.robinfriedli.botify.login.Login;

public class SpotifyContext {

    private ClientCredentials clientCredentials;
    private CountryCode market;
    private Login login;

    public CountryCode getMarket() {
        return market;
    }

    public void setMarket(CountryCode market) {
        this.market = market;
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    public ClientCredentials getClientCredentials() {
        return clientCredentials;
    }

    public void setClientCredentials(ClientCredentials clientCredentials) {
        this.clientCredentials = clientCredentials;
    }
}
