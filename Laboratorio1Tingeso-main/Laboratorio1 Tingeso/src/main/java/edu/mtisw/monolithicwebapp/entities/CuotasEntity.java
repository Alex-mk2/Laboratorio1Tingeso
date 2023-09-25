package edu.mtisw.monolithicwebapp.entities;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;


@Entity
@Table(name = "cuotas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CuotasEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id_cuota;
    private String rut_estudiante;
    private Integer numero_examenes_rendidos;
    private Integer promedio_puntaje_examenes;
    private Integer montoTotalArancel; //Arancel total por ejemplo: 1.500.000
    private String tipoPago;
    private Integer numeroCuotasPactadas;
    private Integer numeroCuotasPagadas;
    private Integer montoTotalPagado;
    private String fechaUltimoPago;
    private Integer saldoPorPagar;
    private Integer numeroCuotasConRetraso;

}
