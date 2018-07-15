package app.identity;

import org.springframework.security.core.GrantedAuthority;

public class Authority implements GrantedAuthority {
    private String name;

    public Authority(String name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return name;
    }
}
