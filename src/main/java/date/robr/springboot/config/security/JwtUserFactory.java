package date.robr.springboot.config.security;



import date.robr.springboot.user.domian.UserAuth;



final class JwtUserFactory {

    private JwtUserFactory() {
    }

    static JwtUser create(UserAuth user) {
        return new JwtUser(
                user.getUserIde(),
                user.getUserInfo()
                //mapToGrantedAuthorities(user.getUserInfo().getUserRoles().stream().map(UserRole::getRoleName).collect(Collectors.toList()))
        );
    }

/*    private static List<GrantedAuthority> mapToGrantedAuthorities(List<String> authorities) {
        return authorities.stream()
                .map(SimpleGrantedAuthority())
                .collect(Collectors.toList());
    }*/
}
