import DTO.PartidoDTO;
import model.*;
import model.notificaciones.Notificacion;
import repository.PartidoRepository;
import repository.UserRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, World!");

        UserRepository ur = new UserRepository();
        PartidoRepository pr = new PartidoRepository();
        Partido p2 = pr.findById("1");
        System.out.println(p2.getFecha().toString());
        p2.setEstadoActual(new Armado());
        p2.confirmar();
        p2.iniciar();

    }
}