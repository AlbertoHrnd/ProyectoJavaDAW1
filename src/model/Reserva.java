/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import alquilervehiculos.Utils;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Alberto
 */
@Entity
@Table(name = "reserva")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Reserva.findAll", query = "SELECT r FROM Reserva r")
    , @NamedQuery(name = "Reserva.findById", query = "SELECT r FROM Reserva r WHERE r.id = :id")
    , @NamedQuery(name = "Reserva.findByFechaInicio", query = "SELECT r FROM Reserva r WHERE r.fechaInicio = :fechaInicio")
    , @NamedQuery(name = "Reserva.findByFechaFin", query = "SELECT r FROM Reserva r WHERE r.fechaFin = :fechaFin")
    , @NamedQuery(name = "Reserva.findByLitrosGasolina", query = "SELECT r FROM Reserva r WHERE r.litrosGasolina = :litrosGasolina")
    , @NamedQuery(name = "Reserva.findByEntregado", query = "SELECT r FROM Reserva r WHERE r.entregado = :entregado")
    , @NamedQuery(name = "Reserva.findByDevuelto", query = "SELECT r FROM Reserva r WHERE r.devuelto = :devuelto")
    , @NamedQuery(name = "Reserva.findByPrecioDia", query = "SELECT r FROM Reserva r WHERE r.precioDia = :precioDia")})
public class Reserva implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "fecha_inicio")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    @Basic(optional = false)
    @Column(name = "fecha_fin")
    @Temporal(TemporalType.DATE)
    private Date fechaFin;
    @Column(name = "litros_gasolina")
    private Integer litrosGasolina;
    @Basic(optional = false)
    @Column(name = "entregado")
    private boolean entregado;
    @Basic(optional = false)
    @Column(name = "devuelto")
    private boolean devuelto;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "precio_dia")
    private BigDecimal precioDia;
    @JoinColumn(name = "agencia_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Agencia agenciaId;
    @JoinColumn(name = "cliente_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Cliente clienteId;
    @JoinColumn(name = "vehiculo_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Vehiculo vehiculoId;

    public Reserva() {
    }

    public Reserva(Integer id) {
        this.id = id;
    }

    public Reserva(Integer id, Date fechaInicio, Date fechaFin, boolean entregado, boolean devuelto, BigDecimal precioDia) {
        this.id = id;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.entregado = entregado;
        this.devuelto = devuelto;
        this.precioDia = precioDia;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Integer getLitrosGasolina() {
        return litrosGasolina;
    }

    public void setLitrosGasolina(Integer litrosGasolina) {
        this.litrosGasolina = litrosGasolina;
    }

    public boolean getEntregado() {
        return entregado;
    }

    public void setEntregado(boolean entregado) {
        this.entregado = entregado;
    }

    public boolean getDevuelto() {
        return devuelto;
    }

    public void setDevuelto(boolean devuelto) {
        this.devuelto = devuelto;
    }

    public BigDecimal getPrecioDia() {
        return precioDia;
    }

    public void setPrecioDia(BigDecimal precioDia) {
        this.precioDia = precioDia;
    }

    public Agencia getAgenciaId() {
        return agenciaId;
    }

    public void setAgenciaId(Agencia agenciaId) {
        this.agenciaId = agenciaId;
    }

    public Cliente getClienteId() {
        return clienteId;
    }

    public void setClienteId(Cliente clienteId) {
        this.clienteId = clienteId;
    }

    public Vehiculo getVehiculoId() {
        return vehiculoId;
    }

    public void setVehiculoId(Vehiculo vehiculoId) {
        this.vehiculoId = vehiculoId;
    }
    
    public BigDecimal getPrecioTotal() {
        long dias = calculaDiasEntre(fechaInicio, fechaFin);
        return precioDia.multiply(BigDecimal.valueOf(dias));
    }
    
    private long calculaDiasEntre(Date fechaInicio, Date fechaFin) {        
        long MILLISECS_PER_DAY = 24 * 60 * 60 * 1000; //Milisegundos al día
        
        long diferencia = ((fechaFin.getTime() - fechaInicio.getTime()) / MILLISECS_PER_DAY);
        return diferencia;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Reserva)) {
            return false;
        }
        Reserva other = (Reserva) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return vehiculoId.getMarca() + " " + vehiculoId.getModelo()
                + ": De " + Utils.formateaFecha(fechaInicio)
                + " a " + Utils.formateaFecha(fechaFin) + ".";
    }
}
