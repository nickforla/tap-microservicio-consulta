package nforla.tap.microservicioconsulta.servicios;

public class ValidadorCuil {

    public static boolean esCuilValido(String cuil){

        return cuil.matches("[0-9]{11}+");

    }
}
