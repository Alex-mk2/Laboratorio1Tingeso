package edu.mtisw.monolithicwebapp.entities;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "pruebas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PruebaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id_Prueba;
    @ManyToOne
    @JoinColumn(name = "estudiante_id", referencedColumnName = "id_estudiante", nullable = false)
    private estudianteEntity estudiante;
    private LocalDate fecha_examen;
    private Integer puntaje_obtenido;
}
