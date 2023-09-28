package edu.mtisw.monolithicwebapp.entities;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "pagoArancel")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class pagoArancelEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_arancel;
    @ManyToOne
    @JoinColumn(name = "estudiante_id", referencedColumnName = "id_estudiante", nullable = false)
    private estudianteEntity estudiante;
    private Integer numeroExamenesRendidos;
    private Double promedioPuntajeExamenes;
    private Double montoTotalArancel;
    private String tipoPago;
    private Integer numeroTotalCuotasPactadas;
    private Integer numeroCuotasPagadas;
    private Double montoTotalPagado;
    private LocalDate fechaUltimoPago;
    private Double saldoPorPagar;
    private Integer numeroCuotasConRetraso;
    private String nombres;
    private String rut;

}
