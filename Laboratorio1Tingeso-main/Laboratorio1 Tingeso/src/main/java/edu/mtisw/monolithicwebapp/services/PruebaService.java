package edu.mtisw.monolithicwebapp.services;

import edu.mtisw.monolithicwebapp.entities.PruebaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import edu.mtisw.monolithicwebapp.repositories.estudianteRepository;
import edu.mtisw.monolithicwebapp.repositories.pruebaRepository;
import edu.mtisw.monolithicwebapp.repositories.pagoArancelRepository;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import edu.mtisw.monolithicwebapp.entities.estudianteEntity;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.util.Objects;

@Service
public class PruebaService{
    @Autowired
    pruebaRepository pruebaRepository;
    @Autowired
    estudianteRepository estudianteRepository;
    @Autowired
    pagoArancelRepository pagoArancelRepository;

    public void guardarPruebaEstudiante(PruebaEntity prueba) {
        pruebaRepository.save(prueba);
    }

    public ArrayList<PruebaEntity> obtenerEstudianteById(Long idEstudiante) {
        return pruebaRepository.findEstudentByid(idEstudiante);
    }

    private final Logger logger = LoggerFactory.getLogger(PruebaService.class);

    @Generated
    public ArrayList<PruebaEntity> ObtenerDatosPruebas() {
        return (ArrayList<PruebaEntity>) pruebaRepository.findAll();
    }

    @Generated
    public String VerificarArchivo(MultipartFile file) {
        if (file.isEmpty()) {
            return "Archivo vacío";
        }

        try {
            InputStreamReader reader = new InputStreamReader(file.getInputStream());
            CSVReader csvReader = new CSVReader(reader);
            String[] nextLine;
            while ((nextLine = csvReader.readNext()) != null) {
                if (nextLine.length != 3) {
                    return "El archivo debe poseer 3 columnas: Rut, puntaje, fecha";
                }
                String rut = nextLine[0];
                String puntaje = nextLine[1];
                String fecha = nextLine[2];

                /*Se verifica que puntaje sea un número*/
                if (!puntaje.matches("^[0-9]+$")) {
                    return "Puntaje debe ser un número";
                }

                /*Se verifica que fecha siga el formato de fecha*/
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                try {
                    dateFormat.parse(fecha);
                } catch (ParseException e) {
                    return "El campo 'fecha' no tiene un formato de fecha válido (dd-MM-yyyy).";
                }
            }

            /*Si cumple el formato se entrega string vacío*/
            return "";
        } catch (IOException | CsvValidationException e) {
            return "Error al intentar procesar archivo";
        }
    }

    @Generated
    public String GuardarNombreArchivo(MultipartFile file) {
        String filename = file.getOriginalFilename();
        if (filename != null) {
            if (!file.isEmpty()) {
                try {
                    byte[] bytes = file.getBytes();
                    Path path = Paths.get(file.getOriginalFilename());
                    Files.write(path, bytes);
                    logger.info("Archivo guardado");
                } catch (IOException e) {
                    logger.error("ERROR", e);
                }
            }
            return "Archivo guardado con exito!";
        } else {
            return "No se pudo guardar el archivo";
        }
    }

    @Generated
    public void LeerArchivoCsv(String direccion) {
        String texto = "";
        BufferedReader bf = null;
        try {
            bf = new BufferedReader(new FileReader(direccion));
            String temp = "";
            String bfRead;
            int count = 1;
            while ((bfRead = bf.readLine()) != null) {
                if (count == 1) {
                    count = 0;
                } else {
                    GuardarPruebaEnBD(bfRead.split(";")[0], bfRead.split(";")[1], bfRead.split(";")[2]);
                    temp = temp + "\n" + bfRead;
                }
            }
            texto = temp;
            System.out.println("Archivo leido exitosamente");
        } catch (Exception e) {
            System.err.println("No se encontro el archivo");
        } finally {
            if (bf != null) {
                try {
                    bf.close();
                } catch (IOException e) {
                    logger.error("ERROR", e);
                }
            }
        }
    }

    @Generated
    public void GuardarPruebaEnBD(String rut, String Puntaje, String Fecha_Realizacion) {
        PruebaEntity Prueba = new PruebaEntity();
        estudianteEntity estudiante;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        estudiante = estudianteRepository.findEstudianteByRut(rut);
        if (estudiante == null) {
            return;
        }
        Prueba.setIdEstudiante(estudiante.getIdEstudiante());
        if (Objects.equals(Puntaje, "")) {
            Prueba.setPuntaje_obtenido(150);
        } else if (Integer.parseInt(Puntaje) < 150 || Integer.parseInt(Puntaje) > 1000) {
            Prueba.setPuntaje_obtenido(150);
        } else {
            Prueba.setPuntaje_obtenido(Integer.parseInt(Puntaje));
        }
        Prueba.setFecha_examen(LocalDate.parse(Fecha_Realizacion, formatter));
        Prueba.setFecha_resultados(LocalDate.now());
        pruebaRepository.save(Prueba);
    }

    @Generated
    public void EliminarPruebas(ArrayList<PruebaEntity> Pruebas) {
        pruebaRepository.deleteAll(Pruebas);
    }


    public ArrayList<PruebaEntity> ObtenerPruebasPorRutEstudiante(String Rut) {
        estudianteEntity estudiante = estudianteRepository.findEstudianteByRut(Rut);
        if (estudiante == null) {
            ArrayList<PruebaEntity> listafinal = new ArrayList<>();
            PruebaEntity Prueba = new PruebaEntity();
            Prueba.setPuntaje_obtenido(-1);
            listafinal.add(Prueba);

            return listafinal;
        } else {
            return pruebaRepository.findEstudentByid(estudiante.getIdEstudiante());
        }
    }

    public Integer PromediosPruebasEstudiante(ArrayList<PruebaEntity> Pruebas) {
        int i = 0;
        Integer Suma = 0;
        if (Pruebas.size() > 0) {
            while (i < Pruebas.size()) {
                Suma = Suma + Pruebas.get(i).getPuntaje_obtenido();
                i++;
            }
            return (Suma / Pruebas.size());
        } else {
            return 0;
        }
    }
}
