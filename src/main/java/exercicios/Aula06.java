package exercicios;

import exercicios.base.Aula;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Aula06 extends Aula {

    private final Predicate<Estudante> isMulher       = e -> e.getSexo()=='F';
    private final Predicate<Estudante> hasCurso        = e -> e.getCurso() != null;
    private final Predicate<Estudante> notaAprovada    = e -> e.getNota() >= 6.0;

    private final Predicate<Estudante> mulheresAprovadas =
            isMulher.and(hasCurso).and(notaAprovada);

    // Comparadores reutilizáveis
    private final Comparator<Estudante> porCurso =
            Comparator.comparing(e -> e.getCurso());

    private final Comparator<Estudante> porNota =
            Comparator.comparingDouble(e -> e.getNota());

    public Aula06() {
        Predicate<Estudante> isHomem = Estudante::hasCurso;

        long homens = estudantes.stream()
                .filter(isHomem)
                .count();
        System.out.println(homens);

        long homensPalmas = estudantes.stream()
                .filter(isHomem)
                .filter(e -> e.getCidade().getNome().equalsIgnoreCase("Palmas"))
                .count();
        System.out.println(homensPalmas);
    }

    public static void main(String[] args) {
        new Aula06();
    }

    /**
     * Sem ordenação — lista NÃO-MODIFICÁVEL.
     */
    public List<Estudante> getEstudantesMulheresAprovadas() {
        return estudantes.stream()
                .filter(mulheresAprovadas)
                .collect(Collectors.toUnmodifiableList());
    }

    /**
     * Ordenada por curso (crescente) e depois por nota (crescente).
     */
    public List<Estudante> getEstudantesMulheresAprovadasOrdenadasPorCursoAndNota() {
        return estudantes.stream()
                .filter(mulheresAprovadas)
                .sorted(porCurso.thenComparing(porNota))
                .collect(Collectors.toUnmodifiableList());
    }

    /**
     * Curso decrescente, nota crescente.
     */
    public List<Estudante> getEstudantesMulheresAprovadasOrdenadasPorCursoDecrescenteAndNotaCrescente() {
        return estudantes.stream()
                .filter(mulheresAprovadas)
                .sorted(porCurso.reversed().thenComparing(porNota))
                .collect(Collectors.toUnmodifiableList());
    }

    /**
     * Sem ordenação — lista MODIFICÁVEL.
     */
    public List<Estudante> getEstudantesMulheresAprovadasNaoOrdenadasModificavel() {
        return estudantes.stream()
                .filter(mulheresAprovadas)
                .collect(Collectors.toList());   // toList() mutável
    }

    /**
     * Curso decrescente e nota decrescente.
     */
    public List<Estudante> getEstudantesMulheresAprovadasOrdenadasTotalmenteDecrescente() {
        return estudantes.stream()
                .filter(mulheresAprovadas)
                .sorted(porCurso.reversed().thenComparing(porNota.reversed()))
                .collect(Collectors.toUnmodifiableList());
    }

    /**
     * Curso crescente, nota decrescente.
     */
    public List<Estudante> getEstudantesMulheresAprovadasOrdenadasPorCursoCrescenteAndNotaDecrescente() {
        return estudantes.stream()
                .filter(mulheresAprovadas)
                .sorted(porCurso.thenComparing(porNota.reversed()))
                .collect(Collectors.toUnmodifiableList());
    }
}