package Modelos;

public class Bebida {
    public int id;
    public String nombre;
    public String ingredientes;

    public Bebida(int id, String nombre, String ingredientes) {
        this.nombre = nombre;
        this.ingredientes = ingredientes;
        this.id = id;
    }

    public Bebida(int id, String nombre) {
        this.nombre = nombre;
        this.id = id;
    }

    public Bebida(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(String ingredientes) {
        this.ingredientes = ingredientes;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
