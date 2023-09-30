package edu.mtisw.monolithicwebapp.entities;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "estudiante")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class estudianteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long idEstudiante;
    private String nombres;
    private String apellidos;
    private String email;
    private String rut;
    private String fecha_nacimiento;
    private String tipo_establecimiento;
    private String nombre_establecimiento;
    private String egreso;
    @OneToMany(mappedBy = "estudiante", cascade = CascadeType.ALL)
    private List<pagoArancelEntity> pagoArancel;
    @OneToMany(mappedBy = "estudiante", cascade = CascadeType.ALL)
    private List<PruebaEntity> pruebas;
}
