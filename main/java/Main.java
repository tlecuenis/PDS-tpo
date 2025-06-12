import model.Usuario;
import repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, World!");

        UserRepository ur = new UserRepository();
        List<Usuario> u = new ArrayList<Usuario>();
        u = ur.findByDeporte("fútbol");
        for(Usuario user : u){
            System.out.println(user.getNombre());
        }
        
    }
}