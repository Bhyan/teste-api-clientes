public class Client {
    int id;
    String nome;
    int idade;
    int risco;

    public Client(int id, String nome, int idade, int risco) {
        this.id = id;
        this.nome = nome;
        this.idade = idade;
        this.risco = risco;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public int getRisco() {
        return risco;
    }

    public void setRisco(int risco) {
        this.risco = risco;
    }
}
