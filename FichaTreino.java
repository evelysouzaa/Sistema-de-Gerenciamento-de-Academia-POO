import java.util.ArrayList;

public class FichaTreino {

    private int id;
    private Aluno aluno;
    private Instrutor instrutor;
    private String objetivo;
    private ArrayList<String> exercicios;
    private String dataInicio;

    public FichaTreino(int id, Aluno aluno, Instrutor instrutor, String objetivo, String dataInicio) {
        setId(id);
        setAluno(aluno);
        setInstrutor(instrutor);
        setObjetivo(objetivo);
        setDataInicio(dataInicio);
        this.exercicios = new ArrayList<>();
        instrutor.incrementarAlunosOrientados();
    }

    public int getId() { return id; }
    public Aluno getAluno() { return aluno; }
    public Instrutor getInstrutor() { return instrutor; }
    public String getObjetivo() { return objetivo; }
    public ArrayList<String> getExercicios() { return exercicios; }
    public String getDataInicio() { return dataInicio; }

    public void setId(int id) {
        if (id <= 0) throw new IllegalArgumentException("ID deve ser positivo.");
        this.id = id;
    }
    public void setAluno(Aluno aluno) {
        if (aluno == null) throw new IllegalArgumentException("Aluno não pode ser nulo.");
        this.aluno = aluno;
    }
    public void setInstrutor(Instrutor instrutor) {
        if (instrutor == null) throw new IllegalArgumentException("Instrutor não pode ser nulo.");
        this.instrutor = instrutor;
    }
    public void setObjetivo(String objetivo) {
        if (objetivo == null || objetivo.trim().isEmpty())
            throw new IllegalArgumentException("Objetivo não pode ser vazio.");
        this.objetivo = objetivo;
    }
    public void setDataInicio(String dataInicio) {
        if (dataInicio == null || dataInicio.trim().isEmpty())
            throw new IllegalArgumentException("Data não pode ser vazia.");
        this.dataInicio = dataInicio;
    }

    public void adicionarExercicio(String exercicio) {
        if (exercicio == null || exercicio.trim().isEmpty())
            throw new IllegalArgumentException("Exercício não pode ser vazio.");
        exercicios.add(exercicio);
    }

    @Override
    public String toString() {
        return String.format("FichaTreino[ID=%d, Aluno=%s, Instrutor=%s, Objetivo=%s, Exercícios=%d, Início=%s]",
                id, aluno.getNome(), instrutor.getNome(), objetivo, exercicios.size(), dataInicio);
    }
}
