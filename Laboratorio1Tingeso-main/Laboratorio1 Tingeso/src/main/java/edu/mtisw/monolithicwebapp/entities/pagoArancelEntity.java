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
    private Long idArancel;
    @ManyToOne
    @JoinColumn(name = "idEstudiante", nullable = false)
    private estudianteEntity estudiante;
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
    private LocalDate plazoPago;
    private String estadoCuota;
}
