package mx.com.qtx.mod05proyMvcSpringBoot.seguridad;

import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

public class UtilPasswords {
    public static void main(String[] args) {
//        testOperacionesBasicasPasswordEncoder();
        proponerHashes("tekamachalko","tekolutla","tlatelolko");
    }

    public static String getHashDePswd(String pswdCrudo){
        PasswordEncoder pe = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        return pe.encode(pswdCrudo);
    }

    public static void proponerHashes(String... pawds){
        for(String pwdI : pawds){
            System.out.println(pwdI + ":" + getHashDePswd(pwdI));
        }
    }

    private static void testOperacionesBasicasPasswordEncoder() {
        PasswordEncoder pe = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        String[] contrasenias = {"camaleonVerde","O5oN3gr0negr151m@","jv4d4bAD**"};

        List<String> hashes = new ArrayList<>();

        for(String contraseniaI:contrasenias){
            hashes.add(pe.encode(contraseniaI));
        }
        hashes.forEach(hI-> System.out.println("hI = " + hI));

        int iRandom =((int) (Math.random() * 10000)) % contrasenias.length;
        String contraseniaRandom = contrasenias[iRandom];

        System.out.println("\ncontraseniaRandom = " + contraseniaRandom);
        String hash = hashes.get(0);

        if(pe.matches(contraseniaRandom,hash)){
            System.out.println("Hash y contraseña coinciden. Bienvenido!");
        }
        else{
            System.out.println("Hash y contraseña NO COINCIDEM. largo!!");
        }
    }
}
