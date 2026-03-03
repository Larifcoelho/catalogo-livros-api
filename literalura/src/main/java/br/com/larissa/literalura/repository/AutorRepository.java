package br.com.larissa.literalura.repository;

import br.com.larissa.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface AutorRepository extends JpaRepository<Autor, Long> {

    @Query("SELECT a FROM Autor a WHERE :ano BETWEEN a.anoNascimento AND a.anoFalecimento")
    List<Autor> autoresVivosNoAno(Integer ano);
}